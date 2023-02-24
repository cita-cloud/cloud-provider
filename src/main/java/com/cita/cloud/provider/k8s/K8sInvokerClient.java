// Copyright Rivtower Technologies LLC.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.cita.cloud.provider.k8s;

import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.*;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import io.kubernetes.client.util.credentials.ClientCertificateAuthentication;
import io.kubernetes.client.util.generic.GenericKubernetesApi;
import io.kubernetes.client.util.generic.options.CreateOptions;
import io.kubernetes.client.util.generic.options.DeleteOptions;
import io.kubernetes.client.util.generic.options.PatchOptions;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * k8s客户端调用工具类
 * 主要目的是初始化k8s的客户端并且提供一些重要的原生api调用方法
 * 以util包的方式降低使用成本（不需要依赖框架或者其他服务）
 *
 * @author : FreeQ
 * @version : 1.0
 * @date : 2023/1/6 18:05
 **/
public class K8sInvokerClient {

    private static final Logger log = LoggerFactory.getLogger(K8sInvokerClient.class);

    private static final String SERVER_URL_MARK = "kubernetes.master.url";

    private static final String NAMESPACE_MARK = "kubernetes.namespace";

    private static final String SERVER_CERTIFICATE_MARK = "kubernetes.certs.data";

    private static final String CLIENT_AUTH_TOKEN_MARK = "kubernetes.auth.token";

    private static final String CLIENT_CERTIFICATE_MARK = "kubernetes.certs.client.data";

    private static final String CLIENT_KEY_MARK = "kubernetes.certs.client.key.data";

    /* k8s的用户自定义CRD资源操作对象（通过 CRD 我们可以向 Kubernetes API 中增加新资源类型，而不需要修改 Kubernetes 源码来创建自定义的 API server，该功能大大提高了 Kubernetes 的扩展能力） */
    private static final String API_OBJECT_CRD = "customObjectsApi";

    /* k8s核心api操作对象，包括对namespace、node、pod的各种操作 */
    private static final String API_OBJECT_CORE = "coreV1Api";

    /* k8s的网络操作对象，例如：ingress等*/
    private static final String API_OBJECT_NETWORK = "networkingV1Api";

    /* k8s通用pod操作对象*/
    private static final String API_OBJECT_GENERIC = "genericKubernetesApi";

    /* k8s的应用操作对象，例如：deployment等 */
    private static final String API_OBJECT_APP = "appsV1Api";

    /* k8s拓展操作对象 */
    private static final String API_OBJECT_EXTENSION = "apiExtensionsV1Api";
    /* 定义一个hash map用于缓存各种常用的k8s api对象 */
    private static final ConcurrentHashMap<String, Object> k8sApiObjectMap = new ConcurrentHashMap<>();
    private static final HashMap<String, String> kubeConfigProperty = new HashMap<>();
    private static ApplicationContext applicationContext = null;
    private static ApiClient apiClient;

    static {
        kubeConfigProperty.put(SERVER_URL_MARK, "");
        kubeConfigProperty.put(NAMESPACE_MARK, "");
        kubeConfigProperty.put(SERVER_CERTIFICATE_MARK, "");
        kubeConfigProperty.put(CLIENT_AUTH_TOKEN_MARK, "");
        kubeConfigProperty.put(CLIENT_CERTIFICATE_MARK, "");
        kubeConfigProperty.put(CLIENT_KEY_MARK, "");
        k8sApiObjectMap.put(API_OBJECT_CRD, Optional.empty());
        k8sApiObjectMap.put(API_OBJECT_CORE, Optional.empty());
        k8sApiObjectMap.put(API_OBJECT_NETWORK, Optional.empty());
        k8sApiObjectMap.put(API_OBJECT_GENERIC, Optional.empty());
        k8sApiObjectMap.put(API_OBJECT_APP, Optional.empty());
        k8sApiObjectMap.put(API_OBJECT_EXTENSION, Optional.empty());
    }

    /**
     * @description 私有化构造方法，防止随意通过new关键字来实例化util类型的类
     * @author FreeQ
     * @date 2023/1/16 10:26
     **/
    private K8sInvokerClient() {

    }

    /**
     * @param context【spring boot框架上下文】
     * @description 注入容器的上下文对象
     * 注意！注意！注意！这里不会自动注入，util类型的类如果对容器对象有需要，应通过spring boot启动类中进行手动设置注入
     * @author FreeQ
     * @date 2023/1/12 15:50
     **/
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }


    /**
     * @description 从spring boot工程框架的上下文获取环境信息并加载kube客户端相关配置
     * @author FreeQ
     * @date 2023/1/14 21:28
     **/
    private static void loadPropertyFromContext() {
        if (applicationContext != null) {
            for (Map.Entry<String, String> entry : kubeConfigProperty.entrySet()) {
                Environment environment = applicationContext.getEnvironment();
                if (!Objects.requireNonNull(environment.getProperty(entry.getKey())).isEmpty()) {
                    kubeConfigProperty.put(entry.getKey(), environment.getProperty(entry.getKey()));
                }
            }
        }
    }

    /**
     * @param kubeConfigURL【传入k8s客户端配置资源（本地文件或ftp文件或http协议文件）】
     * @description k8s-api客户端的初始化方法
     * 注意！注意！注意！在调用本类中其它子方法前必须使用本方法进行一次初始化（相当于构造函数）
     * 支持通过三种方式对k8s的客户端配置进行设值（优先级为 方式1 > 方式2 > 方式3）
     * 方式一：直接传入kubeConfig文件（可以是本地文件、ftp文件以及http协议的远程文件等，需要符合java的URL对象规范；如果传入空值，则忽略此方式转而执行方式二的逻辑）
     * 方式二：通过系统运行时框架加载的自定义配置读取相关证书信息（如果application.yml中读取到相关配置则按配置的值来设定，否则忽略此方法转而执行方式三的逻辑）
     * 方式三：系统运行时自动获取到的k8s集群内默认环境信息
     * @author FreeQ
     * @date 2023/1/10 11:42
     **/
    public static synchronized void initApiClient(URL kubeConfigURL) throws K8sClientInitException {
        //apiClient只能被有效初始化一次（初始化时没有出错，得到的对象不是空值，那么就判定是初始化成功的，如果在后续使用过程中出现异常，可通过refresh方法对客户端对象进行刷新）
        if (K8sInvokerClient.apiClient == null) {
            try {
                //最优先判断是否明确指定k8s客户端的config文件资源
                //【注意】URL对象中如果是本地文件形式，那么资源地址的格式形如：file:/C:/Users/Desktop/demo.txt
                //【注意】URL对象中如果是ftp文件形式，那么资源地址的格式形如：ftp:/META-INF/license.txt
                if (kubeConfigURL != null) {
                    setApiClientByURL(kubeConfigURL);
                } else if (applicationContext != null) {
                    //通过读框架中取注入的上下文来获取到外部配置中的客户端连接参数来初始化api client
                    setApiClientByProperty();
                    if (apiClient == null) {
                        //如果通过加载上下文注入的属性方式没有成功初始化api client，那么会继续通过加载运行时环境方式再次尝试初始化
                        setApiClientByDefault();
                    }
                } else {
                    setApiClientByDefault();
                }
                if (apiClient == null) {
                    throw new K8sClientInitException("创建【k8s-api客户端】发生异常，不能获取到有效的kubeConfig信息导致生成的api-client对象为空！");
                }
                Configuration.setDefaultApiClient(apiClient);
                CoreV1Api coreV1Api = new CoreV1Api(apiClient);
                //验证创建出的客户端在初始化这一刻是否真实有效（发起一次实际调用）
                if (coreV1Api.getAPIResources() != null) {
                    log.debug("成功初始化【k8s-api客户端】...{}", coreV1Api.getAPIResources().getGroupVersion());
                }
                k8sApiObjectMap.put(API_OBJECT_CORE, coreV1Api);
            } catch (IOException e) {
                log.error("创建【k8s api客户端】发生IO异常...详细异常信息==>{}", e.toString());
                throw new K8sClientInitException("初始化【k8s-api客户端】发生IO异常...详细异常信息==>" + e);
            } catch (ApiException e) {
                log.error("创建【k8s api客户端】发生k8s api调用异常...详细异常信息==>{}", e.toString());
                throw new K8sClientInitException("初始化【k8s-api客户端】发生k8s api调用异常...详细异常信息==>" + e);
            }
        }
    }

    private static void setApiClientByURL(URL kubeConfigURL) throws IOException {
        log.debug("尝试通过读取传入URL资源中的kubeConfig文件对【api-client】进行初始化...");
        URLConnection urlConnection = kubeConfigURL.openConnection();
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new InputStreamReader(urlConnection.getInputStream()));
        apiClient = ClientBuilder.kubeconfig(kubeConfig).build();
    }

    private static void setApiClientByProperty() {
        log.debug("尝试读取框架application中的配置项对【api-client】进行初始化...");
        loadPropertyFromContext();
        if (kubeConfigProperty.get(SERVER_URL_MARK).isEmpty() || kubeConfigProperty.get(SERVER_CERTIFICATE_MARK).isEmpty()) {
            log.warn("从框架上下文中加载的配置信息缺少k8s客户端的关键性输入参数，放弃使用属性加载方式初始化k8s客户端！");
            return;
        }
        ClientBuilder clientBuilder = new ClientBuilder();
        if (!kubeConfigProperty.get(CLIENT_AUTH_TOKEN_MARK).isEmpty()) {
            byte[] encodedServerCert = Base64.decodeBase64(kubeConfigProperty.get(SERVER_CERTIFICATE_MARK));
            //优先使用auth token进行设置
            apiClient = clientBuilder.setBasePath(kubeConfigProperty.get(SERVER_URL_MARK)).
                    setCertificateAuthority(encodedServerCert).setVerifyingSsl(true).
                    setAuthentication(new AccessTokenAuthentication(kubeConfigProperty.get(CLIENT_AUTH_TOKEN_MARK))).build();
        } else if (!kubeConfigProperty.get(CLIENT_CERTIFICATE_MARK).isEmpty() &&
                !kubeConfigProperty.get(CLIENT_KEY_MARK).isEmpty()) {
            //注意：需要对kubeConfig中获取到的配置参数进行base64转码后再转为字节数组
            byte[] encodedServerCert = Base64.decodeBase64(kubeConfigProperty.get(SERVER_CERTIFICATE_MARK));
            byte[] encodedClientCert = Base64.decodeBase64(kubeConfigProperty.get(CLIENT_CERTIFICATE_MARK));
            byte[] encodedClientKey = Base64.decodeBase64(kubeConfigProperty.get(CLIENT_KEY_MARK));
            ClientCertificateAuthentication clientAuth = new ClientCertificateAuthentication(
                    encodedClientCert, encodedClientKey);
            apiClient = clientBuilder.setBasePath(kubeConfigProperty.get(SERVER_URL_MARK)).
                    setCertificateAuthority(encodedServerCert).setVerifyingSsl(true).
                    setAuthentication(clientAuth).build();
        }
    }

    private static void setApiClientByDefault() throws IOException {
        log.debug("尝试读取运行时环境默认的kubeConfig信息方式对【api-client】进行初始化...");
        apiClient = Config.defaultClient();
    }

    private static <T> T getSuitableParam(Object arg, Class<T> clazz) {
        return arg == null ? null : clazz.cast(arg);
    }

    public static ApiClient getApiClient(URL kubeConfigURL) {
        if (K8sInvokerClient.apiClient == null) {
            initApiClient(kubeConfigURL);
        }
        return K8sInvokerClient.apiClient;
    }

    public static void refreshApiClient(URL kubeConfigURL) {
        apiClient = null;
        initApiClient(kubeConfigURL);
        //刷新client后需要重新覆盖objectMap中依赖apiClient的一系列apiObject
        k8sApiObjectMap.put(API_OBJECT_CRD, new CustomObjectsApi(apiClient));
        k8sApiObjectMap.put(API_OBJECT_APP, new AppsV1Api(apiClient));
        k8sApiObjectMap.put(API_OBJECT_EXTENSION, new ApiextensionsV1Api(apiClient));
        k8sApiObjectMap.put(API_OBJECT_NETWORK, new NetworkingV1Api(apiClient));
        k8sApiObjectMap.put(API_OBJECT_GENERIC, new GenericKubernetesApi<>(V1Pod.class, V1PodList.class, "", "v1", "pods", apiClient));
    }

    /**
     * @return io.kubernetes.client.openapi.apis.CustomObjectsApi
     * @description 获取k8s的CRD资源api操作对象
     * @author FreeQ
     * @date 2023/1/15 22:25
     **/
    public static CustomObjectsApi getCustomApiObj() {
        if (k8sApiObjectMap.get(API_OBJECT_CRD).equals(Optional.empty())) {
            if (apiClient == null) {
                throw new K8sClientInitException("【客户端初始化异常】请先调用init方法创建k8s客户端...");
            }
            k8sApiObjectMap.put(API_OBJECT_CRD, new CustomObjectsApi(apiClient));
        }
        return (CustomObjectsApi) k8sApiObjectMap.get(API_OBJECT_CRD);
    }

    /**
     * @return io.kubernetes.client.openapi.apis.CoreV1Api
     * @description 获取k8s核心api操作对象
     * @author FreeQ
     * @date 2023/1/15 22:24
     **/
    public static CoreV1Api getCoreApiObj() {
        if (k8sApiObjectMap.get(API_OBJECT_CORE).equals(Optional.empty())) {
            if (apiClient == null) {
                throw new K8sClientInitException("【客户端初始化异常】请先调用init方法创建k8s客户端...");
            }
            k8sApiObjectMap.put(API_OBJECT_CORE, new CoreV1Api(apiClient));
        }
        return (CoreV1Api) k8sApiObjectMap.get(API_OBJECT_CORE);
    }

    /**
     * @return io.kubernetes.client.openapi.apis.AppsV1Api
     * @description 获取k8s运行应用的api操作对象
     * @author FreeQ
     * @date 2023/1/16 10:35
     **/
    public static AppsV1Api getAppApiObj() {
        if (k8sApiObjectMap.get(API_OBJECT_APP).equals(Optional.empty())) {
            if (apiClient == null) {
                throw new K8sClientInitException("【客户端初始化异常】请先调用init方法创建k8s客户端...");
            }
            k8sApiObjectMap.put(API_OBJECT_APP, new AppsV1Api(apiClient));
        }
        return (AppsV1Api) k8sApiObjectMap.get(API_OBJECT_APP);
    }

    /**
     * @return io.kubernetes.client.openapi.apis.ApiextensionsV1Api
     * @description 获取k8s扩展api操作对象
     * @author FreeQ
     * @date 2023/1/15 22:23
     **/
    public static ApiextensionsV1Api getExtensionApiObj() {
        if (k8sApiObjectMap.get(API_OBJECT_EXTENSION).equals(Optional.empty())) {
            if (apiClient == null) {
                throw new K8sClientInitException("【客户端初始化异常】请先调用init方法创建k8s客户端...");
            }
            k8sApiObjectMap.put(API_OBJECT_EXTENSION, new ApiextensionsV1Api(apiClient));
        }
        return (ApiextensionsV1Api) k8sApiObjectMap.get(API_OBJECT_EXTENSION);
    }

    public static NetworkingV1Api getNetworkApiObj() {
        if (k8sApiObjectMap.get(API_OBJECT_NETWORK).equals(Optional.empty())) {
            if (apiClient == null) {
                throw new K8sClientInitException("【客户端初始化异常】请先调用init方法创建k8s客户端...");
            }
            k8sApiObjectMap.put(API_OBJECT_NETWORK, new NetworkingV1Api(apiClient));
        }
        return (NetworkingV1Api) k8sApiObjectMap.get(API_OBJECT_NETWORK);
    }

    public static GenericKubernetesApi<V1Pod, V1PodList> getGenericK8sApiObj() {
        if (k8sApiObjectMap.get(API_OBJECT_GENERIC).equals(Optional.empty())) {
            if (apiClient == null) {
                throw new K8sClientInitException("【客户端初始化异常】请先调用init方法创建k8s客户端...");
            }
            k8sApiObjectMap.put(API_OBJECT_GENERIC, new GenericKubernetesApi<>(V1Pod.class, V1PodList.class, "", "v1", "pods", apiClient));
        }
        return (GenericKubernetesApi<V1Pod, V1PodList>) k8sApiObjectMap.get(API_OBJECT_GENERIC);
    }

    //-------------------------------------------------集群资源写操作-----------------------------------------------------

    public static V1Namespace createNameSpace(Object[] args) throws ApiException, ClassCastException {
        CoreV1Api api = getCoreApiObj();
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("【入参无效异常】本操作必须输入[V1Namespace]对象作为第一入参...");
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[5];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.createNamespace(getSuitableParam(suitableParams[0], V1Namespace.class),
                    getSuitableParam(suitableParams[1], String.class),
                    getSuitableParam(suitableParams[2], String.class),
                    getSuitableParam(suitableParams[3], String.class),
                    getSuitableParam(suitableParams[4], String.class));
        }
    }

    public static V1Status deleteNameSpace(Object[] args) throws ApiException, ClassCastException {
        CoreV1Api api = getCoreApiObj();
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("【入参无效异常】本操作必须输入namespace的[name]作为第一入参...");
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[7];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.deleteNamespace(getSuitableParam(suitableParams[0], String.class),
                    getSuitableParam(suitableParams[1], String.class),
                    getSuitableParam(suitableParams[2], String.class),
                    getSuitableParam(suitableParams[3], Integer.class),
                    getSuitableParam(suitableParams[4], Boolean.class),
                    getSuitableParam(suitableParams[5], String.class),
                    getSuitableParam(suitableParams[6], V1DeleteOptions.class));
        }
    }

    public static V1Ingress createIngress(Object[] args) throws ApiException, ClassCastException {
        NetworkingV1Api api = getNetworkApiObj();
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("【入参无效异常】本操作必须输入[namespaceName]和[ingressBody]作为前两位入参...");
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[6];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.createNamespacedIngress(getSuitableParam(suitableParams[0], String.class),
                    getSuitableParam(suitableParams[1], V1Ingress.class),
                    getSuitableParam(suitableParams[2], String.class),
                    getSuitableParam(suitableParams[3], String.class),
                    getSuitableParam(suitableParams[4], String.class),
                    getSuitableParam(suitableParams[5], String.class));
        }
    }

    //----------------------------------------------容器资源写操作-------------------------------------------------

    public static V1Pod createPod(Object[] args) throws ApiException, ClassCastException {
        GenericKubernetesApi<V1Pod, V1PodList> api = getGenericK8sApiObj();
        if (args == null || args.length < 3) {
            throw new IllegalArgumentException("【入参无效异常】本操作必须输入[namespace]、[V1Pod]、[CreateOptions]作为入参...");
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[3];
            System.arraycopy(args, 0, suitableParams, 0, suitableParams.length);
            return api.create(getSuitableParam(suitableParams[0], String.class),
                    getSuitableParam(suitableParams[1], V1Pod.class),
                    getSuitableParam(suitableParams[2], CreateOptions.class)).throwsApiException().getObject();
        }
    }

    public static V1Pod updatePod(Object[] args) throws ApiException, ClassCastException {
        GenericKubernetesApi<V1Pod, V1PodList> api = getGenericK8sApiObj();
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("【入参无效异常】本操作必须输入目标pod的[namespace]和[name]作为前两位入参...");
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[5];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.patch(getSuitableParam(suitableParams[0], String.class),
                    getSuitableParam(suitableParams[1], String.class),
                    getSuitableParam(suitableParams[2], String.class),
                    getSuitableParam(suitableParams[3], V1Patch.class),
                    getSuitableParam(suitableParams[4], PatchOptions.class)).throwsApiException().getObject();
        }
    }

    public static V1Pod deletePod(Object[] args) throws ApiException, ClassCastException {
        GenericKubernetesApi<V1Pod, V1PodList> api = getGenericK8sApiObj();
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("【入参无效异常】本操作必须输入目标pod的[namespace]和[name]作为前两位入参...");
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[3];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.delete(getSuitableParam(suitableParams[0], String.class),
                    getSuitableParam(suitableParams[1], String.class),
                    getSuitableParam(suitableParams[2], DeleteOptions.class)).throwsApiException().getObject();
        }
    }

    //----------------------------------------------服务资源写操作-------------------------------------------------


    public static V1PodList getAllPodList(Object[] args) throws K8sClientInitException, ApiException {
        // new a CoreV1Api
        CoreV1Api api = getCoreApiObj();
        // invokes the CoreV1Api client
        if (args == null) {
            return api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[10];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.listPodForAllNamespaces(getSuitableParam(suitableParams[0], Boolean.class),
                    getSuitableParam(suitableParams[1], String.class),
                    getSuitableParam(suitableParams[2], String.class),
                    getSuitableParam(suitableParams[3], String.class),
                    getSuitableParam(suitableParams[4], Integer.class),
                    getSuitableParam(suitableParams[5], String.class),
                    getSuitableParam(suitableParams[6], String.class),
                    getSuitableParam(suitableParams[7], String.class),
                    getSuitableParam(suitableParams[8], Integer.class),
                    getSuitableParam(suitableParams[9], Boolean.class));
        }
    }

    public static V1PodList getNamespacePodList(Object[] args) throws K8sClientInitException, ApiException {
        CoreV1Api api = getCoreApiObj();
        if (args == null) {
            return api.listNamespacedPod(null, null, null, null, null, null, null, null, null, null, null);
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[11];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.listNamespacedPod(getSuitableParam(suitableParams[0], String.class),
                    getSuitableParam(suitableParams[1], String.class),
                    getSuitableParam(suitableParams[2], Boolean.class),
                    getSuitableParam(suitableParams[3], String.class),
                    getSuitableParam(suitableParams[4], String.class),
                    getSuitableParam(suitableParams[5], String.class),
                    getSuitableParam(suitableParams[6], Integer.class),
                    getSuitableParam(suitableParams[7], String.class),
                    getSuitableParam(suitableParams[8], String.class),
                    getSuitableParam(suitableParams[9], Integer.class),
                    getSuitableParam(suitableParams[10], Boolean.class));
        }
    }

    public static V1NamespaceList getAllNamespace(Object[] args) throws K8sClientInitException, ApiException {
        // new a CoreV1Api
        CoreV1Api api = getCoreApiObj();
        // invokes the CoreV1Api client
        if (args == null) {
            return api.listNamespace(null, true, null, null, null, null, null, null, null, null);
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[10];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.listNamespace(getSuitableParam(suitableParams[0], String.class),
                    getSuitableParam(suitableParams[1], Boolean.class),
                    getSuitableParam(suitableParams[2], String.class),
                    getSuitableParam(suitableParams[3], String.class),
                    getSuitableParam(suitableParams[4], String.class),
                    getSuitableParam(suitableParams[5], Integer.class),
                    getSuitableParam(suitableParams[6], String.class),
                    getSuitableParam(suitableParams[7], String.class),
                    getSuitableParam(suitableParams[8], Integer.class),
                    getSuitableParam(suitableParams[9], Boolean.class));
        }
    }

    public static V1APIResourceList getApiResources() throws K8sClientInitException, ApiException {
        CoreV1Api api = getCoreApiObj();
        return api.getAPIResources();
    }

    public static Object getClusterCustomObject(Object[] args) throws K8sClientInitException, ApiException {
        CustomObjectsApi api = getCustomApiObj();
        if (args == null) {
            return api.getClusterCustomObject(null, null, null, null);
        } else {
            //适配具体要调用的k8s接口的参数个数，防止数组越界
            Object[] suitableParams = new Object[4];
            for (int i = 0; i < suitableParams.length; i++) {
                if (i > args.length - 1) {
                    suitableParams[i] = null;
                } else {
                    suitableParams[i] = args[i];
                }
            }
            return api.getClusterCustomObject(getSuitableParam(suitableParams[0], String.class),
                    getSuitableParam(suitableParams[1], String.class),
                    getSuitableParam(suitableParams[2], String.class),
                    getSuitableParam(suitableParams[3], String.class));
        }

    }
}

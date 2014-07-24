package com.jscn.commons.core.http;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.impl.nio.conn.PoolingClientAsyncConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.conn.ClientAsyncConnectionManager;
import org.apache.http.nio.conn.scheme.AsyncScheme;
import org.apache.http.nio.conn.ssl.SSLLayeringStrategy;
import org.apache.http.nio.params.NIOReactorParams;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jscn.commons.core.exceptions.SystemException;

@SuppressWarnings("deprecation")
public class HttpAsyncClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpAsyncClientUtil.class);

    private HttpAsyncClientUtil() {
    }

    private static HttpAsyncClient httpAsyncClient  = null;

    private static HttpAsyncClient httpsAsyncClient = null;
    static {
        try {

            httpAsyncClient = getHttpAsyncClient();
            httpAsyncClient.start();

            httpsAsyncClient = getHttpAsyncClient();
            httpsAsyncClient.start();
        } catch (Exception e) {
            logger.error("init httpAsyncClient error:", e);
        }
    }

    /**
     * 默认的HTTP响应实体编码 = "UTF-8"
     */
    private static final String    DEFAULT_CHARSET  = "UTF-8";

    private static HttpAsyncClient getHttpAsyncClient() throws IOReactorException {
        HttpParams params = new BasicHttpParams();
        // 读超时30秒
        HttpConnectionParams.setSoTimeout(params, 30 * 1000);
        // 连接超时30秒
        HttpConnectionParams.setConnectionTimeout(params, 30 * 1000);

        IOReactorConfig config = new IOReactorConfig();
        config.setSelectInterval(NIOReactorParams.getSelectInterval(params));
        config.setShutdownGracePeriod(NIOReactorParams.getGracePeriod(params));
        config.setInterestOpQueued(NIOReactorParams.getInterestOpsQueueing(params));
        config.setIoThreadCount(20);
        config.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(params));
        config.setSoTimeout(HttpConnectionParams.getSoTimeout(params));
        config.setSoLinger(HttpConnectionParams.getLinger(params));
        config.setConnectTimeout(HttpConnectionParams.getConnectionTimeout(params));
        config.setSoReuseAddress(HttpConnectionParams.getSoReuseaddr(params));

        ConnectingIOReactor asa = new DefaultConnectingIOReactor(config);
        PoolingClientAsyncConnectionManager niocm = new PoolingClientAsyncConnectionManager(asa);
        // 最大连接数
        niocm.setMaxTotal(400);
        // 每路由最大连接数
        niocm.setDefaultMaxPerRoute(200);

        HttpAsyncClient httpAsyncClient = new DefaultHttpAsyncClient(niocm);
        return httpAsyncClient;
    }

    // /////////////////////////////////////////////////////////////////////////
    // <<Get>>

    /**
     * Get
     * 
     * @param url 请求url
     * @param callback 回调方法
     */
    public static void get(String url, FutureCallback<HttpResponse> callback) {
        if (logger.isDebugEnabled())
            logger.debug("Get [" + url + "] ...");

        HttpGet getMethod = null;
        try {
            // HttpAsyncClient client = new DefaultHttpAsyncClient(niocm);
            // client.start();

            getMethod = new HttpGet(url);
            httpAsyncClient.execute(getMethod, callback);
        } catch (Exception e) {
            logger.error("async get error:", e);
            throw new SystemException(e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // <<Post>>

    /**
     * Post XML（使用默认字符集）
     * 
     * @param url 请求的URL
     * @param xml XML格式请求内容
     * @param callback 回调方法
     */
    public static void postXml(String url, String xml, FutureCallback<HttpResponse> callback) {
        postXml(url, xml, DEFAULT_CHARSET, callback);
    }

    /**
     * Post XML
     * 
     * @param url 请求的URL
     * @param xml XML格式请求内容
     * @param requestCharset 请求内容字符集
     * @param callback 回调方法
     */
    public static void postXml(String url, String xml, String requestCharset,
            FutureCallback<HttpResponse> callback) {
        post(url, xml, "text/xml; charset=" + requestCharset, "text/xml", requestCharset, callback);
    }

    /**
     * Post JSON（使用默认字符集）
     * 
     * @param url 请求的URL
     * @param json JSON格式请求内容
     * @param callback 回调方法
     */
    public static void postJson(String url, String json, FutureCallback<HttpResponse> callback) {
        postJson(url, json, DEFAULT_CHARSET, callback);
    }

    /**
     * Post JSON
     * 
     * @param url 请求的URL
     * @param json JSON格式请求内容
     * @param requestCharset 请求内容字符集
     * @param callback 回调方法
     */
    public static void postJson(String url, String json, String requestCharset,
            FutureCallback<HttpResponse> callback) {
        post(url, json, "application/json; charset=" + requestCharset, "application/json",
                requestCharset, callback);
    }

    /**
     * Post
     * 
     * @param url 请求的URL
     * @param content 请求内容
     * @param contentType 请求内容类型，HTTP Header中的<code>Content-type</code>
     * @param mimeType 请求内容MIME类型
     * @param requestCharset 请求内容字符集
     * @param callback 回调方法
     */
    public static void post(String url, String content, String contentType, String mimeType,
            String requestCharset, FutureCallback<HttpResponse> callback) {

        HttpPost post = null;
        try {
            // HttpAsyncClient client = new DefaultHttpAsyncClient(niocm);
            // client.start();

            post = new HttpPost(url);
            post.setHeader("Content-Type", contentType);
            HttpEntity requestEntity = new StringEntity(content, ContentType.create(mimeType,
                    requestCharset));
            post.setEntity(requestEntity);

            httpAsyncClient.execute(post, callback);
        } catch (Exception e) {
            logger.error("async post error:", e);
            throw new SystemException(e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }

    /**
     * Post（使用 {@link UrlEncodedFormEntity} 封装参数）
     * 
     * @param url 请求url
     * @param params 请求参数
     * @param callback 回调方法
     */
    public static void post(String url, Map<String, String> params,
            FutureCallback<HttpResponse> callback) {
        post(url, params, DEFAULT_CHARSET, callback);
    }

    /**
     * Post（使用 {@link UrlEncodedFormEntity} 封装参数）
     * 
     * @param url 请求URL
     * @param params 请求参数
     * @param paramEncoding 请求参数编码
     * @param callback 回调方法
     */
    public static void post(String url, Map<String, String> params, String paramEncoding,
            FutureCallback<HttpResponse> callback) {
        if (logger.isDebugEnabled())
            logger.debug("Post [" + url + "] ...");
        HttpPost post = null;
        try {
            // client = new DefaultHttpAsyncClient(niocm);
            // client.start();
            post = new HttpPost(url);

            if (params != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : params.keySet()) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, paramEncoding);
                post.setEntity(formEntity);
            }

            httpAsyncClient.execute(post, callback);
        } catch (Exception e) {
            logger.error("async post error:", e);
            throw new SystemException(e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }

    }

    // /////////////////////////////////////////////////////////////////////////
    // <<SSL Get>>

    public static void sslGet(String url, Map<String, String> params, String keyType,
            String keyPath, String keyPassword, int sslport, String authString,
            FutureCallback<HttpResponse> callback) {
        try {
            AsyncScheme sch = getSslScheme(keyType, keyPath, keyPassword, sslport);
            sslGet(url, params, sch, authString, callback);
        } catch (Exception e) {
            logger.error("async ssl get error:", e);
            throw new SystemException(e);
        }
    }

    public static void sslGet(String url, Map<String, String> params, AsyncScheme sch,
            String authString, FutureCallback<HttpResponse> callback) {
        if (logger.isDebugEnabled())
            logger.debug("SSL Get [" + url + "] ...");
        HttpGet getMethod = null;
        try {
            // HttpAsyncClient client = new DefaultHttpAsyncClient(niocm);
            // client.start();

            httpsAsyncClient.getConnectionManager().getSchemeRegistry().register(sch);
            getMethod = new HttpGet(url);

            if (authString != null)
                getMethod.setHeader("Authorization", authString);

            if (params != null) {
                HttpParams httpParams = new BasicHttpParams();
                for (String key : params.keySet()) {
                    httpParams.setParameter(key, params.get(key));
                }
                getMethod.setParams(httpParams);
            }

            httpAsyncClient.execute(getMethod, callback);
        } catch (Exception e) {
            logger.error("async ssl get error:", e);
            throw new SystemException(e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // <<SSL Post>>

    /**
     * SSL Post XML
     * 
     * @param url 请求的URL
     * @param xml XML请求内容
     * @param keyType 密钥类型
     * @param keyPath 密钥文件路径
     * @param keyPassword 密钥文件密码
     * @param sslPort SSL端口
     * @param authString 头部认证信息
     * @param callback 回调方法
     */
    public static void sslPostXml(String url, String xml, String keyType, String keyPath,
            String keyPassword, int sslPort, String authString,
            FutureCallback<HttpResponse> callback) {
        try {
            AsyncScheme sch = getSslScheme(keyType, keyPath, keyPassword, sslPort);
            sslPost(url, xml, "text/xml; charset=" + DEFAULT_CHARSET, "text/xml", DEFAULT_CHARSET,
                    sch, authString, callback);
        } catch (Exception e) {
            logger.error("async ssl post xml error:", e);
            throw new SystemException(e);
        }
    }

    /**
     * SSL Post JSON
     * 
     * @param url 请求的URL
     * @param json JSON格式请求内容
     * @param keyType 密钥类型
     * @param keyPath 密钥文件路径
     * @param keyPassword 密钥文件密码
     * @param sslPort SSL端口
     * @param authString 头部认证信息
     * @param callback 回调方法
     */
    public static void sslPostJson(String url, String json, String keyType, String keyPath,
            String keyPassword, int sslPort, String authString,
            FutureCallback<HttpResponse> callback) {
        try {
            AsyncScheme sch = getSslScheme(keyType, keyPath, keyPassword, sslPort);
            sslPost(url, json, "application/json; charset=" + DEFAULT_CHARSET, "application/json",
                    DEFAULT_CHARSET, sch, authString, callback);
        } catch (Exception e) {
            logger.error("async ssl post json error:", e);
            throw new SystemException(e);
        }
    }

    /**
     * SSL Post
     * 
     * @param url 请求的URL
     * @param content 请求内容
     * @param contentType 请求内容类型，HTTP Header中的<code>Content-type</code>
     * @param mimeType 请求内容MIME类型
     * @param requestCharset 请求内容字符集
     * @param sch Scheme
     * @param authString 头部信息中的<code>Authorization</code>
     * @param callback 回调方法
     */
    public static void sslPost(String url, String content, String contentType, String mimeType,
            String requestCharset, AsyncScheme sch, String authString,
            FutureCallback<HttpResponse> callback) {
        if (logger.isDebugEnabled())
            logger.debug("SSL Post [" + url + "] ...");

        HttpPost post = null;
        try {

            httpsAsyncClient.getConnectionManager().getSchemeRegistry().register(sch);

            post = new HttpPost(url);

            if (authString != null)
                post.setHeader("Authorization", authString);
            if (contentType != null)
                post.setHeader("Content-Type", contentType);

            HttpEntity requestEntity = new StringEntity(content, ContentType.create(mimeType,
                    requestCharset));
            post.setEntity(requestEntity);

            httpsAsyncClient.execute(post, callback);
        } catch (Exception e) {
            logger.error("async ssl post error:", e);
            throw new SystemException(e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // <<内部辅助方法>>

    private static AsyncScheme getSslScheme(String keyType, String keyPath, String keyPassword,
            int sslPort) throws Exception {
        KeyStore trustStore = KeyStore.getInstance(keyType);
        FileInputStream instream = new FileInputStream(new File(keyPath));
        try {
            trustStore.load(instream, keyPassword.toCharArray());
        } finally {
            try {
                instream.close();
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }

        // 验证密钥源
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
        kmf.init(trustStore, keyPassword.toCharArray());

        // 同位体验证信任决策源
        TrustManager[] trustManagers = { new X509TrustManager() {
            /*
             * Delegate to the default trust manager.
             */
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            /*
             * Delegate to the default trust manager.
             */
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            /*
             * Merely pass this through.
             */
            public X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        } };

        // 初始化安全套接字
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(kmf.getKeyManagers(), trustManagers, null);
        SSLLayeringStrategy layeringStrategy = new SSLLayeringStrategy(sslContext);
        return new AsyncScheme("https", sslPort, layeringStrategy);
    }

}

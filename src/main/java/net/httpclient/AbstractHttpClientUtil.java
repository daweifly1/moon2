//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractHttpClientUtil {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final int DEFAULT_SOCKET_TIMEOUT = 5000;
    private static final int DEFAULT_RETRY_TIMES = 0;
    private static SSLConnectionSocketFactory socketFactory;
    private static TrustManager manager = new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    };

    public AbstractHttpClientUtil() {
    }

    public static CloseableHttpClient createHttpClient() {
        return createHttpClient(0, 5000);
    }

    public static CloseableHttpClient createHttpClient(int socketTimeout) {
        return createHttpClient(0, socketTimeout);
    }

    public static CloseableHttpClient createHttpClient(int retryTimes, int socketTimeout) {
        Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(5000);
        builder.setConnectionRequestTimeout(1000);
        if (socketTimeout >= 0) {
            builder.setSocketTimeout(socketTimeout);
        }

        RequestConfig defaultRequestConfig = builder.setCookieSpec("standard-strict").setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList("NTLM", "Digest")).setProxyPreferredAuthSchemes(Arrays.asList("Basic")).build();
        enableSSL();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (retryTimes > 0) {
            setRetryHandler(httpClientBuilder, retryTimes);
        }

        CloseableHttpClient httpClient = httpClientBuilder.setConnectionManager(connectionManager).setDefaultRequestConfig(defaultRequestConfig).build();
        return httpClient;
    }

    private static void enableSSL() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init((KeyManager[]) null, new TrustManager[]{manager}, (SecureRandom) null);
            socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }

    protected static HttpEntity getEntity(Object paramsObj, String charset) throws UnsupportedEncodingException {
        if (paramsObj == null) {
            System.out.println("当前未传入参数信息，无法生成HttpEntity");
            return null;
        } else if (Map.class.isInstance(paramsObj)) {
            Map<String, String> paramsMap = (Map) paramsObj;
            List<NameValuePair> list = getNameValuePairs(paramsMap);
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(list, charset);
            httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            return httpEntity;
        } else if (String.class.isInstance(paramsObj)) {
            String paramsStr = (String) paramsObj;
            StringEntity httpEntity = new StringEntity(paramsStr, charset);
            if (paramsStr.startsWith("{")) {
                httpEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            } else if (paramsStr.startsWith("<")) {
                httpEntity.setContentType(ContentType.APPLICATION_XML.getMimeType());
            } else {
                httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            }

            return httpEntity;
        } else {
            System.out.println("当前传入参数不能识别类型，无法生成HttpEntity");
            return null;
        }
    }

    protected static String getResult(CloseableHttpResponse httpResponse, String charset) throws ParseException, IOException {
        String result = null;
        if (httpResponse == null) {
            return result;
        } else {
            HttpEntity entity = httpResponse.getEntity();
            if (entity == null) {
                return result;
            } else {
                result = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
                return result;
            }
        }
    }

    protected static String getCharset(String charset) {
        return charset == null ? "UTF-8" : charset;
    }

    private static List<NameValuePair> getNameValuePairs(Map<String, String> paramsMap) {
        List<NameValuePair> list = new ArrayList();
        if (paramsMap != null && !paramsMap.isEmpty()) {
            Iterator var3 = paramsMap.entrySet().iterator();

            while (var3.hasNext()) {
                Entry<String, String> entry = (Entry) var3.next();
                list.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }

            return list;
        } else {
            return list;
        }
    }

    private static void setRetryHandler(HttpClientBuilder httpClientBuilder, final int retryTimes) {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= retryTimes) {
                    return false;
                } else if (exception instanceof InterruptedIOException) {
                    return false;
                } else if (exception instanceof UnknownHostException) {
                    return false;
                } else if (exception instanceof ConnectTimeoutException) {
                    return false;
                } else if (exception instanceof SSLException) {
                    return false;
                } else {
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                    return idempotent;
                }
            }
        };
        httpClientBuilder.setRetryHandler(myRetryHandler);
    }
}

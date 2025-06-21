package http;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

/**
 *     请求头：
 *     Accept：用于指示客户端可以接受的MIME类型，例如text/html, application/json表示接受HTML和JSON格式的数据。
 *     Accept-Charset：用于指示客户端可以接受的字符集，例如utf-8表示接受UTF-8编码的数据。
 *     Accept-Encoding：用于指示客户端可以接受的内容编码，例如gzip, deflate表示接受Gzip和Deflate编码的数据。
 *     Accept-Language：用于指示客户端可以接受的语言，例如en-US表示接受美国英语。
 *     Authorization：用于包含客户端提供的身份验证信息，例如Basic dXNlcm5hbWU6cGFzc3dvcmQ=表示使用基本身份验证，用户名为username，密码为password。
 *     Cookie：用于发送客户端的cookie信息。
 *     Expect：用于指示客户端期望服务器满足的要求，例如100-continue表示客户端期望服务器在接收完请求头后返回100 Continue状态码。
 *     From：用于指示发送请求的用户的电子邮件地址。
 *     Host：用于指示请求的目标服务器，例如www.example.com表示请求的目标服务器为www.example.com。
 *     If-Match：用于指示客户端提供的实体标记，如果实体标记匹配，则请求成功。
 *     If-Modified-Since：用于指示客户端提供的日期和时间，如果资源自该时间以来未被修改，则返回304 Not Modified状态码。
 *     If-None-Match：用于指示客户端提供的实体标记，如果实体标记不匹配，则请求成功。
 *     Referer：用于指示当前请求页面的来源页面地址。
 *     User-Agent：用于指示客户端的信息，例如浏览器类型、操作系统等。
 *     ---------------------------------------------------------------------------------------------------------------
 *     响应头：
 *     Access-Control-Allow-Origin：用于指示哪些源可以访问资源，例如\*表示允许所有源访问。
 *     Cache-Control：用于控制缓存行为，例如max-age=3600表示缓存有效期为1小时。
 *     Content-Encoding：用于指示资源的内容编码，例如gzip表示资源使用Gzip编码。
 *     Content-Language：用于指示资源的语言，例如en-US表示资源使用美国英语。
 *     Content-Length：用于指示资源的长度，例如1234表示资源长度为1234字节。
 *     Content-Location：用于指示资源的URI，例如/index.html表示资源的URI为/index.html。
 *     Content-Type：用于指示资源的MIME类型，例如text/html; charset=utf-8表示资源是HTML格式的UTF-8编码文本。
 *     Date：表示响应发送的日期和时间。
 *     ETag：用于指示资源的实体标记，例如"123456"表示资源的实体标记为123456。
 *     Expires：用于指示资源的过期时间，例如Thu, 01 Dec 1994 16:00:00 GMT表示资源在1994年12月1日16:00:00过期。
 *     Last-Modified：用于指示资源的最后修改时间，例如Tue, 15 Nov 1994 12:45:26 GMT表示资源在1994年11月15日12:45:26被修改。
 *     Location：用于指示资源的重定向URI，例如http://www.example.com表示资源被重定向到http://www.example.com
 *     Pragma：用于包含实现特定的指令，例如no-cache表示不使用缓存。
 *     Server：用于指示服务器的信息，例如服务器类型、操作系统等。
 *     Set-Cookie：用于设置客户端的cookie信息。
 *     Vary：用于指示响应是否依赖于请求头中的哪些字段，例如Accept-Encoding表示响应依赖于请求头中的Accept-Encoding字段。
 */
public class ApacheHttpClient {

    public static void main(String[] args) {

        //doPost();

        doGet();
    }

    private static void doGet() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet("https://jsonplaceholder.typicode.com/posts/2");
            httpGet.setHeader("Accept", "text/html");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)){
                System.out.println("Status Code: " + response.getCode());
                System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doPost() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost("https://jsonplaceholder.typicode.com/posts");

            //设置 JSON 请求体
            String jsonBody = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
            httpPost.setEntity(new StringEntity(jsonBody));

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                System.out.println("Status Code: " + response.getCode());
                System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

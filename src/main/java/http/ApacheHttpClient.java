package http;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class ApacheHttpClient {

    public static void main(String[] args) {

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

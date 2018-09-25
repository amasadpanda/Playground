import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.Fields;

import java.util.concurrent.TimeUnit;

public class Client
{
  public static void main(String args[]) throws Exception
  {

    Fields data = new Fields();
    data.add("hello", "1");
    HttpClient client = new HttpClient();
    client.start();
    client.FORM("http://localhost:8080/", data).getStatus();
    ContentResponse response = client.newRequest("http://localhost:8080")
    .timeout(5, TimeUnit.SECONDS)
    .send();
    client.GET("http://localhost:8080/").getStatus();
    ContentResponse responseGet = client.newRequest("http://localhost:8080")
    .timeout(5, TimeUnit.SECONDS)
    .send();
    int status = response.getStatus();
    System.out.println(status + responseGet.getContentAsString());
  }
}

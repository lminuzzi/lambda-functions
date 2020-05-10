package helloworld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AppTest {
  @Test
  public void successfulResponse() {
    //App app = new App();
    //String result = String.valueOf(app.handleRequest(null, null));
    //assertNotNull(result);
    //assertEquals(result, "\"Test Lambda Function S3 Luciano Minuzzi\"");

    /*
    App app = new App();
    GatewayResponse result = (GatewayResponse) app.handleRequest(null, null);
    assertEquals(result.getStatusCode(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"message\""));
    assertTrue(content.contains("\"hello world\""));
    assertTrue(content.contains("\"location\""));
    */
  }
}

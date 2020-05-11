package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Handler for requests to Lambda function.
 */
public class App extends InventoryS3Client implements RequestHandler<HttpQuerystringRequest, HttpProductResponse> {

    public HttpProductResponse handleRequest(final HttpQuerystringRequest request, final Context context) {
        context.getLogger().log("requestValue: " + request);
        if (request.queryStringParameters == null) {
            return new HttpProductResponse(getAllProducts());
        }
        String id = request.queryStringParameters.get("id");
        if (id == null || id.isEmpty()) {
            return null;
        }
        return new HttpProductResponse(getProductById(Integer.parseInt(id)));
    }
}

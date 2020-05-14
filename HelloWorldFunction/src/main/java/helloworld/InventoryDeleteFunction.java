package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.List;

public class InventoryDeleteFunction extends InventoryS3Client implements RequestHandler<HttpRequest, HttpProductResponse> {
    @Override
    public HttpProductResponse handleRequest(HttpRequest request, Context context) {
        context.getLogger().log("requestInput: " + request);

        String id = request.pathParameters.get("id");
        if (id == null || id.isEmpty()) {
            return null;
        }
        int productId = Integer.parseInt(id);
        List<Product> productsList = getAllProductsList();
        Boolean didRemove = productsList.removeIf(p -> p.id == productId);

        HttpProductResponse response = new HttpProductResponse();
        if (didRemove) {
            if (updateAllProducts(productsList)) {
                return response;
            }
        }
        response.setStatusCode(String.valueOf(HttpStatusCode.NOT_FOUND));
        return response;
    }
}

package helloworld;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class InventoryInsertFunction extends InventoryS3Client
        implements RequestHandler<HttpRequest, HttpProductResponse> {
    @Override
    public HttpProductResponse handleRequest(HttpRequest request, Context context) {
        context.getLogger().log("requestInput: " + request);
        String body = request.getBody();

        Gson gson = new Gson();
        Product product = gson.fromJson(body, Product.class);

        List<Product> productsList = getAllProductsList();
        productsList.add(product);

        HttpProductResponse response = new HttpProductResponse();
        if (updateAllProducts(productsList)) {
            return response;
        }
        response.setStatusCode("500");
        return response;
    }
}

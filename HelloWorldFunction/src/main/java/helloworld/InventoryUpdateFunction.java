package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.List;

public class InventoryUpdateFunction extends InventoryS3Client implements RequestHandler<HttpRequest, HttpProductResponse> {
    @Override
    public HttpProductResponse handleRequest(HttpRequest request, Context context) {
        context.getLogger().log("requestInput: " + request);

        String body = request.getBody();

        Gson gson = new Gson();
        Product product = gson.fromJson(body, Product.class);

        List<Product> productsList = getAllProductsList();
        productsList.removeIf(prod -> prod.getId() == product.getId());

        HttpProductResponse response = new HttpProductResponse(product);

        productsList.add(product);
        if (!super.updateAllProducts(productsList)) {
            response.setStatusCode(String.valueOf(HttpStatusCode.INTERNAL_SERVER_ERROR));
        }

        return response;
    }
}

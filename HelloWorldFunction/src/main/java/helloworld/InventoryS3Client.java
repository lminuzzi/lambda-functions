package helloworld;

import com.google.gson.Gson;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryS3Client {
    private final String bucketName = "lpm-inventory-data";
    private final String key = "handy-tool-catalog.json";
    private final Region region = Region.EU_WEST_3;

    protected Product getProductById(int prodId) {
        return getAllProductsList().stream().filter(prod -> prod.id == prodId).findFirst().orElse(null);
    }

    protected Product[] getAllProducts() {
        S3Client s3Client = S3Client.builder().region(region).build();
        ResponseInputStream<?> objectData = s3Client
                .getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build());

        InputStreamReader isr = new InputStreamReader(objectData);
        BufferedReader br = new BufferedReader(isr);

        Gson gson = new Gson();
        return gson.fromJson(br, Product[].class);
    }

    protected ArrayList<Product> getAllProductsList() {
        return new ArrayList<>(Arrays.asList(getAllProducts()));
    }

    protected boolean updateAllProducts(Product[] products) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(products);
        S3Client s3Client = S3Client.builder().region(region).build();

        PutObjectResponse putResponse = s3Client.putObject(
                PutObjectRequest.builder().bucket(bucketName).key(key).build(), RequestBody.fromString(jsonString));

        return putResponse.sdkHttpResponse().isSuccessful();
    }

    protected boolean updateAllProducts(List<Product> productList) {
        Product[] products = productList.toArray(new Product[productList.size()]);
        return updateAllProducts(products);
    }
}

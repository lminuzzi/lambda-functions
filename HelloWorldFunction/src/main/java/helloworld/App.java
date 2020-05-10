package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<String, Object> {

    public Object handleRequest(final String input, final Context context) {
        context.getLogger().log("Input = " + input);
        return getProductById(102).toString();
    }

    private Product getProductById(int prodId) {
        Region region = Region.EU_WEST_3;
        S3Client s3Client = S3Client.builder().region(region).build();

        ResponseInputStream<?> responseInputStream = s3Client
                .getObject(GetObjectRequest.builder()
                        .bucket("lpm-inventory-data")
                        // .key("s3testdata.txt")
                        .key("handy-tool-catalog.json").build());

        InputStreamReader inputStreamReader = new InputStreamReader(responseInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        Gson gson = new Gson();
        Product[] products = gson.fromJson(bufferedReader, Product[].class);

        return Arrays.stream(products).filter(prod -> prod.id == prodId).collect(Collectors.toList()).get(0);
    }
}

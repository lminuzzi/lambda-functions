package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<String, Object> {

    public Object handleRequest(final String input, final Context context) {
        context.getLogger().log("Input = " + input);
        Region region = Region.EU_WEST_3;
        S3Client s3Client = S3Client.builder().region(region).build();

        ResponseInputStream<?> responseInputStream = s3Client
                .getObject(GetObjectRequest.builder()
                        .bucket("lpm-inventory-data")
                        // .key("s3testdata.txt")
                        .key("handy-tool-catalog.json").build());

        InputStreamReader inputStreamReader = new InputStreamReader(responseInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String outputStream = null;

        try {
            outputStream = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            context.getLogger().log("An exception was generated when attempting to readLine() BufferedReader");
        }
        return outputStream;
    }
}

// File: App.java
// Lambda function that triggers off an s3 put by a zip file and decompresses it in another s3 bucket
package helloworld;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3Object;


/**
 * This is the main App class that triggers off a s3 put of a zip file
 * and decompresses the files in another s3 bucket
 *
 * @see com.amazonaws.services.lambda.runtime
 * @see com.amazonaws.services.s3*
 *
 * @author byvictorrr
 */


public class App implements RequestHandler<S3EventNotification, Object> {


    final String SRC_BUCKET = "4n6-dynamodb-endpoint";
    /**
     * Takes the contents from the <code>S3EventNotification</code>, then gets
     * the contents of the zip file specified in the S3 event. The contents are
     * then stored in a <code>ZipInputStream</code>. This is handed off to the
     * singleton class <code>ZipDecompressor</code> class.
     *
     * @param input a S3 trigger with information about it
     * @parm Context, im not really sure what this does
     * @return an <code> Object </code> that specifies result of the run
     */
    public Object handleRequest(final S3EventNotification input, final Context context) {
        final ZipDecompressor decompressor = ZipDecompressor.getInstance();
        S3EventNotification.S3Entity s3Entity;
        String key, eventBucket;
        ZipInputStream zin;

        try{

            s3Entity = input.getRecords().get(0).getS3();
            key = s3Entity.getObject().getKey();
            eventBucket = s3Entity.getBucket().getName();
            decompressor.decompress(eventBucket,SRC_BUCKET,key);

        }catch (Exception e){
            e.printStackTrace();
        }


        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        return new GatewayResponse("{}", headers, 200);
    }


}

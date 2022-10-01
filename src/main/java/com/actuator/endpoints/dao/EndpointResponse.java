package com.actuator.endpoints.dao;

import com.actuator.endpoints.PropertyConfig;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class EndpointResponse {
    @Autowired
    PropertyConfig propertyConfig;

    public Response metricsResponse(String path) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(propertyConfig.getBaseUri().concat("/").concat(path))
                .build();

        return client.newCall(request).execute();
    }

    public Response metricsArrayResponse(String pathVariable) throws IOException {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(propertyConfig.getBaseUri()
                        .concat("/metrics/")
                        .concat(pathVariable))
                .build();

        return client.newCall(request).execute();
    }
}

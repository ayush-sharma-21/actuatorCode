package com.actuator.endpoints;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/monitor")
public class customEndpoint {
    @Autowired
    PropertyConfig propertyConfig;

    OkHttpClient client = new OkHttpClient();

    JSONParser parser = new JSONParser();

    @GetMapping("/metrics")
    public ResponseEntity metricsDataResponse() throws IOException, ParseException {

        JSONArray responseArr = new JSONArray();

        Request request = new Request.Builder()
                .url(propertyConfig.getBaseUri().concat("/metrics"))
                .build();

        Response response = client.newCall(request).execute();

        if (response.code() == HttpStatus.OK.value()) {

            String responseBody = response.body().string();
            JSONObject manageEndpointObject = (JSONObject) parser.parse(responseBody);
            JSONArray namesArr = (JSONArray) manageEndpointObject.get("names");

            for (Object name : namesArr) {
                JSONObject responseObj = new JSONObject();

                String metrics = name.toString();
                responseObj.put(metrics, callAPI(metrics));

                responseArr.add(responseObj);

            }
        }
        return new ResponseEntity<>(responseArr, HttpStatus.OK);
    }

    public JSONObject callAPI(String pathVariable) {
        JSONArray measurementsArr = null;
        JSONObject manageEndpointObject = null;

        JSONObject statisticObj = new JSONObject();

        try {
            Request request = new Request.Builder()
                    .url(propertyConfig.getBaseUri()
                            .concat("/metrics/")
                            .concat(pathVariable))
                    .build();

            Response response = client.newCall(request).execute();

            if (response.code() == HttpStatus.OK.value()) {
                String responseBody = response.body().string();
                manageEndpointObject = (JSONObject) parser.parse(responseBody);
                measurementsArr = (JSONArray) manageEndpointObject.get("measurements");
            }
            JSONObject meaurementValueJsonObj = new JSONObject();

            for (int i = 0; i < measurementsArr.size(); i++) {
                JSONObject measurementArrJsonObject = (JSONObject) measurementsArr.get(i);

                meaurementValueJsonObj.put(measurementArrJsonObject.get("statistic"), measurementArrJsonObject.get("value"));

            }
            statisticObj.put("statistic", meaurementValueJsonObj);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return statisticObj;
    }
}

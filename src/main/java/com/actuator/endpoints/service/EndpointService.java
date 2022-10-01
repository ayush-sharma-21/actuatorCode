package com.actuator.endpoints.service;

import com.actuator.endpoints.dao.EndpointResponse;
import com.actuator.endpoints.helper.MetricsHelper;
import com.actuator.endpoints.helper.NameArrayHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class EndpointService {

    @Autowired
    EndpointResponse endpointResponse;
    @Autowired
    MetricsHelper metricsHelper;
    @Autowired
    NameArrayHelper namesArray;

    @Autowired
    JSONParser jsonParser;

    public JSONArray metricsService() throws IOException, ParseException {
        Response response = endpointResponse.metricsResponse("metrics");
        JSONArray responseArr = new JSONArray();

        if (response.code() == HttpStatus.OK.value()) {
            for (Object name : namesArray.namesArray(response, "names")) {
                JSONObject responseObj = new JSONObject();

                String metrics = name.toString();
                responseObj.put(metrics, metricsHelper.callAPI(metrics));
                responseArr.add(responseObj);
            }
        }
        return responseArr;
    }


    public JSONObject envService() throws IOException, ParseException {
        JSONObject responseObj = new JSONObject();

        Response response = endpointResponse.metricsResponse("env");
//        if (response.code() == HttpStatus.OK.value()) {
//            for (Object name : namesArray.namesArray(response)) {
//
//                String metrics = name.toString();
//                responseObj.put(metrics, metricsHelper.callAPI(metrics));
//            }
//        }
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.body().string());
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap hashMap = objectMapper.convertValue(jsonObject, HashMap.class);

        System.out.println(hashMap.keySet());
        return jsonObject;
    }
}

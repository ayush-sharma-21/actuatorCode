package com.actuator.endpoints.helper;

import com.actuator.endpoints.dao.EndpointResponse;
import com.squareup.okhttp.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MetricsHelper {
    @Autowired
    EndpointResponse endpointResponse;
    public JSONObject callAPI(String pathVariable) {
        JSONArray measurementsArr = null;
        JSONObject manageEndpointObject = null;

        JSONObject meaurementValueJsonObj = new JSONObject();
        JSONObject statisticObj = new JSONObject();
        JSONParser parser = new JSONParser();

        try {
            Response response = endpointResponse.metricsArrayResponse(pathVariable);

            if (response.code() == HttpStatus.OK.value()) {
                String responseBody = response.body().string();
                manageEndpointObject = (JSONObject) parser.parse(responseBody);
                measurementsArr = (JSONArray) manageEndpointObject.get("measurements");
            }

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

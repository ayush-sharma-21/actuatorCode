package com.actuator.endpoints.helper;

import com.squareup.okhttp.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NameArrayHelper {

    @Autowired
    JSONParser jsonParser;

    public JSONArray namesArray(Response response,String key) throws ParseException, IOException {

        JSONObject manageEndpointObject = (JSONObject) jsonParser.parse(response.body().string());
        return (JSONArray) manageEndpointObject.get(key);
    }
}

package com.actuator.endpoints;

import com.squareup.okhttp.OkHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class AppConfig {

    @Bean
    public JSONParser jsonParser() {
        return new JSONParser();
    }

    @Bean
    public JSONArray jsonArray() {
        return new JSONArray();
    }

}
package com.dd.playwright.apiTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.springframework.context.annotation.DependsOn;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class CreateuserPostCallTest {

    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;
    String userId;

    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    public static String getRandomEmail() {
        String email = "shivatest" + System.currentTimeMillis() + "@gmail.com";
        return email;
    }

    @Test
    public void createUser() {
        Map<String, Object> data = new HashMap();
        data.put("name", "shivaprasad");
        data.put("email", getRandomEmail());
        data.put("gender", "male");
        data.put("status", "active");
        APIResponse createUser = requestContext.post("https://gorest.co.in/public/v2/users",
            RequestOptions.create().setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer 3cf580d1ec34eee42d1953c362d56dddefed1d42ecde8abc48fb8de0e0b9b6cb").setData(data));
        System.out.println("created user status code: " + createUser.status());
        Assert.assertEquals(createUser.status(), 201);
        String json = createUser.text();
        System.out.println("Response body of created user: " + json);
        JsonObject jsonResponse = (JsonObject) (new Gson()).fromJson(json, JsonObject.class);
        userId = jsonResponse.get("id").getAsString();
        System.out.println("userId: " + userId);
    }

    @Test
    @DependsOn
    public void getUser() {
        System.out.println("userId in getUser: " + userId);
        APIResponse getUser = requestContext.get("https://gorest.co.in/public/v2/users/" + userId,
            RequestOptions.create().setHeader("Authorization", "Bearer 3cf580d1ec34eee42d1953c362d56dddefed1d42ecde8abc48fb8de0e0b9b6cb"));
        int statusCode = getUser.status();
        System.out.println("status code for get user : " + statusCode);
    }

    @AfterAll
    public void tearDown() {
        playwright.close();
    }
}

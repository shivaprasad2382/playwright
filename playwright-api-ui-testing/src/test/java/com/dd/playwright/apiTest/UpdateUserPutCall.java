package com.dd.playwright.apiTest;

import org.junit.jupiter.api.AfterAll;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class UpdateUserPutCall {

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

    @Test
    public void updateUser() {

        Users users = new Users();
        users.setStatus("inactive");
        users.setName("Never-more");
        users.setEmail("nevermore@gmail.com");
        users.setGender("female");

        APIResponse apiPutRespoane = requestContext.put("https://gorest.co.in/public/v2/users/8150892",
            RequestOptions.create().setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer 3cf580d1ec34eee42d1953c362d56dddefed1d42ecde8abc48fb8de0e0b9b6cb").setData(users));

        System.out.println(apiPutRespoane.status() + ": " + apiPutRespoane.statusText());
        System.out.println("response after user updated..." + apiPutRespoane.text());
    }

    @AfterAll
    public void tearDown() {
        playwright.close();
    }
}
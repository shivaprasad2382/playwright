package com.dd.playwright.apiTest;

import org.junit.jupiter.api.AfterAll;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class DeleteUserApiTest {

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
    public void createUser() throws Exception, JsonProcessingException {

        // create user using builder pattern..
        Users users = Users.builder().name("Williams").email("williams1@gmail.com").gender("female").status("active").build();

        APIResponse createUser = requestContext.post("https://gorest.co.in/public/v2/users",
            RequestOptions.create().setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer 3cf580d1ec34eee42d1953c362d56dddefed1d42ecde8abc48fb8de0e0b9b6cb").setData(users));
        System.out.println("created user status code: " + createUser.status());
        Assert.assertEquals(createUser.status(), 201);
        String json = createUser.text();
        System.out.println("Response body of created user: " + json);

        // convert response text/json to POJO --> deserialization
        ObjectMapper objectMapper = new ObjectMapper();
        Users actualUser = objectMapper.readValue(json, Users.class);
        Assert.assertEquals(actualUser.getName(), users.getName());
        System.out.println("email: " + actualUser.getEmail());
        userId = actualUser.getId();
    }

    @Test(dependsOnMethods = { "createUser" })
    public void deleteUser() {
        APIResponse deleteUser = requestContext.delete("https://gorest.co.in/public/v2/users/" + userId,
            RequestOptions.create().setHeader("Authorization", "Bearer 3cf580d1ec34eee42d1953c362d56dddefed1d42ecde8abc48fb8de0e0b9b6cb"));
        System.out.println(deleteUser.status() + ": " + deleteUser.statusText());
    }

    @Test
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

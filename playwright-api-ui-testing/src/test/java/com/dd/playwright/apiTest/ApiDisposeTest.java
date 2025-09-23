package com.dd.playwright.apiTest;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

public class ApiDisposeTest {

    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @Test
    public void disposeResponseTest() {
        APIResponse response = requestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = response.status();
        System.out.println("Response Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        String responseBodyInTextFormat = response.text();
        System.out.println("responseBodyInTextFormat: " + responseBodyInTextFormat);
        response.dispose();
        int statusCodeAfterDispose = response.status();
        System.out.println("Response Code After Dispose: " + statusCodeAfterDispose);
    }

    @AfterMethod
    public void tearDown() {
        playwright.close();
    }
}

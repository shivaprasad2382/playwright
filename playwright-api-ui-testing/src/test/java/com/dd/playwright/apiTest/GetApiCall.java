package com.dd.playwright.apiTest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import com.microsoft.playwright.options.RequestOptions;

public class GetApiCall {

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
    public void getSpecificUser() {
        APIResponse response = requestContext.get("https://gorest.co.in/public/v2/users",
            RequestOptions.create().setQueryParam("name", "Aarya Nayar").setQueryParam("status", "active"));
        System.out.println("get user: " + response.text());
    }

    @Test
    public void getAPIUsersTest() {
        APIResponse response = requestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = response.status();
        System.out.println("Response Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        String url = response.url();
        System.out.println("url: " + url);
        byte[] body = response.body();
        System.out.println("body: " + body);
        Map<String, String> headers = response.headers();
        headers.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });
        Assert.assertEquals((String) headers.get("content-type"), "application/json; charset=utf-8");
        Assert.assertEquals((String) headers.get("connection"), "keep-alive");
        System.out.println("headers: " + headers);
        System.out.println("****************************");
        List<HttpHeader> httpHeader = response.headersArray();
        Iterator var = httpHeader.iterator();

        while (var.hasNext()) {
            HttpHeader list = (HttpHeader) var.next();
            System.out.println(list.name + " : " + list.value);
        }

        Boolean ok = response.ok();
        Assert.assertEquals(ok, true);
        System.out.println("ok: " + ok);
        String statusText = response.statusText();
        System.out.println("statusText: " + statusText);
        String responseBodyInTextFormat = response.text();
        System.out.println("responseBodyInTextFormat: " + responseBodyInTextFormat);
    }

    @AfterMethod
    public void tearDown() {
        playwright.close();
    }
}

package com.dd.playwright.basics;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Basics {

    public static void main(String[] args) throws InterruptedException {

        Playwright playwright = Playwright.create();
        // by default it will launch the browser in headless mode
//        Browser browser = playwright.chromium().launch();
        // launch in normal mode and chrome, webkit, firefox also be used and setSlowMo(50) slow-downs execution...
//        Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));

        // this will launch chrome browser not chromium browser...
        LaunchOptions launchOptions = new LaunchOptions();
        launchOptions.setChannel("msedge");
        launchOptions.setHeadless(false);
        Browser browser = playwright.chromium().launch(launchOptions);

        Page page = browser.newPage();
        // this will pause the execution and opens a debug window to debug from the specific point
        page.pause();

        page.navigate("https://github.com/login");
        System.out.println("page title: " + page.title());
        System.err.println("page url: " + page.url());

        Thread.sleep(3000);
        browser.close();
        playwright.close();

    }

}

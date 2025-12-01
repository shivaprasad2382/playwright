package com.dd.playwright.basics;

import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PopupAndTab {

    public static void main(String[] args) throws Exception {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));

        BrowserContext bx1 = browser.newContext(new Browser.NewContextOptions().setViewportSize(null)
            .setRecordVideoDir(Paths.get("/home/digital/Documents/java-practice/playwright-api-ui-testing/Recordings")));
        Page page = bx1.newPage();
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        page.waitForLoadState();
        // we can able to perform actions on the popup and here gng to other page by clicking on element
//        Page popup = page.waitForPopup(() -> {
//            page.locator("(//div[@class='orangehrm-login-footer-sm']//a)[3]").click();
//        });
//        popup.waitForLoadState();
//        System.out.println("Title of popup window is: " + popup.title());
//        popup.close();
//        System.out.println("Title of main page: " + page.title());
//        page.close();

        // here opening a new tab and navigating to a desired url from the existing page
        Page popup2 = page.waitForPopup(() -> {
            page.click("a[target='_blank']");// opens a new window/tab
        });
        popup2.waitForLoadState();
        popup2.navigate("https://www.youtube.com/");
        System.out.println("Title of popup2: " + popup2.title());
        popup2.close();
        // It’s very important to close the browser context; otherwise, the video will not be recorded.
        bx1.close();
        page.close();
        browser.close();
        playwright.close();

    }

}

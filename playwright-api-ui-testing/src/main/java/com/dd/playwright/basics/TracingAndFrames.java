package com.dd.playwright.basics;

import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

public class TracingAndFrames {

    public static void main(String[] args) throws InterruptedException {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50).setArgs(Arrays.asList("--start-maximized")));
            
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));// opens browser in full
                                                                                                               // screen by default
            context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
            Page page = context.newPage();
            page.navigate("https://academy.naveenautomationlabs.com/");
            page.click("text=Login");
            // switching to the frame
            FrameLocator frame = page.frameLocator("#microfe-popup-login");
            // by id
//            page.frameLocator("#microfe-popup-login").locator("#input-fname").fill("shiva");
            frame.locator("#input-fname").fill("shiva");
            frame.locator("#input-email").fill("shivatest@gmail.com");
            // with placeholder
            frame.getByPlaceholder("Password").fill("jhsbdjwehbf");
            frame.locator("input[placeholder='Enter your number']").fill("+91 95057-62382");
            frame.locator("#loginPopupCloseBtn svg").click();
            page.click("text=Courses");
            page.goBack();
            page.click("text=Webinars");
            page.goBack();
            Thread.sleep(3000);
            page.getByPlaceholder("Search").first().fill("java");
            page.getByPlaceholder("Search").first().press("Enter");

//            page.goForward();
            context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
            Thread.sleep(3000);
            page.close();
            playwright.close();
        }
    }

}

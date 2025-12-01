package com.dd.playwright.basics;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class HdfcAlertsHandling {

    public static void main(String[] args) throws Exception {

        Playwright playwright = Playwright.create();
        //setting channel as chrome opens chrom browser instead of chromium
        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setChannel("ms-edge").setHeadless(false).setSlowMo(10000).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bcx = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page page = bcx.newPage();
        page.navigate("https://netbanking.hdfcbank.com/netbanking/");
        page.waitForLoadState();

        page.onDialog(dialog -> {
            System.out.println("Alert text: " + dialog.message());
            dialog.dismiss();
        });
//        Thread.sleep(5000);
        FrameLocator loginFrame = page.frameLocator("frame[name='login_page']");
        loginFrame.locator("a:text('CONTINUE')").click();
        System.out.println("Page title: " + page.title());
        System.out.println("CONTINUE button clicked...");
        loginFrame.locator("input[name='fldLoginUserId']").fill("shiva123");
        playwright.close();
    }

}

package com.dd.playwright.rayv;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import playwrightUtils.CommonUtils;

public class RayvDummy {

    public static void main(String[] args) throws InterruptedException, IOException {
        Playwright pw = Playwright.create();
        Browser br = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
//        BrowserContext bcx = br
//            .newContext(new Browser.NewContextOptions().setViewportSize(null).setStorageStatePath(Paths.get("rayvCredentials.json")));
//        Page pg = bcx.newPage();
//        pg.navigate("https://business.test.apps.rayv.ai/");

//        pg.click("//*[@id='nav-ps-next']/li[3]");
//        pg.mouse().move(300, 0);
//
//        Thread.sleep(3000);
//        List<Field> fields = CommonUtils
//            .loadFieldsFromJson("/home/digital/Documents/java-practice/playwright-api-ui-testing/campaign.json");
//
////       
//        pw.close();

        // Admin

//        pg.click("//*[@id='nav-ps-next']/li[3]");
//        pg.mouse().move(300, 0);

//        Thread.sleep(5000);
        BrowserContext bcx1 = br
            .newContext(new Browser.NewContextOptions().setViewportSize(null).setStorageStatePath(Paths.get("rayvAdminCredentials.json")));

        Page pg1 = bcx1.newPage();
        pg1.setDefaultTimeout(60000);
        pg1.navigate("https://business.test.apps.rayv.ai/");
        Thread.sleep(5000);
        pg1.click("//*[@id='nav-ps-next']/li[3]");
        pg1.mouse().move(300, 0);
       
        // it will take us to the page required
//        pg.bringToFront();

    }

}

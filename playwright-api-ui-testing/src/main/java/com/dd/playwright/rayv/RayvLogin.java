package com.dd.playwright.rayv;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class RayvLogin {

    public static void main(String[] args) throws InterruptedException {

        Playwright pw = Playwright.create();
        Browser br = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bcx = br.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page pg = bcx.newPage();
        pg.navigate("https://business.test.apps.rayv.ai");
        pg.waitForLoadState();
        pg.locator("#signInName").fill("shivaprasadm86@gmail.com");
        pg.locator("#password").fill("RayvInc@123");
        Thread.sleep(3000);
        pg.click("#next");
        Thread.sleep(3000);
        bcx.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("rayvCredentials.json")));

        pg.close();
        bcx.close();
        br.close();
        pw.close();

    }

}

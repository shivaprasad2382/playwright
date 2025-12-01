package com.dd.playwright.basics;

import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class AuthLoginUsage {

    public static void main(String[] args) throws InterruptedException {

        Playwright pw = Playwright.create();
        Browser br = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
        // Loads the previously saved browser storage state from applogin.json,
        // allowing the session to continue without logging in again
        BrowserContext bcx = br
            .newContext(new Browser.NewContextOptions().setViewportSize(null).setStorageStatePath(Paths.get("applogin.json")));

        Page pg = bcx.newPage();
        pg.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        Thread.sleep(3000);
        pg.close();
        bcx.close();
        br.close();
        pw.close();
    }

}

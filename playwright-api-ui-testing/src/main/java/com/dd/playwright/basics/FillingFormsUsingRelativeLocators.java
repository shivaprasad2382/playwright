package com.dd.playwright.basics;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FillingFormsUsingRelativeLocators {

    public static void main(String[] args) throws Exception {
        Playwright pw = Playwright.create();
        Browser bw = pw.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(300).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bx = bw.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page pg = bx.newPage();
        pg.navigate("https://selectorshub.com/xpath-practice-page/");
        pg.waitForLoadState();
//        Locator email = pg.locator("input:below(:text('User Email'))").first();
//        email.click();
//        email.fill("jfberyjfvref");
        
        pg.locator("input:below(:text('User Email'))").first().click();
        pg.locator("input:below(:text('User Email'))").first().fill("jhfwvjvf");
        Thread.sleep(5000);
        pg.locator("input:below(:text('Password'))").first().fill("jhfwvjvf");
//        pg.locator("input:below(:text('Mobile Number'))").first().fill("9505762382");
        pg.locator("input:below(:text('Country'))").first().fill("India");

        
        pw.close();
    }

}

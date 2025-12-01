package com.dd.playwright.basics;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class JsPopupHandle {

    public static void main(String[] args) {

        Playwright pw = Playwright.create();
        Browser br = pw.chromium()
            .launch(new BrowserType.LaunchOptions().setSlowMo(300).setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bcx = br.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page pg = bcx.newPage();
        pg.navigate("https://the-internet.herokuapp.com/javascript_alerts");

        // Js alerts, prompts, confirmation pop-ups

        // 1.
        // pg.click("text='Click for JS Alert'");

        // 2. custom alerts handling using playwright listener
//        pg.onDialog(dialog -> {
//            String message = dialog.message();
//            System.out.println("alert message: "+message);
//            dialog.dismiss();
//         });
//         pg.click("text='Click for JS Confirm'");

        // 3. prompt
        pg.onDialog(dialog -> {
            String message = dialog.message();
            System.out.println("alert message: " + message);
            dialog.accept("prompt accepted...");
            // dialog.dismiss();
        });
        pg.click("text='Click for JS Prompt'");

        String result = pg.textContent("#result");
        System.out.println("captured result: " + result);

        pw.close();
    }

}

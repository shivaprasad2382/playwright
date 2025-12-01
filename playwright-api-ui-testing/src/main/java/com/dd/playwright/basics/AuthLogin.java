package com.dd.playwright.basics;

import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class AuthLogin {

    public static void main(String[] args) throws InterruptedException {

        Playwright pw = Playwright.create();
        Browser br = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bcx = br.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page pg = bcx.newPage();
        pg.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        Locator username = pg.locator("input[name='username']");
//        highlight(pg, username);
        username.fill("Admin");
        pg.locator("input[name='password']").fill("admin123");
        pg.click("//button[@type='submit']");

        // Stores the authenticated browser state (e.g., cookies and local/session storage)
        // so it can be reused later without logging in again. Saved to applogin.json.
        bcx.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("applogin.json")));
        Thread.sleep(3000);
        pg.close();
        bcx.close();
        br.close();
        pw.close();

    }

    public static void highlight(Page page, Locator locator) {
        // You MUST get the ElementHandle to pass into evaluate()
        ElementHandle handle = locator.elementHandle();

        page.evaluate("element => {" + "element.style.border = '2px solid red';" + "element.style.background = 'yellow';"
            + "element.style.zIndex = '9999';" + "}", handle);
    }

}

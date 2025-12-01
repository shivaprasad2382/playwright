package com.dd.playwright.basics;

import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;

public class BrowserContextAndTracing {

    public static void main(String[] args) throws Exception {

        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(30).setArgs(Arrays.asList("--start-maximized")));

        BrowserContext bcx1 = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page p1 = bcx1.newPage();
        bcx1.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        p1.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        p1.getByPlaceholder("Username").fill("Admin");
        p1.getByPlaceholder("Password").fill("admin123");
//        p1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
        Locator login = p1.locator("text= Login ").first();
        login.click();
        Thread.sleep(3000);
        p1.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/p1_orangeHRM.png")));
        bcx1.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("zip/p1_orangeHRM.zip")));
        p1.close();
        bcx1.close();

        BrowserContext bcx2 = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page p2 = bcx2.newPage();
        bcx2.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        p2.navigate("https://academy.naveenautomationlabs.com/");
        p2.click("text=Login");
        FrameLocator frame = p2.frameLocator("#microfe-popup-login");
        frame.locator("#input-fname").fill("shiva");
        frame.locator("#input-email").fill("shivatest@gmail.com");
        frame.getByPlaceholder("Password").fill("jhsbdjwehbf");
        frame.locator("input[placeholder='Enter your number']").fill("+91 95057-62383");
        frame.locator("#loginPopupCloseBtn svg").click();
        Thread.sleep(3000);
        p2.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/p2_naveenacademy.png")));
        bcx2.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("zip/p2_naveenacademy.zip")));
        p2.close();
        bcx2.close();

        BrowserContext bcx3 = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page p3 = bcx3.newPage();
        bcx3.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        p3.navigate("https://github.com/login");
        p3.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/p3_github.png")));
        bcx3.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("zip/p3_github.zip")));
        System.out.println("p3 page title: " + p3.title());
        System.err.println("p3 page url: " + p3.url());
        p3.close();
        bcx3.close();

        BrowserContext bcx4 = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page p4 = bcx4.newPage();
        bcx4.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        p4.navigate("https://playwright.dev/java/docs/trace-viewer");
        p4.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/p4_traceviewer.png")));
        bcx4.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("zip/p4_traceviewer.zip")));
        System.out.println("p4 page title: " + p4.title());
        System.err.println("p4 page url: " + p4.url());
        p4.close();
        bcx4.close();

        BrowserContext bcx5 = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page p5 = bcx5.newPage();
        bcx5.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        p5.navigate("https://business.test.apps.rayv.ai");
        Thread.sleep(5000);
        p5.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/p5_rayvai.png")));
        bcx5.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("zip/p5_rayvai.zip")));
        System.out.println("p5 page title: " + p5.title());
        System.err.println("p5 page url: " + p5.url());
        p5.close();
        bcx5.close();

        browser.close();
        playwright.close();

    }

}

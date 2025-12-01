package com.dd.playwright.basics;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class NaveenSiteWithCodeGen {

    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://academy.naveenautomationlabs.com/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Name")).click();
            //this will pause the execution and opens a debug window to debug from the specific point
            page.pause();
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Name")).fill("shiva");
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Email address")).click();
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Email address")).fill("shiva7test@gmail.com");
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Email address")).press("Enter");
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Email address")).click();
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Password")).click();
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Password")).fill("Shiviasvbwei");
            page.locator("#microfe-popup-login").contentFrame().getByRole(AriaRole.BUTTON).first().click();
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Enter your number")).click();
            page.locator("#microfe-popup-login").contentFrame()
                .getByRole(AriaRole.TEXTBOX, new FrameLocator.GetByRoleOptions().setName("Enter your number")).fill("+91 95057-62382");
            page.locator("#microfe-popup-login").contentFrame().locator("#loginPopupCloseBtn svg").click();
        }

    }
}
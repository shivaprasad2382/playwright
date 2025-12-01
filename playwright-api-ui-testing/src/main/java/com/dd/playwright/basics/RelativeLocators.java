package com.dd.playwright.basics;

import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class RelativeLocators {

    public static void main(String[] args) {

        Playwright pw = Playwright.create();
        Browser bw = pw.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(300).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bx = bw.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page pg = bx.newPage();
        pg.navigate("https://selectorshub.com/xpath-practice-page/");
//        pg.locator("input[type='checkbox']:left-of(:text('Garry.White'))").nth(2).click(); //To access the desired element
//        pg.locator("input[type='checkbox']:left-of(:text('Jasmine.Morgan'))").last().click(); //To access last element
//        pg.locator("input[type='checkbox']:left-of(:text('Jasmine.Morgan'))").first().click(); // To access first element
//        pg.locator("input[type='checkbox']:left-of(:text('Joe.Root'))").first().click();
//
//        String userRole = pg.locator("td:right-of(:text('Garry.White'))").first().textContent();
//        System.out.println("Role of Garry.White " + userRole);
//        String employeeName = pg.locator("td:right-of(:text('Garry.White'))").nth(3).textContent();
//        System.out.println("employeeName: " + employeeName);
//        String userAboveJasmine = pg.locator("a:above(:text('Jasmine.Morgan'))").first().textContent();
//        System.out.println("user above Jasmine is: " + userAboveJasmine);
//        String userBelowJasmine = pg.locator("a:below(:text('Jasmine.Morgan'))").first().textContent();
//        System.out.println("user below Jasmine: " + userBelowJasmine);
        Locator nearElements = pg.locator("a:near(:text('Jasmine.Morgan'),100)");
//        List<String> allNearElements = nearElements.allInnerTexts();
//        for (String ele : allNearElements) {
//            System.out.println(ele);
//        }
        nearElements.allInnerTexts().forEach(e -> System.out.println(e));
        pw.close();
    }

}

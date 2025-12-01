package com.dd.playwright.rayv;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import playwrightUtils.CommonUtils;
import playwrightUtils.Field;

public class CampaignCreation {

    public static void main(String[] args) throws InterruptedException, IOException {
        Playwright pw = Playwright.create();
        Browser br = pw.chromium()
            .launch(new BrowserType.LaunchOptions().setSlowMo(50).setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bcx = br.newContext(new Browser.NewContextOptions().setViewportSize(null)
            .setRecordVideoDir(Paths.get("Videos/User/Create")).setRecordVideoSize(1280, 1080)

            .setStorageStatePath(Paths.get("rayvCredentials.json")));
        Page pg = bcx.newPage();
        bcx.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        Instant start = Instant.now();
        pg.setDefaultTimeout(60000);
        pg.navigate("https://business.test.apps.rayv.ai/");
        Instant end = Instant.now();
        Duration pageLoadTime = Duration.between(start, end);
        System.out.println("Page load time: " + pageLoadTime.toSeconds());
        pg.waitForLoadState();
        Thread.sleep(10000);
        List<Field> fields = CommonUtils
            .loadFieldsFromJson("/home/digital/Documents/java-practice/playwright-api-ui-testing/campaign.json");
        CommonUtils.processFieldsByNameAndClick(fields, "goToCampaigns", pg);
        pg.mouse().move(300, 0);
        Thread.sleep(3000);
        // create campaign
        CommonUtils.processFieldsByNameAndClick(fields, "createCampaign", pg);
        CommonUtils.fillFormData(fields, pg, "./Screenshots/03_campaignCreateFormFilling.png");
        List<Field> paymentFields = CommonUtils
            .loadFieldsFromJson("/home/digital/Documents/java-practice/playwright-api-ui-testing/Payment.json");
        Thread.sleep(3000);
        CommonUtils.fillFormData(paymentFields, pg, "./Screenshots/04_paymentFormFilling.png");
        Thread.sleep(10000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/05_activeCampaigns_gridView.png")));
        CommonUtils.processFieldsByNameAndClick(fields, "pendingTabButton", pg);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/06_pendingCampaigns_gridView.png")));
        Thread.sleep(2000);

        // checking created campaign is in pending or not
        String campaignName = CommonUtils.processFieldsByNameAndGetElementValue(fields, "campaignName", "value");
        Locator table = pg.locator("(//div[@role='tabpanel'])[2]");
        Locator campName = table.getByText(campaignName);
        String result = campName.count() > 0 ? campName.innerText() : "Not Found";
        System.out.println("Search Result: " + result);
        System.out.println("Created campaign found in Pending " + campaignName.equals(result));

        bcx.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("zip/rayvUserTracing.zip")));

        Thread.sleep(2000);
        CommonUtils.processFieldsByNameAndClick(fields, "rejectedTabButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/07_rejectedCampaigns_gridView.png")));
        CommonUtils.processFieldsByNameAndClick(fields, "draftTabButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/08_draftCampaigns_gridView.png")));
        CommonUtils.processFieldsByNameAndClick(fields, "archiveTabButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/09_archivedCampaigns_gridView.png")));
        Thread.sleep(2000);

        CommonUtils.processFieldsByNameAndClick(fields, "termsOfServiceButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/15_termsOfService")));
        pg.goBack();
        CommonUtils.processFieldsByNameAndClick(fields, "privacyPolicyButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/16_privacyPolicy")));
        pg.goBack();
        Thread.sleep(5000);

        // changing to list view and capturing them
//        pg.waitForLoadState(LoadState.NETWORKIDLE);
        pg.mouse().move(300, 0);
        CommonUtils.processFieldsByNameAndClick(fields, "listViewButton", pg);
        Thread.sleep(2000);
        CommonUtils.processFieldsByNameAndClick(fields, "activeTabButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/10_activeCampaigns_listView.png")));
        CommonUtils.processFieldsByNameAndClick(fields, "pendingTabButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/11_pendingCampaigns_listView.png")));
        CommonUtils.processFieldsByNameAndClick(fields, "rejectedTabButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/11_rejectedCampaigns_listView.png")));
        CommonUtils.processFieldsByNameAndClick(fields, "draftTabButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/12_draftCampaigns_listView.png")));
        CommonUtils.processFieldsByNameAndClick(fields, "archiveTabButton", pg);
        Thread.sleep(2000);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/13_archivedCampaigns_listView.png")));
        Thread.sleep(4000);

        // Admin approval for the campaign creation
        BrowserContext bcx1 = br.newContext(new Browser.NewContextOptions().setViewportSize(null)
            .setRecordVideoDir(Paths.get("Videos/Admin")).setStorageStatePath(Paths.get("rayvAdminCredentials.json")));
        Page pg1 = bcx1.newPage();
        bcx1.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        pg1.setDefaultNavigationTimeout(60000);
        pg1.navigate("https://business.test.apps.rayv.ai/");
        Thread.sleep(5000);
        pg1.click("//*[@id='nav-ps-next']/li[3]");
        pg1.mouse().move(300, 0);
        Thread.sleep(5000);
        pg1.mouse().move(300, 0);
        // clicking on view of form...
        CommonUtils.clickLatestVersionTasks(pg1, "Campaign Approval Review");
        Thread.sleep(3000);
        // Admin approval based on the form...
        CommonUtils.adminAction(pg1, campaignName, "rejected by admin...", true, "./Screenshots/14_adminapprovalOfCreatedCampaign.png");

        bcx1.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("zip/rayvAdminTracing.zip")));

        // gng to user screen again...
        pg.bringToFront();
        Thread.sleep(2000);
        CommonUtils.processFieldsByNameAndClick(fields, "activeTabButton", pg);
        pg.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./Screenshots/17_activeCampaignsAfterAdminApproval.png")));

        pg.close();
        pg1.close();
        bcx.close();
        bcx1.close();
        br.close();
        pw.close();

    }

}

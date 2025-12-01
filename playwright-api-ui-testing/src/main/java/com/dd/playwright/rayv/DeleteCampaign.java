package com.dd.playwright.rayv;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import playwrightUtils.CommonUtils;
import playwrightUtils.Field;

public class DeleteCampaign {

    public static void main(String[] args) throws IOException, InterruptedException {
        Playwright pw = Playwright.create();
        Browser br = pw.chromium()
            .launch(new BrowserType.LaunchOptions().setSlowMo(50).setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bcx = br.newContext(new Browser.NewContextOptions().setViewportSize(null).setRecordVideoSize(1280, 1080)
            .setRecordVideoDir(Paths.get("Videos/User/Delete")).setStorageStatePath(Paths.get("rayvCredentials.json")));
        Page pg = bcx.newPage();
        bcx.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        pg.setDefaultTimeout(60000);
        pg.navigate("https://business.test.apps.rayv.ai/");
        List<Field> fields = CommonUtils
            .loadFieldsFromJson("/home/digital/Documents/java-practice/playwright-api-ui-testing/campaign.json");
        CommonUtils.processFieldsByNameAndClick(fields, "goToCampaigns", pg);
        pg.mouse().move(300, 0);
        pg.waitForLoadState(LoadState.NETWORKIDLE);
        CommonUtils.processFieldsByNameAndClick(fields, "listViewButton", pg);

        String campaignName = CommonUtils.processFieldsByNameAndGetElementValue(fields, "campaignName", "value");

        pg.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(campaignName)).getByRole(AriaRole.BUTTON).nth(1).click();
        pg.fill("//input[@placeholder='Comment']", "OK");
        pg.click("text='Yes'");

        BrowserContext bcx1 = br.newContext(new Browser.NewContextOptions().setViewportSize(null)
            .setRecordVideoDir(Paths.get("Videos/Admin")).setStorageStatePath(Paths.get("rayvAdminCredentials.json")));
        Page pg1 = bcx1.newPage();
        bcx1.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        pg1.navigate("https://business.test.apps.rayv.ai/");
        Thread.sleep(5000);
        pg1.click("//*[@id='nav-ps-next']/li[3]");
        pg1.mouse().move(300, 0);
        Thread.sleep(5000);
        pg1.mouse().move(300, 0);
        // clicking on view of form...
        CommonUtils.clickLatestVersionTasks(pg1, "Campaign Cancellation Form");
        Thread.sleep(3000);
        // Admin approval based on the form...
        CommonUtils.adminAction(pg1, campaignName, "rejected by admin...", true, "./Screenshots/18_adminapprovalOfDeletedCampaign.png");

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

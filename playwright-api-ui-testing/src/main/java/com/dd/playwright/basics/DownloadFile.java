package com.dd.playwright.basics;

import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class DownloadFile {

    public static void main(String[] args) throws InterruptedException {

        Playwright pw = Playwright.create();
        Browser br = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bcx = br.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page pg = bcx.newPage();
        pg.navigate("https://chromedriver.storage.googleapis.com/index.html?path=102.0.5005.27/");

        Download download = pg.waitForDownload(() -> {
            pg.click("a:text('chromedriver_mac64.zip')");
        });

        // Validates that the file was downloaded correctly; throws an error if the download
        // failed, timed out, or the browser reported an incomplete/corrupt download.
        download.failure();
        System.out.println(download.url());

        // Print the suggested filename to verify that it was retrieved correctly
        System.out.println(download.suggestedFilename());

        download.saveAs(Paths.get("/home/digital/Documents/java-practice/playwright-api-ui-testing/downloadFile"));
        String path = download.path().toString();
        System.out.println("path of downloaded file: " + path);

        // download.cancel();//cancel download
        pg.close();
        bcx.close();
        br.close();
        pw.close();
    }

}

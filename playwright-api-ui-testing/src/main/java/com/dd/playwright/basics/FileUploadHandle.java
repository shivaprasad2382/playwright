package com.dd.playwright.basics;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FilePayload;

public class FileUploadHandle {

    public static void main(String[] args) throws InterruptedException {

        Playwright pw = Playwright.create();
        Browser br = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
        BrowserContext bcx = br.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page pg = bcx.newPage();
//        pg.navigate("https://davidwalsh.name/demo/multiple-file-upload.php");

        // any file upload
//        pg.setInputFiles("input#filesToUpload", Paths.get("/home/digital/Pictures/jpeg/IMG_5395-1440x1080.jpeg"));
//        Thread.sleep(5000);
//        //deselcting the upload file if selected wrong file
//        pg.setInputFiles("input#filesToUpload", new Path[0]);

        // multiple files
//        pg.setInputFiles("input#filesToUpload",
//            new Path[] { Paths.get("/home/digital/Pictures/jpeg/IMG_5395-1440x1080.jpeg"),
//                Paths.get("/home/digital/Pictures/jpeg/1707e465c721981c84f553f05664387b_w.jpeg"),
//                Paths.get("/home/digital/Pictures/jpeg/4148fb560c5ea7e1fe03ae36534a0123_w.jpeg"),
//                Paths.get("/home/digital/Pictures/jpeg/IMG_5433_jpg-1440x1080.jpeg") });
//
//        Thread.sleep(5000);
//        // here it will remove all the files uploaded...
//        pg.setInputFiles("input#filesToUpload", new Path[0]);

        // run-time file uploads & explore more abt file type mime...
        pg.navigate("https://cgi-lib.berkeley.edu/ex/fup.html");

        pg.setInputFiles("input[name='upfile']",
            new FilePayload("shiva.text", "text/plain", "Welcome to the digital world...".getBytes(StandardCharsets.UTF_8)));
        pg.click("input[value='Press']");
        System.out.println("Title: " + pg.title());
        Thread.sleep(5000);
        pg.close();
        bcx.close();
        br.close();
        pw.close();

    }

}

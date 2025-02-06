package org.qafellas.pages;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;

public class BasePage {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    /**
     * Initialize browser, page and navigate to website
     * @return page
     */
    public Page initializeBrowser(){
        playwright = Playwright.create();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--enable-javascript-dialogs");
        arguments.add("--start-maximized");
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setChannel("chrome"));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = context.newPage();
        page.navigate("https://qafellas-estate.onrender.com/");
        return page;
    }

    /**
     * Quit browser and playwright session
     */
    public void quitBrowser(){
        context.close();
        playwright.close();
    }
}

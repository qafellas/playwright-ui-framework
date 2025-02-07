package org.qafellas.pages;

import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class BasePage {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    Properties prop;

    /**
     * Initialize browser, page and navigate to website
     * @return page
     */
    public Page initializeBrowser( Properties prop){
        playwright = Playwright.create();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--enable-javascript-dialogs");
        arguments.add("--start-maximized");

        String browserName = prop.getProperty("browser");
        String url = prop.getProperty("url");
        String headless = prop.getProperty("headless");

        if(browserName.equalsIgnoreCase("chrome")){
            if(headless.equals("yes")){
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setArgs(arguments).setChannel("chrome").setSlowMo(2000));
            }
            else{
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setChannel("chrome"));
            }
        }

        else if(browserName.equalsIgnoreCase("firefox")){
            if(headless.equals("yes")){
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setArgs(arguments));
            }
            else{
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments));
            }
        }

        else{
            if(headless.equals("yes")){
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setArgs(arguments).setChannel("msedge"));
            }
            else{
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setChannel("msedge"));
            }
        }

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        context.setDefaultTimeout(120000);
        page = context.newPage();
        page.navigate(url);
        return page;
    }

    /**
     * Quit browser and playwright session
     */
    public void quitBrowser(){
        context.close();
        playwright.close();
    }

    /**
     * Retrieve property file
     * @return properties
     * @throws IOException
     */
    public Properties initializeProperties() {
        prop = new Properties();
        try{
            FileInputStream fis = new FileInputStream("src/test/java/org/qafellas/config/config.properties");
            prop.load(fis);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return prop;
    }
}

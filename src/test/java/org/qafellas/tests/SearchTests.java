package org.qafellas.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.qafellas.pages.LoginPage;
import org.qafellas.pages.SearchPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.util.ArrayList;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchTests {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    LoginPage loginPage;
    SearchPage searchPage;


    @BeforeClass
    public void launchBrowser() {
        playwright = Playwright.create();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--enable-javascript-dialogs");
        arguments.add("--start-maximized");
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setChannel("chrome").setSlowMo(2000));
    }

    @AfterClass
    public void closeBrowser() {
        playwright.close();
    }

    @BeforeMethod
    public void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = context.newPage();
        loginPage = new LoginPage(page);
        searchPage = new SearchPage(page);
        page.navigate("https://qafellas-estate.onrender.com/");
    }

    @AfterMethod
    public void closeContext() {
        context.close();
    }

    @Test
    public void shouldSearchListingWithKeyword(){
        loginPage.login("xyz@gmail.com", "X1234567@");
        searchPage.searchBox.click();
        searchPage.searchBox.fill("unique");
        searchPage.searchSubmitBtn.click();
        assertThat(searchPage.listingResult).containsText("Listing results:");
        assertThat(searchPage.listingResult).containsText("unique");
    }

    @Test
    public void shouldFilterOnlyRentalListings(){
        loginPage.login("xyz@gmail.com", "X1234567@");
        searchPage.searchNavigationBtn.click();
        searchPage.rentCheckBox.check();
        searchPage.advancedSearchBtn.click();
        searchPage.clickAndVerifyRentalListingCards();
    }


}

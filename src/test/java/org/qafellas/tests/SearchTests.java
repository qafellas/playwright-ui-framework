package org.qafellas.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.qafellas.pages.BasePage;
import org.qafellas.pages.LoginPage;
import org.qafellas.pages.SearchPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Properties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchTests {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    BasePage basePage;
    LoginPage loginPage;
    SearchPage searchPage;
    Properties prop;


    @BeforeMethod
    public void setUp() {
        basePage = new BasePage();
        prop = basePage.initializeProperties();
        page = basePage.initializeBrowser(prop);
        loginPage = new LoginPage(page);
        searchPage = new SearchPage(page);
    }

    @AfterMethod
    public void tearDown() {
        basePage.quitBrowser();
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

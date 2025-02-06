package org.qafellas.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchPage {
    Page page;
    public Locator searchBox;
    public Locator searchSubmitBtn;
    public Locator listingResult;
    public Locator searchNavigationBtn;
    public Locator rentCheckBox;
    public Locator advancedSearchBtn;
    public Locator resultCards;
    public Locator listingType;

    public SearchPage(Page page){
        this.page = page;
        this.searchBox = page.getByPlaceholder("Search...");
        this.searchSubmitBtn = page.locator("//input[@placeholder='Search...']//..//button");
        this.listingResult = page.locator("#root");
        this.searchNavigationBtn = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Let's get started..."));
        this.rentCheckBox = page.locator("#rent");
        this.advancedSearchBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
        this.resultCards = page.locator("//a[contains(@href,'/listing')]");//list of cards
        this.listingType = page.getByRole(AriaRole.MAIN);
    }
    public void clickAndVerifyRentalListingCards(){
        for (int i = 0; i < resultCards.count() ; i++) {
            resultCards.nth(i).click();
            assertThat(listingType).containsText("For Rent");
            page.goBack();
        }

    }
}

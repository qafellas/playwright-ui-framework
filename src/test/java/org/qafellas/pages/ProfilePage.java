package org.qafellas.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ProfilePage {

    public Page page;
    public Locator profileIcon;
    public Locator pictureUploadMessage;

    // Locators
    public ProfilePage(Page page) {
        this.page = page;
        this.profileIcon = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("profile"));
        this.pictureUploadMessage = page.locator("//input[@id='username']/preceding-sibling::p/span");
    }
}

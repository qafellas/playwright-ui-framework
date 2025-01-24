package org.qafellas.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {

    public Page page;
    public Locator signInBtn;
    public Locator emailBox;

    public LoginPage(Page page) {
        this.page = page;
        this.signInBtn = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in"));
        this.emailBox = page.getByTestId("email-input");
    }

}

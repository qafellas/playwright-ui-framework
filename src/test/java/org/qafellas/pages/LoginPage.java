package org.qafellas.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {

    public Page page;
    public Locator signInBtn;
    public Locator emailBox;
    public Locator passwordBox;
    public Locator submitBtn;
    public Locator userProfileBtn;
    public Locator passwordErrorNotice;
    public Locator emailErrorNotice;
    public Locator generalError;

    // Locators
    public LoginPage(Page page) {
        this.page = page;
        this.signInBtn = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in"));
        this.emailBox = page.getByTestId("email-input");
        this.passwordBox = page.getByTestId("password-input");
        this.submitBtn = page.getByTestId("sign-in-button");
        this.userProfileBtn = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("profile"));
        this.passwordErrorNotice = page.getByTestId("password-error");
        this.emailErrorNotice = page.getByTestId("email-error");
        this.generalError = page.getByTestId("error-message");
    }

    //actions
    public void login(String email, String password){
        signInBtn.click();
        emailBox.click();
        emailBox.fill(email);
        passwordBox.click();
        passwordBox.fill(password);
        submitBtn.click();
    }


}

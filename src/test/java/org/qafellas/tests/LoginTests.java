package org.qafellas.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.qafellas.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.util.ArrayList;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTests {
    private static final Logger log = LoggerFactory.getLogger(LoginTests.class);
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    LoginPage loginPage;

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
        page.navigate("https://qafellas-estate.onrender.com/");
    }

    @AfterMethod
    public void closeContext() {
        context.close();
    }

    @Test
    public void shouldLoginSuccessfully() {
        loginPage.login("xyz@gmail.com", "X1234567@");
        assertThat(loginPage.userProfileBtn).isVisible();
    }

    @Test
    public void shouldGetInvalidPasswordNotification() {
        loginPage.login("xyz@gmail.com", "64rt646r46r46");
        assertThat(loginPage.passwordErrorNotice).containsText("Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character (including a dot).");
    }

    @Test
    public void shouldGetWarningWhenEmptyValuesEnteredForEmailPassword() {
        loginPage.signInBtn.click();
        loginPage.submitBtn.click();
        assertThat(loginPage.emailErrorNotice).containsText("Email is required");
        assertThat(loginPage.passwordErrorNotice).containsText("Password is required");
    }

    @Test
    public void shouldGetWarningOnNonExistEmail() {
        loginPage.login("ekjehfhfh@gmail.com", "X1234567@");
        assertThat(loginPage.generalError).containsText("User not found!");

    }


}

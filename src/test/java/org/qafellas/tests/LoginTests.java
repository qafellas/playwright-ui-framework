package org.qafellas.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.*;

import java.util.ArrayList;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTests {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeClass
    public void launchBrowser(){
        playwright = Playwright.create();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--enable-javascript-dialogs");
        arguments.add("--start-maximized");
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setChannel("chrome").setSlowMo(2000));
    }

    @AfterClass
    public void closeBrowser(){
        playwright.close();
    }

    @BeforeMethod
    public void createContextAndPage(){
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = context.newPage();
        page.navigate("https://qafellas-estate.onrender.com/");
    }

    @AfterMethod
    public void closeContext(){
        context.close();
    }

    @Test
    public void shouldLoginSuccessfully() {
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in")).click();
            page.getByTestId("email-input").click();
            page.getByTestId("email-input").fill("xyz@gmail.com");
            page.getByTestId("password-input").click();
            page.getByTestId("password-input").click();
            page.getByTestId("password-input").fill("X1234567@");
            page.getByTestId("sign-in-button").click();
            assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("profile"))).isVisible();
    }

    @Test
    public void shouldGetInvalidPasswordNotification() {
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in")).click();
            page.getByTestId("email-input").click();
            page.getByTestId("email-input").fill("xyz@gmail.com");
            page.getByTestId("password-input").click();
            page.getByTestId("password-input").fill("64rt646r46r46");
            page.getByTestId("sign-in-button").click();
            assertThat(page.getByTestId("password-error")).containsText("Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character (including a dot).");
    }

    @Test
    public void shouldGetWarningWhenEmptyValuesEnteredForEmailPassword(){
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in")).click();
            page.getByTestId("sign-in-button").click();
            assertThat(page.getByTestId("email-error")).containsText("Email is required");
            assertThat(page.getByTestId("password-error")).containsText("Password is required");
    }

    @Test
    public void shouldGetWarningOnNonExistEmail(){
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in")).click();
            page.getByTestId("password-input").click();
            page.getByTestId("password-input").fill("X1234567@");
            page.getByTestId("email-input").click();
            page.getByTestId("email-input").fill("ekjehfhfh@gmail.com");
            page.getByTestId("sign-in-button").click();
            assertThat(page.getByTestId("error-message")).containsText("User not found!");

    }


}

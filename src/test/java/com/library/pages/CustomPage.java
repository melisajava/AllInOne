package com.library.pages;

import com.library.utility.BrowserUtil;
import com.library.utility.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomPage extends LoginPage {

    LoginPage loginPage;

    public CustomPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//a[@id='navbarDropdown']/span")
    public WebElement profileName;


    public void login(String email, String password){
        loginPage.emailBox.sendKeys(email);
        loginPage.passwordBox.sendKeys(password);
        BrowserUtil.waitFor(3);
        loginPage.loginButton.click();
    }
}

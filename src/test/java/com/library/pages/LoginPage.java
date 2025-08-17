package com.library.pages;

import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "inputEmail")
    public WebElement emailBox;

    @FindBy(id = "inputPassword")
    public WebElement passwordBox;

    @FindBy(tagName = "button")
    public WebElement loginButton;

    @FindBy(xpath = "//a[@id='navbarDropdown']/span")
    public WebElement profileName;

    @FindBy(css = "#navbarDropdown>span")
    public WebElement accountHolderName;



    public void login(String userType){

        String username= ConfigurationReader.getProperty(userType+"_username");
        String password=ConfigurationReader.getProperty(userType+"_password");


        emailBox.sendKeys(username);
        passwordBox.sendKeys(password);
        loginButton.click();

        // Explicit Wait
        BrowserUtil.waitFor(3);

    }

    public void login(String email,String password){


        emailBox.sendKeys(email);
        passwordBox.sendKeys(password);
        loginButton.click();

        BrowserUtil.waitFor(5);

    }

    public void profileNamePlaceHolder() {
        Driver.getDriver().findElement(By.xpath("//a[@id='navbarDropdown']/span"));
        BrowserUtil.waitFor(2);

    }


}
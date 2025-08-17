package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class UIStepDefs extends BasePage {

    BasePage basePage = new BasePage();
    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();
    Map<String, Object> userFromDataBase;

    //US03/SCENARIO-2
    @And("I logged in Library UI as {string}")
    public void iLoggedInLibraryUIAs(String role) {
        loginPage.login(role);
    }

    @And("I navigate to {string} page")
    public void iNavigateToPage(String page) {
        basePage.navigateModule(page);
    }

    @And("UI, Database and API created book information must match")
    public void uiDatabaseAndAPICreatedBookInformationMustMatch() throws SQLException {
        String nameAPI = String.valueOf(APIStepDefs.randomDataMap.get("name"));
        String isbnAPI = String.valueOf(APIStepDefs.randomDataMap.get("isbn"));
        String yearAPI = String.valueOf(APIStepDefs.randomDataMap.get("year"));
        String authorAPI = String.valueOf(APIStepDefs.randomDataMap.get("author"));
        String bookCategoryIdAPI = String.valueOf(APIStepDefs.randomDataMap.get("book_category_id"));
        String descriptionAPI = String.valueOf(APIStepDefs.randomDataMap.get("description"));

        System.out.println("bookCategoryIdAPI = " + bookCategoryIdAPI);

        // ===== 1) UI: search and verify row contains expected fields =====
        bookPage.search.clear();
        bookPage.search.sendKeys(nameAPI + Keys.ENTER);
        BrowserUtil.waitFor(3);

        List<String> newBookRowsText = new ArrayList<>();
        for (WebElement each : bookPage.allRows) {
            newBookRowsText.add(each.getText());
        }
        String uiTableText = String.join("\n", newBookRowsText);
        System.out.println("uiTableText = " + uiTableText);
        System.out.println("UI table (PASSED):\n" + uiTableText);

        // Minimal checks (keep your assert & add a few more, still "contains" style)
        assertTrue(uiTableText.contains(nameAPI));
        assertTrue(uiTableText.contains(authorAPI));
        assertTrue(uiTableText.contains(isbnAPI));
        assertTrue(uiTableText.contains(yearAPI));

        // ===== 2) DB: fetch the just-created row by ISBN and verify fields =====
        DB_Util.createConnection();
        String query = "SELECT id, name, isbn, author, year, book_category_id, " +
                "description FROM books WHERE isbn =" + isbnAPI;

        DB_Util.runQuery(query);
        System.out.println("query = " + query);

        String allRowAsListOfMap = DB_Util.getAllRowAsListOfMap().toString();
        System.out.println("allRowAsListOfMap = " + allRowAsListOfMap);

        String dataBaseName = DB_Util.getCellValue(1, "name");
        String dataBaseISBN = DB_Util.getCellValue(1, "isbn");
        String dataBaseAuthor = DB_Util.getCellValue(1, "author");
        String dataBaseYear = DB_Util.getCellValue(1, "year");
        String dataBaseCatID = DB_Util.getCellValue(1, "book_category_id");
        String dataBaseDesc = DB_Util.getCellValue(1, "description");

        assertEquals(nameAPI, dataBaseName);
        assertEquals(isbnAPI, dataBaseISBN);
        assertEquals(authorAPI, dataBaseAuthor);
        assertEquals(yearAPI, dataBaseYear);
        assertEquals(bookCategoryIdAPI, dataBaseCatID);
        assertEquals(descriptionAPI, dataBaseDesc);


    }


//    String fullNameAPI = (String.valueOf(APIStepDefs.randomDataMap.get("full_name"))).toString();
//    String emailAPI = (String.valueOf(APIStepDefs.randomDataMap.get("email"))).toString();
//    Object passwordAPI = DB_Util.convertingPassword(APIStepDefs.randomDataMap.get("password").toString());
//    String userGroupIdAPI = (String.valueOf(APIStepDefs.randomDataMap.get("user_group_id"))).toString();
//    String statusAPI = (String.valueOf(APIStepDefs.randomDataMap.get("status"))).toString();
//    String startDateAPI = (String.valueOf(APIStepDefs.randomDataMap.get("start_date"))).toString();
//    String endDateAPI = (String.valueOf(APIStepDefs.randomDataMap.get("end_date"))).toString();
//    String addressAPI = (String.valueOf(APIStepDefs.randomDataMap.get("address"))).toString();


    @And("created user information should match with Database")
    public void createdUserInformationShouldMatchWithDatabase() {

        System.out.println("APIStepDefs.userId = " + APIStepDefs.userId);

        String query = "select * from users where id =" + APIStepDefs.userId;

        DB_Util.runQuery(query);
        System.out.println("query = " + query);

        userFromDataBase = DB_Util.getRowMap(1);
        System.out.println("userFromDataBase = " + userFromDataBase);


        assertEquals(userFromDataBase.get("full_name"),APIStepDefs.randomDataMap.get("full_name"));
        assertEquals(userFromDataBase.get("email"),APIStepDefs.randomDataMap.get("email"));
        assertEquals(userFromDataBase.get("password"), DB_Util.convertingPassword(APIStepDefs.randomDataMap.get("password").toString()));
        assertEquals(userFromDataBase.get("user_group_id"),APIStepDefs.randomDataMap.get("user_group_id"));
        assertEquals(userFromDataBase.get("status"),APIStepDefs.randomDataMap.get("status"));
        assertEquals(userFromDataBase.get("start_date"),APIStepDefs.randomDataMap.get("start_date"));
        assertEquals(userFromDataBase.get("end_date"),APIStepDefs.randomDataMap.get("end_date"));
        assertEquals(userFromDataBase.get("address"),APIStepDefs.randomDataMap.get("address"));

//        assertEquals(userFromDataBase.get("full_name"), fullNameAPI);
//        assertEquals(userFromDataBase.get("email"), emailAPI);
//        assertEquals(userFromDataBase.get("password"), passwordAPI);
//        assertEquals(userFromDataBase.get("user_group_id"), userGroupIdAPI);
//        assertEquals(userFromDataBase.get("status"), statusAPI);
//        assertEquals(userFromDataBase.get("start_date"), startDateAPI);
//        assertEquals(userFromDataBase.get("end_date"), endDateAPI);
//        assertEquals(userFromDataBase.get("address"), addressAPI);

    }


    @And("created user should be able to login Library UI")
    public void createdUserShouldBeAbleToLoginLibraryUI() {

//        loginPage.login(emailAPI, userFromDataBase.get("password").toString());
//        System.out.println("User successfully logged in !!!");
//        System.out.println("emailAPI = " + emailAPI);
//        System.out.println("passwordAPI = " + userFromDataBase.get("password"));


        String userName = userFromDataBase.get("email").toString();
        String password = APIStepDefs.randomDataMap.get("password").toString();

        loginPage.login(userName, password);
        System.out.println("userName = " + userName);
        System.out.println("password = " + password);


    }

    @And("created user name should appear in Dashboard Page")
    public void createdUserNameShouldAppearInDashboardPage() {

        assertEquals(loginPage.accountHolderName.getText(), userFromDataBase.get("full_name").toString());

        String fullName = userFromDataBase.get("full_name").toString();
        System.out.println("fullName = " + fullName);
        //System.out.println("fullNameAPI = " + fullNameAPI);
       


    }
}



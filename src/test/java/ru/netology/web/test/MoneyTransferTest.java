package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationInfo = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationInfo);
    }

    @Test
    void shouldTransferFromFirstCard (){
        val dashboardPage = new DashboardPage();
        int amount = 11000;
        val balanceFirstCardBeforeTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBeforeTransfer = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondCardDeposit();
        transferPage.transferMoney(amount,DataHelper.getFirstCardNumber());
        val balanceFirstCardAfterTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfterTransfer = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstCardBeforeTransfer - amount), balanceFirstCardAfterTransfer);
        assertEquals((balanceSecondCardBeforeTransfer + amount), balanceSecondCardAfterTransfer);
    }
    @Test
    void shouldTransferFromSecondCard () {
        val dashboardPage = new DashboardPage();
        int amount = 1000;
        val balanceFirstCardBeforeTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBeforeTransfer = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardDeposit();
        transferPage.transferMoney(amount, DataHelper.getSecondCardNumber());
        val balanceFirstCardAfterTransfer = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfterTransfer = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstCardBeforeTransfer + amount), balanceFirstCardAfterTransfer);
        assertEquals((balanceSecondCardBeforeTransfer - amount), balanceSecondCardAfterTransfer);
    }
}
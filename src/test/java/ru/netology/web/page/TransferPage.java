package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public TransferPage() {
    }

    public DashboardPage transferMoney(int amountTransfer, DataHelper.CardInfo cardInfo) {
        amountField.setValue(String.valueOf(amountTransfer));
        fromField.setValue(cardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void successTransfer() {
        $(withText("Ваши карты")).shouldBe(Condition.visible);
    }

    public void failedTransfer() {
        $(withText("Недостаточно средств для перевода!")).shouldBe(Condition.visible);
    }

}
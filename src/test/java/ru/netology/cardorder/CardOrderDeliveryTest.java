package ru.netology.cardorder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.generator.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardOrderDeliveryTest {

    final DataGenerator generator = new DataGenerator();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldMakeBookingAndReturnSuccessMessage(){
        $("[data-test-id='city'] input").setValue(generator.cityGenerate());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(generator.dateGenerate());
        $("[data-test-id='name'] input").setValue(generator.nameGenerate());
        $("[data-test-id='phone'] input").setValue(generator.phone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id='success-notification']").shouldHave(text("Успешно"));
    }

    @Test
    void shouldReturnMessageToChangeBookingIfTheSameData(){
        $("[data-test-id='city'] input").setValue(generator.cityGenerate());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(generator.dateGenerate());
        $("[data-test-id='name'] input").setValue(generator.nameGenerate());
        $("[data-test-id='phone'] input").setValue(generator.phone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(withText("Успешно")).shouldBe(visible);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(generator.dateGenerate());
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldHave(text("У вас уже запланирована " +
                "встреча на другую дату. Перепланировать?"));
        $(".notification_visible .button").click();
        $(withText("Успешно")).shouldBe(visible);

    }

    @Test
    void shouldReturnAlertMessageIfWrongCity(){
        $("[data-test-id='city'] input").setValue("Лондон");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE, generator.dateGenerate());
        $("[data-test-id='date'] input").sendKeys(generator.dateGenerate());
        $("[data-test-id='name'] input").setValue(generator.nameGenerate());
        $("[data-test-id='phone'] input").setValue("+79056487564");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldReturnAlertMessageIfDateIsBeforeThreeDays(){
        $("[data-test-id='city'] input").setValue(generator.cityGenerate());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys("04.08.2020");
        $("[data-test-id='name'] input").setValue(generator.nameGenerate());
        $("[data-test-id='phone'] input").setValue("+79056487564");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(" [data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldReturnAlertMessageIfNameInEnglish(){
        $("[data-test-id='city'] input").setValue(generator.cityGenerate());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(generator.dateGenerate());
        $("[data-test-id='name'] input").setValue("Ivan");
        $("[data-test-id='phone'] input").setValue(generator.phone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(" [data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия " +
                "указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldChangeColorOfCheckboxMessageIfNotChecked() {
        $("[data-test-id='city'] input").setValue(generator.cityGenerate());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(generator.dateGenerate());
        $("[data-test-id='name'] input").setValue(generator.nameGenerate());
        $("[data-test-id='phone'] input").setValue(generator.phone());
        //$(".checkbox__box").click();
        $(".button").click();
        $(".input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки " +
                "и использования моих персональных данных"));
    }
}

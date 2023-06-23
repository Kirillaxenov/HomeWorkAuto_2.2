package org.example;

import com.codeborne.selenide.Configuration;

import com.codeborne.selenide.selector.ByText;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTests {
    LocalDate currentDate = LocalDate.now();
    LocalDate futureDate = currentDate.plusDays(4);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";

        open("http://localhost:9999/");
    }


    @Test
    void shouldSomethingTest() {

        $("[data-test-id=city] input").setValue("Белгород");
        String formattedDate = futureDate.format(formatter);
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $("[data-test-id=name] input").setValue("Рогов Вася");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + formattedDate), Duration.ofSeconds(14)).shouldBe(visible);


    }

    @Test
    void CityWrongTest() {

        $("[data-test-id=city] input").setValue("Шебекино");
        String formattedDate = futureDate.format(formatter);
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $("[data-test-id=name] input").setValue("Рогов Вася");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=city].input_invalid .input__sub ").shouldHave(text("Доставка в выбранный город недоступна"));


    }

    @Test
    void NotDateTest() {
        $("[data-test-id=city] input").setValue("Белгород");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Рогов Вася");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=date] span.input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"));
    }

    @Test
    void WrongNameTest() {

        $("[data-test-id=city] input").setValue("Белгород");
        String formattedDate = futureDate.format(formatter);
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $("[data-test-id=name] input").setValue("John Wic");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=name] span.input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void WrongPhoneTest() {

        $("[data-test-id=city] input").setValue("Белгород");
        String formattedDate = futureDate.format(formatter);
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $("[data-test-id=name] input").setValue("Рогов Вася");
        $("[data-test-id=phone] input").setValue("+7999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=phone] span.input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));


    }

    @Test
    void NoneCheckboxTest() {
        $("[data-test-id=city] input").setValue("Белгород");
        String formattedDate = futureDate.format(formatter);
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $("[data-test-id=name] input").setValue("Рогов Вася");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(By.className("button")).click();
        $("[data-test-id=agreement] .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

    @Test
    void CitySelectionMenu() {

        $("[data-test-id=city] input").setValue("Мо");
        $(new ByText("Смоленск")).click();
        String formattedDate = futureDate.format(formatter);
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $("[data-test-id=name] input").setValue("Рогов Вася");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + formattedDate), Duration.ofSeconds(14)).shouldBe(visible);


    }


}



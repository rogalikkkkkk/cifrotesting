import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class Example {
    public static void main(String[] args) {
        // Открытие страницы
        open("https://example.com");

        // Ожидание появления кнопки и клик по ней
        $("#myButton").shouldBe(visible).click();
    }
}



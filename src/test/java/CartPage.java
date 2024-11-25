import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CartPage {
    public List<String> getAllCartSummaryTexts() {
        return $$x("//*[@id=\"checkoutContent\"]/div/div[2]/div/div")
                .first()
                .shouldBe(visible)
                .lastChild()
                .$$("div")
                .texts();
    }

    public SelenideElement firstResearch() {
        return $x("//*[@id=\"checkoutContent\"]/div/div[2]/div/div[1]/div[1]/div/div[2]/div/div[2]/div[3]");
    }

    public void changeRegionOnMoscow() {
        $x("//*[@id=\"__nuxt\"]/div/div[2]/menu/li[1]").scrollIntoView(false);
        sleep(500);
        $x("//*[@id=\"__nuxt\"]/div/div[2]/menu/li[1]").shouldBe(visible).click();
        $x("//*[@id=\"__nuxt\"]/div/div[8]/div/div/div/div[7]/div[2]").shouldBe(visible).click();
    }

    public void clearAndCheckCart() {
        // Clear cart
        $x("//*[@id=\"checkoutContent\"]/div/div[1]/div[2]/button[1]").scrollIntoView(false);
        // Wait for scroll
        sleep(500);
        $x("//*[@id=\"checkoutContent\"]/div/div[1]/div[2]/button[1]").shouldBe(visible).click();

        // Check if cart is empty
        $x("//*[@id=\"cart-page\"]/div/div[1]").shouldBe(visible).shouldHave(text("Корзина пуста"));
    }
}

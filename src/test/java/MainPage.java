import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    public MainPage open() {
        Selenide.open("/");
        return this;
    }

    public void closeNoSuchRegionDialog() {
        try {
            $x("//*[@id=\"__nuxt\"]/div/div[8]/button").shouldBe(visible, Duration.ofSeconds(1)).click();
        } catch (ElementNotFound _) {
        }
    }

    public void scrollToBottom() {
        $x("//*[@id=\"__nuxt\"]/div/div[5]").scrollTo();
    }

    public void addResearchByLabel(String label, String research) {
        // Scroll to selected label
        SelenideElement el = $$("h1").find(text(label));
        el.shouldBe(visible).scrollTo();

        // Wait browser scroll to this element
        sleep(500);

        // Find all cards in selected block
        ElementsCollection discountElements = el.parent().shouldBe(visible).$$(".service-card");

        // Find needed element and add it to cart
        SelenideElement selectedCard = discountElements.find(text(research)).shouldBe(visible);
        selectedCard.$$(".u-button--block").first().shouldBe(visible).click();

        // Check if button text switch to "in cart"
        selectedCard.$$(".u-button--block").first().shouldHave(text("В корзине"));
    }

    public void goToCart() {
        $x("//*[@id=\"__nuxt\"]/div/div[3]/div[1]/div/div[2]/a").shouldBe(visible).click();
    }

    public SelenideElement input() {
        return $x("//*[@id=\"app-header-search-dropdown__input-pc\"]");
    }

    public void searchResearchInInputAndAddToCart(String researchLabel) {
        input().shouldBe(visible).setValue(researchLabel);

        input().parent().parent()
                .$("div .app-header-search-result__cart")
                .shouldBe(visible)
                .$$(".app-header-search-result__cart-icon")
                .last()
                .shouldBe(visible)
                .click();

        input().shouldBe(visible).clear();
    }
}

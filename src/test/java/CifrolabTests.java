import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CifrolabTests {

    @BeforeAll
    static void setup() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://1lab.plus/";
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.fastSetValue = true;
//        Configuration.holdBrowserOpen = true;

    }

    @Test
    void testAddingResearchesFromMainPage() {
        MainPage mainPage = new MainPage();

        mainPage.open().closeNoSuchRegionDialog();


        final List<String> researchesLabel = List.of("ТТГ (тиреотропный гормон)", "АСТ (аспартатаминотрансфераза)", "ПЦР-6");

        // Scroll to bottom for loading page content
        mainPage.scrollToBottom();


        // Find ТТГ and add it to cart
        mainPage.addResearchByLabel("Анализы со скидкой", researchesLabel.getFirst());

        // Find АСТ and add it to cart
        mainPage.addResearchByLabel("Популярные анализы", researchesLabel.get(1));

        // Find ПЦР and add it to cart
        mainPage.addResearchByLabel("Комплексные анализы", researchesLabel.get(2));


        // Go to cart page
        mainPage.goToCart();

        CartPage cartPage = new CartPage();

        // Select summary div and check that all researches are in it
        assertTrue(cartPage.getAllCartSummaryTexts().containsAll(researchesLabel));

        cartPage.clearAndCheckCart();
    }

    @Test
    void testAddingResearchesFromInput() {
        MainPage mainPage = new MainPage();
        mainPage.open().closeNoSuchRegionDialog();

        final List<String> researchesLabel = List.of("СОЭ (капиллярная кровь)",
                "Комплексный анализ крови на витамины группы D (25-ОН D2/ 25-ОН D3/ 1,25-ОН D3/ 24,25-ОН D3)",
                "Билирубин общий");

        // Search all researches and add it to cart
        mainPage.searchResearchInInputAndAddToCart(researchesLabel.getFirst());
        sleep(1000);

        mainPage.searchResearchInInputAndAddToCart(researchesLabel.get(1));
        sleep(1000);

        mainPage.searchResearchInInputAndAddToCart(researchesLabel.get(2));
        sleep(1000);

        // Go to cart page
        mainPage.goToCart();

        CartPage cartPage = new CartPage();

        // Check if all researches are in cart
        assertTrue(cartPage.getAllCartSummaryTexts().containsAll(researchesLabel));

        cartPage.clearAndCheckCart();
    }

    @Test
    void testCartWithChangingRegionAndClearing() {
        MainPage mainPage = new MainPage();
        mainPage.open().closeNoSuchRegionDialog();
        mainPage.scrollToBottom();

        // Find ТТГ and add it to cart
        mainPage.addResearchByLabel("Анализы со скидкой", "ТТГ (тиреотропный гормон)");

        // Go to cart page
        mainPage.goToCart();

        CartPage cartPage = new CartPage();

        // Check price on current region
        cartPage.firstResearch().shouldHave(text("382 ₽"));

        // Switch region on Moscow
        cartPage.changeRegionOnMoscow();

        // Check price on Moskow
        cartPage.firstResearch().shouldHave(text("444 ₽"));

        cartPage.clearAndCheckCart();
    }

}

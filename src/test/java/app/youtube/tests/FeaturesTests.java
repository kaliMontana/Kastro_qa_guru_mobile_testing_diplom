package app.youtube.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static app.youtube.helpers.Attach.attachAsText;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;


public class FeaturesTests extends TestBase {
	private final String SEARCHED_WORD = "Appium";


	@Test
	@Tag("Search")
	public void searchFeatureTest() {
		step("Type search", () -> {
			$(AppiumBy.id("com.google.android.youtube:id/menu_item_1")).shouldBe(enabled, Duration.ofMillis(7000)).click();
			$(AppiumBy.id("com.google.android.youtube:id/search_edit_text")).sendKeys(SEARCHED_WORD);
		});
		step("Verify the found result's quantity", () ->
				$$(AppiumBy.id("com.google.android.youtube:id/text"))
						.shouldHave(CollectionCondition.sizeGreaterThan(0))
		);
		step("Verify existence of searched word in the result set", () -> {
			SoftAssertions softAssertions = new SoftAssertions();

			$$(AppiumBy.id("com.google.android.youtube:id/text")).forEach(x -> softAssertions.assertThat(x.getText())
					.as("Result element does not contains searched word")
					.containsIgnoringCase(SEARCHED_WORD));

			softAssertions.assertAll();
		});
	}

	@Test
	@Tag("Open")
	public void openVideoFeatureTest() {
		final String[] videoTitleFromList = new String[1];
		final String[] videoTitle = new String[1];

		step("Type search", () -> {
			$(AppiumBy.id("com.google.android.youtube:id/menu_item_1")).shouldBe(enabled, Duration.ofMillis(7000)).click();
			$(AppiumBy.id("com.google.android.youtube:id/search_edit_text")).sendKeys(SEARCHED_WORD);
		});
		step("Click on the first result", () -> {
			$$(AppiumBy.id("com.google.android.youtube:id/text")).first().shouldBe(enabled, Duration.ofMillis(4000)).click();
		});
		step("Verify existence of videos in the result set", () -> {
			$$(AppiumBy.className("android.view.ViewGroup"))
					.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofMillis(4000));
		});
		step("Get the title of first video", () -> {
			videoTitleFromList[0] = $$(AppiumBy.className("android.view.ViewGroup")).get(1)
					.shouldBe(visible, Duration.ofMillis(8000)).getAttribute("content-desc");

			attachAsText("Video's title from list", videoTitleFromList[0]);
		});
		step("Click on the first video", () -> {
			$$(AppiumBy.className("android.view.ViewGroup")).get(1).click();
		});
		step("expand video description", () -> {
			$$(AppiumBy.className("android.view.ViewGroup"))
					.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofMillis(8000));

			$$(AppiumBy.className("android.view.ViewGroup")).get(7).click();
			$$(AppiumBy.className("android.view.ViewGroup")).get(7).click();

			videoTitle[0] = $$(AppiumBy.className("android.view.ViewGroup")).get(7)
					.shouldHave(Condition.attribute("content-desc")).getAttribute("content-desc");
		});
		step("Compare the title of the opened and clicked video", () -> {
			Assertions.assertThat(videoTitleFromList[0])
					.as("Video's Title not are the same")
					.contains(videoTitle[0]);
		});
	}

	@Test
	@Tag("Notifications")
	public void checkNotificationsFeatureTest() {
		step("Check 'Notifications' feature", () -> {
			$(AppiumBy.id("com.google.android.youtube:id/menu_item_0")).shouldBe(enabled, Duration.ofMillis(8000)).click();

			$(AppiumBy.className("android.widget.TextView")).shouldBe(enabled, Duration.ofMillis(8000))
					.shouldHave(Condition.text("Уведомления"));
			$$(AppiumBy.className("android.view.ViewGroup")).shouldHave(CollectionCondition.sizeGreaterThan(0));
		});
	}
}

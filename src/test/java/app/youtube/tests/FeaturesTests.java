package app.youtube.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static app.youtube.appPages.NotificationPage.notificationIconElement;
import static app.youtube.appPages.NotificationPage.titleLabelElement;
import static app.youtube.appPages.ResultPage.videoElements;
import static app.youtube.appPages.ResultPage.videoResultListElement;
import static app.youtube.appPages.SearchPage.*;
import static app.youtube.dataProviders.SearchedWords.NOTIFICATION_TITLE;
import static app.youtube.dataProviders.SearchedWords.SEARCHED_WORD;
import static app.youtube.helpers.Attach.attachAsText;
import static app.youtube.helpers.Waiting.*;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static io.qameta.allure.Allure.step;

@Owner("Kastro B.")
@Story("Youtube features")
public class FeaturesTests extends TestBase {


	@Test
	@Tag("Search")
	@Feature("Search")
	@DisplayName("Check search a video")
	public void searchFeatureTest() {
		step("Type search", () -> {
			searchIconElement.shouldBe(enabled, Duration.ofSeconds(SEVEN_SEC.getValue())).click();

			searchEditTextElement.sendKeys(SEARCHED_WORD.getValue());
		});

		step("Verify the found result's quantity", () ->
				videoListElement.shouldHave(CollectionCondition.sizeGreaterThan(0))
		);

		step("Verify existence of searched word in the result set", () -> {
			SoftAssertions softAssertions = new SoftAssertions();

			videoListElement.forEach(x -> softAssertions.assertThat(x.getText())
					.as("Result element does not contains searched word")
					.containsIgnoringCase(SEARCHED_WORD.getValue()));

			softAssertions.assertAll();
		});
	}

	@Test
	@Tag("Open")
	@Feature("Open video")
	@DisplayName("Check open video")
	public void openVideoFeatureTest() {
		final String[] videoTitleFromList = new String[1];
		final String[] videoTitle = new String[1];

		step("Type search", () -> {
			searchIconElement.shouldBe(enabled, Duration.ofSeconds(SEVEN_SEC.getValue())).click();

			searchEditTextElement.sendKeys(SEARCHED_WORD.getValue());
		});

		step("Click on the first result", () -> {
			videoListElement.first().shouldBe(enabled, Duration.ofSeconds(FOUR_SEC.getValue())).click();
		});

		step("Verify existence of videos in the result set", () -> {
			videoResultListElement.shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(FOUR_SEC.getValue()));
		});

		step("Get the title of first video", () -> {
			videoTitleFromList[0] = videoResultListElement.first()
					.shouldBe(visible, Duration.ofSeconds(TEN_SEC.getValue())).getText();

			attachAsText("Video's title from list", videoTitleFromList[0]);
		});

		step("Click on the first result from list", () -> {
			videoElements.get(1).click();
		});

		step("Click on the first video", () -> {
			videoResultListElement.get(6).shouldBe(visible, Duration.ofSeconds(EIGHT_SEC.getValue())).click();

			videoTitle[0] = videoElements.get(7)
					.shouldHave(Condition.attribute("content-desc"), Duration.ofSeconds(EIGHT_SEC.getValue())).getAttribute("content-desc");
		});

		step("Check that the title of the opened videos contains searched word", () -> {
			Assertions.assertThat(videoTitle[0])
					.as("Video's Titles not are the same")
					.containsIgnoringCase(videoTitleFromList[0]);
		});
	}

	@Test
	@Tag("Notifications")
	@Feature("Notifications")
	@DisplayName("Check notifications")
	public void checkNotificationsFeatureTest() {
		step("Check 'Notifications' feature", () -> {
			notificationIconElement.shouldBe(enabled, Duration.ofSeconds(EIGHT_SEC.getValue())).click();

			titleLabelElement.shouldBe(enabled, Duration.ofSeconds(EIGHT_SEC.getValue())).shouldHave(Condition.text(NOTIFICATION_TITLE.getValue()));
			videoResultListElement.shouldHave(CollectionCondition.sizeGreaterThan(0));
		});
	}
}

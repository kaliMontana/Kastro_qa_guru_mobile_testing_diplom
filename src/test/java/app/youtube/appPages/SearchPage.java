package app.youtube.appPages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchPage {
	public static SelenideElement searchIconElement = $(AppiumBy.id("com.google.android.youtube:id/menu_item_1"));
	public static SelenideElement searchEditTextElement = $(AppiumBy.id("com.google.android.youtube:id/search_edit_text"));
	public static ElementsCollection videoListElement = $$(AppiumBy.id("com.google.android.youtube:id/text"));
}
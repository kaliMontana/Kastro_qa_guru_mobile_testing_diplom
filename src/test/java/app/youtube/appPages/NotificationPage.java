package app.youtube.appPages;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.Selenide.$;

public class NotificationPage {
	public static SelenideElement notificationIconElement = $(AppiumBy.id("com.google.android.youtube:id/menu_item_0"));
	public static SelenideElement titleLabelElement = $(AppiumBy.className("android.widget.TextView"));
}
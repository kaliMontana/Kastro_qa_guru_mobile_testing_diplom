package app.youtube.appPages;

import com.codeborne.selenide.ElementsCollection;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.Selenide.$$;

public class ResultPage {
	public static ElementsCollection videoElements = $$(AppiumBy.className("android.view.ViewGroup"));
	public static ElementsCollection videoResultListElement = $$(AppiumBy.className("android.widget.TextView"));
}
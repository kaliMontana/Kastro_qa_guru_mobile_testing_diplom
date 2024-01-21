package app.youtube.dataProviders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SearchedWords {
	SEARCHED_WORD("Appium"),
	NOTIFICATION_TITLE("Уведомления");

	private final String value;
}

package app.youtube.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Waiting {
	FOUR_SEC(
			4
	),
	SEVEN_SEC(
			7
	),
	EIGHT_SEC(
			8
	),
	TEN_SEC(
			10
	);

	private final long value;
}

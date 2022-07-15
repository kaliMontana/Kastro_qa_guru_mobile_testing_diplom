package app.youtube.drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

public class MobileDriver implements WebDriverProvider {

	public static URL getAppiumServerUrl() {
		try {
			return new URL("http://localhost:4723/wd/hub");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public WebDriver createDriver(Capabilities capabilities) {
		File app = getApp();

		UiAutomator2Options options = new UiAutomator2Options();
		options.merge(capabilities);
		options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
		options.setPlatformName("Android");
		options.setDeviceName("RZ8M81PZPVP");
		options.setPlatformVersion("11");
		options.setApp(app.getAbsolutePath());
		options.setAppPackage("com.google.android.youtube");
		options.setAppActivity("com.google.android.youtube.HomeActivity");

		return new AndroidDriver(getAppiumServerUrl(), options);
	}

	private File getApp() {
		String appUrl = "https://www.apklinker.com/apk/google-inc/youtube/youtube-17-26-34-release/"
				+ "youtube-17-26-34-android-apk-download/start/";
		String appPath = "src/test/resources/apps/com.google.android.youtube_17.26.34_1530254784_MinAPI23_"
				+ "(arm64-v8a,armeabi-v7a,x86,x86_64)(nodpi)_apklinker.com.apk";

		File app = new File(appPath);
		if (!app.exists()) {
			try (InputStream in = new URL(appUrl).openStream()) {
				copyInputStreamToFile(in, app);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				throw new AssertionError("Failed to download application", e);
			}
		}

		return app;
	}
}

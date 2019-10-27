package helpers;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class StrokeOrderHelper {
	static String baseUrl =
			"https://raw.githubusercontent.com/nmarley/chinese-char-animations/master/images/";

	@SneakyThrows
	public static void downloadImage(String path, String text) {
		String code = String.format("U+{0:x4}", Character.getNumericValue(text.charAt(0)))
				.replace("U+", "");
		String url = Paths.get(baseUrl).resolve(code + ".gif").toString();
		FileUtils.copyURLToFile(new URL(url), new File(path));
	}
}

package helpers;

import lombok.SneakyThrows;
import models.AnkiItem;
import models.FieldList;
import models.MediaInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeneralHelper {
	public static Map<String, String> EXTENSION_TAG = new HashMap<String, String>() {
		{
			put(".wav", "[sound:{0}]");
			put(".gif", "<img src=\"{0}\"/>");
		}
	};
//	public final static String CSS_MAIN = ".card {\\n font-family: arial;\\n font-size: 20px;\\n text-align: center;\\n color: black;\\n background-color: white;\\n}";

	static String joinFields(String[] list) {
		return list != null ? String.join("\u001f", list) : null;
	}

	static String[] splitFields(String fields) {
		return fields != null ? fields.split("\\x1f", -1) : null;
	}

	public static String joinFields2(String[] list) {
		StringBuilder result = new StringBuilder(128);
		for (int i = 0; i < list.length - 1; i++) {
			result.append(list[i]).append("\u001f");
		}
		if (list.length > 0) {
			result.append(list[list.length - 1]);
		}
		return result.toString();
	}

	public static String jsonToString(String json) {
		return json.replaceAll("\\\\/", "/");
	}

	public static String concatFields(	FieldList flds,
										AnkiItem item,
										String separator,
										MediaInfo info) {
		List<String> matchedFields = flds.stream()
				.map(el -> String.valueOf(item.get(el.getName())))
				.collect(Collectors.toList());
		if (info != null) {
			int indexOfField = matchedFields.indexOf(item.get(info.getField()).toString());
			if (indexOfField != -1) {
				matchedFields
						.add(	indexOfField,
								Optional.ofNullable(matchedFields.get(indexOfField)).orElse("")
									+ String.format(EXTENSION_TAG.get(info.getExtension()),
													matchedFields.get(0) + info.getExtension()));
			}
		}
		return String.join(separator, matchedFields);
	}

	@SneakyThrows
	public static String readResource(String path) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try (InputStream is = classLoader.getResourceAsStream(path)) {
			if (is == null)
				return null;
			try (InputStreamReader isr = new InputStreamReader(is);
					BufferedReader reader = new BufferedReader(isr)) {
				return reader.lines().collect(Collectors.joining(System.lineSeparator()));
			}
		}
	}

	@SneakyThrows
	public static String checkSum(String sfld) {
		return String.valueOf(fieldChecksum(sfld));
	}

	@SneakyThrows
	static Long fieldChecksum(String data) {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		byte[] digest = md.digest(data.getBytes("UTF-8"));
		BigInteger biginteger = new BigInteger(1, digest);
		String result = biginteger.toString(16);
		if (result.length() < 40) {
			String zeroes = "0000000000000000000000000000000000000000";
			result = zeroes.substring(0, zeroes.length() - result.length()) + result;
		}
		return Long.valueOf(result.substring(0, 8), 16);
	}

	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

    public static long getDayStart() {
        OffsetDateTime dateOffset = OffsetDateTime.now();
        dateOffset = dateOffset.minusHours(4);
        dateOffset = OffsetDateTime.of(	dateOffset.getYear(),
                                        dateOffset.getMonthValue(),
                                        dateOffset.getDayOfMonth(),
                                        0,
                                        0,
                                        0,
                                        0,
                                        dateOffset.getOffset());
        dateOffset = dateOffset.plusHours(4);
        return dateOffset.toEpochSecond();

    }
}

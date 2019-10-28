import helpers.GeneralHelper;
import models.AnkiItem;

import static org.junit.Assert.assertTrue;

public class AnkiTest {

	private static final String _PATH_TO_APKG_FILE_ =
			"C:\\Users\\Robert\\Documents\\Java\\ankireader4j\\src\\test\\java\\test.apkg";
	private static final String _NAME_OF_ANKI_PACKAGE_ = "test";
	static String _PATH_FOR_ANKI_FILE_ =
			"C:\\Users\\Robert\\Documents\\Java\\ankireader4j\\src\\test\\java";

	@org.junit.Test
	public void createBasicApkgFile() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.addItem("לְהַנִּיחַ", "я полагаю (предполагать)");
		test.addItem("להעריך", "оценить, рассчитывать");
		test.addItem("להרהר", "размышлять, медитировать");
		test.addItem("מחקר", "исследование");
		test.addItem("לדמיין", "воображать");
		test.addItem("דמיון יוצר", "творческое воображение");
		test.addItem("סביר יותר", "наиболее вероятно");
		test.addItem("דמות", "персонаж образ");
		test.addItem("סבירות נמוכה בהצלחה", "Низкая вероятность успеха");
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
	}

	@org.junit.Test
	public void createApkgFileWithFields() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setFields("Hebrew", "Russian", "English");
		test.addItem("לְהַנִּיחַ", "я полагаю (предполагать)", "");
		test.addItem("להעריך", "оценить, рассчитывать", "");
		test.addItem("להרהר", "размышлять, медитировать", "");
		test.addItem("מחקר", "исследование", "");
		test.addItem("לדמיין", "воображать", "");
		test.addItem("דמיון יוצר", "творческое воображение", "");
		test.addItem("סביר יותר", "наиболее вероятно", "");
		test.addItem("דמות", "персонаж образ", "");
		test.addItem("סבירות נמוכה בהצלחה", "Низкая вероятность успеха", "");
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
		//check file and delete
	}

	@org.junit.Test
	public void createApkgFileHtml() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setCss("");
		test.addItem("Design a deck of cards", GeneralHelper.readResource("file2.html"));
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
	}

	@org.junit.Test
	public void createApkgFileWithCss() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setCss("");
		test.addItem("לְהַנִּיחַ", "я полагаю (предполагать)");
		test.addItem("להעריך", "оценить, рассчитывать");
		test.addItem("להרהר", "размышлять, медитировать");
		test.addItem("מחקר", "исследование");
		test.addItem("לדמיין", "воображать");
		test.addItem("דמיון יוצר", "творческое воображение");
		test.addItem("סביר יותר", "наиболее вероятно");
		test.addItem("דמות", "персонаж образ");
		test.addItem("סבירות נמוכה בהצלחה", "Низкая вероятность успеха");
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
		//check file and delete
	}

	@org.junit.Test
	public void createApkgFileHint() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setFields("Front", "hint:Hint", "Back");
		test.setFormat("{0} - {1} \\n<hr id=answer(.*?)>\\n {2}");
		test.addItem("好的", "ok", "d'accord");
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
		//check file and delete
	}

	@org.junit.Test
	public void createApkgFileWithFormat() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setFields("English", "Spanish", "French");
		//Everything before '<hr id=answer>' is the front of the card, everything after is the behind
		test.setFormat("{0} - {1} \\n<hr id=answer>\\n {2}");
		test.addItem("לְהַנִּיחַ", "я полагаю (предполагать)", "");
		test.addItem("להעריך", "оценить, рассчитывать", "");
		test.addItem("להרהר", "размышлять, медитировать", "");
		test.addItem("מחקר", "исследование", "");
		test.addItem("לדמיין", "воображать", "");
		test.addItem("דמיון יוצר", "творческое воображение", "");
		test.addItem("סביר יותר", "наиболее вероятно", "");
		test.addItem("דמות", "персонаж образ", "");
		test.addItem("סבירות נמוכה בהצלחה", "Низкая вероятность успеха", "");
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
		//check file and delete
	}

	@org.junit.Test
	public void createApkgFileFromFile() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_, new ApkgFile(_PATH_TO_APKG_FILE_));
		// Be careful, keep the same format !
		test.addItem("לְהַנִּיחַ", "я полагаю (предполагать)", "");
		test.addItem("להעריך", "оценить, рассчитывать", "");
		test.addItem("להרהר", "размышлять, медитировать", "");
		test.addItem("מחקר", "исследование", "");
		test.addItem("לדמיין", "воображать", "");
		test.addItem("דמיון יוצר", "творческое воображение", "");
		test.addItem("סביר יותר", "наиболее вероятно", "");
		test.addItem("דמות", "персонаж образ", "");
		test.addItem("סבירות נמוכה בהצלחה", "Низкая вероятность успеха", "");
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
		//check file and delete
	}

	@org.junit.Test
	public void containsItem() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_, new ApkgFile(_PATH_TO_APKG_FILE_));
		// Be careful, keep the same fields !
		AnkiItem item = test.createAnkiItem("סבירות נמוכה בהצלחה", "Низкая вероятность успеха");
		if (!test.containsItem(item)) {
			test.addItem(item);
		}
		assertTrue(test.containsItem(item));
		//		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
		//check file and delete
	}

	@org.junit.Test
	public void containsItemLambda() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_, new ApkgFile(_PATH_TO_APKG_FILE_));
		AnkiItem item = test.createAnkiItem("Hello", "Bonjour");
		if (test.containsItem(x -> !item.get("FrontSide").equals(x.get("FrontSide")))) {
			test.addItem(item);
		}
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
		//check file and delete
	}

	@org.junit.Test
	public void generateAudioMediaInfo() {
		//		MediaInfo info = new MediaInfo();
		//			cultureInfo = new System.Globalization.CultureInfo(_CULTURE_INFO_STRING_),
		//			field = _FIELD_IN_WHICH_THE_AUDIO_WILL_BE_PLAYED_,
		//          audioFormat = new SpeechAudioFormatInfo(_SAMPLES_PER_SECOND_, _BITS_PER_SAMPLE_, _AUDIO_CHANNEL_)
		//		Anki ankiObject = new Anki(_NAME_OF_ANKI_PACKAGE_, info);
	}

}
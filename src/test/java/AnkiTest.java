public class AnkiTest {

	static String _NAME_OF_ANKI_PACKAGE_ = "test";
	static String _PATH_FOR_ANKI_FILE_ =
			"C:\\Users\\Robert\\Documents\\Java\\ankireader4j\\src\\test\\java";

	@org.junit.Test
	public void createApkgFile() {
		Anki test = new Anki("test");
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
}
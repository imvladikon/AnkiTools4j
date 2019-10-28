import helpers.GeneralHelper;
import lombok.SneakyThrows;
import models.AnkiItem;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnkiTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static final String _NAME_OF_ANKI_PACKAGE_ = "test";
	private static final String _PATH_TO_APKG_FILE_ = "src/test/resources/test.apkg";

	@SneakyThrows
	public String getPathForAnkiFile() {
		Path path = Paths.get(temporaryFolder.getRoot().getPath()).resolve("tmp");
		if (!Files.exists(path))
			return temporaryFolder.newFolder("tmp").getAbsolutePath();
		return path.toString();
	}

	@org.junit.Test
	public void testTempFolder() {
		assertEquals(getPathForAnkiFile(), getPathForAnkiFile());
	}

	@org.junit.Test
	public void createBasicApkgFile() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.addItem("לְהַנִּיחַ", "to assume");
		test.addItem("להעריך", "to estimate, value; appreciate");
		test.addItem("מחקר", "research");
		test.addItem("לדמיין", "to imagine");
		test.addItem("דמיון יוצר", "creative imagination");
		test.addItem("סביר יותר", "likely");
		test.addItem("דמות", "character, image");
		test.addItem("סבירות נמוכה בהצלחה", "low probability of success");
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void createApkgFileWithFields() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setFields("Hebrew", "English", "Russian");
		test.addItem("לְהַנִּיחַ", "to assume", "полагать");
		test.addItem("להעריך", "to estimate, value; appreciate", "ценить");
		test.addItem("מחקר", "research", "исследование");
		test.addItem("לדמיין", "to imagine", "воображать");
		test.addItem("דמיון יוצר", "creative imagination", "творческое воображение");
		test.addItem("סביר יותר", "likely", "наиболее вероятно");
		test.addItem("דמות", "character, image", "образ");
		test.addItem(	"סבירות נמוכה בהצלחה",
						"low probability of success",
						"низкая вероятность успеха");
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void createApkgFileHtml() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setCss("");
		test.addItem("Design a deck of cards", GeneralHelper.readResource("file2.html"));
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void createApkgFileWithCss() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setCss("");
		test.addItem("לְהַנִּיחַ", "to assume");
		test.addItem("להעריך", "to estimate, value; appreciate");
		test.addItem("מחקר", "research");
		test.addItem("לדמיין", "to imagine");
		test.addItem("דמיון יוצר", "creative imagination");
		test.addItem("סביר יותר", "likely");
		test.addItem("דמות", "character, image");
		test.addItem("סבירות נמוכה בהצלחה", "low probability of success");
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void createApkgFileHint() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setFields("Front", "hint:Hint", "Back");
		test.setFormat("{0} - {1} \\n<hr id=answer(.*?)>\\n {2}");
		test.addItem("סבבה", "ok, alright", "хорошо, здорово, класс");
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void createApkgFileWithFormat1() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setFields("Hebrew", "English", "Russian");
		//Everything before '<hr id=answer>' is the front of the card, everything after is the behind
		test.setFormat("{0} - {1} \\n<hr id=answer>\\n {2}");
		test.addItem("לְהַנִּיחַ", "to assume", "полагать");
		test.addItem("להעריך", "to estimate, value; appreciate", "ценить");
		test.addItem("מחקר", "research", "исследование");
		test.addItem("לדמיין", "to imagine", "воображать");
		test.addItem("דמיון יוצר", "creative imagination", "творческое воображение");
		test.addItem("סביר יותר", "likely", "наиболее вероятно");
		test.addItem("דמות", "character, image", "образ");
		test.addItem(	"סבירות נמוכה בהצלחה",
						"low probability of success",
						"низкая вероятность успеха");
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void createApkgFileWithFormat2() {
		Anki test = new Anki(_NAME_OF_ANKI_PACKAGE_);
		test.setFields("Hebrew", "English", "Russian");
		//Everything before '<hr id=answer>' is the front of the card, everything after is the behind
		test.setFormat("<div dir=rtl>{0}</div> - {1} \\n<hr id=answer>\\n {2}");
		test.addItem("לְהַנִּיחַ", "to assume", "полагать");
		test.addItem("להעריך", "to estimate, value; appreciate", "ценить");
		test.addItem("מחקר", "research", "исследование");
		test.addItem("לדמיין", "to imagine", "воображать");
		test.addItem("דמיון יוצר", "creative imagination", "творческое воображение");
		test.addItem("סביר יותר", "likely", "наиболее вероятно");
		test.addItem("דמות", "character, image", "образ");
		test.addItem(	"סבירות נמוכה בהצלחה",
				"low probability of success",
				"низкая вероятность успеха");
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void createApkgFileFromFile() {
		Anki test = new Anki(	_NAME_OF_ANKI_PACKAGE_,
								new ApkgFile(new File(ClassLoader.getSystemClassLoader()
										.getResource("contains.apkg")
										.getFile()).getAbsolutePath()));
		// Be careful, keep the same format !
		test.addItem("עיקר", "main point");
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void containsItem() {
		Anki test = new Anki(	_NAME_OF_ANKI_PACKAGE_,
								new ApkgFile(new File(ClassLoader.getSystemClassLoader()
										.getResource("contains.apkg")
										.getFile()).getAbsolutePath()));
		AnkiItem item = test.createAnkiItem("מחקר", "research");
		if (!test.containsItem(item)) {
			test.addItem(item);
		}
		assertTrue(test.containsItem(item));
	}

	@org.junit.Test
	public void containsItemLambda() {
		Anki test = new Anki(	_NAME_OF_ANKI_PACKAGE_,
								new ApkgFile(new File(ClassLoader.getSystemClassLoader()
										.getResource("contains.apkg")
										.getFile()).getAbsolutePath()));
		AnkiItem item = test.createAnkiItem("עיקר", "суть");
		if (test.containsItem(x -> !item.get("Front").equals(x.get("Front")))) {
			test.addItem(item);
		}
		test.createApkgFile(getPathForAnkiFile());
		assertTrue(Files
				.exists(Paths.get(getPathForAnkiFile()).resolve(_NAME_OF_ANKI_PACKAGE_ + ".apkg")));
	}

	@org.junit.Test
	public void generateAudioMediaInfo() {
		//not imlemented yet
	}

}
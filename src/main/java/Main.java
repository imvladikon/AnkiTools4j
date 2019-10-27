import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class Main {

	static String _NAME_OF_ANKI_PACKAGE_ = "test";
	static String _PATH_FOR_ANKI_FILE_ = "C:\\Users\\Robert\\Documents\\Java\\ankireader4j\\";

	@SneakyThrows
	public static void main(String[] args) {
		final String pathname =
				"C:\\Users\\Robert\\RiderProjects\\AnkiCreator\\AnkiCreator\\dictionary-hebrew.xlsx";
		Anki test = new Anki("test");
		try (FileInputStream fis = new FileInputStream(new File(pathname))) {
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				int i = 0;
				String key = "";
				String value = "";
				while (cells.hasNext()) {
					i++;
					cell = (XSSFCell) cells.next();
					if(i==1) key = cell.toString();
					if(i==2) {
						value = cell.toString();
						break;
					}
				}
				if(key.isEmpty()||value.isEmpty()) continue;
				test.addItem(key, value);
			}
			System.out.println();
		}
		test.createApkgFile(_PATH_FOR_ANKI_FILE_);
		//          test.addItem("assume", "я полагаю (предполагать)");
		//			test.addItem("לְהַנִּיחַ", "я полагаю (предполагать)");
		//			test.addItem("להעריך", "оценить, рассчитывать");
		//			test.addItem("להרהר", "размышлять, медитировать");
		//			test.addItem("מחקר", "исследование");
		//			test.addItem("לדמיין", "воображать");
		//			test.addItem("דמיון יוצר", "творческое воображение");
		//			test.addItem("סביר יותר", "наиболее вероятно");
		//			test.addItem("דמות", "персонаж образ");
		//			test.addItem("סבירות נמוכה בהצלחה", "Низкая вероятность успеха");

	}
}

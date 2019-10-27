package helpers;

import lombok.SneakyThrows;
import models.AnkiDictDynamic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
	@SneakyThrows
	public static List<AnkiDictDynamic> mapSQLiteReader(Connection conn, String toExecute) {
		List<AnkiDictDynamic> result = new ArrayList<AnkiDictDynamic>();
		try (ResultSet reader = SQLiteHelper.executeSQLiteCommandRead(conn, toExecute)) {
			final ResultSetMetaData meta = reader.getMetaData();
			final int columnCount = meta.getColumnCount();
			while (reader.next()) {
				AnkiDictDynamic ankiDictDynamic = new AnkiDictDynamic();
				for (int i = 1; i <= columnCount; ++i) {
					//TODO: check
					ankiDictDynamic.set(String.valueOf(reader.getObject(i)), reader.getString(i));
				}
				result.add(ankiDictDynamic);
			}
		}
		return result;
	}
}

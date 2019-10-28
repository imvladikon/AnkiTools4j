package helpers;

import lombok.SneakyThrows;
import models.AnkiDictDynamic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
	@SneakyThrows
	public static List<AnkiDictDynamic> mapSQLiteReader(Connection conn, String toExecute) {
		List<AnkiDictDynamic> result = new ArrayList<AnkiDictDynamic>();
		try (Statement statement = conn.createStatement();
				ResultSet reader = statement.executeQuery(toExecute)) {
			final ResultSetMetaData meta = reader.getMetaData();
			final int columnCount = meta.getColumnCount();
			while (reader.next()) {
				AnkiDictDynamic ankiDictDynamic = new AnkiDictDynamic();
				for (int i = 1; i <= columnCount; ++i) {
					//TODO: check
//					ankiDictDynamic.set(String.valueOf(reader.getObject(i)), reader.getString(i));
					ankiDictDynamic.set(meta.getColumnName(i), reader.getString(i));
				}
				result.add(ankiDictDynamic);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}

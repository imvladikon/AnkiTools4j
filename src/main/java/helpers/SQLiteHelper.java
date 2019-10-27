package helpers;

import lombok.SneakyThrows;
import entity.Insertable;

import java.sql.*;

public class SQLiteHelper implements AutoCloseable {
	private static SQLiteHelper instance = null;
	private static final String DATABASE_NAME = "collection";
	String connectionString;
	Connection connection = null;

	@SneakyThrows
	public static SQLiteHelper getInstance(String connectionString) {
		if (instance == null) {
			instance = new SQLiteHelper(connectionString);
		}
		if (instance.isClosed()) {
			Class.forName("org.sqlite.JDBC");
			instance.connection = DriverManager.getConnection(connectionString);
			DatabaseMetaData meta = instance.connection.getMetaData();
		}
		return instance;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@SneakyThrows
	public static SQLiteHelper getInstance() {
		if (instance == null) {
			instance = new SQLiteHelper();
		}
		if (instance.isClosed() && !instance.connectionString.isEmpty()) {
			Class.forName("org.sqlite.JDBC");
			instance.connection = DriverManager.getConnection(instance.connectionString);
			DatabaseMetaData meta = instance.connection.getMetaData();
		}
		return instance;
	}

	private boolean isClosed() {
		try {
			return connection == null || connection.isClosed();
		} catch (SQLException e) {
			return true;
		}
	}

	private SQLiteHelper(String connectionString) {
		this.connectionString = connectionString;
	}

	private SQLiteHelper() {

	}

	@SneakyThrows
	public static void executeSQLiteCommand(Connection conn, String toExecute) {
		try (Statement statement = conn.createStatement()) {
			statement.executeUpdate(toExecute);
		}
	}

	public static void saveEntity(Insertable entity) {
		try {
			entity.save(getInstance().connection);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@SneakyThrows
	public static ResultSet executeSQLiteCommandRead(Connection conn, String toExecute) {
		try {
			try (Statement statement = conn.createStatement()) {
				return statement.executeQuery(toExecute);
			}
		} catch (Exception e) {
			throw new Exception("Can't execute query : " + toExecute + "; " + e.getMessage());
		}
	}

	public static String createStringFormat(int from, int to) {
		StringBuilder result = new StringBuilder();
		for (int i = from; i < to; ++i) {
			result.append("{").append(i).append("}");
			if (i + 1 < to)
				result.append(", ");
		}
		return result.toString();
	}

	@Override
	public void close() throws Exception {
		try {
			connection.close();
		} catch (SQLException e) {

		}
	}
}
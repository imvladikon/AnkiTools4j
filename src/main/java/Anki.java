import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javaws.exceptions.InvalidArgumentException;
import dto.Field;
import dto.JDTO;
import dto.TemplateDTO;
import entity.Card;
import entity.Collection;
import entity.Note;
import entity.RevLogMetadata;
import helpers.*;
import lombok.Getter;
import lombok.SneakyThrows;
import models.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.Function;

public class Anki {
	@Getter
	private Connection conn;
	@Getter
	private MediaInfo mediaInfo;
	@Getter
	private String name;
	@Getter
	private String path;
	@Getter
	private String collectionFilePath;
	@Getter
	private ArrayList<AnkiItem> ankiItems;
	@Getter
	private LinkedList<Card> cardsMetadatas;
	@Getter
	private List<RevLogMetadata> revLogMetadatas;

	/**
	 * Key : string which represent Mid
	 * Value : Tuple string, string, FieldList which represent repectively the format, the css and the field list
	 */
	TreeMap<String, Triplet<String, String, FieldList>> infoPerMid;

	/**
	 * Creates a Anki object
	 * 
	 * @param name
	 *            - Specify the name of apkg file and deck
	 * @param info
	 * @param path
	 *            - Where to save your apkg file
	 */
	public Anki(String name, MediaInfo info, String path) {
		this.cardsMetadatas = new LinkedList<>();
		this.revLogMetadatas = new ArrayList<>();
		this.mediaInfo = info;
		this.path =
				path == null	? Paths.get(System.getProperty("user.dir")).resolve("tmp").toString()
								: path;
		if (!Files.exists(Paths.get(this.path))) {
			new File(this.path).mkdirs();
		}
		init(this.path, name);
	}

	public Anki(String name) {
		this.cardsMetadatas = new LinkedList<>();
		this.revLogMetadatas = new ArrayList<>();
		this.path = Paths.get(System.getProperty("user.dir")).resolve("tmp").toString();
		if (!Files.exists(Paths.get(this.path))) {
			new File(this.path).mkdirs();
		}
		init(this.path, name);
	}

	/**
	 * Create anki object from an Apkg file
	 *
	 * @param name
	 *            - Specify the name of apkg file and deck
	 * @param file
	 *            - Apkg file
	 */
	public Anki(String name, ApkgFile file, MediaInfo info) {
		this.cardsMetadatas = new LinkedList<>();
		this.revLogMetadatas = new ArrayList<>();
		this.path = Paths.get(System.getProperty("user.dir")).resolve("tmp").toString();
		this.mediaInfo = info;
		if (!Files.exists(Paths.get(this.path))) {
			new File(this.path).mkdirs();
		}
		init(this.path, name);
		this.collectionFilePath = Paths.get(this.path).resolve("collection.db").toString();
		readApkgFile(file.Path());
	}

	public Anki(String name, ApkgFile file) {
		this(name, file, null);
	}

	public void setFields(String... values) {
		FieldList fields = new FieldList();
		for (String value : values) {
			if (value.contains("hint:") || value.contains("type:")) {
				continue;
			}
			fields.add(new Field(value));
		}
		Triplet currentDefault = this.infoPerMid.get("DEFAULT");
		Triplet newDefault =
				new Triplet(currentDefault.getItem1(), currentDefault.getItem2(), fields);
		this.infoPerMid.put("DEFAULT", newDefault);
	}

	public void setCss(String css) {
		Triplet currentDefault = this.infoPerMid.get("DEFAULT");
		Triplet newDefault = new Triplet(currentDefault.getItem1(), css, currentDefault.getItem3());
		this.infoPerMid.put("DEFAULT", newDefault);
	}

	public void setFormat(String format) {
		Triplet currentDefault = this.infoPerMid.get("DEFAULT");
		Triplet newDefault =
				new Triplet(format, currentDefault.getItem2(), currentDefault.getItem3());
		this.infoPerMid.put("DEFAULT", newDefault);
	}

	/**
	 * Create a apkg file with all the words
	 * 
	 * @param path
	 */
	public void createApkgFile(String path) {
		createDbFile();
		createMediaFile();
		executeSQLiteCommands();
		createZipFile(path);
	}

	/**
	 * Creates an AnkiItem and add it to the Anki object
	 * 
	 * @param properties
	 */
	@SneakyThrows
	public void addItem(String... properties) {
		String mid = "";
		for (Map.Entry<String, Triplet<String, String, FieldList>> entry : this.infoPerMid
				.entrySet()) {
			if (isRightFieldList(entry.getValue().getItem3(), properties)) {
				mid = entry.getKey();
				break;
			}
		}
		if (mid.equals("") || (this.infoPerMid.containsKey(mid)
			&& properties.length != (this.infoPerMid.get(mid)).getItem3().size())) {
			throw new InvalidArgumentException(new String[] { "Number of fields provided is not the same as the one expected" });
		}
		AnkiItem item = new AnkiItem((this.infoPerMid.get(mid)).getItem3(), properties);
		item.setMid(mid);
		if (containsItem(item)) {
			return;
		}
		this.ankiItems.add(item);
	}

	/**
	 * Add AnkiItem to the Anki object
	 * 
	 * @param item
	 */
	@SneakyThrows
	public void addItem(AnkiItem item) {
		if (item.getMid() == "") {
			item.setMid("DEFAULT");
		}
		if (this.infoPerMid.containsValue(item.getMid())
			&& item.getCount() != (this.infoPerMid.get(item.getMid())).getItem3().size()) {
			throw new InvalidArgumentException(new String[] { "Number of fields provided is not the same as the one expected" });
		} else if (containsItem(item)) {
			return;
		}
		this.ankiItems.add(item);
	}

	/**
	 * Tell if the anki object contains an AnkiItem (strict comparison)
	 * 
	 * @param item
	 * @return
	 */
	public boolean containsItem(AnkiItem item) {
		int matching = 1;
		for (AnkiItem ankiItem : this.ankiItems) {
			if (item == ankiItem)
				++matching;
		}
		return matching == item.getCount();
	}

	/**
	 * Tell if the anki object contains an AnkiItem (user defined comparison)
	 * 
	 * @param comparison
	 * @return
	 */
	public boolean containsItem(Function<AnkiItem, Boolean> comparison) {
		for (AnkiItem ankiItem : this.ankiItems) {
			if (comparison.apply(ankiItem))
				return true;
		}
		return false;
	}

	public AnkiItem createAnkiItem(String... properties) {
		FieldList list = null;
		for (Map.Entry<String, Triplet<String, String, FieldList>> entry : this.infoPerMid
				.entrySet()) {
			if (isRightFieldList(entry.getValue().getItem3(), properties)) {
				list = entry.getValue().getItem3();
				break;
			}
		}
		return new AnkiItem(list, properties);
	}

	@SneakyThrows
	private void createDbFile(String name) {
		final Path path = Paths.get(this.path).resolve(name);
		this.collectionFilePath = path.toString();
		if (Files.exists(path)) {
			Files.delete(path);
		}
		try (InputStream in = this.getClass().getResourceAsStream("collection.db");
				OutputStream out = new FileOutputStream(this.collectionFilePath)) {
			IOUtils.copy(in, out);
		}
	}

	private void createDbFile() {
		createDbFile("collection.db");
	}

	private void init(String path, String name) {
		this.infoPerMid = new TreeMap<>();
		this.name = name;
		this.ankiItems = new ArrayList<>();
		this.path = path;
		FieldList fields = new FieldList() {
			{
				add(new Field("Front"));
				add(new Field("Back"));
			}
		};
		this.infoPerMid.put("DEFAULT", new Triplet<>("", GeneralHelper.readResource("CardStyle.css"), fields));
	}

	private boolean isRightFieldList(FieldList list, String[] properties) {
		return list.size() == properties.length;
	}

	@SneakyThrows
	private void createZipFile(String path) {
		Path anki2FilePath = Paths.get(this.path).resolve("collection.anki2");
		Path mediaFilePath = Paths.get(this.path).resolve("media");
		if (Files.exists(anki2FilePath)) {
			Files.delete(anki2FilePath);
		}
		Files.move(Paths.get(this.collectionFilePath), anki2FilePath);
		Path zipPath = Paths.get(path).resolve(this.name + ".apkg");
		if (Files.exists(zipPath)) {
			Files.delete(zipPath);
		}
		ZipFileUtils.createFromDirectory(this.path, zipPath.toString());
		Files.delete(anki2FilePath);
		Files.delete(mediaFilePath);
		int i = 0;
		Path currentFile = Paths.get(this.path).resolve(String.valueOf(i));
		while (Files.exists(currentFile)) {
			Files.delete(currentFile);
			++i;
			currentFile = Paths.get(this.path).resolve(String.valueOf(i));
		}
	}

	@SneakyThrows
	private long createCol() {
		Collection entity = Collection.create(this.infoPerMid, this.ankiItems, this.name);
		SQLiteHelper.saveEntity(entity);
		return entity.getDeckId();
	}

	private void createNotesAndCards(long id_deck, Anki anki) {
		Anki currentAnki = anki == null ? this : anki;
		for (AnkiItem ankiItem : currentAnki.ankiItems) {
			FieldList fields = (currentAnki.infoPerMid.get(ankiItem.getMid())).getItem3();
			Note note = Note.create(fields, currentAnki.mediaInfo, ankiItem);
			SQLiteHelper.saveEntity(note);
			SQLiteHelper.saveEntity(Card.create(this.cardsMetadatas, note, id_deck));
		}
	}

	@SneakyThrows
	private void addRevlogMetadata() {
		for (RevLogMetadata revlogMetadata : this.revLogMetadatas) {
			SQLiteHelper.saveEntity(revlogMetadata);
		}
	}

	@SneakyThrows
	private void executeSQLiteCommands(Anki anki) {
		try {
			this.conn = SQLiteHelper.getInstance("jdbc:sqlite:" + this.collectionFilePath)
					.getConnection();
			String column = GeneralHelper.readResource("sql/ColumnTable.sql");
			String notes = GeneralHelper.readResource("sql/NotesTable.sql");
			String cards = GeneralHelper.readResource("sql/CardsTable.sql");
			String revLogs = GeneralHelper.readResource("sql/RevLogTable.sql");
			String graves = GeneralHelper.readResource("sql/GravesTable.sql");
			//			SQLiteHelper.executeSQLiteCommand(this.conn, column);
			//			SQLiteHelper.executeSQLiteCommand(this.conn, notes);
			//			SQLiteHelper.executeSQLiteCommand(this.conn, cards);
			//			SQLiteHelper.executeSQLiteCommand(this.conn, revLogs);
			//			SQLiteHelper.executeSQLiteCommand(this.conn, graves);
			long id_deck = createCol();
			createNotesAndCards(id_deck, anki);
			addRevlogMetadata();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			this.conn.close();
		}
	}

	@SneakyThrows
	private void executeSQLiteCommands() {
		executeSQLiteCommands(null);
	}

	@SneakyThrows
	private void createMediaFile() {
		Path mediaFilePath = Paths.get(this.path).resolve("media");
		if (Files.exists(mediaFilePath)) {
			Files.delete(mediaFilePath);
		}
		StringBuilder data = new StringBuilder("{");
		int i = 0;
		if (this.mediaInfo != null) {
			for (AnkiItem item : this.ankiItems) {
				if (this.mediaInfo.getExtension().equals(".gif")
					&& this.mediaInfo.getUserStringLocale().equals("zh-CN")) {
					StrokeOrderHelper.downloadImage(
													Paths.get(this.path)
															.resolve(String.valueOf(i))
															.toString(),
													item.get(this.mediaInfo.getField()).toString());
				} else if (this.mediaInfo.getExtension().equals(".wav")) {
					SynthetizerHelper
							.createAudio(	Paths.get(this.path).resolve(String.valueOf(i)).toString(),
											item.get(this.mediaInfo.getField()).toString(),
											this.mediaInfo.getCultureInfo(),
											this.mediaInfo.getAudioFormat());
				}
				data.append("\"")
						.append(i)
						.append("\": \"")
						.append(item.get(this.mediaInfo.getField()))
						.append(this.mediaInfo.getExtension())
						.append("\"");
				if (i < this.ankiItems.size() - 1) {
					data.append(", ");
				}
				i++;
			}
		}
		data.append("}");
		try (FileOutputStream fos = new FileOutputStream(mediaFilePath.toString())) {
			fos.write(data.toString().getBytes(StandardCharsets.UTF_8));
		}
		//		FileUtils.writeByteArrayToFile(	mediaFilePath.toFile(),
		//										data.toString().getBytes(StandardCharsets.UTF_8));
	}

	@SneakyThrows
	private void readApkgFile(String path) {
		final Path ppath = Paths.get(this.path);
		if (Files.exists(ppath.resolve("collection.db"))) {
			Files.delete(ppath.resolve("collection.db"));
		}
		if (Files.exists(ppath.resolve("media"))) {
			Files.delete(ppath.resolve("media"));
		}
		ZipFileUtils.extractToDirectory(path, this.path);
		Path anki2File = ppath.resolve("collection.anki2");
		Files.move(anki2File, Paths.get(this.collectionFilePath));
		try {
			this.conn = SQLiteHelper.getInstance("jdbc:sqlite:" + this.collectionFilePath)
					.getConnection();
			List<AnkiDictDynamic> cardMetadatas = Mapper
					.mapSQLiteReader(	this.conn,
										"SELECT id, mod, type, queue, due, ivl, factor, reps, lapses, left, odue, odid FROM cards");
			for (AnkiDictDynamic cardMetadata : cardMetadatas) {
				this.cardsMetadatas.add((Card) cardMetadata.toObject(Card.class));
			}
			List<Long> mids = new ArrayList<>();
			List<String[]> result = new ArrayList<>();
			try (ResultSet reader = SQLiteHelper
					.executeSQLiteCommandRead(	this.conn,
												"SELECT notes.flds, notes.mid FROM notes")) {
				while (reader.next()) {
					long currentMid = reader.getLong(1);
					if (!mids.contains(currentMid)) {
						mids.add(currentMid);
					}
					result.add(reader.getString(0).split(String.valueOf((char) 0x1f)));
				}
			}
			try (ResultSet reader =
					SQLiteHelper.executeSQLiteCommandRead(this.conn, "SELECT models FROM col")) {
				String models = "";
				while (reader.next()) {
					models = reader.getString(0); //TODO: ???
				}
				addFields(models, mids);
			}
			List<AnkiDictDynamic> revLogMetadatas =
					Mapper.mapSQLiteReader(this.conn, "SELECT * FROM revlog");
			for (AnkiDictDynamic revLogMetadata : revLogMetadatas) {
				this.revLogMetadatas
						.add((RevLogMetadata) revLogMetadata.toObject(RevLogMetadata.class));
			}
			for (String[] res : result) {
				addItem(res);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			this.conn.close();
		}
	}

	@SneakyThrows
	private void addFields(String jsonString, List<Long> mids) {
		TypeReference<HashMap<Long, JDTO>> typeRef = new TypeReference<HashMap<Long, JDTO>>() {};
		for (Map.Entry<Long, JDTO> entry : new ObjectMapper().readValue(jsonString, typeRef)
				.entrySet()) {
			FieldList fields = new FieldList();
			fields.addAll(entry.getValue().getFlds());
			Long mid = entry.getKey();
			String qfmt = entry.getValue()
					.getTmpls()
					.stream()
					.findFirst()
					.map(TemplateDTO::getQfmt)
					.orElse("");
			String afmt = entry.getValue()
					.getTmpls()
					.stream()
					.findFirst()
					.map(TemplateDTO::getAfmt)
					.orElse("");
			afmt = afmt.replaceAll("\\{\\{FrontSide}}", qfmt);
			String css = entry.getValue().getCss();
			this.infoPerMid.put("" + mid,
								new Triplet(afmt.replaceAll("\n", "\\n"),
											css.replaceAll("\n", "\\n"),
											fields));
		}
	}
}
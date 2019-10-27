package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import helpers.GeneralHelper;
import lombok.*;
import models.AnkiItem;
import models.FieldList;
import models.Triplet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collection implements Insertable {
	@Builder.Default
	long id = 1;
	long crt;
	long mod;
	long scm;
	long ver;
	long dty;
	long usn;
	long lastSync;
	@Builder.Default
	@NonNull
	String conf = "";
	@Builder.Default
	@NonNull
	String models = "";
	@Builder.Default
	@NonNull
	String decks = "";
	@Builder.Default
	@NonNull
	String dconf = "";
	@Builder.Default
	@NonNull
	String tags = "{}";

	@JsonIgnore
	long deckId;

	@SneakyThrows
	public static Collection create(TreeMap<String, Triplet<String, String, FieldList>> infoPerMid,
									ArrayList<AnkiItem> ankiItems,
									String name) {
		long timestamp = System.currentTimeMillis();
		long mid = timestamp;
		Collection result = Collection.builder()
				.mod(timestamp)
				.crt(GeneralHelper.getDayStart())
				.deckId(timestamp)
				.conf(ConfDTO.create().setCurModel(timestamp).toJson())
				.build();
		List<Field> flds = new ArrayList<>();
		List<TemplateDTO> tmpls = new ArrayList<>();
		List<String> alreadyAdded = new ArrayList<>();
		String css = "";
		for (String key : infoPerMid.keySet()) {
			Triplet<String, String, FieldList> obj = infoPerMid.get(key);
			flds = new ArrayList<>(obj.getItem3());
			css = obj.getItem2() == null ? "" : obj.getItem2();
			if (alreadyAdded.contains(obj.getItem3()
					.toJSON()
					.replaceAll("hint:", "")
					.replaceAll("type:", ""))) {
				continue;
			}
			if (key.equals("DEFAULT")) {
				Triplet<String, String, FieldList> newEntry = infoPerMid.get("DEFAULT");
				infoPerMid.put(String.valueOf(mid), newEntry);
				ankiItems.forEach(x -> x.mid =
						x.mid.equals("DEFAULT") ? String.valueOf(mid) : x.mid);
			}
			String format = (!obj.getItem1().equals("")	? obj.getItem3().format(obj.getItem1())
														: obj.getItem3().toFrontBack())
																.replaceAll("\\\\n", "\n");
			String qfmt = format.split("<hr id=answer(.*?)>")[0]; //TODO:NRE
			//			String afmt = format.replaceAll(
			//											qfmt.replaceAll("\\{\\{", "")
			//													.replaceAll("}}", "")
			//													.replaceAll("\\\\n", ""),
			//											"FrontSide");
			String afmt = "{{FrontSide}}\n" + "<hr id=answer(.*?)>"
				+ format.split("<hr id=answer(.*?)>")[1];
			tmpls.add(TemplateDTO.builder()
					.name("Forward")
					.qfmt(qfmt)
					.did(null)
					.bafmt("")
					.afmt(afmt)
					.ord(0L)
					.bqfmt("")
					.build());
			alreadyAdded
					.add(obj.getItem3().toJSON().replaceAll("hint:", "").replaceAll("type:", ""));
		}
		HashMap<Long, JDTO> hashMap = new HashMap<>();
		flds.forEach(f -> f.setMedia(new String[] {}));
		hashMap.put(result.mod,
					JDTO.create()
							.setDid(result.deckId)
							.setFlds(flds)
							.setTmpls(tmpls)
							.setId(mid)
							.setCss(css)
							.setMod(result.mod));
		HashMap<String, DeckDTO> decks = new HashMap<>();
		decks.put("1", DeckDTO.create());
		decks.put(	String.valueOf(result.deckId),
					DeckDTO.create()
							.setName(name)
							.setUsn(-1)
							.setNewToday(new int[] { 365, 0 })
							.setTimeToday(new int[] { 365, 0 })
							.setRevToday(new int[] { 365, 0 })
							.setLrnToday(new int[] { 365, 0 })
							.setId(result.deckId)
							.setMode(result.mod));
		HashMap<String, DconfDTO> dconfs = new HashMap<>();
		dconfs.put("1", DconfDTO.create());
		result.dconf = new ObjectMapper().writeValueAsString(dconfs);
		result.decks = new ObjectMapper().writeValueAsString(decks);
		result.models = new ObjectMapper().writeValueAsString(hashMap);
		return result;
	}

	@Override
	public void save(Connection conn) throws SQLException {
		String sql = "INSERT INTO col VALUES(?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, this.id);
			pstmt.setLong(2, this.crt);
			pstmt.setLong(3, this.mod);
			pstmt.setLong(4, this.scm);
			pstmt.setLong(5, this.ver);
			pstmt.setLong(6, this.dty);
			pstmt.setLong(7, this.usn);
			pstmt.setLong(8, this.lastSync);
			pstmt.setString(9, this.conf);
			pstmt.setString(10, this.models);
			pstmt.setString(11, this.decks);
			pstmt.setString(12, this.dconf);
			pstmt.setObject(13, this.tags);
			pstmt.executeUpdate();
		}
	}
}

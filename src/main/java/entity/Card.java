package entity;

import lombok.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Insertable {
	long id;
	long nid;
	long did;
	long ord;
	long mod;
	long usn;
	int type;
	int queue;
	long due;
	int ivl;
	int factor;
	int reps;
	int lapses;
	int left;
	int odue;
	int odid;
	int flags;
	@NonNull
	@Builder.Default
	String data = "";

	public static Card create(LinkedList<Card> cardsMetadatas, Note note, long id_deck) {
		long current_id = System.currentTimeMillis();
		long mod = id_deck;//System.currentTimeMillis();
		Card entity = new Card();
		if (cardsMetadatas.size() != 0) {
			Card metadata = cardsMetadatas.remove();
			entity = Card.builder()
					.id(metadata.getId())
					.nid(note.getId())
					.did(id_deck)
					.ord(0)
					.mod(metadata.getMod())
					.usn(-1)
					.type(metadata.getType())
					.queue(metadata.getQueue())
					.due(metadata.getDue())
					.ivl(metadata.getIvl())
					.factor(metadata.getFactor())
					.reps(metadata.getReps())
					.lapses(metadata.getLapses())
					.left(metadata.getLeft())
					.odue(metadata.getOdue())
					.odid(metadata.getOdid())
					.flags(0)
					.data("")
					.build();
		} else {
			entity = Card.builder()
					.id(current_id)
					.nid(note.getId())
					.did(id_deck)
					.mod(mod)
					.usn(-1)
					.due(note.getId())
					.data("")
					.build();
		}
		return entity;
	}

	@Override
	public void save(Connection conn) throws SQLException {
		String sql =
				"INSERT INTO cards VALUES(?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, this.id);
			pstmt.setLong(2, this.nid);
			pstmt.setLong(3, this.did);
			pstmt.setLong(4, this.ord);
			pstmt.setLong(5, this.mod);
			pstmt.setLong(6, this.usn);
			pstmt.setLong(7, this.type);
			pstmt.setInt(8, this.queue);
			pstmt.setLong(9, this.due);
			pstmt.setInt(10, this.ivl);
			pstmt.setInt(11, this.factor);
			pstmt.setInt(12, this.reps);
			pstmt.setInt(13, this.lapses);
			pstmt.setInt(14, this.left);
			pstmt.setInt(15, this.odue);
			pstmt.setInt(16, this.odid);
			pstmt.setInt(17, this.flags);
			pstmt.setString(18, this.data);
			pstmt.executeUpdate();
		}
	}
}

package entity;

import lombok.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevLogMetadata implements Insertable {
	long id;
	long cid;
	int usn;
	int ease;
	int ivl;
	int lastIvl;
	int factor;
	long time;
	int type;

	@Override
	public void save(Connection conn) throws SQLException {
		String sql = "INSERT INTO revlog VALUES(?, ?, ?, ?, ? , ?, ?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, this.id);
			pstmt.setLong(2, this.cid);
			pstmt.setLong(3, this.usn);
			pstmt.setLong(4, this.ease);
			pstmt.setLong(5, this.ivl);
			pstmt.setInt(6, this.lastIvl);
			pstmt.setInt(7, this.factor);
			pstmt.setLong(8, this.time);
			pstmt.setInt(9, this.type);
			pstmt.executeUpdate();
		}
	}
}

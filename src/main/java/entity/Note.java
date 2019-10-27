package entity;

import helpers.GeneralHelper;
import helpers.ShortGuid;
import lombok.*;
import models.AnkiItem;
import models.FieldList;
import models.MediaInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Note implements Insertable {
	long id;
	@NonNull
	@Builder.Default
	String guid = "";
	long mid;
	long mod;
	int usn;
	@NonNull
	@Builder.Default
	String tags = "";
	@NonNull
	@Builder.Default
	String flds = "";
	@NonNull
	@Builder.Default
	String sfld = "";
	@NonNull
	@Builder.Default
	String csum = "";
	int flags;
	@NonNull
	@Builder.Default
	String data = "";

	public static Note create(FieldList fields, MediaInfo mediaInfo, AnkiItem ankiItem) {
		long timestamp = System.currentTimeMillis();
		return Note.builder()
				.id(timestamp)
				.guid((ShortGuid.from(UUID.randomUUID())).toString().substring(0, 10))
				.mid(Long.parseLong(ankiItem.getMid()))
				.mod(timestamp)
				.flds(GeneralHelper
						.concatFields(fields, ankiItem, String.valueOf((char) 0x1f), mediaInfo))
				.sfld(ankiItem.get(fields.get(0).getName()).toString())
				.csum(GeneralHelper.checkSum(ankiItem.get(fields.get(0).getName()).toString()))
				.data("")
				.flags(0)
				.usn(-1)
				.build();
	}

	@Override
	public void save(Connection conn) throws SQLException {
		String sql = "INSERT INTO notes VALUES(?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, this.id);
			pstmt.setString(2, this.guid);
			pstmt.setLong(3, this.mid);
			pstmt.setLong(4, this.id);//mod
			pstmt.setLong(5, this.usn);
			pstmt.setString(6, this.tags);
			pstmt.setString(7, this.flds);
			pstmt.setString(8, this.sfld);
			pstmt.setString(9, this.csum);
			pstmt.setInt(10, this.flags);
			pstmt.setString(11, this.data);
			pstmt.executeUpdate();
		}
	}
}
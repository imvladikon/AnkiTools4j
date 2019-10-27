package helpers;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.apache.commons.codec.binary.*;

public class ShortGuid {

	public static ShortGuid Empty = new ShortGuid(new UUID(0L, 0L));

	UUID guid;
	String value;

	public ShortGuid(String value) {
		this.value = value;
		this.guid = decode(value);
	}

	private ShortGuid(UUID guid) {
		this.value = encode(guid);
		this.guid = guid;
	}

	public static ShortGuid from(UUID guid) {
		return new ShortGuid(guid);
	}

	public UUID getGuid() {
		return this.guid;
	}

	public void setGuid(UUID value) {
		if (value != this.guid) {
			this.guid = value;
			this.value = encode(value);
		}
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (!this.value.equals(value)) {
			this.value = value;
			this.guid = decode(value);
		}
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ShortGuid)
			return this.guid.equals(((ShortGuid) obj).guid);
		if (obj instanceof UUID)
			return this.guid.equals((UUID) obj);
		if (obj instanceof String)
			return this.guid.equals(((ShortGuid) obj).guid);
		return false;
	}

	@Override
	public int hashCode() {
		return this.guid.hashCode();
	}

	public static ShortGuid newGuid() {
		return new ShortGuid(UUID.randomUUID());
	}

	public static String encode(String value) {
		UUID guid = UUID.fromString(value);
		return encode(guid);
	}

	public static String encode(UUID guid) {
		String encoded = (new Base64()).encodeAsString(UuidUtils.asBytes(guid));
		encoded = encoded.replace("/", "_").replace("+", "-");
		return encoded.substring(0, 22);
	}

	public static UUID decode(String value) {
		value = value.replace("_", "/").replace("-", "+");
		byte[] buffer = (new Base64()).decode(value + "==");
		return UuidUtils.asUuid(buffer);
	}

	static class UuidUtils {
		public static UUID asUuid(byte[] bytes) {
			ByteBuffer bb = ByteBuffer.wrap(bytes);
			long firstLong = bb.getLong();
			long secondLong = bb.getLong();
			return new UUID(firstLong, secondLong);
		}

		public static byte[] asBytes(UUID uuid) {
			ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
			bb.putLong(uuid.getMostSignificantBits());
			bb.putLong(uuid.getLeastSignificantBits());
			return bb.array();
		}
	}
}
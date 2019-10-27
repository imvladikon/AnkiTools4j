package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeckDTO {
	String desc;
	String name;
	Long extendRev;
	int usn;
	boolean collapsed;
	int[] newToday;
	int[] timeToday;
	int dyn;
	int extendNew;
	int conf;
	int[] revToday;
	int[] lrnToday;
	long id;
	long mode;

	public static DeckDTO create() {
		return DeckDTO.builder()
				.desc("")
				.name("Default")
				.extendRev(50L)
				.usn(0)
				.collapsed(false)
				.newToday(new int[] { 0, 0 })
				.timeToday(new int[] { 0, 0 })
				.dyn(0)
				.extendNew(10)
				.conf(1)
				.revToday(new int[] { 0, 0 })
				.lrnToday(new int[] { 0, 0 })
				.id(1L)
				.mode(1533970713L)
				.build();
	}

	public DeckDTO setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	public DeckDTO setName(String name) {
		this.name = name;
		return this;
	}

	public DeckDTO setExtendRev(Long extendRev) {
		this.extendRev = extendRev;
		return this;
	}

	public DeckDTO setUsn(int usn) {
		this.usn = usn;
		return this;
	}

	public DeckDTO setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
		return this;
	}

	public DeckDTO setNewToday(int[] newToday) {
		this.newToday = newToday;
		return this;
	}

	public DeckDTO setTimeToday(int[] timeToday) {
		this.timeToday = timeToday;
		return this;
	}

	public DeckDTO setDyn(int dyn) {
		this.dyn = dyn;
		return this;
	}

	public DeckDTO setExtendNew(int extendNew) {
		this.extendNew = extendNew;
		return this;
	}

	public DeckDTO setConf(int conf) {
		this.conf = conf;
		return this;
	}

	public DeckDTO setRevToday(int[] revToday) {
		this.revToday = revToday;
		return this;
	}

	public DeckDTO setLrnToday(int[] lrnToday) {
		this.lrnToday = lrnToday;
		return this;
	}

	public DeckDTO setId(long id) {
		this.id = id;
		return this;
	}

	public DeckDTO setMode(long mode) {
		this.mode = mode;
		return this;
	}
}

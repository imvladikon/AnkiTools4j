package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColDeckJObject {
	//name of deck
	String name;
	//extended review card limit (for custom study)
	//Potentially absent, in this case it's considered to be 10 by aqt.customstudy
	Long extendRev;
	//usn: Update sequence number: used in same way as other usn vales in db
	int usn;
	//true when deck is collapsed
	boolean collapsed;
	//true when deck collapsed in browser
	boolean browserCollapsed;
	int[] newToday;
	int[] revToday;
	int[] lrnToday;
	//two number array used somehow for custom study. Currently unused in the code
	int[] timeToday;
	//1 if dynamic (AKA filtered) deck
	int dyn;
	//extended new card limit (for custom study)
	int extendNew;
	//id of option group from dconf in `col` table. Or absent if the deck is dynamic.
	int conf;
	//deck ID (automatically generated long)
	long id;
	//last modification time
	long mode;
	//deck description
	String desc;

	public static ColDeckJObject create() {
		return ColDeckJObject.builder()
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

	public ColDeckJObject setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	public ColDeckJObject setName(String name) {
		this.name = name;
		return this;
	}

	public ColDeckJObject setExtendRev(Long extendRev) {
		this.extendRev = extendRev;
		return this;
	}

	public ColDeckJObject setUsn(int usn) {
		this.usn = usn;
		return this;
	}

	public ColDeckJObject setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
		return this;
	}

	public ColDeckJObject setNewToday(int[] newToday) {
		this.newToday = newToday;
		return this;
	}

	public ColDeckJObject setTimeToday(int[] timeToday) {
		this.timeToday = timeToday;
		return this;
	}

	public ColDeckJObject setDyn(int dyn) {
		this.dyn = dyn;
		return this;
	}

	public ColDeckJObject setExtendNew(int extendNew) {
		this.extendNew = extendNew;
		return this;
	}

	public ColDeckJObject setConf(int conf) {
		this.conf = conf;
		return this;
	}

	public ColDeckJObject setRevToday(int[] revToday) {
		this.revToday = revToday;
		return this;
	}

	public ColDeckJObject setLrnToday(int[] lrnToday) {
		this.lrnToday = lrnToday;
		return this;
	}

	public ColDeckJObject setId(long id) {
		this.id = id;
		return this;
	}

	public ColDeckJObject setMode(long mode) {
		this.mode = mode;
		return this;
	}
}

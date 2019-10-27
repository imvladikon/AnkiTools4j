package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfDTO {
	int nextPos;
	boolean estTimes;
	int[] activeDecks;
	String sortType;
	int timeLim;
	boolean sortBackwards;
	boolean addToCur;
	int curDeck;
	boolean newBury;
	int newSpread;
	boolean dueCounts;
	long curModel;
	Long collapseTime;

	public static ConfDTO create() {
		return ConfDTO.builder()
				.nextPos(1)
				.estTimes(true)
				.activeDecks(new int[] { 1 })
				.sortType("noteFld")
				.timeLim(0)
				.sortBackwards(false)
				.addToCur(true)
				.curDeck(1)
				.newBury(true)
				.newSpread(0)
				.dueCounts(true)
				.collapseTime(1200L)
				.build();
	}

	@SneakyThrows
	public String toJson() {
		return new ObjectMapper().writeValueAsString(this);
	}

	public ConfDTO setNextPos(int nextPos) {
		this.nextPos = nextPos;
		return this;
	}

	public ConfDTO setEstTimes(boolean estTimes) {
		this.estTimes = estTimes;
		return this;
	}

	public ConfDTO setActiveDecks(int[] activeDecks) {
		this.activeDecks = activeDecks;
		return this;
	}

	public ConfDTO setSortType(String sortType) {
		this.sortType = sortType;
		return this;
	}

	public ConfDTO setTimeLim(int timeLim) {
		this.timeLim = timeLim;
		return this;
	}

	public ConfDTO setSortBackwards(boolean sortBackwards) {
		this.sortBackwards = sortBackwards;
		return this;
	}

	public ConfDTO setAddToCur(boolean addToCur) {
		this.addToCur = addToCur;
		return this;
	}

	public ConfDTO setCurDeck(int curDeck) {
		this.curDeck = curDeck;
		return this;
	}

	public ConfDTO setNewBury(boolean newBury) {
		this.newBury = newBury;
		return this;
	}

	public ConfDTO setNewSpread(int newSpread) {
		this.newSpread = newSpread;
		return this;
	}

	public ConfDTO setDueCounts(boolean dueCounts) {
		this.dueCounts = dueCounts;
		return this;
	}

	public ConfDTO setCurModel(long curModel) {
		this.curModel = curModel;
		return this;
	}

	public ConfDTO setCollapseTime(Long collapseTime) {
		this.collapseTime = collapseTime;
		return this;
	}
}

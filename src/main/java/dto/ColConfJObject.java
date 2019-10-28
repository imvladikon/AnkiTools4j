package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColConfJObject {
	//The id (as int) of the last deck selectionned (review, adding card, changing
	// the deck of a card)
	int curDeck;
	//The list containing the current deck id and its descendent (as ints)
	int[] activeDecks;
	//In which order to view to review the cards. This can be selected in Preferences>Basic. Possible values are:
	//  0 -- NEW_CARDS_DISTRIBUTE (Mix new cards and reviews)
	//  1 -- NEW_CARDS_LAST (see new cards after review)
	//  2 -- NEW_CARDS_FIRST (See new card before review)
	int newSpread;
	//'Preferences>Basic>Learn ahead limit'*60.
	//If there are no other card to review, then we can review cards
	// in learning in advance if they are due in less than this number of seconds.
	Long collapseTime;
	int timeLim;
	int nextPos;
	boolean estTimes;
	String sortType;
	boolean sortBackwards;
	boolean addToCur;
	//A Boolean. Always set to true and not read anywhere in the code 
	// but at the place where it is set to True if it is not already true. 
	// Hence probably quite useful.
	boolean newBury;
	boolean dueCounts;
	long curModel;

	public static ColConfJObject create() {
		return ColConfJObject.builder()
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

	public ColConfJObject setNextPos(int nextPos) {
		this.nextPos = nextPos;
		return this;
	}

	public ColConfJObject setEstTimes(boolean estTimes) {
		this.estTimes = estTimes;
		return this;
	}

	public ColConfJObject setActiveDecks(int[] activeDecks) {
		this.activeDecks = activeDecks;
		return this;
	}

	public ColConfJObject setSortType(String sortType) {
		this.sortType = sortType;
		return this;
	}

	public ColConfJObject setTimeLim(int timeLim) {
		this.timeLim = timeLim;
		return this;
	}

	public ColConfJObject setSortBackwards(boolean sortBackwards) {
		this.sortBackwards = sortBackwards;
		return this;
	}

	public ColConfJObject setAddToCur(boolean addToCur) {
		this.addToCur = addToCur;
		return this;
	}

	public ColConfJObject setCurDeck(int curDeck) {
		this.curDeck = curDeck;
		return this;
	}

	public ColConfJObject setNewBury(boolean newBury) {
		this.newBury = newBury;
		return this;
	}

	public ColConfJObject setNewSpread(int newSpread) {
		this.newSpread = newSpread;
		return this;
	}

	public ColConfJObject setDueCounts(boolean dueCounts) {
		this.dueCounts = dueCounts;
		return this;
	}

	public ColConfJObject setCurModel(long curModel) {
		this.curModel = curModel;
		return this;
	}

	public ColConfJObject setCollapseTime(Long collapseTime) {
		this.collapseTime = collapseTime;
		return this;
	}
}

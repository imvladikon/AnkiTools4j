package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColDConfJObject {
	//whether the audio associated to a question should be
	//played when the question is shown
	boolean autoplay;
	//deck ID (automatically generated long). Not present by default in decks.py
	long id;
	//The configuration for lapse cards.
	LapseJObject lapse;
	//The number of seconds after which to stop the timer
	long maxTaken;
	//Last modification time
	long mod;
	//The name of the configuration
	String name;
	//The configuration for new cards.
	@JsonProperty("new")
	NewJObject newJObject;
	//whether the audio associated to a question should be played when the answer is shown
	boolean replayq;
	//The configuration for review cards.
	RevJObject rev;
	//whether timer should be shown (1) or not (0)
	long timer;
	//See usn in cards table for details.
	int usn;

	public static ColDConfJObject create() {
		return ColDConfJObject.builder()
				.name("Default")
				.replayq(true)
				.lapse(LapseJObject.builder()
						.leechFails(8)
						.minInt(1)
						.delays(new int[] { 10 })
						.leechAction(0)
						.mult(0)
						.build())
				.rev(RevJObject.builder()
						.perDay(200)
						.fuzz(0.05)
						.ivlFct(1L)
						.maxIvl(36500)
						.ease4(1.3)
						.bury(false)
						.minSpace(1)
						.build())
				.timer(0L)
				.maxTaken(60)
				.usn(0)
				.newJObject(NewJObject.builder()
						.perDay(20)
						.delays(new int[] { 1, 10 })
						.separate(true)
						.ints(new int[] { 1, 4, 7 })
						.initialFactor(2500)
						.bury(false)
						.order(1)
						.build())
				.mod(0L)
				.id(1L)
				.autoplay(true)
				.build();
	}
}

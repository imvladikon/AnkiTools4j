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
public class DconfDTO {
	String name;
	boolean replayq;
	LapseDTO lapse;
	RevDTO rev;
	long timer;
	long maxTaken;
	int usn;
	@JsonProperty("new")
	NewDTO newDTO;
	long mod;
	long id;
	boolean autoplay;

	public static DconfDTO create() {
		return DconfDTO.builder()
				.name("Default")
				.replayq(true)
				.lapse(LapseDTO.builder()
						.leechFails(8)
						.minInt(1)
						.delays(new int[] { 10 })
						.leechAction(0)
						.mult(0)
						.build())
				.rev(RevDTO.builder()
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
				.newDTO(NewDTO.builder()
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

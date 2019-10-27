package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewDTO {
	int perDay;
	int[] delays;
	boolean separate;
	int[] ints;
	long initialFactor;
	boolean bury;
	int order;
}

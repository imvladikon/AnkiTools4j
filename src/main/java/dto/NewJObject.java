package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewJObject {
	//Maximal number of new cards shown per day.
	int perDay;
	//The list of successive delay between
	// the learning steps of the new cards, as explained in the manual.
	int[] delays;
	boolean separate;
	//The list of delays according to the button pressed while leaving
	// the learning mode. Good, easy and unused. In the GUI, the first two elements corresponds to Graduating Interval and Easy interval
	int[] ints;
	//The initial ease factor
	long initialFactor;
	//Whether to bury cards related to new cards answered
	boolean bury;
	//In which order new cards must be shown. NEW_CARDS_RANDOM = 0 and NEW_CARDS_DUE = 1.
	int order;
}

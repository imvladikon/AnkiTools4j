package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevJObject {
	long perDay;
	double fuzz;
	long ivlFct;
	long maxIvl;
	double ease4;
	boolean bury;
	int minSpace;
}

package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {
	String name;
	String qfmt;
	Long did;
	String bafmt;
	String afmt;
	Long ord;
	String bqfmt;
}

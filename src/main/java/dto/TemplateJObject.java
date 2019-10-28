package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateJObject {
	//template name
	String name;
	//question format string
	String qfmt;
	//deck override (null by default)
	Long did;
	//browser answer format: used for displaying answer in browser
	String bafmt;
	//answer template string
	String afmt;
	//template number, see flds
	Long ord;
	//browser question format: used for displaying question in browser
	String bqfmt;
}

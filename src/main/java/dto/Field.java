package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class Field {
	//field name
	String name;
	//boolean, right-to-left script
	@Builder.Default
	boolean rtl = false;
	//sticky fields retain the value that was last added
	//when adding new notes
	@Builder.Default
	boolean sticky = false;
	//array of media. appears to be unused
	@Builder.Default
	String[] media = new String[] {};
	//ordinal of the field - goes from 0 to num fields -1
	int ord;
	//display font
	@Builder.Default
	String font = "Ariel";
	//font size
	@Builder.Default
	int size = 12;

	public Field(String name, String font, int size) {
		this.name = name;
		this.font = font;
		this.size = size;
	}

	public Field(String name, String font) {
		this(name, font, 12);
	}

	public Field(String name, int size) {
		this(name, "Arial", size);
	}

	public Field(String name) {
		this(name, "Ariel", 12);
	}

	@SneakyThrows
	public String toJson() {
		return new ObjectMapper().writeValueAsString(this);
	}
}

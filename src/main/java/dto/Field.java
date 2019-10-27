package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class Field {
	String name;
	boolean rtl;
	boolean sticky;
	String[] media;
	int ord;
	String font;
	int size;

	public Field() {
		this.rtl = false;
		this.sticky = false;
		this.media = new String[] {};
		this.font = "Ariel";
		this.size = 12;
	}

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

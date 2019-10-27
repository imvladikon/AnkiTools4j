package models;

import dto.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FieldList extends ArrayList<Field> {
	private String format;

	public FieldList() {
	}

	public FieldList(String format) {
		this.format = format;
	}

	public boolean add(Field field) {
		field.setOrd(size());
		return super.add(field);
	}

	public String toJSON() {
		return super.stream().filter(Objects::nonNull)
				.map(Field::toJson)
				.collect(Collectors.joining(",\n"));
	}

	public String toFrontBack() {
		return this.stream()
				.map(f -> "{{" + f.getName() + "}}")
				.collect(Collectors.joining("\\n<hr id=answer />\\n"));
	}

	@Override
	public String toString() {
		return this.stream().map(Field::toString).collect(Collectors.joining("\\n<br>\\n"));
	}

	public String format(String format) {
		final List<Field> array = new ArrayList<>(this);
		for (int i = 0; i < array.size(); ++i) {
			format = format.replace("{" + i + "}", array.get(i).toString());
		}
		return format;
	}
}

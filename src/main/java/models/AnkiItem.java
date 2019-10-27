package models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class AnkiItem {

	Map<String, Object> DICTIONARY = new HashMap<String, Object>();
	@Getter
	@Setter
	public String mid;

	public Object get(String elem) {
		return DICTIONARY.get(elem);
	}

	public int getCount() {
		return DICTIONARY.size();
	}

	public AnkiItem(FieldList fields, String... properties) {
		for (int i = 0; i < properties.length; ++i) {
			DICTIONARY.put(fields.get(i).getName(), properties[i].replace("'", "’"));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AnkiItem)) {
			return false;
		}
		for (Map.Entry pair : this.DICTIONARY.entrySet()) {
			if (((AnkiItem) obj).DICTIONARY.containsKey(pair.getKey())
				&& ((AnkiItem) obj).DICTIONARY.get(pair.getKey()).toString() != pair.getValue()
						.toString())
				return false;
		}
		return true;
	}

}

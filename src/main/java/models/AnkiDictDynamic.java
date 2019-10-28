package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class AnkiDictDynamic<T> {

	Map<String, Object> dictionary = new HashMap<>();

	public Object get(String elem) {
		return dictionary.get(elem);
	}

	public void set(String elem, Object value) {
		dictionary.put(elem, value);
	}

	public int getCount() {
		return dictionary.size();
	}

	public AnkiDictDynamic() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AnkiDictDynamic)) {
			return false;
		}
		for (Map.Entry pair : this.dictionary.entrySet()) {
			if (((AnkiDictDynamic) obj).dictionary.get(pair.getKey()).toString() != pair.getValue()
					.toString())
				return false;
		}
		return true;
	}

	@SneakyThrows
	public T toObject(Class<T> classz) {
		return new ObjectMapper().convertValue(dictionary, classz);
	}
}

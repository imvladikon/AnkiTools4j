import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ApkgFile {
	private String path;

	public String Path() {
		return path;
	}

	/**
	 * Representation of Apkg file
	 * 
	 * @param path-
	 *            path of apkg file
	 */
	@SneakyThrows
	public ApkgFile(String path) {
		if (!path.contains(".apkg")) {
			throw new Exception("Need apkg file");
		}
		if (!Files.exists(Paths.get(path))) {
			throw new Exception("Need existing file");
		}
		this.path = path;
	}
}
package helpers;

import lombok.SneakyThrows;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileUtils {
	private static final String PATH_SEPARATOR = "/";
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final int EOF = -1;

	public static void createFromDirectory(String path, File zipPath) {
		pack(new File(path), zipPath, Deflater.DEFAULT_COMPRESSION);
	}

	public static void createFromDirectory(String path, String zipPath) {
		pack(new File(path), new File(zipPath), Deflater.DEFAULT_COMPRESSION);
	}

	@SneakyThrows
	public static void pack(File sourceDir, File targetZip, int compressionLevel) {
		if (!sourceDir.exists()) {
			throw new Exception("Given file '" + sourceDir + "' doesn't exist!");
		}
		try (ZipOutputStream out =
				new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(targetZip)))) {
			out.setLevel(compressionLevel);
			pack(sourceDir, out, "", true);

		} catch (IOException e) {
			//            throw ZipExceptionUtil.rethrow(e);
		}
	}

	@SneakyThrows
	private static void pack(	File dir,
								ZipOutputStream out,
								String pathPrefix,
								boolean mustHaveChildren) {
		String[] filenames = dir.list();
		if (filenames == null) {
			if (!dir.exists()) {
				throw new Exception("Given file '" + dir + "' doesn't exist!");
			}
			throw new IOException("Given file is not a directory '" + dir + "'");
		}
		if (mustHaveChildren && filenames.length == 0) {
			throw new Exception("Given directory '" + dir + "' doesn't contain any files!");
		}
		for (int i = 0; i < filenames.length; i++) {
			String filename = filenames[i];
			File file = new File(dir, filename);
			boolean isDir = file.isDirectory();
			String path = pathPrefix + file.getName();
			if (isDir) {
				path += PATH_SEPARATOR;
			}
			String name = path;
			ZipEntry zipEntry = new ZipEntry(name);
			if (!file.isDirectory()) {
				zipEntry.setSize(file.length());
			}
			zipEntry.setTime(file.lastModified());
			out.putNextEntry(zipEntry);
			if (!isDir) {
				copy(file, out);
			}
			out.closeEntry();
			if (isDir) {
				pack(file, out, path, false);
			}
		}
	}

	@SneakyThrows
	public static void copy(File file, OutputStream out) {
		FileInputStream in = new FileInputStream(file);
		try (InputStream input = new BufferedInputStream(in)) {
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			long count = 0;
			int n = 0;
			while (EOF != (n = input.read(buffer))) {
				out.write(buffer, 0, n);
				count += n;
			}
		}
	}

	@SneakyThrows
	public static void extractToDirectory(String inputFile, String destination) {
		File target = new File(destination);
		try (ZipInputStream zip = new ZipInputStream(new FileInputStream(inputFile))) {
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				File file = new File(target, entry.getName());
				if (!file.toPath().normalize().startsWith(target.toPath())) {
					throw new IOException("Bad zip entry");
				}
				if (entry.isDirectory()) {
					file.mkdirs();
					continue;
				}
				byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
				file.getParentFile().mkdirs();
				try (BufferedOutputStream out =
						new BufferedOutputStream(new FileOutputStream(file))) {
					int count;
					while ((count = zip.read(buffer)) != -1) {
						out.write(buffer, 0, count);
					}
				}
			}
		}
	}
}

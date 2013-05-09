package openccsensors.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import openccsensors.OpenCCSensors;

public class ResourceExtractingUtils {

	/**
	 * Copies a file from source to destination
	 */
	private static final void copyFile(File source, File destination)
			throws IOException {
		FileInputStream inputStream = new FileInputStream(source);
		FileOutputStream outputStream = new FileOutputStream(destination);
		FileChannel sourceChannel = inputStream.getChannel();
		FileChannel targetChannel = outputStream.getChannel();
		sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
		sourceChannel.close();
		targetChannel.close();
		inputStream.close();
		outputStream.close();
	}
	/**
	 * Recursively copies a directory from source to destination including
	 * all the files within the folders
	 */
	public static final void copyDirectory(File source, File destination)
			throws IOException {
		if (!source.isDirectory()) {
			throw new IllegalArgumentException("Source (" + source.getPath()
					+ ") must be a directory.");
		}

		if (!source.exists()) {
			throw new IllegalArgumentException("Source directory ("
					+ source.getPath() + ") doesn't exist.");
		}

		destination.mkdirs();
		File[] files = source.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				copyDirectory(file, new File(destination, file.getName()));
			} else {
				copyFile(file, new File(destination, file.getName()));
			}
		}
	}
	public static final void copy(File source, File destination)
			throws IOException {
		if (source.isDirectory()) {
			copyDirectory(source, destination);
		} else {
			copyFile(source, destination);
		}
	}

	/**
	 * Extract a zip in sourceFolder into destFolder
	 */
	public static void extractZipToLocation(File zipFile, String sourceFolder,
			String destFolder) {
		try {
			sourceFolder = sourceFolder.substring(1) + "/";
			File destFile = new File(OpenCCSensors.proxy.getBase(), destFolder);
			String destinationName = destFile.getAbsolutePath();
			byte[] buf = new byte[1024];
			ZipInputStream zipinputstream = null;
			ZipEntry zipentry;
			zipinputstream = new ZipInputStream(new FileInputStream(zipFile));
			zipentry = zipinputstream.getNextEntry();
			while (zipentry != null) {
				// for each entry to be extracted
				String zipentryName = zipentry.getName();
				
				if (!zipentryName.startsWith(sourceFolder)) {
					zipentry = zipinputstream.getNextEntry();
					continue;
				}
				
				String entryName = destinationName
						+ zipentryName.substring(Math.min(
								zipentryName.length(),
								sourceFolder.length() - 1));
				entryName = entryName.replace('/', File.separatorChar);
				entryName = entryName.replace('\\', File.separatorChar);
				int n;
				FileOutputStream fileoutputstream;
				File newFile = new File(entryName);
				if (zipentry.isDirectory()) {
					if (!newFile.mkdirs()) {
						break;
					}
					zipentry = zipinputstream.getNextEntry();
					continue;
				}

				fileoutputstream = new FileOutputStream(entryName);

				while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
					fileoutputstream.write(buf, 0, n);
				}

				fileoutputstream.close();
				zipinputstream.closeEntry();
				zipentry = zipinputstream.getNextEntry();

			}// while

			zipinputstream.close();
		} catch (Exception e) {
			OCSLog.warn("Error while extracting Lua files. Peripheral may not automount Lua files! Exception follows.");
			e.printStackTrace();
		}
	}

}

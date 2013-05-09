package openccsensors.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import openccsensors.OpenCCSensors;
import openccsensors.common.CommonProxy;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class LanguageUtils {
	public static void setupLanguages() {

		ArrayList arrayList = new ArrayList();

		try {
			InputStream input = CommonProxy.class.getResourceAsStream(String
					.format("%s/languages.txt", OpenCCSensors.LANGUAGE_PATH));

			if (input == null) {
				OCSLog.warn("Cannot load languages.txt. Names may not be displayed correctly.");
				return;
			}

			BufferedReader var2 = new BufferedReader(new InputStreamReader(
					input, "UTF-8"));

			for (String var3 = var2.readLine(); var3 != null; var3 = var2
					.readLine()) {
				arrayList.add(var3);
			}
		} catch (IOException var5) {
			OCSLog.warn("Cannot load languages.txt. Names may not be displayed correctly. Stack trace follows.");
			var5.printStackTrace();
			return;
		}

		Iterator iterator = arrayList.iterator();

		while (iterator.hasNext()) {
			String langString = (String) iterator.next();
			URL url = CommonProxy.class.getResource(String.format("%s/%s.lang",
					OpenCCSensors.LANGUAGE_PATH, langString));
			if (url == null) {
				OCSLog.warn(
						"Skipping loading of language %s - language file not found.",
						langString);
				continue;
			}
			LanguageRegistry.instance()
					.loadLocalization(url, langString, false);
		}
	}
}

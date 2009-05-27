package ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageCache
{
	private static Map<URL, Image>	map	= new HashMap<URL, Image>();

	public static Image get(URL url)
	{
		if (map.containsKey(url))
			return map.get(url);

		Image toStore = Toolkit.getDefaultToolkit().createImage(url);
		map.put(url, toStore);
		return toStore;
	}
}

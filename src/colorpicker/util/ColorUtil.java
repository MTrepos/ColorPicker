package colorpicker.util;

import java.awt.Color;

public class ColorUtil {

	public static String toHTMLColorCode(Color c) {
		StringBuffer sb = new StringBuffer("#");

		sb.append(String.format("%02X", c.getRed()));
		sb.append(String.format("%02X", c.getGreen()));
		sb.append(String.format("%02X", c.getBlue()));

		return sb.toString();
	}

}

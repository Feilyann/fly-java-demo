package com.fly.demo.util;

import java.awt.*;

/**
 * @project: creatar-sde
 * @package: com.creatar.sde.sdk.util
 * @author: feilongyan
 * @time: 2024/8/28 0028
 * @description: TODO
 * @version: 1.0
 */
public class ColorUtil {

    public static int bgrToRgb(int colorInt) {
        return ((colorInt >> 16) & 0XFF)
                | (((colorInt >> 8) & 0XFF) << 8)
                | ((colorInt & 0XFF) << 16)
                | (((colorInt >> 24) & 0XFF) << 24);
    }

    public static String bgrToHexRgb(int colorInt) {
        return rgbToHexRgb(bgrToRgb(colorInt));
    }

    public static String rgbToHexRgb(int rgba) {
//        int a = (rgba >> 24) & 0xFF;
        int r = (rgba >> 16) & 0xFF;
        int g = (rgba >> 8) & 0xFF;
        int b = rgba & 0xFF;
        return String.format("#%02X%02X%02X", r, g, b);
    }

    public static int hexRgbToRgb(String hex) {
        return (int) Long.parseLong(hex.replaceAll("(0[x|X])|[#]", ""), 16);
    }

    public static Color hexRgbToColor(String hex) {
        long i = Long.parseLong(hex.replaceAll("(0[x|X])|[#]", ""), 16);
        return new Color((int) i);
    }
}

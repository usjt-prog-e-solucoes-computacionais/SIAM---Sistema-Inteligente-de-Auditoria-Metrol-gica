package br.com.siam.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public final class IconUtils {

    private IconUtils() {
    }

    public static Image getApplicationIcon() {

        URL iconUrl =
                IconUtils.class.getResource(
                        "/images/siam-icon.png"
                );

        if (iconUrl == null) {
            return null;
        }

        return new ImageIcon(iconUrl).getImage();
    }

    public static ImageIcon getScaledLogo(
            int width,
            int height
    ) {

        URL iconUrl =
                IconUtils.class.getResource(
                        "/images/siam-icon.png"
                );

        if (iconUrl == null) {
            return null;
        }

        Image image =
                new ImageIcon(iconUrl)
                        .getImage()
                        .getScaledInstance(
                                width,
                                height,
                                Image.SCALE_SMOOTH
                        );

        return new ImageIcon(image);
    }
}
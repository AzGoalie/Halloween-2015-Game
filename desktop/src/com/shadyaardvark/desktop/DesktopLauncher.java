package com.shadyaardvark.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.shadyaardvark.SpookyDDR;
import com.shadyaardvark.utils.Constants;

public class DesktopLauncher {
    public static void main(String[] arg) {

        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        TexturePacker.process(settings, "../images", "../assets", "images");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = Constants.SCREEN_WIDTH;
        config.height = Constants.SCREEN_HEIGHT;
        new LwjglApplication(new SpookyDDR(), config);
    }
}

package com.shadyaardvark.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureAtlas atlas;
    public static TextureRegion upArrow;
    public static TextureRegion downArrow;
    public static TextureRegion leftArrow;
    public static TextureRegion rightArrow;

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("images.atlas"));
        upArrow = atlas.findRegion("arrowUp");
        downArrow = atlas.findRegion("arrowDown");
        leftArrow = atlas.findRegion("arrowLeft");
        rightArrow = atlas.findRegion("arrowRight");
    }

    public static void dispose() {
        atlas.dispose();
    }
}

package com.shadyaardvark;

import com.badlogic.gdx.Game;
import com.shadyaardvark.screens.SpookyScreen;
import com.shadyaardvark.utils.Assets;

public class SpookyDDR extends Game {

    @Override
    public void create() {
        Assets.load();
        setScreen(new SpookyScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
    }
}

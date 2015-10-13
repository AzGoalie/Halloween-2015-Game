package com.shadyaardvark;

import com.badlogic.gdx.Game;
import com.shadyaardvark.screens.SpookyScreen;

public class SpookyDDR extends Game {

    @Override
    public void create() {
        setScreen(new SpookyScreen(this));
    }
}

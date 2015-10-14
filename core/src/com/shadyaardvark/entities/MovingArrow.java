package com.shadyaardvark.entities;

import static com.shadyaardvark.utils.Constants.OFF_SCREEN_Y;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.screens.SpookyScreen;
import com.shadyaardvark.utils.Constants;

public class MovingArrow extends Arrow {
    public static float arrowSpeed = 100;
    public static Array<MovingArrow> arrows = new Array<MovingArrow>();

    public MovingArrow(Direction direction) {
        super(direction);
        setColor(Color.WHITE);
        setPosition(getX(), Constants.START_HEIGHT);
        arrows.add(this);
    }

    public void update(float delta) {
        setPosition(getX(), getY() + (arrowSpeed * delta));
        if (getY() > OFF_SCREEN_Y) {
            arrows.removeValue(this, true);
            remove();
            SpookyScreen.combo = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        update(Gdx.graphics.getDeltaTime());
    }

    public void destory() {
        arrows.removeValue(this, true);
        remove();
    }
}

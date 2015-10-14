package com.shadyaardvark.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.shadyaardvark.utils.Assets;
import com.shadyaardvark.utils.Constants;

public class Arrow extends Actor {
    protected TextureRegion arrow;
    protected Direction direction;

    public Arrow(Direction direction) {
        this.direction = direction;

        switch (direction) {
        case UP:
            arrow = Assets.upArrow;
            setPosition(Constants.UP_POSITION, Constants.HIT_HEIGHT);
            break;
        case DOWN:
            arrow = Assets.downArrow;
            setPosition(Constants.DOWN_POSITION, Constants.HIT_HEIGHT);
            break;
        case LEFT:
            arrow = Assets.leftArrow;
            setPosition(Constants.LEFT_POSITION, Constants.HIT_HEIGHT);
            break;
        case RIGHT:
            arrow = Assets.rightArrow;
            setPosition(Constants.RIGHT_POSITION, Constants.HIT_HEIGHT);
            break;
        }
        setColor(Color.DARK_GRAY);
        setWidth(arrow.getRegionWidth());
        setHeight(arrow.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(arrow, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), arrow.getRegionWidth(), arrow.getRegionHeight());
    }

    public Direction getDirection() {
        return direction;
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}

package com.shadyaardvark.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.shadyaardvark.utils.Assets;
import com.shadyaardvark.utils.Constants;

public class Arrow extends Actor {
    protected Sprite arrow;
    protected Direction direction;

    public Arrow(Direction direction) {
        this.direction = direction;

        switch (direction) {
        case UP:
            arrow = new Sprite(Assets.upArrow);
            setPosition(Constants.UP_POSITION, Constants.HIT_HEIGHT);
            break;
        case DOWN:
            arrow = new Sprite(Assets.downArrow);
            setPosition(Constants.DOWN_POSITION, Constants.HIT_HEIGHT);
            break;
        case LEFT:
            arrow = new Sprite(Assets.leftArrow);
            setPosition(Constants.LEFT_POSITION, Constants.HIT_HEIGHT);
            break;
        case RIGHT:
            arrow = new Sprite(Assets.rightArrow);
            setPosition(Constants.RIGHT_POSITION, Constants.HIT_HEIGHT);
            break;
        }
        setColor(Color.DARK_GRAY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        arrow.draw(batch, parentAlpha);
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), arrow.getRegionWidth(), arrow.getRegionHeight());
    }

    @Override
    public void setScale(float scaleXY) {
        arrow.setScale(scaleXY);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        arrow.setPosition(x, y);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        arrow.setColor(color);
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        arrow.setRotation(degrees);
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

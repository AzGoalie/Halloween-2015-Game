package com.shadyaardvark.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.SpookyDDR;

public class SpookyScreen implements Screen {
    private final float LEFT_POSITION = 200;
    private final float DOWN_POSITION = 300;
    private final float UP_POSITION = 400;
    private final float RIGHT_POSITION = 500;

    private final float HIT_HEIGHT = 475;
    private final float START_HEIGHT = -75;
    private final float INTERVAL = .25f;

    private SpookyDDR game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture upArrow;
    private Texture downArrow;
    private Texture leftArrow;
    private Texture rightArrow;

    private Sprite leftHit;
    private Sprite downHit;
    private Sprite upHit;
    private Sprite rightHit;

    private Array<Sprite> arrows;
    private float arrowSpeed = 100;
    private float arrowInterval = 1f;
    private float nextArrowTime = 0;
    private float lastAccuracy = 0;
    private int score = 0;
    private int combo = 0;

    public SpookyScreen(SpookyDDR game) {
        this.game = game;
        batch = new SpriteBatch();
        arrows = new Array<Sprite>();

        font = new BitmapFont();

        Gdx.input.setInputProcessor(new MyInputHandler());

        upArrow = new Texture(Gdx.files.internal("arrowUp.png"));
        downArrow = new Texture(Gdx.files.internal("arrowDown.png"));
        leftArrow = new Texture(Gdx.files.internal("arrowLeft.png"));
        rightArrow = new Texture(Gdx.files.internal("arrowRight.png"));

        upArrow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        downArrow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        leftArrow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rightArrow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        upHit = new Sprite(upArrow);
        downHit = new Sprite(downArrow);
        leftHit = new Sprite(leftArrow);
        rightHit = new Sprite(rightArrow);

        upHit.setAlpha(.5f);
        downHit.setAlpha(.5f);
        leftHit.setAlpha(.5f);
        rightHit.setAlpha(.5f);

        leftHit.setPosition(LEFT_POSITION, HIT_HEIGHT);
        downHit.setPosition(DOWN_POSITION, HIT_HEIGHT);
        upHit.setPosition(UP_POSITION, HIT_HEIGHT);
        rightHit.setPosition(RIGHT_POSITION, HIT_HEIGHT);
    }

    private void spawnArrow() {
        int type = MathUtils.random(0, 3);
        Sprite arrow;

        if (type == 0) {
            arrow = new Sprite(upArrow);
            arrow.setPosition(UP_POSITION, START_HEIGHT);
        } else if (type == 1) {
            arrow = new Sprite(downArrow);
            arrow.setPosition(DOWN_POSITION, START_HEIGHT);
        } else if (type == 2) {
            arrow = new Sprite(leftArrow);
            arrow.setPosition(LEFT_POSITION, START_HEIGHT);
        } else {
            arrow = new Sprite(rightArrow);
            arrow.setPosition(RIGHT_POSITION, START_HEIGHT);
        }

        arrows.add(arrow);
    }

    private void update(float delta) {
        for (Sprite arrow : arrows) {
            arrow.setPosition(arrow.getX(), arrow.getY() + (arrowSpeed * delta));
            if (arrow.getY() > 700) {
                arrows.removeValue(arrow, true);
                combo = 0;
                //arrow.setPosition(arrow.getX(), START_HEIGHT);
            }
        }

        if (nextArrowTime <= 0) {
            spawnArrow();
            nextArrowTime = arrowInterval;
        }

        nextArrowTime -= delta;
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.setColor(Color.DARK_GRAY);
        leftHit.draw(batch);
        downHit.draw(batch);
        upHit.draw(batch);
        rightHit.draw(batch);

        batch.setColor(Color.WHITE);
        for (Sprite arrow : arrows) {
            arrow.draw(batch);
        }

        font.draw(batch, "Accuracy: " + lastAccuracy, 25, 575);
        font.draw(batch, "Combo: " + combo, 25, 550);
        font.draw(batch, "Score: " + score, 675, 575);
        font.draw(batch, "Interval: " + arrowInterval, 675, 550);
        font.draw(batch, "Speed: " + arrowSpeed, 675, 525);
        batch.end();

        update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        upArrow.dispose();
        downArrow.dispose();
        leftArrow.dispose();
        rightArrow.dispose();
    }

    private class MyInputHandler extends InputAdapter {
        @Override
        public boolean keyDown(int keycode) {

            switch (keycode) {
            case Input.Keys.LEFT:
                for (Sprite s : arrows) {
                    if (s.getX() == LEFT_POSITION && leftHit.getBoundingRectangle()
                            .overlaps(s.getBoundingRectangle())) {
                        arrowHit(s);
                    }
                }
                leftHit.setScale(1.25f);
                break;
            case Input.Keys.RIGHT:
                for (Sprite s : arrows) {
                    if (s.getX() == RIGHT_POSITION && rightHit.getBoundingRectangle()
                            .overlaps(s.getBoundingRectangle())) {
                        arrowHit(s);
                    }
                }
                rightHit.setScale(1.25f);
                break;
            case Input.Keys.UP:
                for (Sprite s : arrows) {
                    if (s.getX() == UP_POSITION && upHit.getBoundingRectangle()
                            .overlaps(s.getBoundingRectangle())) {
                        arrowHit(s);
                    }
                }
                upHit.setScale(1.25f);
                break;
            case Input.Keys.DOWN:
                for (Sprite s : arrows) {
                    if (s.getX() == DOWN_POSITION && downHit.getBoundingRectangle()
                            .overlaps(s.getBoundingRectangle())) {
                        arrowHit(s);
                    }
                }
                downHit.setScale(1.25f);
                break;
            case Input.Keys.Z:
                arrowSpeed += INTERVAL * 100;
                break;
            case Input.Keys.X:
                arrowSpeed -= INTERVAL * 100;
                break;
            case Input.Keys.A:
                arrowInterval += INTERVAL;
                break;
            case Input.Keys.S:
                arrowInterval -= INTERVAL;
                break;
            }

            return super.keyDown(keycode);
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
            case Input.Keys.LEFT:
                leftHit.setScale(1.0f);
                break;
            case Input.Keys.RIGHT:
                rightHit.setScale(1.0f);
                break;
            case Input.Keys.UP:
                upHit.setScale(1.0f);
                break;
            case Input.Keys.DOWN:
                downHit.setScale(1.0f);
                break;
            }

            return super.keyDown(keycode);
        }

        private void arrowHit(Sprite s) {
            lastAccuracy =
                    (HIT_HEIGHT < s.getY() ? HIT_HEIGHT / s.getY() : s.getY() / HIT_HEIGHT) * 100;

            combo++;
            if (lastAccuracy == 100) {
                score += 1000000 * combo;
            } else if (lastAccuracy >= 98) {
                score += 300 * combo;
            } else if (lastAccuracy >= 95) {
                score += 200 * combo;
            } else if (lastAccuracy >= 90) {
                combo = 0;
                score += 100;
            } else {
                combo = 0;
                score += 50;
            }

            arrows.removeValue(s, true);
        }
    }
}
package com.shadyaardvark.screens;

import static com.shadyaardvark.utils.Constants.HIT_HEIGHT;
import static com.shadyaardvark.utils.Constants.INTERVAL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.shadyaardvark.SpookyDDR;
import com.shadyaardvark.entities.Arrow;
import com.shadyaardvark.entities.MovingArrow;
import com.shadyaardvark.utils.Constants;

public class SpookyScreen implements Screen {
    public static float arrowInterval = 1f;

    public static float nextArrowTime = 0;

    public static float lastAccuracy = 0;

    public static int score = 0;

    public static int combo = 0;

    private SpookyDDR game;

    private Stage stage;

    private SpriteBatch batch;

    private BitmapFont font;

    private Arrow leftHit;

    private Arrow downHit;

    private Arrow upHit;

    private Arrow rightHit;

    public SpookyScreen(SpookyDDR game) {
        this.game = game;
        stage = new Stage(new ExtendViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));

        batch = new SpriteBatch();
        font = new BitmapFont();

        Gdx.input.setInputProcessor(new MyInputHandler());

        upHit = new Arrow(Arrow.Direction.UP);
        downHit = new Arrow(Arrow.Direction.DOWN);
        leftHit = new Arrow(Arrow.Direction.LEFT);
        rightHit = new Arrow(Arrow.Direction.RIGHT);

        stage.addActor(upHit);
        stage.addActor(downHit);
        stage.addActor(leftHit);
        stage.addActor(rightHit);
    }

    private void spawnArrow() {
        int type = MathUtils.random(0, 3);
        MovingArrow arrow;

        if (type == 0) {
            arrow = new MovingArrow(Arrow.Direction.UP);
        } else if (type == 1) {
            arrow = new MovingArrow(Arrow.Direction.DOWN);
        } else if (type == 2) {
            arrow = new MovingArrow(Arrow.Direction.LEFT);
        } else {
            arrow = new MovingArrow(Arrow.Direction.RIGHT);
        }
        stage.addActor(arrow);
    }

    private void update(float delta) {
        if (nextArrowTime <= 0) {
            spawnArrow();
            nextArrowTime = arrowInterval;
        }
        nextArrowTime -= delta;

        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        batch.begin();
        font.draw(batch, "Accuracy: " + lastAccuracy, 25, 575);
        font.draw(batch, "Combo: " + combo, 25, 550);
        font.draw(batch, "Score: " + score, 675, 575);
        font.draw(batch, "Interval: " + arrowInterval, 675, 550);
        font.draw(batch, "Speed: " + MovingArrow.arrowSpeed, 675, 525);
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
        stage.dispose();
    }

    private class MyInputHandler extends InputAdapter {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
            case Input.Keys.LEFT:
                for (MovingArrow s : MovingArrow.arrows) {
                    if (s.getDirection() == Arrow.Direction.LEFT && leftHit.getBoundingRectangle()
                            .overlaps(s.getBoundingRectangle())) {
                        arrowHit(s);
                    }
                }
                leftHit.setScale(1.25f);
                break;
            case Input.Keys.RIGHT:
                for (MovingArrow s : MovingArrow.arrows) {
                    if (s.getDirection() == Arrow.Direction.RIGHT && rightHit.getBoundingRectangle()
                            .overlaps(s.getBoundingRectangle())) {
                        arrowHit(s);
                    }
                }
                rightHit.setScale(1.25f);
                break;
            case Input.Keys.UP:
                for (MovingArrow s : MovingArrow.arrows) {
                    if (s.getDirection() == Arrow.Direction.UP && upHit.getBoundingRectangle()
                            .overlaps(s.getBoundingRectangle())) {
                        arrowHit(s);
                    }
                }
                upHit.setScale(1.25f);
                break;
            case Input.Keys.DOWN:
                for (MovingArrow s : MovingArrow.arrows) {
                    if (s.getDirection() == Arrow.Direction.DOWN && downHit.getBoundingRectangle()
                            .overlaps(s.getBoundingRectangle())) {
                        arrowHit(s);
                    }
                }
                downHit.setScale(1.25f);
                break;
            case Input.Keys.Z:
                MovingArrow.arrowSpeed += INTERVAL * 100;
                break;
            case Input.Keys.X:
                MovingArrow.arrowSpeed -= INTERVAL * 100;
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

        private void arrowHit(MovingArrow s) {
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

            s.destory();
        }
    }
}
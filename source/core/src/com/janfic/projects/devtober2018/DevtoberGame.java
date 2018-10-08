package com.janfic.projects.devtober2018;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.janfic.projects.devtober2018.world.World;
import com.janfic.projects.devtober2018.world.World.WorldType;

public class DevtoberGame extends ApplicationAdapter {

    private AssetManager assetManager;
    World world;
    SpriteBatch batch;
    Texture cube, fullWater, water_1, water_2, water_3;

    OrthographicCamera cam;
    Viewport viewport;

    private final int TILE_WIDTH = 24, TILE_HEIGHT = 12;
    public final static int WIDTH = 1200, HEIGHT = 800;
    public static final float ZOOM = 2f;

    @Override
    public void create() {
        cam = new OrthographicCamera(WIDTH / ZOOM, HEIGHT / ZOOM);
        viewport = new FitViewport(WIDTH / ZOOM, HEIGHT / ZOOM, cam);
        assetManager = new AssetManager();
        assetManager.load("world/cube_out.png", Texture.class);
        assetManager.load("world/water/water_4_out.png", Texture.class);
        assetManager.load("world/water/water_3_out.png", Texture.class);
        assetManager.load("world/water/water_2_out.png", Texture.class);
        assetManager.load("world/water/water_1_out.png", Texture.class);
        world = new World(3, 2, 0, WorldType.FLAT, 0.01d);
        while (!assetManager.update()) {
           
        }
        batch = new SpriteBatch();
        cube = assetManager.get("world/cube_out.png");
        fullWater = assetManager.get("world/water/water_4_out.png");
        water_1 = assetManager.get("world/water/water_1_out.png");
        water_2 = assetManager.get("world/water/water_2_out.png");
        water_3 = assetManager.get("world/water/water_3_out.png");
    }

    float delta;

    @Override
    public void render() {
        delta += Gdx.graphics.getDeltaTime();
        if (delta > 0.05f) {
            if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
                world.addWater(world.getSize() / 2, world.getSize() / 2, World.PARTS_PER_UNIT);
                //world.addWater( World.PARTS_PER_UNIT);
            }

            world.updateWater();
            delta = 0;
        }
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
//                world.addWater(world.getSize() / 2, world.getSize() / 2, World.PARTS_PER_UNIT);
            world.addWater(World.PARTS_PER_UNIT);
        }
        if (Gdx.input.isKeyJustPressed(Keys.SHIFT_RIGHT)) {
            if (world.getWater(world.getSize() / 2, world.getSize() / 2) > World.PARTS_PER_UNIT) {
                world.addWater(world.getSize() / 2, world.getSize() / 2, -World.PARTS_PER_UNIT);
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            cam.translate(0, -1);
        }
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (int y = 0; y < world.getSize(); y++) {
            for (int x = 0; x < world.getSize(); x++) {
                int height = (int) world.getHeight(x, y);
                for (int z = 1; z <= height; z++) {
                    batch.draw(cube, isoXtoScreen(x, y, z), isoYtoScreen(x, y, z));
                }
                for (int z = 1; z <= world.getWater(x, y); z++) {
                    batch.draw(fullWater, isoXtoScreen(x, y, height + z), isoYtoScreen(x, y, height + z));
                }
                if (world.getWater(x, y) % 1f > 0) {
                    //System.out.println(world.getWater(x, y) + " : " + world.getWater(x, y) % 1);
                    int p = (int) ((world.getWater(x, y) % 1) / World.PARTS_PER_UNIT);
                    if (p != 0) {
                        batch.draw(p == 1 ? water_1 : p == 2 ? water_2 : water_3, isoXtoScreen(x, y, height + (int) world.getWater(x, y) + 1), isoYtoScreen(x, y, height + (int) world.getWater(x, y) + 1));
                    }
                }
            }
        }
        batch.end();
    }

    public int isoXtoScreen(int x, int y, int z) {
        return (x * TILE_WIDTH / 2) - (y * TILE_WIDTH / 2);
    }

    public int isoYtoScreen(int x, int y, int z) {
        return -(x + y) * TILE_HEIGHT / 2 + (z * (TILE_HEIGHT - 1));
    }

    @Override
    public void dispose() {
    }
}

package com.janfic.projects.devtober2018;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
    Texture cube, fullWater;

    OrthographicCamera cam;
    Viewport viewport;

    private final int TILE_WIDTH = 24, TILE_HEIGHT = 12;
    public final static int WIDTH = 1200, HEIGHT = 800;

    @Override
    public void create() {
        cam = new OrthographicCamera(WIDTH / 2, HEIGHT / 2);
        viewport = new FitViewport(WIDTH / 2, HEIGHT / 2, cam);
        assetManager = new AssetManager();
        assetManager.load("world/cube_out.png", Texture.class);
        assetManager.load("world/water/water_4_out.png", Texture.class);
        world = new World(10, 1, 0, WorldType.FLAT);
        while (!assetManager.update()) {
            //System.out.println("LOADING ASSETS...");
        }
        batch = new SpriteBatch();
        cube = assetManager.get("world/cube_out.png");
        fullWater = assetManager.get("world/water/water_4_out.png");

        world.addWater(World.PARTS_PER_UNIT);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (int y = 0; y < world.getSize(); y++) {
            for (int x = 0; x < world.getSize(); x++) {
                int height = (int) world.getHeight(x, y);
                for (int z = 1; z <= height; z++) {
                    batch.draw(cube, isoXtoScreen(x, y, z), isoYtoScreen(x, y, z));
                }
                for (int z = 0; z < world.getWater(x, y) / World.PARTS_PER_UNIT; z++) {
                    batch.draw(fullWater, isoXtoScreen(x, y, height + z + 1), isoYtoScreen(x, y, height + z + 1));
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

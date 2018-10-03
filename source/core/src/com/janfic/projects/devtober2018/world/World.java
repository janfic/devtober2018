package com.janfic.projects.devtober2018.world;

import java.util.Random;

/**
 *
 * @author Jan Fic
 */
public class World {

    public static final int PARTS_PER_UNIT = 4;
    
    private Random rand;
    private int[][] terrain;
    private int[][] water;
    private TileData[][] data;
    private int size;

    public World(int w, int mh, int seed, WorldType type) {
        rand = (seed == 0) ? new Random() : new Random(seed);
        size = w;

        terrain = new int[w][w];
        data = new TileData[w][w];
        if (null != type) {
            switch (type) {
                case FLAT:
                    for (int x = 0; x < w; x++) {
                        for (int y = 0; y < w; y++) {
                            data[y][x] = new TileData(mh, 0);
                        }
                    }
                    break;
                case RANDOM:
                    for (int x = 0; x < w; x++) {
                        for (int y = 0; y < w; y++) {
                            data[y][x] = new TileData(rand.nextInt(mh) + 1, 0);;
                        }
                    }
                    break;
                case PERLIN:
                    break;
                default:
                    break;
            }
        }

    }
    
    public void addWater(int parts) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                data[y][x].water += parts;
            }
        }
    }

    public float getHeight(int x, int y) {
        return data[y][x].height;
    }

    public float getWater(int x, int y) {
        return data[y][x].water;
    }

    public enum WorldType {
        FLAT, RANDOM, PERLIN
    }

    public int getSize() {
        return size;
    }

}

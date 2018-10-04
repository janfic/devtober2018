package com.janfic.projects.devtober2018.world;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jan Fic
 */
public class World {

    public static final float PARTS_PER_UNIT = 0.25f;

    private Random rand;
    private TileData[][] data;
    private int size;

    public World(int w, int mh, int seed, WorldType type) {
        rand = (seed == 0) ? new Random() : new Random(seed);
        size = w;

        data = new TileData[w][w];
        if (null != type) {
            switch (type) {
                case FLAT:
                    for (int x = 0; x < w; x++) {
                        for (int y = 0; y < w; y++) {
                            data[y][x] = new TileData(x, y, mh, 0);
                        }
                    }
                    break;
                case RANDOM:
                    for (int x = 0; x < w; x++) {
                        for (int y = 0; y < w; y++) {
                            data[y][x] = new TileData(x, y, rand.nextInt(mh) + 1, 0);;
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

    public void addWater(float amount) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                data[y][x].water += amount;
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

    public void updateWater() {
        float[][] change = new float[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                change[y][x] = 0;
            }
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                //check if tile has water
                if (data[y][x].water > 0) {
                    ArrayList<TileData> pool = new ArrayList<TileData>();
                    pool.add(data[y][x]);
                    if (x < size - 1 && data[y][x].getTotalHeight() > data[y][x + 1].getTotalHeight()) {
                        pool.add(data[y][x + 1]);
                    }
                    if (x > 0 && data[y][x].getTotalHeight() > data[y][x - 1].getTotalHeight()) {
                        pool.add(data[y][x - 1]);
                    }
                    if (y < size - 1 && data[y][x].getTotalHeight() > data[y + 1][x].getTotalHeight()) {
                        pool.add(data[y + 1][x]);
                    }
                    if (y > 0 && data[y][x].getTotalHeight() > data[y - 1][x].getTotalHeight()) {
                        pool.add(data[y - 1][x]);
                    }

                    boolean doRandom = false;
                    float maxHeight = 0;
                    if (data[y][x].water > (float) (pool.size() * PARTS_PER_UNIT)) {

                        for (TileData tile : pool) {
                            if (tile.getTotalHeight() + 1 > data[y][x].getTotalHeight() - pool.size() * PARTS_PER_UNIT) {
                                doRandom = true;
                            }
                            maxHeight = tile.getTotalHeight();
                        }
                    } else {
                        doRandom = true;
                    }
                    if (data[y][x].water > PARTS_PER_UNIT) {
                        if (doRandom) {
                            Random rand = new Random();
                            int index = rand.nextInt(pool.size());
                            change[y][x] -= PARTS_PER_UNIT;
                            change[pool.get(index).y][pool.get(index).x] += PARTS_PER_UNIT;
                            pool.remove(index);
                        } else {
                            change[y][x] -= pool.size() * PARTS_PER_UNIT;
                            for (TileData tileData : pool) {
                                change[tileData.y][tileData.x] += PARTS_PER_UNIT;
                            }
                        }
                    }
                    if (data[y][x].water <= PARTS_PER_UNIT) {
                        ArrayList<TileData> eligable = new ArrayList<TileData>(pool);
                        for (TileData tileData : pool) {
                            if (!(tileData.getTotalHeight() < data[y][x].height)) {
                                eligable.remove(tileData);
                            }
                        }
                        if (eligable.size() > 0) {
                            Random rand = new Random();
                            int index = rand.nextInt(eligable.size());
                            change[y][x] -= PARTS_PER_UNIT;
                            change[eligable.get(index).y][eligable.get(index).x] += PARTS_PER_UNIT;
                            pool.remove(index);
                        }
                    }
                }
            }
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                data[y][x].water += change[y][x];
            }
        }
        for (int i = 0; i < change.length; i++) {
            //System.out.println(Arrays.toString(change[i]));
        }
        //System.out.println("---");

    }

    public void addWater(int x, int y, float amount) {
        data[y][x].water += amount;
    }
}

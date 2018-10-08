package com.janfic.projects.devtober2018.world;

/**
 *
 * @author Jan Fic
 */
public class TileData {

    public int x, y;
    public float height;
    public float water;
    public int[] current;
    public float fountainStrength;

    public TileData(int x, int y, float height, float water, float fountainStrength) {
        this.height = height;
        this.water = water;
        this.x = x;
        this.y = y;
        this.current = new int[]{0, 0};
        this.fountainStrength = fountainStrength;
    }

    public float getTotalHeight() {
        return height + water;
    }

    public enum TileType {
        NORMAL, FOUNTAIN, DRAIN
    }

}

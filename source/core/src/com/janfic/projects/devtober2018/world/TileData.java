package com.janfic.projects.devtober2018.world;

/**
 *
 * @author Jan Fic
 */
public class TileData {

    public int x, y;
    public float height;
    public float water;

    public TileData(int x, int y,float height, float water) {
        this.height = height;
        this.water = water;
        this.x = x;
        this.y = y;
    }
    
    public float getTotalHeight() {
        return height + water;
    }

}

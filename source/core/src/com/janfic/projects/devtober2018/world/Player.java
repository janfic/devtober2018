package com.janfic.projects.devtober2018.world;

/**
 *
 * @author Jan Fic
 */
public class Player {

    int x, y;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        x += dy;
        y += dy;
    }
}

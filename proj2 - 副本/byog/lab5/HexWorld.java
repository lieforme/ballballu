package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.PropertyPermission;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int SEIZE = 3;

    private static final long SEED = 20000413;
    private static final Random RANDOM = new Random(SEED);

    private static class Position {
        int x;
        int y;
        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        private void Change(int dx, int dy) {
            this.x += dx;
            this.y += dy;
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        fillWithWorld(world, p, s, t);

    }

    private static void fillWithWorld(TETile[][] world, Position p, int s, TETile t) {
        for(int i = 0; i < s; i++) {
            for(int j = -1 * i; j < s + i; j++) {
                world[p.x + j][p.y + i] = t;
                world[p.x + j][p.y + s * 2 -1 - i] = t;
            }
        }
    }

    private static void Init(TETile[][] world) {
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.FLOOR;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.LOCKED_DOOR;
            default: return Tileset.GRASS;
        }
    }

    public static void tesselation (Position p, TETile[][] world, int s, int direction){
        if (s == 0) return;
        Position pp = new Position(p.x, p.y);

        for(int j = 0; j < SEIZE + (s-1); j++) {
                addHexagon(world, pp, SEIZE, randomTile());
                pp.Change(0,SEIZE * 2);
        }

        if (direction == 0) {
            tesselation(new Position(p.x + SEIZE * 2 - 1, p.y + SEIZE), world, s - 1, 1);
            tesselation(new Position(p.x - SEIZE * 2 + 1, p.y + SEIZE), world, s - 1, -1);
        }
        else if(direction == 1) {
            tesselation(new Position(p.x + SEIZE * 2 - 1, p.y + SEIZE), world, s - 1, 1);
        }
        else {
            tesselation(new Position(p.x - SEIZE * 2 + 1, p.y + SEIZE), world, s - 1, -1);
        }
    }

    public static void main(String args[]) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Init(world);

        Position origin = new Position(WIDTH / 2 - 1, 0);
        tesselation(origin, world, SEIZE, 0);

        ter.renderFrame(world);
    }
}

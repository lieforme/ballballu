package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldGenerator {
    private int width;
    private  int height;

    private TETile[][] world;
    private static Random RANDOM;

    List<Position> unrelatedLB = new LinkedList<>();
    List<Position> unrelatedRU = new LinkedList<>();

    public static class Position {
        int x;
        int y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /* input the position of locked door */
    public WorldGenerator(int w, int h, long seed) {
        width = w;
        height = h;
        world = new TETile[width][height];
        RANDOM = new Random(seed);
        Position startPoint;
        startPoint = new Position(RANDOM.nextInt(w), RANDOM.nextInt(h));

        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) world[i][j] = Tileset.NOTHING;
        }

//        world[startPoint.x][startPoint.y] = Tileset.LOCKED_DOOR;
        makeWorldES(new Position(startPoint.x, startPoint.y - 1), new Position(width - 1, 0));
        makeWorldEN(new Position(startPoint.x, startPoint.y), new Position(width - 1, height - 1));
        makeWorldWN(new Position(startPoint.x - 1, startPoint.y + 1), new Position(0, height - 1));
        makeWorldWS(new Position(startPoint.x - 1, startPoint.y), new Position(0, 0));
    }

    public boolean check( Position leftBottom, Position rightUpper ) {
        for (int i = leftBottom.x; i <= rightUpper.x; i++) {
            for (int j = leftBottom.y; j <= rightUpper.y; j++) {
                if (world[i][j] == Tileset.WALL || world[i][j] == Tileset.FLOOR)  return false;
            }
        }
        return true;
    }

    public void makeRoom(Position leftBottom, Position rightUpper) {
        if( rightUpper.x - leftBottom.x < 2 || rightUpper.y - leftBottom.y < 2 ) return;
        if(check(leftBottom, rightUpper) == false) return;

        for(int i = leftBottom.x; i <= rightUpper.x; i++) {
            for(int j = leftBottom.y; j <= rightUpper.y; j++) {
                if((i == leftBottom.x || i == rightUpper.x
                        || j == leftBottom.y || j == rightUpper.y)) {
                    if(world[i][j] != Tileset.FLOWER)  world[i][j] = Tileset.WALL;
                }
                else world[i][j] = Tileset.FLOOR;
            }
        }
        if(unrelatedLB.isEmpty() == true){
            unrelatedLB.add(leftBottom);
            unrelatedRU.add(rightUpper);
        } else {
            int flag = 0;
            for(int i = 0; i < unrelatedLB.size(); i++) {
                if(makeWay(unrelatedLB.get(i), unrelatedRU.get(i), leftBottom, rightUpper) == true) {
                    unrelatedRU.remove(i);
                    unrelatedLB.remove(i);
                    flag = 1;
                    break;
                }
            }
            if(flag == 0) {
                unrelatedLB.add(leftBottom);
                unrelatedRU.add(rightUpper);
            }
        }
    }


    private boolean makeWay(Position LB1, Position RU1, Position LB2, Position RU2) {
        //TODO:MAKE 1 BE ON THE LEFT OF 2
        Position leftLB, leftRU, rightLB, rightRU;
        if(LB1.x < LB2.x) {
            leftLB = LB1;
            leftRU = RU1;
            rightLB = LB2;
            rightRU = RU2;
        } else {
            leftLB = LB2;
            leftRU = RU2;
            rightLB = LB1;
            rightRU = RU1;
        }
        if((rightLB.x > leftRU.x && rightRU.y < leftLB.y) || (rightLB.x > leftRU.x && rightLB.y > leftRU.y)) return false;

        //make horizontal way
        if(leftRU.y > rightLB.y && leftRU.y < rightRU.y) {
            int interval = RANDOM.nextInt(leftRU.y - rightLB.y);
            makeHorizontalWay(leftRU.x, rightLB.x, leftRU.y - interval);
        } else if(rightRU.y > leftLB.y && rightRU.y < leftRU.y) {
            int interval = RANDOM.nextInt(rightRU.y - leftLB.y);
            makeHorizontalWay(leftRU.x, rightLB.x, rightRU.y - interval);
        } else return false;
        return true;
    }

    private void makeHorizontalWay(int x1, int x2, int y) {
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);

//        if(check(new Position(minX, y-1), new Position(maxX, y + 1)) == false) return;
        for(int i = minX; i <= maxX + 1; i++) {
            world[i][y] = Tileset.FLOWER;
//            world[i][y-1] = Tileset.WALL;
//            world[i][y+1] = Tileset.WALL;
        }
    }

    @Test
    public void makeWorldES(Position startPoint, Position endPoint) {
        if( startPoint.x >= width - 2 || startPoint.y < 2
                || endPoint.x - startPoint.x < 2 || startPoint.y - endPoint.y < 2) return;

        int roomWidth = RANDOM.nextInt(endPoint.x - startPoint.x + 1);
        int roomHeight = RANDOM.nextInt(- endPoint.y + startPoint.y + 1);
        Position rightUpper = new Position(startPoint.x + roomWidth, startPoint.y);
        Position leftBottom = new Position(startPoint.x, startPoint.y - roomHeight);

        if( RANDOM.nextInt(2) == 1 ) {
            makeRoom(leftBottom, rightUpper);
        }

        if( RANDOM.nextInt(2) == 1 ){
            makeWorldES(new Position(rightUpper.x + 1, rightUpper.y), new Position(width - 1, 0));
            makeWorldES(new Position(leftBottom.x, leftBottom.y - 1), new Position(rightUpper.x, 0));
        } else {
            makeWorldES(new Position(rightUpper.x + 1, rightUpper.y), new Position(width - 1, leftBottom.y));
            makeWorldES(new Position(leftBottom.x, leftBottom.y - 1), new Position(width - 1, 0));
        }
    }

    public void makeWorldEN(Position startPoint, Position endPoint) {
        if( startPoint.x >= width - 2 || startPoint.y >= height - 2
                || endPoint.x - startPoint.x < 2 || endPoint.y - startPoint.y < 2) return;

        int roomWidth = RANDOM.nextInt(endPoint.x - startPoint.x + 1);
        int roomHeight = RANDOM.nextInt(endPoint.y - startPoint.y + 1);
        Position rightUpper = new Position(startPoint.x + roomWidth, startPoint.y + roomHeight);
        Position leftBottom = new Position(startPoint.x, startPoint.y);

        if( RANDOM.nextInt(2) == 1 ) makeRoom(leftBottom, rightUpper);

        if( RANDOM.nextInt(2) == 1 ){
            makeWorldEN(new Position(leftBottom.x, rightUpper.y + 1), new Position(rightUpper.x, height - 1));
            makeWorldEN(new Position(rightUpper.x + 1, leftBottom.y), new Position(width - 1, height - 1));
        } else {
            makeWorldEN(new Position(leftBottom.x + 1, rightUpper.y + 1), new Position(width - 1, height - 1));
            makeWorldEN(new Position(leftBottom.x + 1, leftBottom.y), new Position(width - 1, rightUpper.y));
        }
    }

    public void makeWorldWN(Position startPoint, Position endPoint) {
        if( startPoint.x < 2 || startPoint.y >= height - 2
                || startPoint.x - endPoint.x < 2 || endPoint.y - startPoint.y < 2) return;

        int roomWidth = RANDOM.nextInt(- endPoint.x + startPoint.x + 1);
        int roomHeight = RANDOM.nextInt(endPoint.y - startPoint.y + 1);
        Position rightUpper = new Position(startPoint.x, startPoint.y + roomHeight);
        Position leftBottom = new Position(startPoint.x - roomWidth, startPoint.y);

        if( RANDOM.nextInt(2) == 1 ) makeRoom(leftBottom, rightUpper);

        if( RANDOM.nextInt(2) == 1 ){
            makeWorldWN(new Position(leftBottom.x - 1, leftBottom.y), new Position(0, rightUpper.y));
            makeWorldWN(new Position(rightUpper.x, rightUpper.y + 1), new Position(0, height - 1));
        } else {
            makeWorldWN(new Position(leftBottom.x - 1, leftBottom.y), new Position(0, height - 1));
            makeWorldWN(new Position(rightUpper.x, rightUpper.y + 1), new Position(leftBottom.x, height - 1));
        }
    }

    public void makeWorldWS(Position startPoint, Position endPoint) {
        if( startPoint.x < 2 || startPoint.y >= height - 2
                || startPoint.x - endPoint.x < 2 || - endPoint.y + startPoint.y < 2) return;

        int roomWidth = RANDOM.nextInt(- endPoint.x + startPoint.x + 1);
        int roomHeight = RANDOM.nextInt(- endPoint.y + startPoint.y + 1);
        Position rightUpper = new Position(startPoint.x, startPoint.y);
        Position leftBottom = new Position(startPoint.x - roomWidth, startPoint.y - roomHeight);

        if( RANDOM.nextInt(2) == 1 ) makeRoom(leftBottom, rightUpper);

        if( RANDOM.nextInt(2) == 1 ){
            makeWorldWS(new Position(leftBottom.x - 1, rightUpper.y), new Position(0, leftBottom.y));
            makeWorldWS(new Position(rightUpper.x, leftBottom.y - 1), new Position(0, 0));
        } else {
            makeWorldWS(new Position(leftBottom.x - 1, rightUpper.y), new Position(0, 0));
            makeWorldWS(new Position(rightUpper.x, leftBottom.y - 1), new Position(leftBottom.x, 0));
        }
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);

        WorldGenerator worldgenerator = new WorldGenerator(50, 50, 289919);
        ter.renderFrame(worldgenerator.world);
    }

}

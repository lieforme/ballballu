package byog.Core;

import org.junit.Test;

public class TestWorldGenerator {

    WorldGenerator worldGenerator = new WorldGenerator(50, 50, 20000413);

    @Test
    public void testMakeWorldWS() {
        worldGenerator.makeWorldES(new WorldGenerator.Position(25, 25), new WorldGenerator.Position(49, 0));
    }
}

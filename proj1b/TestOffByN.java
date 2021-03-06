import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByN = new OffByN(5);

    // Your tests go here.
    /** Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/

    @Test
    public void testequalChars() {
        assertTrue(offByN.equalChars('a','f'));
        assertTrue(offByN.equalChars('f','a'));
        assertFalse(offByN.equalChars('f','h'));
    }
}


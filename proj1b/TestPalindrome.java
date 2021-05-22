import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static OffByOne offbyone = new OffByOne();
    static OffByN offbyN = new OffByN(4);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    /* Uncomment this class once you've created your Palindrome class. */

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(null));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("horse"));

    }

    @Test
    public void testisPalindrome_offbyone() {
        assertTrue(palindrome.isPalindrome("flake",offbyone));
        assertFalse(palindrome.isPalindrome("ccc",offbyone));
        assertTrue(palindrome.isPalindrome("detrude",offbyone));
        assertTrue(palindrome.isPalindrome(null,offbyone));
        assertTrue(palindrome.isPalindrome("",offbyone));
    }

    @Test
    public void testisPalindrome_offbyN() {
        assertFalse(palindrome.isPalindrome("aE",offbyN));
        assertTrue(palindrome.isPalindrome(" ",offbyN));
    }
}

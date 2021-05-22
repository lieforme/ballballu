public class Palindrome {

    /** otherwise "non static method cannot be referenced from a static context" **/
// why : You can't call something that doesn't exist. Since you haven't created an object,
// the non-static method doesn't exist yet. A static method (by definition) always exists.

    private static Palindrome palindrome = new Palindrome();

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque<>();
        if (word == null) return result;
        for(int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> DequeOfWord = wordToDeque(word);
        return PalindromeHelper(DequeOfWord);
    }

    private boolean PalindromeHelper(Deque<Character> word) {
        if (word.size() == 1 || word.size() == 0) return true;
        char First = word.removeFirst();
        char Last = word.removeLast();
        if (First != Last ) return false;
        return PalindromeHelper(word);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> DequeOfWord = wordToDeque(word);
        return PalindromeHelper(DequeOfWord, cc);
    }

    private boolean PalindromeHelper(Deque<Character> word, CharacterComparator cc) {
        if(word.size() == 1 || word.size() == 0) return true;
        char First = word.removeFirst();
        char Last = word.removeLast();
        if(cc.equalChars(First, Last) == false) return false;
        return PalindromeHelper(word, cc);
    }
}

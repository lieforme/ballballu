public class GreatestPal {
    private static Palindrome palindrome = new Palindrome();

    private String helper(String s) {
        if (s == null) return null;
        if (palindrome.isPalindrome(s) == true) return s;
        String left = helper(s.substring(1,s.length()));
        String right = helper(s.substring(0, s.length()-1));
        if (left.length() > right.length()) return left;
        return right;
    }

    public static void main(String[] args) {
        GreatestPal gp = new GreatestPal();
        String res = gp.helper("stennet");
        if(res != null) System.out.println(res);
    }
}

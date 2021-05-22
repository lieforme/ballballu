//can't use palindrome.java
public class GreatestPaltwo {
    public String helper(String s) {
        if (s == null) return null;
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length()-1-i)) {
                String right = helper(s.substring(1, s.length()));
                String left = helper(s.substring(0,s.length()-1));
                if (left == null && right == null) return null;
                if(left == null) return right;
                if(right == null) return left;
                if (left.length() >= right.length()) return left;
                else return right;
            }
        }
        return s;
    }

    public static void main(String[] args) {
        GreatestPaltwo gpt = new GreatestPaltwo();
        String res = gpt.helper("abc");
        System.out.println(res);
    }
}

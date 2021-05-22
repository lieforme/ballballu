import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    StudentArrayDeque<Integer> s = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> t = new ArrayDequeSolution<>();

    private  static String message = "";

    private void randomAdd (Double random, Integer a) {
        if (random < 0.5){
            s.addFirst(a);
            t.addFirst(a);
            message += "\naddFirst(" + a + ")";
        } else {
            s.addLast(a);
            t.addLast(a);
            message += "\naddLast(" + ")";
        }
    }

    private void randomRemove (Double random) {
        Integer expected;
        Integer actual;
        if (random < 0.5) {
            expected = t.removeFirst();
            actual = s.removeFirst();
            message += "\nremoveFirst()";
        } else {
            expected = t.removeLast();
            actual = s.removeLast();
            message += "\nremoveLast()";
        }
        assertEquals(message, expected, actual);
    }

    @Test
    public void testStudentArrayDeque() {
        for (Integer i = 0; i < 1000; i++) {
            if (s.isEmpty() == true) {
                double random = StdRandom.uniform();
                randomAdd(random, i);
            } else {
                double random1 = StdRandom.uniform();
                double random2 = StdRandom.uniform();
                if (random1 < 0.5){
                    randomAdd(random2, i);
                } else {
                    randomRemove(random2);
                }
            }
        }
    }
}

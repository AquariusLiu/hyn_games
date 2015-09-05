package com.hyenae.stage2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberGameTest {

    NumberGame g = new NumberGame_LXY_V2();

    @Test
    public void testCountBigger() {
        assertEquals(90000000, g.countBigger("?5???????", 76000000));
        assertEquals(70, g.countBigger("?3?", 300));
        assertEquals(90, g.countBigger("??", 9));
        assertEquals(0, g.countBigger("8?3", 910));
        assertEquals(100, g.countBigger("36?1?8", 236428));
        assertEquals(999, g.countBigger("???", 0));
        assertEquals(899999999, g.countBigger("?????????", 100000000));
        assertEquals(899999990, g.countBigger("?????????", 100000009));
        assertEquals(4, g.countBigger("?", 5));
        assertEquals(9, g.countBigger("1?0", 100));
        assertEquals(10, g.countBigger("1?1", 100));
        assertEquals(10, g.countBigger("1?9999999", 100000009));
        assertEquals(9, g.countBigger("1?0000001", 100000009));

        for (int i = 0; i < 100000; i++) {
            int len = 5;
            String s = Integer.toString(i);
            len = len - s.length();
            while (len > 0) {
                s = "0" + s;
                len--;
            }

            char[] chars = s.toCharArray();
            for (int j = 0; j < len; j++) {
                String s1 = "?" + chars[1] + chars[2] + chars[3] + chars[4];
                String s2 = chars[0] + "?" + chars[2] + chars[3] + chars[4];
                String s3 = chars[0] + chars[1] + "?" + chars[3] + chars[4];
                String s4 = chars[0] + chars[1] + chars[2] + "?" + chars[4];
                String s5 = chars[0] + chars[1] + chars[2] + chars[3] + "?";

                String d1 = "??" + chars[2] + chars[3] + chars[4];
                String d2 = chars[0] + "??" + chars[3] + chars[4];
                String d3 = chars[0] + chars[1] + "??" + chars[4];
                String d4 = chars[0] + chars[1] + chars[2] + "??";

                // 个位
                int c1 = getPositive(9 - Integer.parseInt(chars[4] + ""));

                // 10位
                int c2 = getPositive(9 - Integer.parseInt(chars[3] + ""));

                int c3 = getPositive(9 - Integer.parseInt(chars[2] + ""));
                int c4 = getPositive(9 - Integer.parseInt(chars[1] + ""));
                int c5 = getPositive(9 - Integer.parseInt(chars[0] + ""));

                assertEquals(d1, c5 * 10 + c4, g.countBigger(d1, i));
                assertEquals(d2, c4 * 10 + c3, g.countBigger(d2, i));
                assertEquals(d3, c3 * 10 + c2, g.countBigger(d3, i));
                assertEquals(d4, c2 * 10 + c1, g.countBigger(d4, i));

                assertEquals(s5, getPositive(9 - Integer.parseInt(chars[4] + "")), g.countBigger(s5, i));
                assertEquals(s4, getPositive(9 - Integer.parseInt(chars[3] + "")), g.countBigger(s4, i));
                assertEquals(s3, getPositive(9 - Integer.parseInt(chars[2] + "")), g.countBigger(s3, i));
                assertEquals(s2, getPositive(9 - Integer.parseInt(chars[1] + "")), g.countBigger(s2, i));
                assertEquals(s1, getPositive(9 - Integer.parseInt(chars[0] + "")), g.countBigger(s1, i));
            }
        }
    }

    static int getPositive(int target) {
        if (target < 0)
            return 0;
        return target;
    }
}

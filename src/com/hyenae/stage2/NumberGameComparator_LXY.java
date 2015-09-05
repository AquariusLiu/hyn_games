package com.hyenae.stage2;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class NumberGameComparator_LXY {
    public static Class[] numberGames = { NumberGame_LXY.class, NumberGame_LXY_V2.class };
    public static String[] authors = { "YAOQian", "LXY_V2" };
    
    public static void compareRlt(NumberGameThread t1, NumberGameThread t2) {
        int diff = 0;
        HashMap<String, int[]> rlt1 = t1.countBiggerRlt;
        HashMap<String, int[]> rlt2 = t2.countBiggerRlt;
        int[] targets = NumberGameThread.targets;
        Set<String> patternSet = rlt1.keySet();
        for (String pattern : patternSet) {
            int[] counts1 = rlt1.get(pattern);
            int[] counts2 = rlt2.get(pattern);
            for (int i=0; i<NumberGameThread.targets.length; i++) {
                if (counts1[i] != counts2[i]) {
                    diff++;
                    printCountBigger(pattern, targets[i], counts1[i], counts2[i], t1.author, t2.author);
                }
            }
        }
        System.out.println("different:" + diff);
    }
    
    public static void printCountBigger(String pattern, int target, int count1, int count2, String author1, String author2) {
        System.out.println(String.format("countBigger(%s, %s), %s return %s , %s return %s", pattern, target, author1, count1, author2, count2));
    }
    
    public static void main(String[] args) throws Exception {
        NumberGameThread t1 = NumberGameThread.threadByClassAndName(numberGames[0], authors[0]);
        NumberGameThread t2 = NumberGameThread.threadByClassAndName(numberGames[1], authors[1]);
        t1.start();
        t2.start();
        while (true) {
            Thread.sleep(1000);
            if (t1.isFinish && t2.isFinish) {
                compareRlt(t1, t2);
                System.out.println(t1.author + ":" + t1.getRunTime() + "ms");
                System.out.println(t2.author + ":" + t2.getRunTime() + "ms");
                break;
            }
        }
    }
}

class NumberGameThread extends Thread {
    private static final int PATTERN_LEN = 6;
    public static final int[] targets = { 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000 };
    
    public HashMap<String, int[]> countBiggerRlt = new HashMap<String, int[]>();
    private NumberGame numberGame;
    private int countOfPatterns = 0;
    private Calendar start;
    private Calendar end;
    public boolean isFinish;
    public String author;
    
    public static NumberGameThread threadByClassAndName(Class numberGameClass, String name) throws Exception {
        NumberGameThread thread = new NumberGameThread();
        thread.numberGame = (NumberGame) numberGameClass.newInstance();
        thread.author = name;
        return thread;
    }
    
    private NumberGameThread() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void run() {
        start = Calendar.getInstance();
        for (int idx=0; idx<PATTERN_LEN; idx++) {
            String[] patterns = getPatterns(0, 99999, idx);
            for (String pattern : patterns) {
                countBiggers(pattern);
            }
        }
        
        for (int idx1=0; idx1<PATTERN_LEN; idx1++) {
            for (int idx2=idx1+1; idx2<PATTERN_LEN; idx2++) {
                String[] patterns = getPatterns(0, 9999, idx1, idx2);
                for (String pattern : patterns) {
                    countBiggers(pattern);
                }
            }
        }
        end = Calendar.getInstance();
        isFinish = true;
    }
    
    public long getRunTime() {
        return end.getTimeInMillis() - start.getTimeInMillis();
    }
    
    public void countBiggers(String pattern) {
        countOfPatterns++;
        int[] rlt = new int[targets.length];
        for (int i=0; i<targets.length; i++) {
            rlt[i] = numberGame.countBigger(pattern, targets[i]);
        }
        countBiggerRlt.put(pattern, rlt);
    }
    
    public static String[] getPatterns(int min, int max, int idx) {
        String[] rlt = new String[max-min+1];
        for (int i=min; i<=max; i++) {
            rlt[i-min] = getPatternOneWildCard(i, "?", idx);
        }
        return rlt;
    }
    
    public static String[] getPatterns(int min, int max, int idx1, int idx2) {
        String[] rlt = new String[max-min+1];
        for (int i=min; i<=max; i++) {
            rlt[i-min] = getPatternTwoWildCard(i, "?", idx1, idx2);
        }
        return rlt;
    }
    
    public static String getPatternOneWildCard(int number, String wildcard, int idx) {
        assert(number < 100000);
        StringBuffer rlt = new StringBuffer();
        for (int i=0; i<PATTERN_LEN; i++) {
            if (i == idx) {
                rlt.append(wildcard);
            } else {
                rlt.append(number % 10);
                number /= 10;
            }
        }
        return rlt.reverse().toString();
    }
    
    public static String getPatternTwoWildCard(int number, String wildcard, int idx1, int idx2) {
        assert(number < 10000);
        StringBuffer rlt = new StringBuffer();
        for (int i=0; i<PATTERN_LEN; i++) {
            if (i == idx1 || i == idx2) {
                rlt.append(wildcard);
            } else {
                rlt.append(number % 10);
                number /= 10;
            }
        }
        return rlt.reverse().toString();
    }
}
package com.hyenae.stage2;

public class NumberGame_LXY_V2 implements NumberGame {

	@Override
	public int countBigger(String pattern, int target) {
		//将int转为String并补0
		String targetStr = Integer.toString(target);
		while(pattern.length()-targetStr.length() > 0) {
			targetStr = "0"+targetStr;
		}
		
		int count = 0;
		int diff = 0;
		for(int i=0; i<pattern.length(); i++) {
			if(pattern.charAt(i) == '?') {
				if(diff<0) {
					count = count*10;
				} else if(diff > 0) {
					count = count*10 + 9;
				} else {
					count = count*10 + (9-Integer.parseInt(targetStr.charAt(i)+""));
				}
			} else {
				diff = diff*10 + (pattern.charAt(i) - targetStr.charAt(i));
			}
		}
		if(diff > 0) {
			count++;
		}
		return count;
	}
}
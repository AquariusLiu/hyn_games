package com.hyenae.stage2;


public class NumberGame_LXY implements NumberGame{
	@Override
	public int countBigger(String pattern, int target) {
		char[] charArr = pattern.toCharArray();
		int[] intArr = new int[charArr.length];
		int temp = -1;
		int flag = 0;
		for(int i=0; i<charArr.length; i++) {
			intArr[charArr.length-i-1] = target % 10;
			target /= 10;
		}
		int count = 0;
		for(int i=0; i<charArr.length; i++) {
			if(charArr[i] == '?') {
				if(i-temp > 1) {
					for(int j=temp+1; j<i; j++) {
						flag = flag*10 + charArr[j]-intArr[j]-0x30;
					}
				}
				if(flag == 0) {
					count = count*10 + 9-intArr[i];
				} else if(flag > 0) {
					count = count*10 + 9;
				} else {
					count = count*10;
				}
				temp = i;
			}
		}
		for(int j=temp+1; j<charArr.length; j++) {
			flag = flag*10 + charArr[j]-intArr[j]-0x30;
		}
		if(flag > 0) {
			count++;
		}
		return count;
	}
}

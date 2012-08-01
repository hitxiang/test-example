package com.playfish.palindrome.util;

public class StringUtil {
	public static boolean isPalindrome(String s) {
		char[] letters = s.toCharArray();
		int firstCnt = 0;
		int secCnt = letters.length - 1;
		while (firstCnt < secCnt) {
            while (!Character.isLetterOrDigit(letters[firstCnt])){
            	firstCnt ++;
            }
            
            while (!Character.isLetterOrDigit(letters[secCnt])){
            	secCnt --;
            }
            
            if (firstCnt < secCnt) break;
            
			if (letters[firstCnt] != letters[secCnt]) {
				return false;
			}
			firstCnt ++;
			secCnt --;
		}
		return true;
	}
}

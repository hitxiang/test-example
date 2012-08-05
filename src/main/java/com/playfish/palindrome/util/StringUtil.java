package com.playfish.palindrome.util;

public class StringUtil {
  /*
   * A palindrome is a word, phrase, number, or other sequence of units that may be read the same way in either direction, 
   * with general allowances for adjustments to punctuation and word dividers.
   * 
   * 
   * @see http://en.wikipedia.org/wiki/Palindrome
   * */
  public static int countPalindrome(String s) {
    
    if (s == null || s.length() == 0) return 0;

    
    char[] letters = s.toCharArray();
    int secCnt = letters.length - 1;
    while (!isPalindromeChar(letters[secCnt]) && secCnt > 0) {
      secCnt--;
    }
    if (secCnt == 0) return 0;
    
    int count = 0;
    int firstCnt = 0;
    char firstChar, secChar;
    while (firstCnt < secCnt) {
      while (!isPalindromeChar(letters[firstCnt])) {
        firstCnt++;
      }

      while (!isPalindromeChar(letters[secCnt])) {
        secCnt--;
      }

      if (firstCnt > secCnt) break;

      firstChar = letters[firstCnt];
      secChar = letters[secCnt];
      
      // 'a' - 'A' = 32 or 'A' - 'a' = -32
      if (firstChar != secChar && Math.abs(firstChar - secChar) != 32) {
        return 0; 
      }
      
      count += 2;
      firstCnt++;
      secCnt--;
    }
    
    if (firstCnt == secCnt && isPalindromeChar(letters[firstCnt])) {
    	count ++;
    }
    
    //half of the length, and round up
    return (count +1)/2;
  }
  
  /***
   * This method only checks English characters and numbers.
   */
  private static boolean isPalindromeChar(char ch){
    return Character.isLetter(ch);   
  }
  
}

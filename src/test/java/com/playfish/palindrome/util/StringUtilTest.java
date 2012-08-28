package com.playfish.palindrome.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

  @Test
  public void testcountPalindrome_Null() {
    assertEquals(0, StringUtil.countPalindrome(null));
  }

  @Test
  public void testcountPalindrome_FullOfNonPalindromeChars() {
    assertEquals(0, StringUtil.countPalindrome("///////////"));
    assertEquals(0, StringUtil.countPalindrome("////   ///,///"));
    assertEquals(0, StringUtil.countPalindrome("    "));
  }

  @Test
  public void testcountPalindrome_False() {
    String[] test_array =
        new String[] {"Lvel", "Never dd or even", "Too bad – hid a boot",
            "A man, a plan, a ct, a ham, a yak, a yam, a hat, a canal - Panama!", "20111002",
            " 2011 1002 ", "11/02/2111", "11/0,2/211,1"};

    for (String str : test_array) {
      assertEquals(0, StringUtil.countPalindrome(str));
    }

  }

  @Test
  public void testcountPalindrome_Ture() {
    String[] test_array =
        new String[] {"Level", "Never odd or even", "Too bad - I hid a boot",
            "A man, a plan, a cat, a ham, a yak, a yam, a hat, a canal - Panama!", "abbA",
//				"11/02/2011", 
//				"11/02/2,011", 
//				"11/22/11", 
//				"1/10/2011",
//				"1/ 10/2 011",
//				"20111102",
            "tattarrattat"};

    for (String str : test_array) {
      assertEquals((countLetters(str) + 1) / 2, StringUtil.countPalindrome(str));
    }

  }

  private int countLetters(String str) {
    char[] letters = str.toCharArray();

    int count = 0;
    for (char ch : letters) {
      if (Character.isLetterOrDigit(ch)) count++;
    }
    return count;
  }

}

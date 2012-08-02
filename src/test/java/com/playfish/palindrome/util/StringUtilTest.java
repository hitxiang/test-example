package com.playfish.palindrome.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {
 
  @Test
  public void testIsPalindrome_Null(){
    assertFalse(StringUtil.isPalindrome(null));
  }
  
  @Test
  public void testIsPalindrome_FullOfNonPalindromeChars(){
    assertFalse(StringUtil.isPalindrome("///////////"));
    assertFalse(StringUtil.isPalindrome("////   ///,///"));
    assertFalse(StringUtil.isPalindrome("    "));
  }
  
  @Test
  public void testIsPalindrome_False(){
    String[] test_array =
        new String[] {
                      "Lvel",
                      "Never dd or even", 
                      "Too bad – hid a boot",
                      "A man, a plan, a ct, a ham, a yak, a yam, a hat, a canal - Panama!",
                      "20111002",
                      " 2011 1002 ",
                      "11/02/2111",
                      "11/0,2/211,1"
                      };
    
    for (String str : test_array){
      assertFalse(StringUtil.isPalindrome(str));
    }
    
  }
  
  @Test
  public void testIsPalindrome_Ture(){
    String[] test_array =
        new String[] {
                      "Level",
                      "Never odd or even", 
                      "Too bad – I hid a boot",
                      "A man, a plan, a cat, a ham, a yak, a yam, a hat, a canal - Panama!",
                      "abbA",
                      "tattarrattat",
                      "11/02/2011",
                      "11/22/11",
                      "1/10/2011",
                      "20111102"
                      };
    
    for (String str : test_array){
      assertTrue(StringUtil.isPalindrome(str));
    }
    
  }
	

}

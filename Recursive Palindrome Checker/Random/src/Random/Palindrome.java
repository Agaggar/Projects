package Random;

public class Palindrome {
	
	public String s;
	
	public Palindrome(String s) {
		this.s = s;
	}
	
	public static boolean checkPalindrome (String s) {
		s = s.toUpperCase();
		s = s.replace(" ", "");
		s = s.replace("!", "");
		s= s.replace("-", "");
		s = s.replace(",", "");
		return isPalindrome(s);
	}
	
	public static boolean isPalindrome(String s) {
		if (s.length() <= 1)
			return true;
		System.out.println(s);
		if(s.charAt(0) == s.charAt(s.lastIndexOf(s)))
			return isPalindrome(s.substring(1, s.length() - 1));
		else
			return false;
	}
	
	public static void main(String[] args) {
		String s = new String("a man, a plan, a canal - Panama");
		Palindrome palindrome = new Palindrome(s);
		Palindrome.checkPalindrome(s);
		System.out.println(s + " \nIs a Palindrome");
		String t = new String("A man, a pizza, rap a person, noser paparazzi - Panama!");
		Palindrome palindrome2 =
				new Palindrome(t);
		Palindrome.checkPalindrome(t);
		System.out.println(t + " -- Is a Palindrome");
	}

}

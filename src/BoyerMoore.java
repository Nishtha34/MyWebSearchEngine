
public class BoyerMoore
{
	private char[] pattern; // store the pattern as a character array
	private String pat; // or as a string

	private final int R; // the radix
	private static int[] right; // the bad-character skip array

	public BoyerMoore(String pattern) 
	{
		this.pat = pattern;
		this.R = 10000;
		
		// position of rightmost occurrence of c in the pattern
		right = new int[R];
		for (int c = 0; c < R; c++)
			right[c] = -1;
		for (int j = 0; j < pattern.length(); j++)
			right[pattern.charAt(j)] = j;
	}

	// return offset of first match; returns N if match not found
	public int search(String pat, String txt) 
	{
		int M = pat.length();
		int N = txt.length();
		int skip;
		for (int i = 0; i <= N - M; i += skip)
		{
			skip = 0;
			for (int j = M - 1; j >= 0; j--) 
			{
				if (pat.charAt(j) != txt.charAt(i + j)) 
				{
					skip = Math.max(1, j - right[txt.charAt(i + j)]);
					break;
				}
			}
			if (skip == 0)
				return i; // found
		}
		return N; // not found
	}

}

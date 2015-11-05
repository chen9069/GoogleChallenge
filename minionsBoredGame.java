import java.util.Arrays;

public class minionsBoredGame {
	public static int answer(int t, int n) {
		int[] cur = new int[n];
		int[] next = new int[n];
		cur[0] = 1;
		for (int i = 1; i <= t; i ++) {
			for (int j = 0; j < n; j ++) {
				next[j] = cur[j];
				if (j > 0)
					next[j] += cur[j-1];
				if (j < n-2)
					next[j] += cur[j+1];
				next[j] %= 123454321;
			}
			System.out.println(i);
			System.out.println(Arrays.toString(cur));
			System.out.println(Arrays.toString(next));
			int[] tmp = cur;
			cur = next;
			next = tmp;
		}
		return cur[n-1];
	}
}

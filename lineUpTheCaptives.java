import java.util.*;
import java.math.BigInteger;

public class lineUpTheCaptives {
	static Map<Integer, Map<Integer, BigInteger>> map = new HashMap<Integer, Map<Integer, BigInteger>>();

	public static BigInteger seperates(int n, int m) {
		BigInteger[][] bi = new BigInteger[n][];
		for (int i = 0; i < n; i++)
			bi[i] = new BigInteger[m];
		bi[0][0] = BigInteger.ONE;
		System.out.println(n + "," + m);
		for (int i = 1; i < n; i++) {
			BigInteger factorial = BigInteger.ONE;
			for (int k = 2; k <= i; k++)
				factorial = factorial.multiply(new BigInteger(String.valueOf(k)));
			bi[i][0] = factorial;
			for (int j = 1; j < i && j < m; j++) {
				bi[i][j] = bi[i - 1][j - 1].add(bi[i - 1][j].multiply(new BigInteger(String.valueOf(i))));
			}
			if (i < m)
				bi[i][i] = BigInteger.ONE;
		}
		System.out.println(Arrays.deepToString(bi));
		return bi[n - 1][m - 1];
	}

	static BigInteger combination(int n, int k) {
		if (k > n)
			return BigInteger.ZERO;
		BigInteger ret = BigInteger.ONE;
		if (k == 0 || k == n)
			ret = BigInteger.ONE;
		else if (k == 1)
			ret = new BigInteger(String.valueOf(n));
		else {
			if (k > n / 2)
				k = n - k;
			for (int i = 0; i < k; i++)
				ret = ret.multiply(new BigInteger(String.valueOf(n - i)));
			for (int i = 1; i <= k; i++)
				ret = ret.divide(new BigInteger(String.valueOf(i)));
		}
		return ret;

	}

	public static String answer(int x, int y, int n) {
		if (x + y > n + 1)
			return "0";
		return seperates(n - 1, x + y - 2).multiply(combination(x + y - 2, x - 1)).toString();
	}
}

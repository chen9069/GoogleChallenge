import java.math.BigInteger;
import java.util.*;

public class zombitPandemic {
	static final int MAX_NUM = 50;
	static BigInteger[] connectCount = new BigInteger[MAX_NUM];
	static BigInteger[] maxCount = new BigInteger[MAX_NUM];
	static BigInteger[] factorial = new BigInteger[MAX_NUM];
	static BigInteger factorial(int n) {
		if (factorial[n] != null)
			return factorial[n];
		BigInteger ret = BigInteger.ONE;
		for (int i = 2; i <= n; i++)
			ret = ret.multiply(new BigInteger(String.valueOf(i)));
		factorial[n] = ret;
		return ret;
	}
	static BigInteger combination(int n, int k) {
		if (k > n)
			return BigInteger.ZERO;
		BigInteger num = BigInteger.ONE;
		BigInteger den = BigInteger.ONE;
		if (k == 0 || k == n)
			return BigInteger.ONE;
		else if (k == 1)
			return new BigInteger(String.valueOf(n));
		else {
			if (k > n / 2)
				k = n - k;
			for (int i = n-k+1; i <= n; i++)
				num = num.multiply(new BigInteger(String.valueOf(i)));
			for (int i = 2; i <= k; i++)
				den = den.multiply(new BigInteger(String.valueOf(i)));
		}
		return num.divide(den);

	}
	public static void warrens(int size) {
		connectCount[0] = BigInteger.ZERO;
		connectCount[1] = BigInteger.ONE;
		maxCount[0] = BigInteger.ZERO;
		maxCount[1] = BigInteger.ONE;
		for (int i = 2; i < size; i ++) {
			BigInteger tmp = new BigInteger(String.valueOf(i));
			tmp = tmp.pow(i+1);
			maxCount[i] = tmp;
		}
		for (int i = 2; i < size; i ++) {
			connectCount[i] = new BigInteger(maxCount[i].toString());
			for (int j = 1; j < i; j ++) {
				connectCount[i] = connectCount[i].subtract(connectCount[j].multiply(maxCount[i-j-1]).multiply(combination(i, j)));
			}
		}
	}
	static void divide(int k, int n, Stack<Integer> cur, List<List<Integer>> partition) {		
		if (k > n)
			return;
		if (n == 0) {
			List<Integer> p = new ArrayList<Integer>(cur);
			partition.add(p);
			return;
		}
		int i = k;
		for (; i <= n-i; i ++) {
			cur.push(i);
			divide(i, n-i, cur, partition);
			cur.pop();
		}
		cur.push(n);
		List<Integer> p = new ArrayList<Integer>(cur);
		partition.add(p);
		cur.pop();
		return;
	}
	static List<List<Integer>> partition(int n) {
		Stack<Integer> cur = new Stack<Integer>();
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		divide(2, n, cur, ret);
		return ret;
	}
	public static String answer(int n) {
		warrens(n);
		BigInteger num = BigInteger.ZERO;
		for (List<Integer> p : partition(n)) {
			BigInteger maxP = new BigInteger(String.valueOf(p.get(p.size() - 1)));
			BigInteger num1 = BigInteger.ONE;
			BigInteger num2 = BigInteger.ONE;
			BigInteger den2 = BigInteger.ONE;
			int m = n;
			for (int i : p) {
				num1 = connectCount[i-1].multiply(num1);
				num2 = num2.multiply(combination(m, i));
				m -= i;
			}
			int count = 0;
			int prev = p.get(0);
			for (int i : p) {
				if (i != prev && count > 0) {
					den2 = den2.multiply(factorial(count));
					prev = i;
					count = 0;
				}
				count ++;
			}
			den2 = den2.multiply(factorial(count));
			num = num.add(num1.multiply(num2).multiply(maxP).divide(den2));
		}
		BigInteger den = maxCount[n-1];
		BigInteger gcd = num.gcd(den);
		return num.divide(gcd) + "/" + den.divide(gcd);
	}
}

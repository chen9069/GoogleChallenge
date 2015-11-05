import java.math.*;
import java.util.*;

public class undercoverUnderground {
	static int MAX_N = 200;
	static Map<Integer, Map<Integer, BigInteger>> c_values = new HashMap<Integer, Map<Integer, BigInteger>>();
	static Map<Integer, Map<Integer, BigInteger>> path_num = new HashMap<Integer, Map<Integer, BigInteger>>();

	static BigInteger choose(int n, int k) {
		if (k > n)
			return BigInteger.ZERO;
		if (c_values.containsKey(n) && c_values.get(n).containsKey(k)) {
			// System.out.println("found:" + n + "," + k);
			return c_values.get(n).get(k);
		}
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
		if (!c_values.containsKey(n))
			c_values.put(n, new HashMap<Integer, BigInteger>());
		c_values.get(n).put(k, ret);
		return ret;

	}

	public static BigInteger getPath(int N, int K) {
		if (path_num.containsKey(N) && path_num.get(N).containsKey(K)) {
			// System.out.println("hit:" + N + "," + K);
			return path_num.get(N).get(K);
		}
		BigInteger ret;
		if (N == 1)
			return BigInteger.ONE;
		int P = N * (N - 1) / 2;
		if (K < N - 1)
			ret = BigInteger.ZERO;
		else {
			ret = choose(P, K);
			if (K <= P - N + 1)
				for (int i = 1; i < N; i++) {
					// choose i nodes that construct a valid graph
					BigInteger nodeCount = choose(N, i);
					int minP = i - 1;
					int maxP = Math.min(K, i * (i - 1) / 2);
					for (int p = minP; p <= maxP; p++) {
						BigInteger validCount = getPath(i, p);
						BigInteger pathCount = choose((N - i) * (N - i - 1) / 2, K - p);
						ret = ret.subtract(nodeCount.multiply(pathCount).multiply(validCount));
					}
				}

		}
		if (!path_num.containsKey(N))
			path_num.put(N, new HashMap<Integer, BigInteger>());
		path_num.get(N).put(K, ret);
		return ret;
	}

	public static String answer(int N, int K) {
		return getPath(N, K).toString();
	}
}

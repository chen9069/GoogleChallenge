public class carrotlant {
	static long gcd(long a, long b) {
		if (a < 0)
			a = -a;
		if (b < 0)
			b = -b;
		if (a == 0)
			return b;
		if (b == 0)
			return a;
		if (a < b)
			return gcd(a, b%a);
		return gcd(b, a%b);
	}
	public static int answer(int[][] vertices) {
		long x0 = vertices[0][0];
		long y0 = vertices[0][1];
		long x1 = vertices[1][0];
		long y1 = vertices[1][1];
		long x2 = vertices[2][0];
		long y2 = vertices[2][1];
		long A = Math.abs(x0 * y1 + x1 * y2 + x2 * y0 - x0 * y2 - x1 * y0 - x2 * y1) / 2;
		long b = gcd(x1-x0, y1-y0) + gcd(x2-x1, y2-y1) + gcd(x0-x2, y0-y2);
		System.out.println(b);
		return (int) (A - b/2 + 1);
	}
}

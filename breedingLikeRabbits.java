import java.math.*;
import java.util.*;
public class breedingLikeRabbits {
	static BigInteger two = new BigInteger("2");
	static Map<BigInteger, BigInteger> map = new HashMap<BigInteger, BigInteger>();
	public static BigInteger population(BigInteger n) {
		if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE))
			return BigInteger.ONE;
		if (n.equals(two))
			return two;
		if (map.containsKey(n))
			return map.get(n);
		BigInteger[] bi = n.divideAndRemainder(two);
		if (bi[1].equals(BigInteger.ZERO)) {
			BigInteger ret = population(bi[0]).add(population(bi[0].add(BigInteger.ONE)).add(bi[0]));
			map.put(n, ret);
			return ret;
		}
		else {
			BigInteger ret = population(bi[0]).add(population(bi[0].subtract(BigInteger.ONE)).add(BigInteger.ONE));
			map.put(n, ret);
			return ret;
		}
	}
	public static String answer(String str_S) {
		BigInteger goal = new BigInteger(str_S);
		BigInteger left = BigInteger.ZERO, right = goal.divide(two);
		while (left.compareTo(right) <= 0) {
			BigInteger mid = left.add(right).divide(two);
			BigInteger n = mid.multiply(two).add(BigInteger.ONE);
			BigInteger p = population(n);
			//System.out.println(n + ": " + p);
			if (p.equals(goal))
				return n.toString();
			if (p.compareTo(goal) < 0) {
				left = mid.add(BigInteger.ONE);
			}
			else
				right = mid.subtract(BigInteger.ONE);
		}

		left = BigInteger.ZERO;
		right = goal.divide(two);
		while (left.compareTo(right) <= 0) {
			BigInteger mid = left.add(right).divide(two);
			BigInteger n = mid.multiply(two);
			BigInteger p = population(n);
			//System.out.println(n + ": " + p);
			if (p.equals(goal))
				return n.toString();
			if (p.compareTo(goal) < 0) {
				left = mid.add(BigInteger.ONE);
			}
			else
				right = mid.subtract(BigInteger.ONE);
		}
		return "None";
		
	}
	public static String answer3(String str_S) {
		LinkedList<Long> q = new LinkedList<Long>();
		q.add((long) 1);
		q.add((long) 2);
		q.add((long) 3);
		long goal = Long.parseLong(str_S);
		long n = 2;
		while (true) {
			long R_nMinus = q.poll();
			long R_n = q.peek();
			long R_nPlus = q.get(1);
			long Ra = R_n + R_nPlus + n;
			if (Ra == goal)
				return String.valueOf(n * 2);
			q.add(Ra);
			long Rb = R_n + R_nMinus+ 1;
			if (Rb == goal)
				return String.valueOf(n * 2 + 1);
			if (Rb > goal)
				return "None";
			q.add(Rb);
			n ++;
		}
	}
	public static String answer2(String str_S) {
		Map<BigInteger, BigInteger> population = new HashMap<BigInteger, BigInteger>();
		BigInteger TWO = new BigInteger("2");
		population.put(BigInteger.ZERO, BigInteger.ONE);
		population.put(BigInteger.ONE, BigInteger.ONE);
		population.put(TWO, TWO);
		BigInteger bi = TWO;
		BigInteger value;
		BigInteger goal = new BigInteger(str_S);
		while (bi.toString().length() <= 25) {
			bi = bi.add(BigInteger.ONE);
			BigInteger[] div = bi.divideAndRemainder(TWO);
			//System.out.print(div[0] + ": ");
			if (div[1].intValue() == 0) {
				//if (!population.containsKey(div[0]))
					//	continue;
				BigInteger r1 = population.get(div[0]);
				BigInteger addOne = div[0].add(BigInteger.ONE);
				//if (!population.containsKey(addOne))
					//	continue;
				BigInteger r2 = population.get(addOne);
				value = r1.add(r2).add(div[0]);
				//if (value.compareTo(goal) > 0)
					//continue;
				System.out.print(value + " ");
			} else {
				//if (!population.containsKey(div[0]))
					//return "None";;
				BigInteger r1 = population.get(div[0]);
				BigInteger minusOne = div[0].subtract(BigInteger.ONE);
				//if (!population.containsKey(minusOne))
					//return "None";;
				BigInteger r2 = population.get(minusOne);
				value = r1.add(r2).add(BigInteger.ONE);
				System.out.println(value);
				if (value.compareTo(goal) > 0)
					return "None";
					
			}
			if (value.equals(goal))
				return bi.toString();
			population.put(bi, value);
		}
		return "None";
	}
}

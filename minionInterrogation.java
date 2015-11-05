import java.util.*;

class Minion {
	int num;
	int time;
	int numerator;
	int denominator;
	Minion(int num, int time, int numerator, int denominator) {
		this.num = num;
		this.time = time;
		this.numerator = numerator;
		this.denominator = denominator;
	}
}
public class minionInterrogation {
	public static int[] answer(int[][] minions) {
		List<Minion> list = new ArrayList<Minion>();
		for (int i = 0; i < minions.length; i ++) {
			list.add(new Minion(i, minions[i][0], minions[i][1], minions[i][2]));
		}
		Collections.sort(list, new Comparator<Minion>(){
			public int compare(Minion m1, Minion m2) {
				return m1.time * m2.numerator * m1.denominator - m2.time * m1.numerator * m2.denominator;
					
			}
		});
		int[] ret = new int[list.size()];
		int i = 0;
		for (Iterator<Minion> it = list.iterator(); it.hasNext();) {
			ret[i++] = it.next().num;
		}
		return ret;
	}
}

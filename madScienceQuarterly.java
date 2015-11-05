
import java.util.*;

public class madScienceQuarterly {
	public static int answer(int[] L, int k) {
		int[] len = new int[L.length];
		Queue<Integer> curL = new LinkedList<Integer>();
		int[] cur = new int[k + 1];
		cur[0] = 0;
		int max = 0;
		for (int i = 0; i < L.length; i++) {
			if (curL.isEmpty()) {
				if (L[i] > 0) {
					curL.add(1);
					cur[1] = L[i];
					max = cur[1] > max ? cur[1] : max;
				}
			}
			else {
				int size = curL.size();
				for (int j = 0; j < size; j ++) {
					int l = curL.poll();
					if (l < k) {
						if (cur[l] + L[i] > 0) {
							curL.add(l + 1);
							cur[l+1] = cur[l] + L[i]; 
							max = cur[l+1] > max ? cur[l+1] : max;
						}
					}
				}
				if (L[i] > 0) {
					curL.add(1);
					cur[1] = L[i];
					max = cur[1] > max ? cur[1] : max;
				}
			}
		}
		return max;
	}
}

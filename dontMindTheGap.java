import java.util.*;

// path exists if and only if there is a path for any 2 stations that goes to the same destination.
public class dontMindTheMap {
	static int MAX_STATIONS = 51;
	public static boolean pathExist(int[][] subway) {
		List<Map<Integer, List<Integer>>> lineMap = new ArrayList<Map<Integer, List<Integer>>>();
		int total_stations = subway.length;
		int total_lines = subway[0].length;
		if (total_stations <= 1)
			return true;
		for (int i = 0; i < total_lines; i ++)  {
			Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
			for (int j = 0; j < total_stations; j ++) {
				int from = j, to = subway[j][i];
				if (!map.containsKey(to))
					map.put(to, new ArrayList<Integer>());
				map.get(to).add(from);
			}
			lineMap.add(map);
		}
		//System.out.println(lineMap);
		Set<Integer> stationsOf2 = new HashSet<Integer>();
		// initiate
		// all the station_of_2 that goes to 1 station within one step.
		for (int i = 0; i < total_lines; i ++) {
			Map<Integer, List<Integer>> map = lineMap.get(i);
			for (int key : map.keySet()) {
				List<Integer> list = map.get(key);
				if (list.size() > 1) {
					for (int l1 = 0; l1 < list.size() - 1; l1 ++)
						for (int l2 = l1 + 1; l2 < list.size(); l2 ++) {
							int num1 = list.get(l1);
							int num2 = list.get(l2);
							if (num1 < num2)
								stationsOf2.add(num1 * MAX_STATIONS + num2);
							else
								stationsOf2.add(num2 * MAX_STATIONS + num1);
						}
				}
			}
		}
		//System.out.println(stationsOf2);
		boolean changed = true;
		// add some station_of_2 that can go to the same destination in each iteration.
		while (changed) {
			changed = false;
			Set<Integer> set = new HashSet<Integer>();
			for (int n : stationsOf2) {
				int s1 = n / MAX_STATIONS;
				int s2 = n % MAX_STATIONS;
				for (Map<Integer, List<Integer>> map : lineMap) {
					if (map.containsKey(s1) && map.containsKey(s2)) {
						List<Integer> inLine1 = map.get(s1);
						List<Integer> inLine2 = map.get(s2);
						for (int l1 : inLine1)
							for (int l2 : inLine2) {
								int num = 0;
								if (l1 < l2)
									num = l1 * MAX_STATIONS + l2;
								else
									num = (l2 * MAX_STATIONS + l1);
								if (!stationsOf2.contains(num)) {
									changed = true;
									set.add(num);
								}
							}
					}
				}
			}
			stationsOf2.addAll(set);
		}
		//System.out.println(stationsOf2);
		// every 2 stations have a path that goes to the same destination.
		return stationsOf2.size() == total_stations * (total_stations-1) / 2;
	}
	public static int[][] remove(int[][] subway, int r) {
		int[][] ret = new int[subway.length - 1][];
		for (int i = 0; i < ret.length; i ++) {
			ret[i] = new int[subway[0].length];
		}
		for (int i = 0; i < subway.length; i ++) {
			if (i == r)
				continue;
			for (int j = 0; j < subway[0].length; j ++) {
				int k = i > r ? i-1 : i;
				if (subway[i][j] == r) 
					ret[k][j] = subway[r][j] == r ? i : subway[r][j];
				else
					ret[k][j] = subway[i][j];
				if (ret[k][j] > r)
					ret[k][j] --;
			}
		}
		//System.out.println(Arrays.deepToString(ret));
		return ret;
	}
	public static int answer(int[][] subway) {
		if (pathExist(subway))
			return -1;
		for (int i = 0; i < subway.length; i ++) {
			int[][] subway_r = remove(subway, i);
			if (pathExist(subway_r))
				return i;
		}
		return -2;
	}
}

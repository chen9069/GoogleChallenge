import java.util.*;
public class saveBetaRabbit {
	public static int answer(int food, int[][] grid) {
		if (grid.length == 0 || grid.length == 1)
			return food;
		Queue<Set<Integer>> queue = new LinkedList<Set<Integer>>();
		Set<Integer> left = new HashSet<Integer>();
		Set<Integer> cur = new HashSet<Integer>();
		Set<Integer> up;
		left.add(food);
		queue.add(left);
		for (int i = 1; i < grid[0].length; i ++) {
			Set<Integer> tmp = new HashSet<Integer>();
			for (int t : left) {
				if (t >= grid[0][i])
					tmp.add(t - grid[0][i]);
			}
			if (tmp.isEmpty())
				return -1;
			left = tmp;
			queue.add(left);
		}
			
		for (int i = 1; i < grid.length; i ++) {
			up = queue.poll();
			cur.clear();
			for (int t : up) {
				if (t >= grid[i][0])
					cur.add(t - grid[i][0]);
			}
			if (cur.isEmpty())
				return -1;
			left = cur;
			cur = up;
			queue.add(left);
			for (int j = 1; j < grid[i].length; j ++) {
				up = queue.poll();
				up.addAll(left);
				cur.clear();
				for (int t : up) {
					if (t >= grid[i][j])
						cur.add(t - grid[i][j]);
				}
				if (cur.isEmpty())
					return -1;
				left = cur;
				cur = up;
				queue.add(left);
			}
		}
		Set<Integer> set = new HashSet<Integer>();
		while (!queue.isEmpty()) 
			set = queue.poll();
		System.out.println(set);
		return Collections.min(set);
	}
}

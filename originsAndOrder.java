import java.util.*;

public class originsAndOrder {
  public static String answer(int x, int y, int z) { 

      // Your code goes here.
      int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
      String ret = "";
      List<List<Integer>> possibilities = new ArrayList<List<Integer>>();
      possibilities.add(Arrays.asList(x, y, z));
      possibilities.add(Arrays.asList(x, z, y));
      possibilities.add(Arrays.asList(y, x, z));
      possibilities.add(Arrays.asList(y, z, x));
      possibilities.add(Arrays.asList(z, y, x));
      possibilities.add(Arrays.asList(z, x, y));
      for (List<Integer> p : possibilities) {
      	if (p.get(0) > 0 && p.get(0) <= 12 && p.get(1) > 0 && p.get(1) <= daysInMonth[p.get(0) - 1]) {
      		String representation = String.format("%02d/%02d/%02d", p.get(0), p.get(1), p.get(2));
      		if (ret.length() > 0 && !ret.equals(representation))
      			return "Ambiguous";
      		else
      			ret = representation;
      	}
      }
      if (ret.length() == 0)
      	throw new IllegalArgumentException();
      return ret;
  } 
}
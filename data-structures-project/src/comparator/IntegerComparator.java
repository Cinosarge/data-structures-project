package comparator;

import java.util.Comparator;

public class IntegerComparator<Integer> implements Comparator<Integer> {

	public int compare(Integer n1, Integer n2) throws ClassCastException {
		int a = (int) n1;
		int b = (int) n2;

		if (a == b)
			return 0;
		if (a < b)
			return -1;
		else
			return 1;
	}

}

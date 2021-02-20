package comparators;

import java.util.Comparator;

import objects.Team;
import simulator.*;

public class SortByName implements Comparator<Team>{
	public int compare(Team a, Team b) {
		return a.getName().compareTo(b.getName());
	}
}



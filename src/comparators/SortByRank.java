package comparators;

import java.util.Comparator;

import objects.Team;
import simulator.*;

public class SortByRank implements Comparator<Team>{
	public int compare(Team a, Team b) {
		return a.getRank() - b.getRank();
	}
}
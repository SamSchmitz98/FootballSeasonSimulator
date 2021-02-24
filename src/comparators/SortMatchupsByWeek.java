package comparators;

import java.util.Comparator;

import objects.Matchup;

public class SortMatchupsByWeek implements Comparator<Matchup>{
	public int compare(Matchup a, Matchup b) {
		if (a.getWeek() - b.getWeek() != 0)
			return a.getWeek() - b.getWeek();
		return a.getHome().getName().compareTo(b.getHome().getName());
	}
}



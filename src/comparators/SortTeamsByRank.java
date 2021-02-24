package comparators;

import java.util.Comparator;

import objects.Team;
import simulator.*;

public class SortTeamsByRank implements Comparator<Team>{
	public int compare(Team a, Team b) {
		return a.getRank() - b.getRank();
	}
}
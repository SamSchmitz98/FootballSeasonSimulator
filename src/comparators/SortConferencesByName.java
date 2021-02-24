package comparators;

import java.util.Comparator;

import objects.Conference;

public class SortConferencesByName implements Comparator<Conference>{
	public int compare(Conference a, Conference b) {
		return a.getName().compareTo(b.getName());
	}
}



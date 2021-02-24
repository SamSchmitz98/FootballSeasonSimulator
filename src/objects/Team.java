package objects;

import java.util.Comparator;

public class Team{

	private String name;
	private String abbreviation;
	private int rank;
	private Conference conference;
	
	public Team(String name){
		this.name = name;
	}
	
	public Team(String name, String abbreviation){
		this.name = name;
		this.abbreviation = abbreviation;
	}
	
	public Team(String name, String abbreviation, int rank) {
		this.name = name;
		this.abbreviation = abbreviation;
		this.rank = rank;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
	public void setAbbreviation(String abbrev) {
		abbreviation = abbrev;
	}
	
	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public void setRank(String rank) {
		this.rank = Integer.parseInt(rank);
	}
	
	public Conference getConference() {
		return conference;
	}
	
	public void setConference(Conference conference) {
		if(this.conference != null) {
			this.conference.removeTeam(this);
		}
		this.conference = conference;
	}
	
	public void removeFromConference() {
		if (conference != null) {
			conference.removeTeam(this);
		}
		conference = null;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof Team)) {
			return false;
		}
		
		return this.name.equals(((Team)o).getName());
	}
	
	public boolean conferenceError() {
		return !conference.contains(this);
	}
}

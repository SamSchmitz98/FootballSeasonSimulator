package objects;

import java.util.ArrayList;
import java.util.Collections;
import comparators.*;

public class Conference {
	
	private String name;
	private ArrayList<Team> teams;
	
	public Conference(String name){
		this.name = name;
		teams = new ArrayList<Team>();
	}
	
	public Team getTeam(int x) {
		return teams.get(x);
	}
	
	public int getConfID(Team team) {
		return teams.indexOf(team);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int addTeam(Team team) {
		if (teams.contains(team)) {
			return -1;
		}
		teams.add(team);
		Collections.sort(teams, new SortTeamsByName());
		return teams.indexOf(team);
	}
	
	public boolean removeTeam(Team team) {
		return teams.remove(team);
	}
	
	public Team removeTeam(int team) {
		return teams.remove(team);
	}
	
	public ArrayList<Team> getTeams(){
		return teams;
	}
		
	
	public String toString() {
		return name;
	}
	
	public int size() {
		return teams.size();
	}
	
	public boolean contains(Team team) {
		return teams.contains(team);
	}
	
	public boolean memberError() {
		for (Team team : teams) {
			if (team.getConference() != this) {
				return true;
			}
		}
		return false;
	}
}

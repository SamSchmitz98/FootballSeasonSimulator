package objects;

public class Matchup {
	
	private String name;
	private Team home;
	private Team away;
	private boolean neutral;
	private int week;
	private int homescore;
	private int awayscore;
	
	public Matchup(Team home, Team away, int week) {
		this.week = week;
		this.home = home;
		this.away = away;
		neutral = false;
	}
	
	public Team getHome() {
		return home;
	}
	
	public void setHome(Team team) {
		this.home = team;
	}
	
	public Team getAway() {
		return away;
	}
	
	public void setAway(Team team) {
		this.away = team;
	}
	
	public boolean isNeutral() {
		return neutral;
	}
	
	public void setNeutral(boolean neutral) {
		this.neutral = neutral;
	}
	
	public int getWeek() {
		return week;
	}
	
	public void setWeek(int week) {
		this.week = week;
	}
	
	public int getHomeScore() {
		return homescore;
	}
	
	public void setHomeScore(int homescore) {
		this.homescore = homescore;
	}
	
	public int getAwayScore() {
		return awayscore;
	}
	
	public void setAwayScore(int awayscore) {
		this.awayscore = awayscore;
	}
	
	public boolean isConference() {
		return home.getConference() == away.getConference();
	}
}

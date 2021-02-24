package simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import comparators.*;
import objects.*;

public class WriterReader {

	static final int MAX_SAVES = 99;

	static boolean unsavedchanges = false;
	static boolean saveselected = false;
	static String savefile;
	static int season = 1;
	static ArrayList<Team> teams = new ArrayList<Team>();
	static ArrayList<Conference> conferences = new ArrayList<Conference>();
	static ArrayList<Matchup> matchups = new ArrayList<Matchup>();
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Welcome!\nPlease select one of the following:\n\nNew Save\nLoad Save\nDelete Save\nExit");
		while (true) {
			String input = scanner.nextLine();
			if (input.equals("end") || input.equals("exit")) {
				if (unsavedchanges) {
					System.out.println("You have unsaved changes! Are you sure you want to exit? (Y/N)");
					input = scanner.nextLine();
					if (!input.toLowerCase().equals("y")) {
						continue;
					}
				}
				System.out.println("Exiting...");
				scanner.close();
				return;
			}
			if (saveselected) {
				if (input.startsWith("add team")) {
					addTeam(input);
				} else if (input.startsWith("add conference")) {
					addConference(input);
				} else if (input.startsWith("add matchup")) {
					addMatchup(input);
				} else if (input.startsWith("teams")) {
					printTeams();
				} else if (input.startsWith("conferences")) {
					printConferences();
				} else if (input.startsWith("matchups")) {
					printMatchups();
				} else if (input.startsWith("saved teams")) {
					printSavedTeams();
				} else if (input.startsWith("rank")) {
					printRankings();
				} else if (input.startsWith("simulate")) {
					printMatchupResult(matchups.get(1));
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < matchups.size(); j++) {
							GameSimulator.generateMatchupScore(matchups.get(j));
							printMatchupResult(matchups.get(j));
							GameSimulator.resetMatchupScore(matchups.get(j));
						}
					}
				} else if (input.startsWith("save")) {
					writeTeams();
					System.out.println("Teams saved to " + savefile + "/Teams");
					writeMatchups();
					System.out.println("Matchups saved to " + savefile + "/Season" + season + "/Matchups.txt");
					unsavedchanges = false;
				} else if (input.startsWith("revert")) {
					readTeams();
					System.out.println("Teams reverted to " + savefile + "/Teams");
					readMatchups();
					System.out.println("Matchups reverted to " + savefile + "/Season" + season + "/Matchups.txt");
				} else {
					System.out.println("Invalid command " + input.split(" ")[0]);
				}
			} else {
				if (input.startsWith("new")) {
					newGame();
				} else if (input.startsWith("load")) {
					loadGame();
				}
			}
		}
	}

	public static void writeTeams() {
		try {
			for (Team team : teams) {
				File filepath = new File(savefile + "/Teams/" + team);
				if (!filepath.exists()) {
					filepath.mkdirs();
				}
				FileWriter f = new FileWriter(filepath + "/TeamInfo.txt");
				String teaminfo = new String();
				teaminfo += "Abbreviation:" + team.getAbbreviation() + "\n";
				teaminfo += "Rank:" + team.getRank() + "\n";
				if (team.getConference() != null) {
					teaminfo += "Conference:" + team.getConference() + "\n";
				}
				f.write(teaminfo);
				f.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeMatchups() {
		try {
			File filepath = new File(savefile + "/Seasons/Season" + season);
			if (!filepath.exists()) {
				filepath.mkdirs();
			}
			FileWriter f = new FileWriter(filepath + "/Matchups.txt");
			String matchupinfo = new String();
			for (Matchup matchup : matchups) {
				matchupinfo += matchup.getHome().toString() + ", ";
				matchupinfo += matchup.getAway().toString() + ", ";
				matchupinfo += matchup.getWeek();
				if (matchup.isNeutral()) {
					matchupinfo += ", neutral";
				}
				matchupinfo += "\n";
			}
			f.write(matchupinfo);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void newGame() {
		for (int i = 1; i <= MAX_SAVES; i++) {
			File directory = new File("Save" + i);
			if (!directory.exists()) {
				savefile = "Save" + i;
				directory.mkdir();
				saveselected = true;
				break;
			}
		}
	}

	public static void loadGame() {
		ArrayList<String> savelist = new ArrayList<String>();
		for (int i = 1; i <= MAX_SAVES; i++) {
			File directory = new File("Save" + i);
			if (directory.exists()) {
				savelist.add("Save" + i);
			}
		}
		if (savelist.isEmpty()) {
			System.out.println("No saves detected");
		} else {
			System.out.println("Please select a save to load:");
			for (String string : savelist) {
				System.out.println(string);
			}
			while (true) {
				String input = scanner.nextLine();
				if (input.startsWith("exit") || input.startsWith("end")) {
					scanner.close();
					return;
				} else if (input.startsWith("back")) {
					return;
				} else if (savelist.contains(input)) {
					savefile = input;
					saveselected = true;
					System.out.println("Opening " + input);
					readTeams();
					readMatchups();
					return;
				} else {
					System.out.println("Unrecognized command '" + input + "'");
					System.out.println("Type 'back' to return to main menu");
					System.out.println("Type 'exit' to close");
				}
			}
		}
	}

	public static void addTeam(String input) {
		if (input.split(" ").length < 3) {
			System.out.println("Invalid argument length for command 'add team'");
		} else {
			String teamname = input.substring(9);
			System.out.println("Please enter abbreviation for " + teamname);
			input = scanner.nextLine();
			teams.add(new Team(teamname, input, teams.size() + 1));
			teams.sort(new SortTeamsByName());
			System.out.println("Added team '" + teamname + "'");
			unsavedchanges = true;
		}
	}

	public static void addConference(String input) {
		if (input.split(" ").length < 3) {
			System.out.println("Invalid argument length for command 'add conference'");
		} else {
			String confname = input.substring(15);
			conferences.add(new Conference(confname));
			conferences.sort(new SortConferencesByName());
			System.out.println("Added conference '" + confname + "'");
			unsavedchanges = true;
		}
	}

	public static void addMatchup(String input) {
		if (input.split(" ").length < 3) {
			System.out.println("Invalid argument length for command 'add matchup'");
		} else {
			String home = input.substring(12);
			while (!teamExists(home)) {
				System.out.println("Team '" + home + "' not found. Please try again");
				home = scanner.nextLine();
			}
			Team hometeam = null;
			for (Team team : teams) {
				if (team.getName().equals(home)) {
					hometeam = team;
				}
			}
			System.out.println("Input away team");
			String away = scanner.nextLine();
			while (!teamExists(away) || home.equals(away)) {
				System.out.println("Team '" + away + "' invalid. Please try again");
				away = scanner.nextLine();
			}
			Team awayteam = null;
			for (Team team : teams) {
				if (team.getName().equals(away)) {
					awayteam = team;
				}
			}
			System.out.println("Input week number");
			String weeknum = scanner.nextLine();
			while (!isInteger(weeknum)) {
				System.out.println("Please enter a valid number");
				weeknum = scanner.nextLine();
			}
			int week = Integer.parseInt(weeknum);
			Matchup matchup = new Matchup(hometeam, awayteam, week);
			matchups.add(matchup);
			matchups.sort(new SortMatchupsByWeek());
			System.out.println(
					"Added 'Week " + matchup.getWeek() + ": " + matchup.getAway() + " at " + matchup.getHome() + "'");
		}
	}

	public static void readTeams() {
		try {
			File[] teamfolders = new File(savefile + "/Teams").listFiles();
			teams.clear();
			// Read objects
			for (int i = 0; i < teamfolders.length; i++) {
				Team currentteam = new Team(teamfolders[i].getName());
				Scanner scanner = new Scanner(new File(teamfolders[i].getPath() + "/TeamInfo.txt"));
				teamlines: while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] linearray = line.split(":");
					if (linearray[0].equals("Abbreviation")) {
						currentteam.setAbbreviation(linearray[1]);
					}
					if (linearray[0].equals("Rank")) {
						currentteam.setRank(linearray[1]);
					}
					if (linearray[0].equals("Conference")) {
						for (Conference conference : conferences) {
							if (conference.getName().equals(linearray[1])) {
								currentteam.setConference(conference);
								conference.addTeam(currentteam);
								continue teamlines;
							}
						}
						Conference newconf = new Conference(linearray[1]);
						currentteam.setConference(newconf);
						newconf.addTeam(currentteam);
						conferences.add(newconf);
						conferences.sort(new SortConferencesByName());
					}
				}
				teams.add(currentteam);
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void readMatchups() {
		try {
			matchups.clear();
			Scanner scanner = new Scanner(new File(savefile + "/Seasons/Season" + season + "/Matchups.txt"));
			while (scanner.hasNextLine()) {
				String input = scanner.nextLine();
				String[] matchuparray = input.split(", ");
				String home = matchuparray[0];
				String away = matchuparray[1];
				int week = Integer.parseInt(matchuparray[2]);
				Team hometeam = null;
				Team awayteam = null;
				for (Team team : teams) {
					if (team.toString().equals(home)) {
						hometeam = team;
					}
					if (team.toString().equals(away)) {
						awayteam = team;
					}
				}
				Matchup matchup = new Matchup(hometeam, awayteam, week);
				if (matchuparray.length > 3) {
					matchup.setNeutral(true);
				}
				matchups.add(matchup);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void printTeams() {
		for (Team team : teams) {
			System.out.println(team);
		}
	}

	public static void printSavedTeams() {
		Scanner scanner;
		File[] teamfolders = new File(savefile + "/Teams").listFiles();

		for (int i = 0; i < teamfolders.length; i++) {
			System.out.println(teamfolders[i].getName());
		}
	}

	public static void printRankings() {
		ArrayList<Team> tempteams = new ArrayList<Team>(teams);
		Collections.sort(teams, new SortTeamsByRank());
		for (Team team : teams) {
			System.out.println(team.getRank() + ": " + team);
		}
		teams = tempteams;
		unsavedchanges = true;
	}

	public static void printConferences() {
		for (Conference conference : conferences) {
			System.out.println(conference);
			for (Team team : conference.getTeams()) {
				System.out.println("   " + team);
			}
		}
		System.out.println("Independents");
		for (Team team : teams) {
			if (team.getConference() == null) {
				System.out.println("   " + team);
			}
		}
	}

	public static void printMatchups() {
		for (Matchup matchup : matchups) {
			System.out.println("Week " + matchup.getWeek() + ": " + matchup.getAway() + " at " + matchup.getHome());
		}
	}

	public static void printMatchupResult(Matchup matchup) {
		if (matchup.getHomeScore() == matchup.getAwayScore()) {
			System.out.println(matchup.getHome() + " and " + matchup.getAway() + " have not played yet");
			return;
		}
		if (matchup.getHomeScore() > matchup.getAwayScore()) {
			System.out.println(matchup.getHome() + " beat " + matchup.getAway() + " " + matchup.getHomeScore() + " to "
					+ matchup.getAwayScore());
		} else {
			System.out.println(matchup.getHome() + " lost to " + matchup.getAway() + " " + matchup.getHomeScore() + " to "
					+ matchup.getAwayScore());
		}
	}

	public static boolean teamExists(String name) {
		for (Team team : teams) {
			if (team.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}

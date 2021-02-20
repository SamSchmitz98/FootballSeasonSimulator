package simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import comparators.*;
import objects.*;

public class WriterReader {

	static boolean unsavedchanges = false;
	static ArrayList<Team> teams = new ArrayList<Team>();

	public static void main(String[] args) {
		readTeams();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Program Started");
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
			} else if (input.startsWith("add")) {
				if (input.split(" ").length < 2) {
					System.out.println("Invalid argument length for command 'add'");
				} else {
					String teamname = input.substring(4);
					System.out.println("Please enter abbreviation for " + teamname);
					input = scanner.nextLine();
					teams.add(new Team(teamname, input, teams.size()+1));
					System.out.println("Added team '" + teamname + "'");
					unsavedchanges = true;
				}
			} else if (input.startsWith("teams")) {
				printTeams();
			} else if (input.startsWith("saved teams")) {
				printSavedTeams();
			} else if (input.startsWith("sort")) {
				Collections.sort(teams, new SortByName());
				printTeams();
				unsavedchanges = true;
			} else if (input.startsWith("ranking")) {
				ArrayList<Team> tempteams = new ArrayList<Team>(teams);
				Collections.sort(teams, new SortByRank());
				printTeams();
				teams = tempteams;
				unsavedchanges = true;
			} else if (input.startsWith("save")) {
				writeTeams();
				System.out.println("Teams saved to TeamInfo.txt");
				unsavedchanges = false;
			} else if (input.startsWith("revert")) {
				readTeams();
				System.out.println("Teams reverted to TeamInfo.txt");
			}else {
				System.out.println("Invalid command " + input.split(" ")[0]);
			}
		}
	}

	public static void writeTeams() {
		try {

			// Write objects to file
			for (Team team : teams) {
				File filepath = new File("Save1/Teams/" + team);
				if (!filepath.exists()) {
				filepath.mkdirs();
				}
				FileWriter f = new FileWriter(filepath + "/TeamInfo.txt");
				String teaminfo = new String();
				
				teaminfo += "Abbreviation:" + team.getAbbreviation() + "\n";
				teaminfo += "Rank:" + team.getRank() + "\n";
				
				f.write(teaminfo);
				f.close();
			}
		} catch (Exception e) {

		}
	}

	public static void readTeams() {
		try {
			File[] teamfolders = new File("Save1/Teams").listFiles();
			
			teams.clear();

			// Read objects
			for (int i = 0; i < teamfolders.length; i++) {
				Team currentteam = new Team(teamfolders[i].getName());
				Scanner scanner = new Scanner(new File(teamfolders[i].getPath() + "/TeamInfo.txt"));
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] linearray = line.split(":");
					if (linearray[0].equals("Abbreviation")) {
						currentteam.setAbbreviation(linearray[1]);
					}
					if (linearray[0].equals("Rank")) {
						currentteam.setRank(linearray[1]);
					}
				}
				teams.add(currentteam);
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
		File[] teamfolders = new File("Save1/Teams").listFiles();

		for (int i = 0; i < teamfolders.length; i++) {
			System.out.println(teamfolders[i].getName());
		}
	}
}

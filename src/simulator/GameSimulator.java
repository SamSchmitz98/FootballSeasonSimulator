package simulator;

import java.util.Random;
import objects.*;

public class GameSimulator {

	public static void generateMatchupScore(Matchup matchup) {
		Random random = new Random();
		int homeconvert = random.nextInt(7);
		int awayconvert = random.nextInt(7);
		int homescore = 0;
		int awayscore = 0;
		while (homescore == 0 && awayscore == 0) {
			for (int i = 0; i < homeconvert; i++) {
				if (random.nextInt(2) == 1) {
					homescore += 7;
				} else {
					homescore += 3;
				}
			}
			for (int i = 0; i < awayconvert; i++) {
				if (random.nextInt(2) == 1) {
					awayscore += 7;
				} else {
					awayscore += 3;
				}
			}
			if (homescore == awayscore) {
				homescore = 0;
				awayscore = 0;
			}
		}
		matchup.setHomeScore(homescore);
		matchup.setAwayScore(awayscore);
	}

	public static void resetMatchupScore(Matchup matchup) {
		matchup.setHomeScore(0);
		matchup.setAwayScore(0);
	}

}

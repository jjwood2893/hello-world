import java.util.Scanner;

public class SacredGeometryIO {

	public static void main(String[] args) {
		int effectiveSpellLevel;
		int knowledgeRanks;
		System.out.println("Welcome to the Sacred Geometry Calculator!\n");
		
		getSpellLevel();
		getKnowledgeRanks();
	}

	private static void getKnowledgeRanks() {
		int knowledgeRanks;
		System.out.print("Please enter the number of ranks in Knowledge(Engineering) you have>>> ");
		boolean validInput = false;
		while (!validInput) {
			try {
				Scanner input = new Scanner(System.in);
				String ranksString = input.nextLine();
				knowledgeRanks = Integer.parseInt(ranksString);
				if (knowledgeRanks <= 20 && knowledgeRanks > 1) {
					validInput = true;
				} else {
					Exception e = new Exception();
					throw e;
				}
				
			} catch (Exception e) {
				System.out.print("Sorry, please enter a number between 2 and 20>>> ");
			}

		}
	}

	private static void getSpellLevel() {
		int effectiveSpellLevel;
		System.out.print("Please enter the effective spell level for the spell you are casting>>> ");
		boolean validInput = false;
		while (!validInput) {
			try {
				Scanner input = new Scanner(System.in);
				String effectiveSpellLevelString = input.nextLine();
				effectiveSpellLevel = Integer.parseInt(effectiveSpellLevelString);
				if (effectiveSpellLevel <= 9 && effectiveSpellLevel > 0) {
					validInput = true;
				} else {
					Exception e = new Exception();
					throw e;
				}
				
			} catch (Exception e) {
				System.out.print("Sorry, please enter a number between 1 and 9>>> ");
			}

		}
	}

}

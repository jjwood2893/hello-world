import java.util.Scanner;

public class Background {
	private String limitedBoost;
	private String freeBoost;
	private String backgroundSkill;
	private String loreSkill;
	private String backgroundFeat;
	Scanner input = new Scanner(System.in);
	
	public Background() {
		
	}
	
	public String getFreeBoost() {
		return freeBoost;
	}

	public String getBackgroundSkill() {
		return backgroundSkill;
	}

	public String getLoreSkill() {
		return loreSkill;
	}

	public String getBackgroundFeat() {
		return backgroundFeat;
	}

	public String getLimitedBoost() {
		return limitedBoost;
	}
	
	public void backgroundSetup() {
		System.out.println("Limited Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
		limitedBoost = input.nextLine();
		System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
		freeBoost = input.nextLine();
		System.out.println("Enter your Background Skill");
		backgroundSkill = input.nextLine();
		System.out.println("Enter your Lore Skill");
		loreSkill = input.nextLine();
		System.out.println("What is your background feat?");
		backgroundFeat = input.nextLine();
	}
	
}

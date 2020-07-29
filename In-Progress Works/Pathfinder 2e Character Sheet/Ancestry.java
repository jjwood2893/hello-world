import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Ancestry {
	private String name;
	private char size;
	private String boostOne;
	private String boostTwo;
	private String freeBoost;
	private String abilityFlaw;
	private int startingHP;
	private int speed;
	private boolean hasLowLightVision;
	private boolean hasDarkvision;
	private String specialTrait1;
	private String specialTrait2;
	private Set<String> languages = new HashSet<String>();
	private Scanner input = new Scanner(System.in);
	
	
	//Defines generalized ancestries, will create separate class for heritages
	public Ancestry(String name) {
		this.name = name.toLowerCase();
		if (name.equals("human")) {
			size = 'M';
			System.out.println("Free Boost 1 (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			boostOne = input.nextLine();
			System.out.println("Free Boost 2 (Must be different from first)");
			boostTwo = input.nextLine();
			freeBoost = "";
			abilityFlaw = "";
			startingHP = 8;
			speed = 25;
			hasLowLightVision = false;
			hasDarkvision = false;
			languages.add("Common");
			System.out.println("Choose one additional language other than Common");
			languages.add(input.nextLine());
		}
		if (name.equals("dwarf")) {
			size = 'M';
			boostOne = "Constitution";
			boostTwo = "Wisdom";
			abilityFlaw = "Charisma";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 10;
			speed = 25;
			hasLowLightVision = true;
			hasDarkvision = true;
			languages.add("Common");
			languages.add("Dwarven");
		}
		if (name.equals("elf")) {
			size = 'M';
			boostOne = "Dexterity";
			boostTwo = "Intelligence";
			abilityFlaw = "Constitution";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 6;
			speed = 30;
			hasLowLightVision = true;
			hasDarkvision = false;
			languages.add("Common");
			languages.add("Elven");
		}
		if (name.equals("gnome")) {
			size = 'S';
			boostOne = "Constitution";
			boostTwo = "Charisma";
			abilityFlaw = "Strength";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 8;
			speed = 25;
			hasLowLightVision = true;
			hasDarkvision = false;
			languages.add("Common");
			languages.add("Gnomish");
			languages.add("Sylvan");
		}
		if (name.equals("goblin")) {
			size = 'S';
			boostOne = "Dexterity";
			boostTwo = "Charisma";
			abilityFlaw = "Wisdom";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 6;
			speed = 25;
			hasLowLightVision = true;
			hasDarkvision = true;
			languages.add("Common");
			languages.add("Goblin");
		}
		if (name.equals("halfling")) {
			size = 'S';
			boostOne = "Dexterity";
			boostTwo = "Wisdom";
			abilityFlaw = "Strength";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 6;
			speed = 25;
			hasLowLightVision = false;
			hasDarkvision = false;
			languages.add("Common");
			languages.add("Halfling");
			specialTrait1 = "Keen Eyes";
		}
		if (name.equals("hobgoblin")) {
			size = 'M';
			boostOne = "Constitution";
			boostTwo = "Intelligence";
			abilityFlaw = "Wisdom";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 8;
			speed = 25;
			hasLowLightVision = true;
			hasDarkvision = true;
			languages.add("Common");
			languages.add("Goblin");
		}
		if (name.equals("leshy")) {
			size = 'S';
			boostOne = "Constitution";
			boostTwo = "Wisom";
			abilityFlaw = "Intelligence";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 8;
			speed = 25;
			hasLowLightVision = true;
			hasDarkvision = false;
			languages.add("Common");
			languages.add("Sylvan");
			specialTrait1 = "Plant Nourishment";
		}
		if (name.equals("lizardfolk")) {
			size = 'M';
			boostOne = "Strength";
			boostTwo = "Wisdom";
			abilityFlaw = "Intelligence";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 8;
			speed = 25;
			hasLowLightVision = false;
			hasDarkvision = false;
			languages.add("Common");
			languages.add("Lizardfolk");
			specialTrait1 = "Unarmed Attack - Claws, 1d4 S, Agile, Finesse";
			specialTrait2 = "Breath Control";
		}
		if (name.equals("shoony")) {
			size = 'S';
			boostOne = "Dexterity";
			boostTwo = "Charisma";
			abilityFlaw = "Constitution";
			System.out.println("Free Boost (Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)");
			freeBoost = input.nextLine();
			startingHP = 6;
			speed = 25;
			hasLowLightVision = true;
			hasDarkvision = false;
			languages.add("Common");
			languages.add("Shoony");
			specialTrait1 = "Blunt Snout";
		}
		
		
		
	}
	//methods needed: getters, toString
			/*
		private String name;
		private char size;
		private String boostOne;
		private String boostTwo;
		private String freeBoost;
		private String abilityFlaw;
		private int startingHP;
		private int speed;
		private boolean hasLowLightVision;
		private boolean hasDarkvision;
		private String specialTrait1;
		private String specialTrait2;
		private Set<String> languages = new HashSet<String>();
		private Scanner input = new Scanner(System.in);
			 */
	
	public String getName() {
		return name;
	}
	public char getSize() {
		return size;
	}
	public String getBoostOne() {
		return boostOne;
	}
	public String getBoostTwo() {
		return boostTwo;
	}
	public String getFreeBoost() {
		return freeBoost;
	}
	public String getAbilityFlaw() {
		return abilityFlaw;
	}
	public int getStartingHP() {
		return startingHP;
	}
	public int getSpeed() {
		return speed;
	}
	public boolean hasLowLightVision() {
		return hasLowLightVision;
	}
	public boolean hasDarkVision() {
		return hasDarkvision;
	}
	public String getSpecialTrait1() {
		return specialTrait1;
	}
	public String getSpecialTrait2() {
		return specialTrait2;
	}
	public Set<String> getLanguages() {
		return languages;
	}
	
	@Override
	public String toString() {
		Object[] languageArray = languages.toArray();
		String languagesString = "";
		for (int i = 0; i < languageArray.length; i++) {
			languagesString = languagesString +", "+ languageArray[i];
		}
		String descriptionOfAncestry = "Ancestry name: " + name +"\nSize: "+size+"\nBoosts: "+boostOne+", "+boostTwo+", "+freeBoost
				+"  Flaw: "+abilityFlaw+"\nStarting HP: "+startingHP+"\nLow Light Vision: "+hasLowLightVision
				+"\nDarkvision: "+hasDarkvision+"\nLanguages: "+languagesString;
		System.out.println(descriptionOfAncestry);
		return descriptionOfAncestry;
	}
	
}

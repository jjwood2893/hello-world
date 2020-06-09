import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SacredGeometryIO {

	public static void main(String[] args) {
		int effectiveSpellLevel;
		int knowledgeRanks;
		Map<Integer, int[]> primeConstantMap = new HashMap<Integer, int[]>();
		populateMap(primeConstantMap);
		System.out.println("Welcome to the Sacred Geometry Calculator!\n");
		getSpellLevel();
		getKnowledgeRanks();
	}
	
	private static void populateMap(Map<Integer, int[]> primeConstantMap) {
		List<Integer> primeNumbers = sieveOfEratosthenes(107);
		int count = 0;
		for (int i = 1; i < primeNumbers.size()-3; i= i+3) {
			int[] threePrimes = new int[3];
			for (int x = 0; x < threePrimes.length; x++) {
				threePrimes[x] = primeNumbers.get(i+x);
				primeConstantMap.put(count, threePrimes);
			}
		count++;
		}
	}
	private static List<Integer> sieveOfEratosthenes(int n) {
	    boolean prime[] = new boolean[n + 1];
	    Arrays.fill(prime, true);
	    for (int p = 2; p * p <= n; p++) {
	        if (prime[p]) {
	            for (int i = p * 2; i <= n; i += p) {
	                prime[i] = false;
	            }
	        }
	    }
	    List<Integer> primeNumbers = new LinkedList<>();
	    for (int i = 2; i <= n; i++) {
	        if (prime[i]) {
	            primeNumbers.add(i);
	        }
	    }
	    return primeNumbers;
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

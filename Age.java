import java.util.*;	//Scanner and Calendar
class Age{
	public static void main(String[] args) {
		if(args.length > 0 && args[0].equals("--help")){	// If java Age --help is run
			help();											// Show help message and exit
			System.exit(0);
		}
		String[] tokens;
		if(args.length == 3){							// If java Age dd mm yyyy is run
			tokens = args;								// Store tokens in 'tokens'
		}
		else if(args.length == 1 && args[0].split("[ /.-]+").length == 3){	// If java Age dd.mm.yyyy (. can also be / or -)
			tokens = args[0].split("[ /.-]+");								// then extract tokens and store in 'tokens'
		}
		else{												// If java Age is run
			Scanner sc = new Scanner(System.in);			// Then take DOB input
			System.out.println("Enter Date of Birth (DD MM YYYY): ");
			tokens = sc.nextLine().split("[ /.-]+");			// Split entered DOB into tokens (dd,mm,yyyy)
			if(tokens.length != 3){								// If there aren't 3 tokens they entered something wrong
				System.out.println("Illegal Date Format, Type Age --help for help.");	// Show error msg and exit
				System.exit(1);
			}
		}
		int[] dmy = new int[3];				// Store tokens as int
		for(int i = 0; i < 3; i++){
			try{
				dmy[i] = Integer.parseInt(tokens[i]);	// Try to store tokens as int
			}
			catch(NumberFormatException nfe){		// If any token is not a number
				System.out.print("Invalid Date Format, Type Age --help for help.");	// Show error msg and exit
				System.exit(1);
			}
			
		}
		dmy[1]--;	// Calendar class sees JANUARY as 0 and so on, so reduce month by 1
		int[] fields = {Calendar.DATE,Calendar.MONTH,Calendar.YEAR};	// Store the fields in an array
		Calendar current = Calendar.getInstance();			// Calendar instance to store Current date
		Calendar dob = Calendar.getInstance();				// Calendar instance to store DOB
		for(int i = 0 ; i < 3 ; i++){
			dob.set(fields[i],dmy[i]);						// Set fields as DOB
		}
		dob.set(Calendar.HOUR,0);						// Set the time as 00:00:00
		dob.set(Calendar.MINUTE,0);
		dob.set(Calendar.SECOND,0);
		System.out.println("Entered Date of Birth: " + dob.getTime().toString());	// Show inputed DOB

		if(dob.after(current)){		// If DOB is in future (wrong DOB or wrong internal clock)
			System.out.println("Entered Date of Birth is in future.\nCorrect DOB or internal clock");
			System.exit(1);
		}							// Else do calculations
		int k = 2; int temp = 0;
		int[] age = new int[3];		// Array to store result age (dd,mm,yyyy)
		while(k>=0){				// Iterate over fields, year -> month -> date
			temp++;					// increase counter
			dob.add(fields[k],1);	// keep increasing field unless DOB becomes future
			if(dob.after(current)){	// If dob becomes future we've added one too many
				dob.add(fields[k],-1);	// Remove last adding
				temp--;					// Reduce counter by 1
				age[k] = temp;			// Set counter in age[k] (It now holds the difference in fields of DOB and current)
				temp = 0;				// Reset counter
				k--;					// Go to next smaller field
			}
		}
		System.out.println("Age: "+ calcYMD(age));	// Print age as __ years, __ months, __ days
		System.out.println("Age as fraction: "+ calcY_MD(age));	// Print age as __.__ years
	}
	private static void help(){						// Show help message, How to run program, and Syntax.
		System.out.println("\t\tAge\n\tby Sayan Ghosh\n\n"+
							"Calculates Age of Person from Date of Birth (DD MM YYYY).\n"+
							"Shows age in two forms:\n"+
								"\tAge in years, months, days\n"+
								"\tAge in years (as fraction)\n"+
							"Syntax:\n"+
								"\tjava Age\t\t\truns program and takes input\n"+
								"\tjava Age <dd mm yyyy>\t\tdirectly shows result taking dob as parameters\n"+
								"\tjava Age --help\t\t\tshows this help\n");
	}
	private static String calcYMD(int[] age){		// Return String joining parameters elements
		return age[2]+" years " + age[1] + " months " + age[0] + " days ";
	}
	private static String calcY_MD(int[] age){	// Calculate age in year fraction and return
		double y = age[2];	// Add number of years
		y += age[1]/12.0;	// Add months as fraction (divided by 12)	// as each year has 12 months
		y += age[0]/365.0;	// Add days as fraction (divided by 365)	// as each year has 365 days
		return y+"";
	}
}
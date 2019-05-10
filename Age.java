import java.util.*;	//Scanner and Calendar
class Age{
	public static void main(String[] args) {
		if(args.length > 0 && args[0].equals("--help")){
			help();
			System.exit(0);
		}
		String[] tokens;
		if(args.length == 3){
			tokens = args;
		}
		else if(args.length == 1 && args[0].split("[ /.-]+").length == 3){
			tokens = args[0].split("[ /.-]+");
		}
		else{
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Date of Birth (DD MM YYYY): ");
			tokens = sc.nextLine().split("[ /.-]+");
			if(tokens.length != 3){
				System.out.println("Illegal Date Format, Type Age --help for help.");
				System.exit(1);
			}
		}
		int[] dmy = new int[3];
		for(int i = 0; i < 3; i++){
			try{
				dmy[i] = Integer.parseInt(tokens[i]);
			}
			catch(NumberFormatException nfe){
				System.out.print("Invalid Date Format, Type Age --help for help.");
				System.exit(1);
			}
			
		}
		dmy[1]--;
		int[] fields = {Calendar.DATE,Calendar.MONTH,Calendar.YEAR};
		Calendar current = Calendar.getInstance();
		Calendar dob = Calendar.getInstance();
		for(int i = 0 ; i < 3 ; i++){
			dob.set(fields[i],dmy[i]);
		}
		dob.set(Calendar.HOUR,0);
		dob.set(Calendar.MINUTE,0);
		dob.set(Calendar.SECOND,0);
		System.out.println("Entered Date of Birth: " + dob.getTime().toString());

		if(dob.after(current)){
			System.out.println("Entered Date of Birth is in future.\nCorrect DOB or internal clock");
			System.exit(1);
		}
		int k = 2; int temp = 0;
		int[] age = new int[3];
		while(k>=0){
			temp++;
			dob.add(fields[k],1);
			if(dob.after(current)){
				dob.add(fields[k],-1);
				temp--;
				age[k] = temp;
				temp = 0;
				k--;
			}
		}
		System.out.println("Age: "+ calcYMD(age));
		System.out.println("Age as fraction: "+ calcY_MD(age));
	}
	private static void help(){
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
	private static String calcYMD(int[] age){
		return age[2]+" years " + age[1] + " months " + age[0] + " days ";
	}
	private static String calcY_MD(int[] age){
		double y = age[2];
		y += age[1]/12.0;
		y += age[0]/365.0;
		return y+"";
	}
}
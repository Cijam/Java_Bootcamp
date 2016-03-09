/**
 * Console based interface that uses the ContactsBook class to allow the user access and 
 * modify ContactEntry objects. Helper methods read saved contact data from a file on 
 * initialisation and save the data to the same file if it has been modified.
 * @author Treacy, Ciarán
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class ContactsConsoleInterface {
	private static ContactsBook cb;
	private static boolean wasModified = false;
	private static final int NAME_FIELD = ContactsBook.NAME_FIELD;
	//OK
	public static void main(String[] args){
		initialiseContacts();
		Scanner scan = new Scanner(System.in);
		int menuOption = 0;
		
		while (true) {
			clearConsole();
			System.out.println("Welcome. Please select menu option by number:"); 		
			System.out.println("\t[1] Search for contact"); 			
			System.out.println("\t[2] Edit a contact"); 
			System.out.println("\t[3] Add a Contact"); 
			System.out.println("\t[4] Remove a Contact"); 			
			System.out.println("\t[5] Display all contacts"); 
			System.out.println("\t[6] Exit system"); 
			System.out.print("Enter your choice > "); 			

			menuOption = scan.nextInt();
			scan.nextLine();
			
			switch(menuOption) {
				case 1: clearConsole();
						contactSearchMenu();
						break;
				case 2: clearConsole();
						contactEditMenu();
						break;	
				case 3: clearConsole();
						contactAddMenu();
						break;
				case 4: clearConsole();
						contactRemoveMenu();
						break;
				case 5: clearConsole();
						contactDisplayMenu();
						break;
				case 6: if(wasModified == false){
							System.out.println("Goodbye");
							scan.close();
							System.exit(0);
							break;	
						}else{
							System.out.println("6. Save changes to contacts? [y/n]");
							String save = scan.next();
							if(save.equalsIgnoreCase("y")){
								writeContacts();
								clearConsole();
								System.out.println("Changes saved.");
						}	
						System.out.println("\nGoodbye");
						scan.close();
						System.exit(0);
						break;	
						}	
				default:System.out.print("You have inputted an invalid option. ");
						System.out.println("press enter to continue.");
						pauseMe();
						break;
			}	
		}	
	}
	//OK
	public static void contactSearchMenu(){
		Scanner scan = new Scanner(System.in);
		int menuOption = 0;
		while(true){
			System.out.println("Please Make a selection:"); 		
			System.out.println("1.[1] Search for a contact "); 
			System.out.println("2.[2] Return to previous menu "); 	
			System.out.println("Enter your choice> ");
			menuOption = scan.nextInt();
			scan.nextLine();
			switch(menuOption) {	
				case 1: searchContacts();
						break;
				case 2: return; 
				default:System.out.print("You have inputted an invalid option. ");
						pauseMe();
						break;
			}
			
		}
	}
	//ok
	private static void searchContacts(){
		Scanner scan = new Scanner(System.in);
		String searchName;
		int contactIndex;
		clearConsole();
		System.out.println("Enter name to search for (e.g. Joe Bloggs)>");
		searchName = scan.nextLine();
		System.out.println("Searching for " + searchName);
		contactIndex = cb.search(NAME_FIELD, searchName);
		
		while(contactIndex < 0){
			clearConsole();
			System.out.println(searchName + " not found. Search again? [y/n]>");
			String answer = scan.nextLine();
			if (answer.equalsIgnoreCase("n")) return;
			System.out.println("Enter name to search for (e.g. Joe Bloggs)>");
			searchName = scan.nextLine();
			System.out.println("Searching for " + searchName);
			contactIndex = cb.search(NAME_FIELD, searchName);
		}
		clearConsole();
		System.out.println("Contact found.\n");
		System.out.println(cb.getEntry(contactIndex).displayForConsole());
		pauseMe();
		
	}
	
	//OK
	public static void contactEditMenu(){
		int menuOption;
		Scanner scan;
		String searchName;
		System.out.println("Please enter name of contact you wish to edit "
				+ "(e.g. Joe Bloggs)> ");
		scan = new Scanner(System.in);
		searchName = scan.nextLine();
		int contactIndex = cb.search(NAME_FIELD, searchName);
		while(contactIndex < 0){
			System.out.println(searchName + " not found. Search again? [y/n]: ");
			String again = scan.nextLine();
			if(again.equalsIgnoreCase("n")){
				return;
			}else{
				System.out.println("Please enter name of contact you wish to edit "
						+ "(e.g. Joe Bloggs)> ");
				scan = new Scanner(System.in);
				searchName = scan.nextLine();
				contactIndex = cb.search(NAME_FIELD, searchName);
			}
		}
			clearConsole();
			System.out.println(searchName + " current details: ");
			System.out.println(cb.getEntry(contactIndex).displayForConsole());
			System.out.println("Please Make a selection:"); 		
			System.out.println("2.[1] Edit contact address "); 
			System.out.println("2.[2] Edit contact phone number ");
			System.out.println("2.[3] Edit contact email "); 
			System.out.println("2.[4] Edit contact zip code ");
			System.out.println("2.[5] Return to previous menu");
			System.out.println("Enter your choice> ");
			menuOption = scan.nextInt();
			scan.nextLine();
			clearConsole();
			int editOption;
			while(true){
				switch (menuOption){
					case 1: System.out.println("Current address: " + cb.getEntry(contactIndex).getAddress() + "\n");
							System.out.println("Enter new address> ");
							break;
					case 2:	System.out.println("Current address: " + cb.getEntry(contactIndex).getPhoneNumber() + "\n");
							System.out.println("Enter new phone number> ");
							break;
					case 3: System.out.println("Current address: " + cb.getEntry(contactIndex).getEmail() + "\n");
							System.out.println("Enter new email> ");
							break;
					case 4: System.out.println("Current address: " + cb.getEntry(contactIndex).getZip() + "\n");
							System.out.println("Enter new zip code> ");
							break;
					case 5: return;
					default:System.out.print("You have inputted an invalid option. ");
							System.out.println("press enter to continue.");
							pauseMe();
							break;
				}
				String newVal = scan.nextLine();
				cb.edit(searchName, menuOption, newVal);
				wasModified = true;
				clearConsole();
				System.out.println(searchName + "'s updated details: ");
				System.out.println(cb.getEntry(contactIndex).displayForConsole() + "\n");
				pauseMe();
				return;
			}
		
	}
	
	public static void contactAddMenu(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter new contact's name " 
		+ "(e.g. Joe Bloggs)>");
		String newName = scan.nextLine();
		System.out.println("Enter new contact's address (do not include commas)>");
		String newAddr = scan.nextLine();
		System.out.println("Enter new contact's phone number> ");
		String newPhone = scan.nextLine();
		System.out.println("Enter new contact's email>");
		String newEmail = scan.nextLine();
		System.out.println("Enter new contact's zip code>");
		String newZip = scan.nextLine();
		if(cb.addEntry(new ContactEntry(newName, newAddr, newPhone, newEmail, newZip))){
			clearConsole();
			System.out.println("New contact added successfully.");
			wasModified = true;
			cb.sortByName();
			int newContact = cb.search(NAME_FIELD, newName);
			System.out.println("New contact's details: ");
			System.out.println(cb.getEntry(newContact).displayForConsole());
		}
		pauseMe();
		//return;
	}
	
	public static void contactRemoveMenu(){
		Scanner scan = new Scanner(System.in);
		int menuOption = 0;
		clearConsole();
		System.out.println("Please Make a selection:"); 		
		System.out.println("4.[1] Remove contact "); 
		System.out.println("4.[2] Retrun to previous menu"); 		
		System.out.print("Enter your choice > "); 			
		menuOption = scan.nextInt();
		scan.nextLine();
		int index = -1;
		String removeName;
		clearConsole();

		while (true) {		
			switch(menuOption) {	
				case 1: System.out.println("Enter contact to delete (e.g. Joe Bloggs)>");
						removeName = scan.nextLine();
						index = cb.search(NAME_FIELD, removeName);
						while (index < 0){
								System.out.println(removeName + " not found. Search again? [y/n]> ");
								String again = scan.nextLine();
								if(again.equalsIgnoreCase("n")){
									return;
								}
								System.out.println("Enter contact to delete (e.g. Joe Bloggs)>");
								removeName = scan.nextLine();
								index = cb.search(NAME_FIELD, removeName);
						}
						String details = cb.getEntry(index).displayForConsole();
						if(cb.deleteEntry(removeName)){
							System.out.println("Contact: \n" + details);
							System.out.println("successfully removed from contacts");
							wasModified = true;
						}
						pauseMe();
						return;	
				case 2: return;
				default:System.out.print("You have inputt an invalid option. ");
						pauseMe();
						break;
			}//close switch
		}//close while 1
	}
	
	//OK
	public static void contactDisplayMenu(){
		Scanner scan = new Scanner(System.in);
		int menuOption = 0;

		while (true) {		
			clearConsole();
			System.out.println("Please Make a selection:"); 		
			System.out.println("5.[1] Display contacts ordered by name "); 
			System.out.println("5.[2] Display contacts ordered by zip code"); 			
			System.out.println("5.[3] Return to previous menu."); 		
			System.out.print("Enter your choice > "); 			

			menuOption = scan.nextInt();
			scan.nextLine();
			clearConsole();
			System.out.println(cb.getNumberOfContacts() + " contacs in book: \n");
			
			switch(menuOption) {	
				case 1: if(!cb.isSortedByName()){
							cb.sortByName();
						}
						cb.showContacts();
						pauseMe();
						break;
				case 2: if(!cb.isSortedByZip()){
							cb.sortByZip();
						}
						cb.showContacts();
						pauseMe();
						break;
				case 3: return;	
				default:System.out.print("You have inputted an invalid option. ");
						pauseMe();
						break;
			}
		}
	}
	
	private static boolean initialiseContacts(){
		FileReader fin;
		cb = new ContactsBook();
		boolean wasCreated;
		try {
			fin = new FileReader("contacts.txt");
			String[] arr = new String[5];
			int i = 0;
			int j = 0;
			Scanner src = new Scanner(fin);
			src.useDelimiter(", *");
			while(src.hasNext()){
				while (i < 5 && src.hasNext()){
					arr[i++] = src.next();
				}
				cb.addEntry(new ContactEntry(arr[0], arr[1], arr[2], arr[3], arr[4]));
				System.out.println(j++ + " Entry added");
				i = 0;
			}
			src.close();
			return wasCreated = true;
		} catch (FileNotFoundException e) {
			System.out.println("Contacts book could not be acessed. See error message: "
					+ e.getMessage());
			return wasCreated = false;
		}
	}
	
	private static boolean writeContacts(){
		boolean wasWritten;
		FileWriter fout;
		try {
			fout = new FileWriter("contacts.txt");
			fout.write(cb.toFile());
			fout.close();
			return wasWritten = true;
		} catch (IOException e) {
			System.out.println("Error occurred when saving contacts. Error details: " 
		+ e.getMessage());
			return wasWritten = false;
		}
	}
	
	private static void clearConsole() {
		for (int i=0; i < 100; i++) {
			System.out.println("");
		}
	}
	
	private static void pauseMe() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("press enter to continue.");		
		scan.nextLine();	
	}
}

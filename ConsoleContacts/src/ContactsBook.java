/**
 * @author Treacy, Ciarán
 * Am array list data structure to store contact entry objects
 * Allows the adding, editing and removing of contact entries
 * and sorting by name (last, then first) and zip code
 * Uses binary search to allow searching by name or zip code
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactsBook {
	private static CompLastName cln = new CompLastName();
	private static Comparator compName = cln.thenComparing(new CompThenByFirstName());
	private static CompZip compZip = new CompZip();
	
	private ArrayList<ContactEntry> contacts;
	
	public static final int NAME_FIELD = 0;
	public static final int ADDRESS_FIELD = 1;
	public static final int PHONE_FIELD = 2;
	public static final int EMAIL_FIELD = 3;
	public static final int ZIP_FIELD = 4;
	
	private static int sortedBy;

	public ContactsBook() {
		this.contacts = new ArrayList<ContactEntry>();
		this.sortedBy = -1;
	}
	
	public int getNumberOfContacts(){
		return this.contacts.size();
	}
	
	public boolean addEntry(ContactEntry ce){
		boolean wasAdded = this.contacts.add(ce);
		if(wasAdded){
			sortedBy = -1;
		}
		return wasAdded;
	}
	
	public boolean deleteEntry (String str){
		int i = this.search(NAME_FIELD, str); 
		boolean wasDeleted = false;
		if (i >= 0){
			this.contacts.remove(i);
			wasDeleted = true;
		}
		return wasDeleted;
	}
	
	public ContactEntry getEntry(int contactIndex)throws ArrayIndexOutOfBoundsException{ 
		return this.contacts.get(contactIndex);
	}
	
	public int search(int searchType, String searchName){
		if (searchType == NAME_FIELD){
			return searchByName(searchName);
		}else{
			return searchByZip(searchName);
		}
		
	}
	
	private int searchByName(String str){
		if(sortedBy != NAME_FIELD){
			this.sortByName();
			sortedBy = NAME_FIELD;
		}
		String[] searchNames = str.split(" ");
		String fName = searchNames[0];
		String lName = searchNames[searchNames.length -1];
		int lo = 0;
		int hi = this.contacts.size() - 1;
		int mid;
		String entryFName = "";
		String entryLName = "";
		String[] entryNames;
		while (lo <= hi){
			mid = lo + (hi - lo)/2;
			entryNames = this.contacts.get(mid).getName().split(" ");
			entryFName = entryNames[0];
			entryLName = entryNames[entryNames.length -1];
			if (entryLName.compareToIgnoreCase(lName) > 0 || 
				(entryLName.compareToIgnoreCase(lName) == 0 && entryFName.compareToIgnoreCase(fName) > 0)){
				hi = mid-1;
			}else if(entryLName.compareToIgnoreCase(lName) < 0 ||
					(entryLName.compareToIgnoreCase(lName) == 0 && entryFName.compareToIgnoreCase(fName) < 0)){
				lo = mid+1;
			}else{
				return mid;
			}
		}
		return -1;
	}
	
	private int searchByZip(String str){
		if(sortedBy != ZIP_FIELD){
			this.sortByZip();
			sortedBy = ZIP_FIELD;
		}
		int lo = 0;
		int hi = this.contacts.size() - 1;
		int mid;
		while (lo <= hi){
			mid = lo + (hi - lo)/2;
			if (this.contacts.get(mid).getZip().compareToIgnoreCase(str) > 0){
				hi = mid-1;
			}else if(this.contacts.get(mid).getZip().compareToIgnoreCase(str) < 0){
				lo = mid+1;
			}else{
				return mid;
			}
		}
		return -1;
	}
	
	public void sortByName(){
		this.contacts.sort(compName);
		sortedBy = NAME_FIELD;
	}
	
	public void sortByZip(){
		this.contacts.sort(compZip);
		sortedBy = ZIP_FIELD;
	}
	
	public boolean isSortedByName(){
		return sortedBy == NAME_FIELD;
	}
	
	public boolean isSortedByZip(){
		return sortedBy == ZIP_FIELD;
	}
	
	public boolean edit(String name, int editField, String newVal){
		if(editField == NAME_FIELD){
			return false;
		}
		int entryIndex = this.search(NAME_FIELD, name);
		if (entryIndex >= 0){
			this.edit(editField, newVal, entryIndex);
			return true;
		}
		return false;
	}
	
	private void edit(int editField, String newVal, int entryIndex){
		switch (editField){
			case ADDRESS_FIELD: this.contacts.get(entryIndex).setAddress(newVal);
							    break;
			case PHONE_FIELD: this.contacts.get(entryIndex).setPhoneNumber(newVal);
							  break;
			case EMAIL_FIELD: this.contacts.get(entryIndex).setEmail(newVal);
							  break;
			case ZIP_FIELD: this.contacts.get(entryIndex).setZip(newVal);
						    break;
			default: System.out.println("Edit could not be completed");
		}
	}
	
	public void showContacts(){
		if(this.contacts.size() > 0){
			for (ContactEntry ce: this.contacts){
				System.out.println(ce.displayForConsole());
			}
		}else{
			System.out.println("No saved contacts.");
		}
	}
	public String toFile(){
		String str = "";
		if(this.contacts.size() > 0){
			for (ContactEntry ce: this.contacts){
				str += ce.toString();
			}
		}
		return str;
	}
}

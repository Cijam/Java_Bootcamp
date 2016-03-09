/**
 * @author Treacy, Ciarán
 * A Comparator used to sort contacts by first name after they've been sorted by last name
 */
import java.util.Comparator;

public class CompThenByFirstName implements Comparator<ContactEntry> {

	@Override
	public int compare(ContactEntry o1, ContactEntry o2) {
		int i, j;
		
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}

/**
 * @author Treacy, Ciarán
 * A comparator used to sort contact entries by last name
 */
import java.util.Comparator;

public class CompLastName implements Comparator<ContactEntry> {

	@Override
	public int compare(ContactEntry o1, ContactEntry o2) {
		int i, j;
		i = o1.getName().lastIndexOf(' ');
		j = o2.getName().lastIndexOf(' ');
		
		
		return o1.getName().substring(i).compareToIgnoreCase(o2.getName().substring(j));
	}

}

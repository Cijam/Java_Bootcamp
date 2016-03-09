/**
 * @author Treacy, Ciarán
 * A comparator used to sort contact entries by zip code
 */
import java.util.Comparator;

public class CompZip implements Comparator<ContactEntry> {

	@Override
	public int compare(ContactEntry o1, ContactEntry o2) {
		
		return o1.getZip().compareToIgnoreCase(o2.getZip());
	}

}

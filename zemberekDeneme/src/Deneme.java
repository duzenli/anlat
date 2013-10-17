import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
public class Deneme {
//aAAAAAAAAAAAAAAAAAAAAAAA
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "klem";
		Zemberek z = new Zemberek(new TurkiyeTurkcesi());
		System.out.print(z.kelimeDenetle(str));

	}

}

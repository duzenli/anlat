/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 13.Eyl.2005
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;

public class CDosyadanAnaliz {

	private static File file = new File("deneme.txt");
	private static char I = Alfabe.CHAR_ii;
	private static char S = Alfabe.CHAR_ss;
	private static String sablonE;
	private static Zemberek zemberek;
	static FileOutputStream fos;

	// yapim asamasinda...
	public static void arama(String ornek) throws Exception {
		String[] s = ayir(ornek);
		String aranacakIz = sadeceCozumle(s);
		dosyadaAra(aranacakIz);
		

	}
	public static String[] ayir(String cumle) {
		String[] kelimeler = cumle.split(" ");
		return kelimeler;
	}
	public static void cozumle(String[] str) throws Exception {
		for (String s : str) {
			harfDegistir(s);
			// System.out.println(s);
			if (zemberek.kelimeDenetle(s) == true) {
				Kelime[] sonuc = zemberek.kelimeCozumle(s);
				Kok kok = sonuc[0].kok();
				// System.out.println("Kok :" + kok.icerik() + " Tipi : " +
				// kok.tip().toString());
				List<Ek> sablon = sonuc[0].ekler();
				if (sablonE == null) {
					sablonE = sablon.toString();
				} else {
					sablonE = sablonE.concat("+" + sablon.toString());

				}
			} else {
				System.out.println(s + " Türkçe deðil");
			}
		}
		// return sablonE;
		dosyaYazma(sablonE);
		System.out.println(sablonE);
		sablonE=null;

	}
	public static void dosyadanOku() throws Exception {
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader("cumleler.txt"));
		String satir = reader.readLine();
		while (satir != null) {
			String[] s = ayir(satir);
			cozumle(s);
			satir = reader.readLine();
		}
	}
	
	public static void dosyadaAra(String iz) throws Exception{
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader("deneme.txt"));
		String satir = reader.readLine();
		int say = 0;
		while(satir!=null){
			if(iz.contains(satir))
				say++;
			satir = null;
			satir = reader.readLine();
		}
		if(say>0)
			System.out.println("iliþki türü IsA");
		else
			System.out.println("Ýliþki türü yok");
	}
	
	
	public static void dosyaYazma(String yazi) throws Exception {
		FileWriter writer = new FileWriter(file, true);
		BufferedWriter buf = new BufferedWriter(writer);
		buf.write(yazi + "\n");
		buf.close();
	}
	public static String harfDegistir(String s) {
		if (s.contains("ý")) {
			s.replace('ý', I);
		}
		if (s.contains("þ")) {
			s.replace('þ', S);
		}
		return s;
	}

	public static void main(String[] args) throws Exception {
		zemberek = new Zemberek(new TurkiyeTurkcesi());
		// String ornek = "elmalarýn ve benzeri meyvelerin";
		// String ornek2= "kýrmýzý ve benzeri renkler";
		// String[] deneme = ayir(ornek);
		// String[] deneme2=ayir(ornek2);
		// cozumle(deneme);
		// cozumle(deneme2);
		//dosyadanOku();
		while(true){
			System.out.println("girsene");
			Scanner s = new Scanner(System.in);
			String inKelime = s.nextLine();
			arama(inKelime);
		}
//		dosyadanOku();
	}

	public static String sadeceCozumle(String[] str) {
		for (String s : str) {
			harfDegistir(s);
			// System.out.println(s);
			if (zemberek.kelimeDenetle(s) == true) {
				Kelime[] sonuc = zemberek.kelimeCozumle(s);
				Kok kok = sonuc[0].kok();
				// System.out.println("Kok :" + kok.icerik() + " Tipi : " +
				// kok.tip().toString());
				List<Ek> sablon = sonuc[0].ekler();
				if (sablonE == null) {
					sablonE = sablon.toString();
				} else {
					sablonE = sablonE.concat("+" + sablon.toString());

				}
			} else {
				System.out.println(s + " Türkçe deðil");
			}
		}
		String a=sablonE;
		sablonE=null;
		return a;
	}
}

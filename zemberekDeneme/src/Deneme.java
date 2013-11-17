/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 13.Eyl.2005
 *
 */


import java.util.ArrayList;
import java.util.List;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;

public class Deneme {

    private static Zemberek zemberek;
    private static char I = Alfabe.CHAR_ii;
    private static char S = Alfabe.CHAR_ss;
    private static String sablonE;

    public static void cozumle(String[] str) {
    	for(String s:str)
    		{
    			harfDegistir(s);
    			System.out.println(s);
    		if (zemberek.kelimeDenetle(s) == true) {
            	Kelime[] sonuc = zemberek.kelimeCozumle(s);
                	Kok kok = sonuc[0].kok();
                	System.out.println("Kok :" + kok.icerik() + " Tipi : " + kok.tip().toString());
                	List<Ek>sablon = sonuc[0].ekler();
                	if(sablonE == null){
                		sablonE = sablon.toString();
                	}
                	else
                	{
                		sablonE=sablonE.concat("+"+sablon.toString());
                	}
        	} else {
            	System.out.println(s + " Türkçe deðil");
        	}
    	}
    	System.out.println(sablonE);
    }
    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
    	String ornek = "elmalarýn ve benzeri meyvelerin";
    	String[] deneme = ayir(ornek);
    	cozumle(deneme);
    } 
    public static String[] ayir(String cumle){
    	String[] kelimeler = cumle.split(" ");
    	return kelimeler;
    }
    public static String harfDegistir(String s){
    	if(s.contains("ý")){
			s.replace('ý', I);
		}
		if(s.contains("þ")){
			s.replace('þ', S);	
		}
    	return s;
    }
   
}


package Resavac;

import java.util.Vector;

import Glavni.Polje;

public class Resavac {
	private static int brResenja;
	
	public static PoljeRes nadjiResenja(int m, int n, PoljeRes[][] polja, int brNeobelezenihMina, boolean gameWon, boolean gameLost, boolean prviKlik) {
		//povratna vrednost
		PoljeRes resenje;
		
		//ako je zavrsena igra prekida se izvrsavanje
		if (gameWon || gameLost)
			return null;
		
		//ako je prvi klik
		if (prviKlik) 
			return polja[m / 2][n / 3];
		
		//odredi susedna polja svakog polja
		Resavac.susednaPoljaInit(m, n, polja);
		
		//napravi listu svih otvorenih polja koja imaju za suseda bar jedno neotvoreno polje koje nije obelezeno kao mina
		Vector<PoljeRes> otvorenaPolja = Resavac.nadjiOtvorenaPolja(polja);
		
		//napravi listu svih neotvorenih polja koja nisu obelezena kao mine i koja za suseda imaju bar jedno otvoreno polje
		Vector<PoljeRes> neotvorenaPolja = Resavac.nadjiNeotvorenaPolja(polja);
			
		//ako nema otvorenih vrati random polje
		//!prepravi ovo jer ne vraca nista!
		if (otvorenaPolja.isEmpty())
			return null;
		
		//ako postoji otvoreno polje oko kojih sigurno nema mina 
		//ili neotvoreno koje je sigurno mina
		for (PoljeRes polje: otvorenaPolja) {
			int razlika = polje.brSusednihMina;
			for (PoljeRes susednoPolje: polje.susednaPolja) 
				if (susednoPolje.minaObelezeno) 
					razlika--;
				
			if (razlika == 0) {
				polje.klik = 3;
				return polje;
			}
			else if (razlika == polje.susednaPoljaNeotvorena.size()) 
				for (PoljeRes susednoPolje: polje.susednaPoljaNeotvorena) {
					susednoPolje.klik = 2;
					return susednoPolje;
				}
		}
		
		//Resavac.ispisOtvorenih(otvorenaPolja);
		
		//ako ima previse kombinacija da bi mogao da izracuna verovatnocu vrati random polje kao resenje
		//if (otvorenaPolja.size() > 30) 
			//return neotvorenaPolja.get(neotvorenaPolja.size() / 2);
		
		//poziv rekurzivne funkcije koja proverava sve kombinacije
		brResenja = 0;
		rekurzijaOtvoreno(otvorenaPolja, 0, otvorenaPolja.size(), neotvorenaPolja, brNeobelezenihMina);
		
		//izracunaj verovatnocu da na svakom polju bude mina
		System.out.println();
		for (PoljeRes polje: neotvorenaPolja) {
			polje.odrediVerovatnocu(brResenja);
			System.out.println(polje);
		}
		System.out.println();
		
		//odredi koje polje ima najvecu verovatnocu da se na njemu nalazi ili ne nalazi mina
		resenje = neotvorenaPolja.get(0);
		for (PoljeRes polje: neotvorenaPolja) 
			if (min(polje.verovatnoca, 1 - polje.verovatnoca) < min(resenje.verovatnoca, 1 - polje.verovatnoca)) 
				resenje = polje;
		
		if (resenje.verovatnoca > 0.5)
			resenje.klik = 2;
	
		return resenje;
	}
	
	private static void rekurzijaOtvoreno(Vector<PoljeRes> otvorenaPolja, int i, int otvorenaPoljaSize, Vector<PoljeRes> neotvorenaPolja, int brNeobelezenihMina) {
		if (brResenja > 1000000)
			return;
		
		if (otvorenaPoljaSize == i) {
			brResenja++;
			for (PoljeRes polje: neotvorenaPolja) 
				if (polje.minaObelezeno)
					polje.brPutaObelezeno++;
			return;
		}
		
		PoljeRes trPolje = otvorenaPolja.get(i);
		int razlika = trPolje.brSusednihMina;
		for (PoljeRes polje: trPolje.susednaPolja) 
			if (polje.minaObelezeno) 
				razlika--;
		
		if (razlika < 0 || razlika > brNeobelezenihMina) 
			return;
		
		rekurzijaNeotvoreno(trPolje.susednaPoljaNeotvorena, 0, razlika, otvorenaPolja, i, otvorenaPoljaSize, neotvorenaPolja, brNeobelezenihMina);
	}
	
	private static void rekurzijaNeotvoreno(Vector<PoljeRes> poljaObradi, int i_po, int razlika, Vector<PoljeRes> otvorenaPolja, int i, int otvorenaPoljaSize, Vector<PoljeRes> neotvorenaPolja, int brNeobelezenihMina) {
		if (razlika == 0) {
			for (PoljeRes polje: poljaObradi)
				polje.obradjeno = true;
			rekurzijaOtvoreno(otvorenaPolja, i + 1, otvorenaPoljaSize, neotvorenaPolja, brNeobelezenihMina);
			for (PoljeRes polje: poljaObradi)
				polje.obradjeno = false;
			return;
		}
		
		if (razlika > poljaObradi.size() - i_po)
			return;
		
		rekurzijaNeotvoreno(poljaObradi, i_po + 1, razlika, otvorenaPolja, i, otvorenaPoljaSize, neotvorenaPolja, brNeobelezenihMina);
		
		if (poljaObradi.get(i_po).obradjeno) 
			return;
		
		poljaObradi.get(i_po).minaObelezeno = true;
		rekurzijaNeotvoreno(poljaObradi, i_po + 1, razlika - 1, otvorenaPolja, i, otvorenaPoljaSize, neotvorenaPolja, brNeobelezenihMina - 1);
		poljaObradi.get(i_po).minaObelezeno = false;
	}
	
	private static Vector<PoljeRes> nadjiNeotvorenaPolja(PoljeRes[][] polja) {
		Vector<PoljeRes> neotvorenaPolja = new Vector<PoljeRes>();
		
		for (PoljeRes[] redPolja: polja) 
			for (PoljeRes polje: redPolja) 
				if (!polje.otvoreno && !polje.minaObelezeno)
					for (PoljeRes susednoPolje: polje.susednaPolja) 
						if (susednoPolje.otvoreno) {
							neotvorenaPolja.add(polje);
							break;
						}
		
		return neotvorenaPolja;
	}
	
	private static Vector<PoljeRes> nadjiOtvorenaPolja(PoljeRes[][] polja) {
		Vector<PoljeRes> otvorenaPolja = new Vector<PoljeRes>();
		
		for (PoljeRes[] redPolja: polja) 
			for (PoljeRes polje: redPolja) 
				if (polje.otvoreno)
					for (PoljeRes susednoPolje: polje.susednaPolja) 
						if (!susednoPolje.otvoreno && !susednoPolje.minaObelezeno) {
							otvorenaPolja.add(polje);
							break;
						}
		
		return otvorenaPolja;
	}
	
	private static void susednaPoljaInit(int m, int n, PoljeRes[][] polja) {
		for (PoljeRes[] redPolja: polja) 
			for (PoljeRes polje: redPolja) 
				polje.susednaPoljaInit(m, n, polja);
	}

	
	
	private static double min(double a, double b) {
		if (a < b) 
			return a;
		return b;
	}
}

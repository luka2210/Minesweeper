package Resavac;

import java.util.Vector;

public class PoljeRes {
	int i, j;
	boolean otvoreno;
	boolean minaObelezeno;
	int brSusednihMina;
	
	PoljeRes[][] polja;
	Vector <PoljeRes> susednaPolja;
	
	public PoljeRes(int i, int j, boolean otvoreno, boolean minaObelezeno, int brSusednihMina) {
		this.i = i;
		this.j = j;
		this.otvoreno = otvoreno;
		this.minaObelezeno = minaObelezeno;
		this.brSusednihMina = brSusednihMina;
	}
	
	void susednaPoljaInit(int m, int n, PoljeRes[][] polja) {
		this.polja = polja;
		susednaPolja = new Vector<PoljeRes>();
		
		int[] i_vals = {i-1, i, i+1};
		int[] j_vals = {j-1, j, j+1};
		
		for (int i_val: i_vals)
			for (int j_val: j_vals) 
				if (i_val >= 0 && i_val < m && j_val >=0 && j_val < n && (i_val != i || j_val != j))
					susednaPolja.add(polja[i_val][j_val]);
	}
}

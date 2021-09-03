package Glavni;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.util.Vector;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Polje {
	JLabel label;
	int i, j, m, n;
	boolean mina;
	Polje[][] polja;
	Vector<Polje> susednaPolja;
	boolean otvoreno;
	int vrednost;
	boolean minaObelezeno;
	
	static int visina = 20;
	static int sirina = 20;
	
	public Polje(int i, int j, int m, int n, int brPolja, int brMina, Polje[][] polja, JFrame frame) {
		this.i = i;
		this.j = j;
		this.m = m;
		this.n = n;
		this.polja = polja;
		otvoreno = false;
		minaObelezeno = false;
		vrednost = 0;
		labelInit(frame);
		mina = minaInit(brMina, brPolja);
	}
	
	public void poljeInit() {
		susednaPoljaInit();
		for (Polje polje: susednaPolja) 
			if (polje.mina)
				vrednost++;
	}
	
	void poljeKliknuto() {
		if (otvoreno || minaObelezeno) return;
		if (mina) {
			gameOver();
			labelMina();
		}
		else
			labelVrednost();
	}
	
	void poljeKliknutoDesniKlik() {
		if (otvoreno) return;
		if (minaObelezeno) 
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje.png")));
		else 
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeObelezeno.png")));
		minaObelezeno = !minaObelezeno;
	}
	
	void poljeKliknutoSrednjiKlik() {
		if (!otvoreno || mina) return;
		int vrednostTemp = 0;
		for (Polje polje: susednaPolja)
			if (polje.minaObelezeno) vrednostTemp++;
		if (vrednost != vrednostTemp) return;
		for (Polje polje: susednaPolja) 
			polje.poljeKliknuto();
	}
	
	private void labelInit(JFrame frame) {
		label = new JLabel();
		label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje.png")));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) 
					poljeKliknutoDesniKlik();
				else if (SwingUtilities.isMiddleMouseButton(e))
					poljeKliknutoSrednjiKlik();
				else 
					poljeKliknuto();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (otvoreno) return;
				label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljePrazno.png")));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (otvoreno) return;
				if (minaObelezeno)
					label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeObelezeno.png")));
				else
					label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje.png")));
			}
		});
		label.setBounds(j * sirina, i * visina + 60, sirina, visina);
		frame.getContentPane().add(label);
	}
	
	private boolean minaInit(int brMina, int brPolja) {
		Random rand = new Random();
		return rand.nextDouble() <= (1.0 * brMina) / brPolja;
	}
	
	private void susednaPoljaInit() { 
		susednaPolja = new Vector<Polje>();
		int[] i_vals = {i-1, i, i+1};
		int[] j_vals = {j-1, j, j+1};
	
		for (int i_val: i_vals)
			for (int j_val: j_vals) 
				if (i_val >= 0 && i_val < m && j_val >=0 && j_val < n && (i_val != i || j_val != j))
					susednaPolja.add(polja[i_val][j_val]);
	}
	
	private void labelVrednost() {
		otvoreno = true;
		switch (vrednost) {
		case 0:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljePrazno.png")));
			for (Polje polje: susednaPolja) 
				polje.poljeKliknuto();
			break;
		case 1:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje1.png")));
			break;
		case 2:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje2.png")));
			break;
		case 3:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje3.png")));
			break;
		case 4:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje4.png")));
			break;
		case 5:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje5.png")));
			break;
		case 6:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje6.png")));
			break;
		case 7:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje7.png")));
			break;
		case 8:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje8.png")));
			break;
		}
	}
	
	private void labelMina() {
		label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeGreska.png")));
		minaObelezeno = false;
	}
	
	private void gameOver() {
		for (Polje[] redPolja: polja)
			for (Polje polje: redPolja) {
				if (!polje.minaObelezeno && polje.mina) 
					polje.label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeMina.png")));
				else if (polje.minaObelezeno && !polje.mina)
					polje.label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljePogresnoObelezeno.png")));
				polje.otvoreno = true;
			}
	}
}

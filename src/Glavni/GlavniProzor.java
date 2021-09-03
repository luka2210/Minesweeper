package Glavni;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;

public class GlavniProzor {

	JFrame frame;
	JLabel dugmeReset;
	int pozX;
	int pozY;
	Polje[][] polja;
	
	Integer m, n, brMina;
	Integer brNeobelezenihMina;
	
	JLabel cifra1, cifra2, cifra3;
	/**
	 * Create the application.
	 */
	public GlavniProzor(int m, int n, int brMina, int pozX, int pozY) {
		this.m = m;
		this.n = n;
		this.brMina = brMina;
		this.brNeobelezenihMina = brMina;
		this.pozX = pozX;
		this.pozY = pozY;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.controlHighlight);
		frame.setBounds(pozX, pozY, Polje.sirina * n + 15, Polje.visina * m + 99);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		dugmeReset = new JLabel("");
		dugmeReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dugmeReset.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeResetPressed.png")));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dugmeReset.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeReset.png")));
			}
			@Override 
			public void mouseClicked(MouseEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				GlavniProzor gp = new GlavniProzor(m, n, brMina, frame.getX(), frame.getY());
			}
		});
		dugmeReset.setBounds(frame.getWidth() / 2 - 20, 10, 40, 40);
		dugmeReset.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeReset.png")));
		frame.getContentPane().add(dugmeReset);
		
		cifra1 = new JLabel("");
		cifra1.setBounds(5, 10, 27, 40);
		frame.getContentPane().add(cifra1);
		
		cifra2 = new JLabel("");
		cifra2.setBounds(5 + 27, 10, 27, 40);
		frame.getContentPane().add(cifra2);
		
		cifra3 = new JLabel("");
		cifra3.setBounds(5 + 27 * 2, 10, 27, 40);
		frame.getContentPane().add(cifra3);
		
		ispisiBrojNeobelezenihMina();
		
		polja = new Polje[m][n];
		int brPolja = m * n;
		int brMina_temp = brMina;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				polja[i][j] = new Polje(i, j, brPolja, brMina_temp, this);
				brPolja--;
				if (polja[i][j].mina) brMina_temp--;
			}
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				polja[i][j].poljeInit();
	}
	
	void ispisiBrojNeobelezenihMina() {
		ispisiCifru(cifra1, this.brNeobelezenihMina / 100);
		ispisiCifru(cifra2, (this.brNeobelezenihMina % 100) / 10);
		ispisiCifru(cifra3, this.brNeobelezenihMina % 10);
	}
	
	private void ispisiCifru(JLabel labela, int br) {
		switch (br) {
		case 0:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj0.png")));
			break;
		case 1:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj1.png")));
			break;
		case 2:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj2.png")));
			break;
		case 3:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj3.png")));
			break;
		case 4:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj4.png")));
			break;
		case 5:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj5.png")));
			break;
		case 6:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj6.png")));
			break;
		case 7:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj7.png")));
			break;
		case 8:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj8.png")));
			break;
		case 9:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj9.png")));
			break;
		}
	}
}

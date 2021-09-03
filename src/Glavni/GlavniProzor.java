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
	
	Integer m, n, brMina;
	/**
	 * Create the application.
	 */
	public GlavniProzor(int m, int n, int brMina, int pozX, int pozY) {
		this.m = m;
		this.n = n;
		this.brMina = brMina;
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
		
		Polje[][] polja = new Polje[m][n];
		int brPolja = m * n;
		int brMina_temp = brMina;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				polja[i][j] = new Polje(i, j, m, n, brPolja, brMina_temp, polja, frame);
				brPolja--;
				if (polja[i][j].mina) brMina_temp--;
			}
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				polja[i][j].poljeInit();
	}
}

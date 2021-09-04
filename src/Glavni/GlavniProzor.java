package Glavni;

import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;

public class GlavniProzor {

	JFrame frame;
	int pozX;
	int pozY;
	Polje[][] polja;
	Integer m; 
	Integer n; 
	Integer brMina;
	Integer brNeobelezenihMina;
	boolean prviKlik;
	
	JLabel cifra1, cifra2, cifra3;
	JLabel dugmeReset;
	
	Timer timer;
	int prosloVreme;
	JLabel timerCifra1, timerCifra2, timerCifra3;
	
	boolean gameWon;
	boolean gameLost;
	
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
		this.prviKlik = true;
		this.gameWon = false;
		this.gameLost = false;
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
		frame.setResizable(false);
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
		dugmeReset.setBounds(frame.getWidth() / 2 - 28, 10, 40, 40);
		dugmeReset.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeReset.png")));
		frame.getContentPane().add(dugmeReset);
		
		cifra1 = new JLabel("");
		cifra1.setBounds(0, 10, 27, 40);
		frame.getContentPane().add(cifra1);
		
		cifra2 = new JLabel("");
		cifra2.setBounds(27, 10, 27, 40);
		frame.getContentPane().add(cifra2);
		
		cifra3 = new JLabel("");
		cifra3.setBounds(27 * 2, 10, 27, 40);
		frame.getContentPane().add(cifra3);
		
		ispisiBrojNeobelezenihMina();
		
		timerCifra1 = new JLabel("");
		timerCifra1.setBounds(frame.getWidth() - 3 * 27 - 15, 10, 27, 40);
		frame.getContentPane().add(timerCifra1);
		
		timerCifra2 = new JLabel("");
		timerCifra2.setBounds(frame.getWidth() - 2 * 27 - 15, 10, 27, 40);
		frame.getContentPane().add(timerCifra2);
		
		timerCifra3 = new JLabel("");
		timerCifra3.setBounds(frame.getWidth() - 1 * 27 - 15, 10, 27, 40);
		frame.getContentPane().add(timerCifra3);
		
		ispisiProtekloVreme();
		
		//inicijalizacija tajmera
		timer = new Timer(1000, taskPerformer);
		timer.setRepeats(true);
		timer.setInitialDelay(0);
		
		//inicijalizacija polja
		polja = new Polje[m][n];
		
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) 
				polja[i][j] = new Polje(i, j, this);
	}
	
	void ispisiProtekloVreme() {
		ispisiCifru(timerCifra1, this.prosloVreme / 100);
		ispisiCifru(timerCifra2, (this.prosloVreme % 100) / 10);
		ispisiCifru(timerCifra3, this.prosloVreme % 10);
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
	
	private ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            ispisiProtekloVreme();
            prosloVreme++;
            
            if (prosloVreme >= 1000) 
            	timer.stop();
        }
    };
}

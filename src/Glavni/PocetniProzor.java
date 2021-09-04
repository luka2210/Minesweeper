package Glavni;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PocetniProzor {

	private JFrame frame;
	private JTextField unosBrPoljaM;
	private JTextField unosBrPoljaN;
	private JTextField unosBrMina;
	
	private Integer m, n, brMina;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PocetniProzor window = new PocetniProzor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PocetniProzor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		unosBrPoljaM = new JTextField();
		unosBrPoljaM.setBounds(102, 47, 86, 20);
		frame.getContentPane().add(unosBrPoljaM);
		unosBrPoljaM.setColumns(10);
		
		unosBrPoljaN = new JTextField();
		unosBrPoljaN.setBounds(102, 86, 86, 20);
		frame.getContentPane().add(unosBrPoljaN);
		unosBrPoljaN.setColumns(10);
		
		unosBrMina = new JTextField();
		unosBrMina.setBounds(102, 124, 86, 20);
		frame.getContentPane().add(unosBrMina);
		unosBrMina.setColumns(10);
		
		Button button = new Button("New button");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!proveriUnos()) return;
				otvoriGlavniProzor();
			}
		});
		button.setBounds(102, 160, 86, 22);
		frame.getContentPane().add(button);
	}
	
	private void otvoriGlavniProzor() {
		GlavniProzor gp = new GlavniProzor(m, n, brMina, 100, 100);
	}
	
	private boolean proveriUnos() {
		try {
			if (unosBrPoljaM.getText().isBlank() || unosBrPoljaN.getText().isBlank() || unosBrMina.getText().isBlank()) throw new Exception("Нисте попунили сва поља!");
			m = Integer.parseInt(unosBrPoljaM.getText());
			n = Integer.parseInt(unosBrPoljaN.getText());
			brMina = Integer.parseInt(unosBrMina.getText());
			if (!(inRange(m, 10, 40))) throw new Exception("Висина табле мора бити цео број између 10 и 40.");
			if (!(inRange(n, 10, 80))) throw new Exception("Ширина табле мора бити цео број између 10 и 80.");
			if (!(inRange(brMina, 1, n * m - 9))) throw new Exception("Број мина за унете димензије табле мора бити цео број између 1 и " + (n * m - 9) + ".");
			return true;
		}
		catch (NumberFormatException err) {
			JOptionPane.showMessageDialog(null, "Унете вредности морају бити цели бројеви.", "Грешка уноса", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		catch (Exception err) {
			JOptionPane.showMessageDialog(null, err.getMessage(), "Грешка уноса", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	private boolean inRange(int num, int min, int max) {
		return num >= min && num <= max;
	}
}

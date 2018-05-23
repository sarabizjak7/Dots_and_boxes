package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igra;
import logika.Plosca;
import logika.Poteza;
import logika.Smer;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener{
	
	/**
	 * JPanel, v katerega risemo crte
	 */
	private IgralnoPolje polje;
	
	/**
	 * Statusna vrstica v spodnjem delu okna
	 */
	private JLabel status;
	
	/**
	 * Logika igre
	 * null, �e se trenutno ne igra
	 */
	private Igra igra;
	
	/**
	 * Strateg, ki rise rdece crte
	 */
	private Strateg rdec;
	
	/**
	 * Strateg, ki rise modre crte
	 */
	private Strateg moder;
	
	// Izbire v menujih 
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	
	public GlavnoOkno() {
		this.setTitle("Dots and Boxes");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu 
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);

		igraClovekRacunalnik = new JMenuItem("�lovek � ra�unalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Ra�unalnik � �lovek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);

		igraRacunalnikRacunalnik = new JMenuItem("Ra�unalnik � ra�unalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

		igraClovekClovek = new JMenuItem("�lovek � �lovek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints izgledPolja = new GridBagConstraints();
		izgledPolja.gridx = 0;
		izgledPolja.gridy = 0;
		izgledPolja.fill = GridBagConstraints.BOTH;
		izgledPolja.weightx = 1.0;
		izgledPolja.weighty = 1.0;
		getContentPane().add(polje, izgledPolja);
		
		// statusna vrstica za sporocila - dokoncaj
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
				status.getFont().getStyle(), 20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// zacnemo novo igro
		novaIgra();
		
	}
	
	/**
	 * Metoda, ki ustvari novo igro
	 */
	private void novaIgra() {
		if (rdec != null) { rdec.prekini(); }
		if (moder != null) { moder.prekini(); }
		this.igra = new Igra();
		rdec = new Clovek(this);
		moder = new Clovek(this);
		switch (igra.stanje()) {
		case NA_POTEZI_RDEC: rdec.na_potezi(); break;
		case NA_POTEZI_MODER: moder.na_potezi(); break;
		default: break;
		}
		osveziGUI();
		repaint();
	}
	
	/**
	 * 
	 * @param p
	 * odigra potezo
	 */
	public void odigraj(Poteza p) {
		igra.odigraj(p);
		osveziGUI();
		switch (igra.stanje()) {
		case NA_POTEZI_RDEC: rdec.na_potezi(); break;
		case NA_POTEZI_MODER: moder.na_potezi(); break;
		case ZMAGA_RDEC: break;
		case ZMAGA_MODER: break;
		case NEODLOCENO: break;
		}
	}
	
	/**
	 * Osvezuje celotni GUI (statusna vrstica)
	 */
	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra se ne izvaja");
		} else {
			switch(igra.stanje()) {
			case NA_POTEZI_RDEC: status.setText("Na potezi: RDEC"); break;
			case NA_POTEZI_MODER: status.setText("Na potezi: MODER"); break;
			case ZMAGA_RDEC: status.setText("Zmaga: RDEC"); break;
			case ZMAGA_MODER: status.setText("Zmaga: MODER"); break;
			case NEODLOCENO: status.setText("Nimata za burek!"); break;
			
			}
		}
		polje.repaint();
	}
	
	/**
	 * 
	 * @return trenutna igralna plosca (null, ce se igra ne izvaja)
	 */
	public Plosca getPlosca() {
		return (igra == null ? null : igra.getPlosca());	
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * Metoda, ki ob kliku na (i, j) pravilno ukrepa 
	 */
	
	// POPRAVI NEKAJ S KLIKOM NA CRTO
	public void klikniPolje (Smer s, int vis, int sir) {
		if (igra != null) {
			switch (igra.stanje()) {
			case NA_POTEZI_RDEC: rdec.klikni(s, vis, sir); break;
			case NA_POTEZI_MODER: moder.klikni(s, vis, sir); break;
			default: break;
			}
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @return kopijo trenutne igre
	 */
	//public Igra kopirajIgro() {
		//return new Igra(igra);
	//}
}

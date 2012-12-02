package fr.interfacePackage;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class FenetreAjout extends FenetreEvenement{

	private JTextField nbMois = new JTextField("4",2);
	private ButtonGroup periode = new ButtonGroup();
	private JRadioButton periodeW = new JRadioButton("Weekly");
	private JRadioButton periodeB = new JRadioButton("Bi-weekly");
	
	public FenetreAjout(JFrame parent, boolean modal) {
		super(parent, "Ajouter votre �venement", modal);
		
		gl.setRows(gl.getRows()+1);
		
		// Parametre du champ R�currence
		JLabel recLabel = new JLabel("Recurrence sur:");
		JLabel recMois = new JLabel("mois."); 
		JPanel recP = new JPanel();
		periode.add(periodeW);
		periode.add(periodeB);
		periodeW.setSelected(true);
		recP.add(recLabel);
		recP.add("Rec", nbMois);
		recP.add(recMois);
		recP.add(periodeW);
		recP.add(periodeB);
				
		elements.add(recP);
		this.setVisible(true);
				
	}

}

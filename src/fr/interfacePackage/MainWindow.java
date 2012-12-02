/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.interfacePackage;

/**
 *
 * @author Florian
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import net.fortuna.ical4j.data.ParserException;
import fr.controlerPackage.GestionnaireEDT;
import fr.modelPackage.Event;
import fr.modelPackage.ICalEvent;
import fr.utilities.MyModel.MyModel;
 
public class MainWindow extends JFrame {

    private GestionnaireEDT mon_gestionnaire = GestionnaireEDT.getInstance();
    /* Menu déroulant*/
    private JMenuBar my_menuBar = new JMenuBar();
    /* Onglets */           
    private JMenu tab1 = new JMenu("Configuration emploi du temps");  
    private JMenu tab2 = new JMenu("Aide");
    /* Items pour onglets */
    private JMenuItem item1_1 = new JMenuItem("Créer un emploi du temps");
    private JMenuItem item1_2 = new JMenuItem("Charger Calendrier");
    private JMenuItem item2_1 = new JMenuItem("Equipe de Developpement");
    /* Boutons */
    private JButton addButton = new JButton("Ajouter");
    private JButton modifyButton = new JButton("Modifier");
    private JButton deleteButton = new JButton("Supprimer");
    
    private JTextArea textInformation = new JTextArea();
    private JPanel my_panel1 = new JPanel();
    private JPanel my_panel2 = new JPanel();
    
    private JTable board;
    Object[][] donnees ;
    
    public MainWindow(){
    this.setTitle("Logiciel de gestion d'emploi du temps");
    this.setSize(900,550);
    //Position the window on the center of the screen
    this.setLocationRelativeTo(null);
    //Termine le processus lorsqu'on clique sur la croix rouge
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    
    this.tab1.add(item1_1);
    this.tab1.add(item1_2);
    this.tab2.add(item2_1);
    item1_2.addActionListener(new loadingFile());
    item2_1.addActionListener(new ItemListener());
    
    this.my_menuBar.add(tab1);
    this.my_menuBar.add(tab2);
    
    this.setJMenuBar(my_menuBar);
    
    //Définition d'un nouveau MouseListener
    //board.addMouseListener(new MyMouseListener());
    //Action des boutons
    addButton.addActionListener(new AddEvent());
    modifyButton.addActionListener(new ModifyEvent());
    deleteButton.addActionListener(new DelRowListener());
        
    //Les données du tableau
    Object[][] donnees = {
      {"12/11/2011", "14:00-16:00", "Concepts et outils"},
      {"15/11/2011", "8:00-9:00", "Application Web"},
      {"17/11/2011", "12:00-16:00", "S�mantique Web"},
      {"19/11/2011", "10:00-11:00", "G�nie Logiciel"}
    };
    
    //Les titres des colonnes
    String titres[] = {"Date", "Horaires", "Matières"};
    
    textInformation = new JTextArea("---------- Informations sur le Module ----------",100,39);  
    textInformation.setEditable(false);
    
    my_panel1.add(textInformation);
    my_panel2.add(addButton);
    my_panel2.add(modifyButton);
    my_panel2.add(deleteButton);
       
    MyModel model = new MyModel(donnees, titres);
    board = new JTable(model);

    
    my_panel2.add(new JScrollPane(board));
    
    this.getContentPane().add(my_panel1, BorderLayout.EAST);
    this.getContentPane().add(my_panel2, BorderLayout.SOUTH);
    this.getContentPane().add(new JScrollPane(board), BorderLayout.WEST);
    }

    /* AFFICHAGE DES INFOS AU CLIC A IMPLEMENTER */
//  class MyMouseListener implements MouseListener{
//      //Rédéfinition d'un MouseListener
//      @Override
//            public void mouseClicked(MouseEvent e) {
//                //MyModel modelTemp = (MyModel) board.getModel();
//                textInformation.setText("---------- Informations ----------" + "\n" +
//                                        "Date : " + " "+ "\n" +
//                                        "Horaire : " +" " + "\n" +
//                                        "Matière : " + " " + "\n");
//        }
//
//        @Override
//        public void mousePressed(MouseEvent me) {
//            //throw new UnsupportedOperationException("Not supported yet.");
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent me) {
//            //throw new UnsupportedOperationException("Not supported yet.");
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent me) {
//            //throw new UnsupportedOperationException("Not supported yet.");
//        }
//
//        @Override
//        public void mouseExited(MouseEvent me) {
//            //throw new UnsupportedOperationException("Not supported yet.");
//        }
//  };
   
  class loadingFile implements ActionListener{
      @Override 
          public void actionPerformed(ActionEvent arg0){
          try {
              mon_gestionnaire.remplirList("myEDT");
              MyModel modelTemp = (MyModel)board.getModel();
              ArrayList<ICalEvent> listTemp = mon_gestionnaire.getICalEvents();
              Object[] contenu = {"Bra", "Bra", "Bra"};
              modelTemp.addRow(contenu);
          } catch (FileNotFoundException ex) {
              Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
          } catch (ParserException ex) {
              Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
          }          
      }
  }
  class AddEvent implements ActionListener{
      //Redéfintion de la méthode actionPerformed()
       @Override
        public void actionPerformed(ActionEvent arg0) {   
            FenetreEvenement ev = new FenetreAjout(null, true);
            ev.setVisible(true);
        }
  }
    class ModifyEvent implements ActionListener{
      //Redéfintion de la méthode actionPerformed()
       @Override
        public void actionPerformed(ActionEvent arg0) {   
            FenetreEvenement ev = new FenetreModification(null, true);
            ev.setVisible(true);
        }
  }
  
 
  class DelRowListener implements ActionListener{
      //Redéfintion de la méthode actionPerformed()
       @Override
        public void actionPerformed(ActionEvent arg0) {   
            int selectedRow = board.getSelectedRow(); 
            MyModel modelTemp = (MyModel)board.getModel();
            modelTemp.removeRow(selectedRow);
        }
  }
  
  class ItemListener implements ActionListener{
    //Redéfinition de la méthode actionPerformed()
    @Override
    public void actionPerformed(ActionEvent arg0) {
        JFrame fenetreDev = new JFrame("Equipe de développement");
        fenetreDev.setSize(300,100);
        fenetreDev.setLocationRelativeTo(null);
        JTextArea textArea = new JTextArea("Ce logiciel à été développé par Florian FAGNIEZ,\nGuillaume Coutable et Noémie RULLIER \nM1 ALMA - TPA");
        JPanel panelDev = new JPanel();
        fenetreDev.getContentPane().add(textArea, BorderLayout.CENTER); 
        fenetreDev.setVisible(true);
    }
  }

  public static void main(String[] args){
    MainWindow fen = new MainWindow();
    fen.setVisible(true);
  }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utilities.MyModel;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Florian
 */
public class MyModel extends AbstractTableModel{

   private Object[][] data;
   private String[] title;
   
   //Constructeur
   public MyModel(Object[][] data, String[] title){
      this.data = data;
      this.title = title;
   }
   
   public int getARowOf(String key, int col) {
	   int i = 0;
	   int taille = getRowCount();
	   while(!data[i][col].toString().equals(key) && i < taille){
		   i++;
	   }
	   if(i>=taille){
		   i = -1;
	   }
	   return i;
   }
   
   @Override
   public String getColumnName(int col) {
     return this.title[col];
   }

   @Override
   public int getColumnCount() {
      return this.title.length;
   }
   
   @Override
   public int getRowCount() {
      return this.data.length;
   }
   
   @Override
   public Object getValueAt(int row, int col) {
      return this.data[row][col];
   }
   
   @Override
   public void setValueAt(Object value, int row, int col) {
      //On interdit la modification sur certaines colonnes !
      if(!this.getColumnName(col).equals("Suppression")) {
           this.data[row][col] = value;
       }
   }
         
   @Override
   public Class getColumnClass(int col){
      return this.data[0][col].getClass();
   }

   
   public void removeRow(int position){ 
      int indice = 0, indice2 = 0;
      int nbRow = this.getRowCount()-1; 
      int nbCol = this.getColumnCount();
      Object temp[][] = new Object[nbRow][nbCol];
      
      for(Object[] value : this.data){
         if(indice != position){
            temp[indice2++] = value;
         }
         System.out.println("Indice = " + indice);
         indice++;
      }
      this.data = temp;
      temp = null;
      //Cette méthode permet d'avertir le tableau que les données 
      //ont été modifiées, ce qui permet une mise à jour complète du tableau
      this.fireTableDataChanged();
   }
   
   public void addRow(Object[] data){
      int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
      
      Object temp[][] = this.data;
      this.data = new Object[nbRow+1][nbCol];
      
      for(Object[] value : temp){
           this.data[indice++] = value;
      }
      
         
      this.data[indice] = data;
      temp = null;
      this.fireTableDataChanged(); //Permet la MAJ du tableau
   }
   
   @Override
   public boolean isCellEditable(int row, int col){
      return false;
   }
}
    
 


/**
 * 
 */
package fr.modelPackage;


/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public enum CategoriesCourse {
	/**
	 * 
	 */
	CM("CM"),
	/**
	 * 
	 */
	TD("TD"),
	/**
	 * 
	 */
	TP("TP"),
	/**
	 * 
	 */
	CC("Controle Continu"),
	/**
	 * 
	 */
	CCTP("Controle Continu de TP"),
	/**
	 * 
	 */
	RENDUPROJET("Rendu de projet"),
	/**
	 * 
	 */
	EXAMEN("Examen");
	
	private String name ;  
    
    private CategoriesCourse(String name) {  
        this.name = name ;  
   }  
     
    public String getName() {  
        return  this.name ;  
   }
    
    public static CategoriesCourse fromString(String text) {
        if (text != null) {
          for (CategoriesCourse c : CategoriesCourse.values()) {
            if (text.equalsIgnoreCase(c.name)) {
              return c;
            }
          }
        }
        return null;
      }
}

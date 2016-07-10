
package GUI;

import DAO.DAOException;
import DAO.DAOManager;
import DAO.SQL.SqlManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/***Clase main que ejecuta la aplicacion
 * @author Headcruser87*/
public class Main 
{
    //metodo main
    public static void main(String[] args) throws DAOException, Exception
    {       
      DAOManager manager=new SqlManager();
      
      java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                  try {
                      UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
                    new UI_GestionUsuario( manager ).setVisible(true);
                   }
                  catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | DAOException e) {
                        System.out.println("ERROR: "+e);
                  } // Fin catch
            } // fin del metodo run
        }); // Fin de evento      
    } // Fin main
} // fin de la clase 


package DAO.SQL;

import DAO.DAOManager;
import DAO.PersonaDao;
import java.sql.Connection;



/**Clase que gestiona a las clases implementadas en el programa
 * @see DAOManager implementa al manejador de los DAO
 * @author Headcruser87*/
public class SqlManager extends Sqlconexion implements DAOManager
{
    /**almacena una referencia de la interfaz PersonaDao*/
    private PersonaDao usuarios;
    
    //almacena la conexion
    private Connection con;
    
    public SqlManager() throws Exception
    {
        //inicioaliza la conexion
        con=getConection();
    }
 
    //Patron Singleton
    @Override
    public PersonaDao getUsuarioDao() 
    {
        if( usuarios==null )
             usuarios=new SqlPersonaDao( con );
      return usuarios;
    } 
} // Fin de la case 

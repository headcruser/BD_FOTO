package DAO.SQL;

import DAO.DAOManager;
import DAO.FotoDao;
import DAO.PersonaDao;
import java.sql.Connection;



/**Clase que gestiona a las clases implementadas en el programa
 * @see DAOManager implementa al manejador de los DAO
 * @author Headcruser87*/
public class SqlManager extends Sqlconexion implements DAOManager
{
    /**almacena una referencia de la interfaz PersonaDao*/
    private PersonaDao usuarios;
    
    private FotoDao fotos;
    
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

    @Override
    public FotoDao getFotoUsuarioDao() {
       if( fotos ==null)
           fotos=new SqlFoto(con );
       return fotos;
    }
} // Fin de la case 

package DAO.SQL;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**Clase que realiza la conexion con la base de datos.
 * @author Headcruser87
 * @see DriverManager <b>Libreria para utilizar sql </b>
 * @version 1.2_2016
 */
public abstract class Sqlconexion implements Serializable
{
    //////////////////////////////////////////////////////////////////////////////////////
    //                                               ATRIBUTOS DE LA CLASE
    //////////////////////////////////////////////////////////////////////////////////////
    
    /**ATRIBUTO PARA LA CONEXION CON EL SERVIDOR */
    private static Connection CONEXION=null;
    
    /**Atributo para indicar el usuario de la base de datos*/
    private static final String USUARIO="DANIEL_SQL";
    
    /**Atributo que indica la contraseña del equipo*/
    private static final String PASSWD="admin120324";
    
    /**Atributo que indica el host predeterminado*/
    private static final String EQUIPO="localhost";
    
    /**Atributo que indica la base de datos a utilizar*/
    private static final String BD_NOMBRE="USUARIOS";
    
    /**Atributo para especificar el driver*/
    private static final String DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
       
 /**Metodo encargado de establecer la conexion con la base de datos. 
   * @return Regresa como referencia, el estado de la conexion.
  * @throws java.lang.Exception  Se lanza una  exepcion si el servicio de sql no funciona.*/
    protected Connection getConection () throws Exception
    {
        if( isConectionNull() )
        {
            try 
            {
                    // SE MANDA LLAMAR AL DRIVER DE CONEXION SQL SERVER
                    Class.forName( DRIVER );

                    // SE ESTABLECE UNA CONEXION CON LA BASE DE DATPS 
                    CONEXION = DriverManager.getConnection( getUrl() );   
            } 
            catch ( SQLException | ClassNotFoundException e) 
                {
                   throw new Exception("ERROR DE CONEXION");
                }
           // RETORNA EL ESTADO DE LA CONEXION 
        }// Fin de if        
        return CONEXION;
    } // FIN DEL METODO 
    
    /**Finaliza la conexion con la base de datos
     * @exception Exception Si la conexion no se puede cerrar, entonces 
     * lanza una exepcion
     */
    public void closeConnection() throws Exception
    {
        try
        {   
            if( !isConectionNull() )
                CONEXION.close();
        }
        catch (SQLException e) 
        {          throw new Exception("No se pudo finalizar la conexion");}
    } // Fin del metodo
    
   
    /**Indica si la conexion es nulla*/
    private boolean isConectionNull() throws SQLException
    {
        return CONEXION==null || CONEXION.isClosed();
    } // fin del metodo 
    
    
    /**Obtiene el url para el driver de la conexion*/
    private String getUrl()
    {                    
       StringBuilder builderURL = new StringBuilder();
        builderURL.append("jdbc:sqlserver://")
                                        .append(EQUIPO)
                                        .append(":49926;")  // NOTA: REVISAR EL NUMERO DE PUERTO DE LA INSTANCIA 
                                        .append("databaseName=")
                                        .append(BD_NOMBRE)
                                        .append(";user=")
                                        .append(USUARIO)
                                        .append(";password=")
                                        .append(PASSWD)
                                        .append(";");
        return builderURL.toString();
    } // fin del metodo 
}// FIN DE LA CLASE

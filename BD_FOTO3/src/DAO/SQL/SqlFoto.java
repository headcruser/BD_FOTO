
package DAO.SQL;

import DAO.FotoDao;
import ModeloBD.Persona;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *Clase que gestiona la tabla foto en la base de datos
 * @see FotoDao interfaz que contiene los metoos para obtener una foto de la base de datos
 * @author Headcruser87
 */
public class SqlFoto implements FotoDao
{
        /**almacena una referencia de la conexion con la base de dato*/
    private Connection conn;
    
    final String GETFOTO="SELECT FOTO  "  +"FROM usuario "  +"WHERE ID_USUARIO=?";

    
    /**constructor de la clase para administrar los registros de la tabla fotos
     * @param con Conexion a la base de datos*/
    public SqlFoto(Connection con) 
    {
        conn=con;
    } // Fin del constructor
    
    
    @Override
    public Image getfoto(Persona p_usuario) throws IOException 
    {
        PreparedStatement pstm=null;
        ResultSet res=null;
        Image data=null;
        
          try
            {    
                pstm = conn.prepareStatement( GETFOTO );
                pstm.setString(1, p_usuario.getId_usuario());
                res = pstm.executeQuery();

                  if(res.next())
                   {
                      //se lee la cadena de bytes de la base de datos
                      byte[] b = res.getBytes("foto");
                      
                      if(b!=null)
                      // esta cadena de bytes sera convertida en una imagen
                            data = ConvertirImagen(b);            
                    }
         res.close();
          } 
            catch(SQLException | IOException e){System.out.println(e);
    }        
    return data;
    }
    
         /**metodo que dada una cadena de bytes la convierte en una imagen con extension jpeg
      * @param bytes Recibe una cadena de bytes
      * @exception IOException Regresa una exepcion en caso de no leer imagen
      */
 private Image ConvertirImagen(byte[] bytes) throws IOException 
 {
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
    ImageReader reader = (ImageReader) readers.next();
    Object source = bis; // File or InputStream
    ImageInputStream iis = ImageIO.createImageInputStream(source);
    reader.setInput(iis, true);
    ImageReadParam param = reader.getDefaultReadParam();
    return reader.read(0, param);
 } // Fin del metodo
   
} // fin de la clase 

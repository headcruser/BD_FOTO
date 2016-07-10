
package DAO.SQL;

import DAO.DAOException;
import DAO.PersonaDao;
import ModeloBD.Persona;
import java.awt.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;

/**
 *Clase geestiona la sintaxis de sql de la tabla Persona
 * @see PersonaDao Implementa la interface y sus metodos
 * @author Headcruser87
 */
public class SqlPersonaDao implements PersonaDao
{
    final String INSERT="{CALL INSERTAR (?,?,?,?,?,?,?,?,?)}";
    
    final String UPDATE="{CALL ACTUALIZAR_USUARIO (?,?,?,?,?,?,?,?,?)}";
    
    final String DELETE="DELETE FROM Usuario WHERE ID_USUARIO=?";
    
    final String GETONE="SELECT *\n" +
                                              "FROM  USUARIO  WHERE ID_USUARIO=?";
    
    final String GETALL="SELECT *  "+ "FROM USUARIO ";
    final String GETFOTO="SELECT FOTO  " 
                                                 +"FROM usuario " 
                                               +"WHERE ID_USUARIO=?";
    
    private Connection conn;

   /**Construte el manejador  de sintaxis de sql
    * @param conn Recibe una instancia de conexion */
    public SqlPersonaDao(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public void insertar(Persona p_usuario) throws DAOException 
    {
         CallableStatement entrada=null;
        Connection conAux=conn;
        try {
             conAux.setAutoCommit(false);
            entrada=conAux.prepareCall(INSERT );

             entrada.setString(1, p_usuario.getDni());
             entrada.setString(2, p_usuario.getNombres());
             entrada.setString(3, p_usuario.getApellidos());
             entrada.setString(4, p_usuario.getCorreo());
             entrada.setString(5, p_usuario.getTelefono());
             entrada.setString(6, p_usuario.getUsuario());
             entrada.setString(7, p_usuario.getFecha());
             entrada.setBinaryStream(8, p_usuario.getFis(),p_usuario.getLongitudBytes());
             entrada.setInt(9, 1);
            
            if(!entrada.execute())
                 JOptionPane.showMessageDialog(null,"EL USUARIO "+p_usuario.getNombres()
                                               +" SE ACTUALIZO CORRECTAMENTE"
                                               ,"PRUEBA",JOptionPane.INFORMATION_MESSAGE);
            conAux.commit();
        } 
        catch ( SQLException ex ) 
        {
             try 
             {
                 conAux.rollback();
                 throw new DAOException("Error al ejecutar el procedimiento INSERT");
             } catch (SQLException ex1) {
                 throw new DAOException("Error en sql", ex);
             }
        }
        finally
        {
                if( entrada!=null )
                {
                    try 
                    {
                        entrada.close();
                        conAux.close();
                    } 
                    catch ( SQLException ex ) {
                        throw new DAOException("Error en sql", ex);
                    }
                }
        } //Fin finally
   } // Fin del metodo
    

    @Override
    public void modificar(Persona p_usuario) throws DAOException 
    {
         PreparedStatement entrada=null;
         Connection conAux=conn;
        try 
        {
            conAux.setAutoCommit(false);
            entrada=conAux.prepareStatement( UPDATE );
            
            entrada.setString(1, p_usuario.getId_usuario());
             entrada.setString(2, p_usuario.getDni());
             entrada.setString(3, p_usuario.getNombres());
             entrada.setString(4, p_usuario.getApellidos());
             entrada.setString(5, p_usuario.getCorreo());
             entrada.setString(6, p_usuario.getTelefono());
             entrada.setString(7, p_usuario.getUsuario());
             entrada.setString(8, p_usuario.getFecha());
            entrada.setBinaryStream(9, p_usuario.getFis(),p_usuario.getLongitudBytes());
            
            if( entrada.executeUpdate()==0 )
                throw new DAOException("Error al actualizar el alumno");
            
            conAux.commit();
        } 
        catch ( SQLException ex ) 
        {
             try 
             {
                 conAux.rollback();
             } catch (SQLException ex1) {
                 throw new DAOException("Error en la transaccion Rollback", ex1);
             }
             throw new DAOException("Error al intentar actualizar", ex);
        }
        finally{
            if( entrada!=null ){
                try {
                    entrada.close();
                    conAux.close();
                } catch ( SQLException ex ) {
                    throw new DAOException("Error en sql", ex);
                }
            }
        }
    } // Fin del metodo

    @Override
    public void eliminar(Persona a) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Persona> obtenerTodos() throws DAOException 
    {
        PreparedStatement stat=null;
        ResultSet rs=null;
        List<Persona> a2=new ArrayList<>();
        try 
        {
            stat=conn.prepareStatement( GETALL );
            rs=stat.executeQuery();
            
            while (rs.next()) 
                a2.add( convertir( rs ) );
                
        } 
        catch (SQLException e) {
            throw new DAOException( "Error sql",e ); 
        }
        finally
        {
            if( rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException( "Error sql",e );
                }
            }//fin if
            
            if(stat!=null){
                try {
                    stat.close();
                } catch (SQLException e) {
                    throw new DAOException( "Error sql",e );
                }
            } //Fin if
        } //Fin finally
        return a2;
    }

    @Override
    public Persona obtener(String id) throws DAOException 
    {
        PreparedStatement stat=null;
        ResultSet rs=null;
        Persona usr=null;
        try 
        {
            stat=conn.prepareStatement( GETONE );
            stat.setString( 1 , id );
            rs=stat.executeQuery();
            
            if( rs.next() )
                usr=convertir(rs);
             else
                throw new DAOException("No se ha encontroado el registro" );
            
        } 
        catch (SQLException e) {
            throw new DAOException( "Error sql",e );
        }
        finally
        {
            if( rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException( "Error sql",e );
                }
            }
            if(stat!=null){
                try {
                    stat.close();
                } catch (SQLException e) {
                    throw new DAOException( "Error sql",e );
                }
            }
                
        }
        return usr;
    }
 
    public Persona convertir(ResultSet rs) throws SQLException
    {
        Persona usuario=new Persona();
        
        usuario.setId_usuario ( rs.getString(1) );
        usuario.setDni(rs.getString(2)  );
        usuario.setNombres(rs.getString(3)  );
        usuario.setApellidos(rs.getString( 4)  );
        usuario.setCorreo(rs.getString(5)  );
        usuario.setTelefono(rs.getString(6)  );
        usuario.setUsuario(rs.getString(7)  );
        usuario.setFecha (  rs.getDate(8) );
        usuario.setEstado ( rs.getInt(10) );
        usuario.setClave("1");
        return usuario;
    }

    @Override
    public Image getfoto(String p_usuario)
    {
        PreparedStatement pstm=null;
        ResultSet res=null;
        Image data=null;
        
          try
            {    
                pstm = conn.prepareStatement( GETFOTO );
                pstm.setString(1, p_usuario);
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
}  // Fin del metodo
    
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
 } 
    
} // Fin de la clase

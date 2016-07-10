package GUI.SwingModel;

import DAO.DAOException;
import DAO.PersonaDao;
import ModeloBD.Persona;
import java.util.List;

import javax.swing.table.AbstractTableModel;
/**
 *Clase que modela a un JTable
 * @see AbstractTableModel Implementa un Modelo de tabla
 * @author Martinez Sierra Daniel
 */
public class TbModeloUsuario extends AbstractTableModel  
{
   /***********************************************************************************
      *                                           ATRIBUTOS DE LA CLASE
     ***************************************************************************************/
    /**Almacena los valores de la tabla*/
   private List<Persona> listaUsuarios;
   
   /**Referencia al manejador de usuario*/
   private PersonaDao usuarios;
    
    /**Titulo de la tabla*/
    private final String[] Titulo={"id_usuario","DNI","Nombres","Apellidos","Correo"};
    

    /**Constructor que Actualiza La tabla.<br>
       *  Obtiene directamente los valores desde la base de datos  y Actualiza la tabl
     * @param p_usr*/
    public TbModeloUsuario(PersonaDao p_usr) 
    {
        usuarios=p_usr;
    }
        
    /*** @throws DAO.DAOException*/
    public void UpdateModel() throws DAOException
    {
            listaUsuarios=usuarios.obtenerTodos();
            
            //Registra que el modelo de la tabla cambio 
            fireTableStructureChanged();
    }
    
/*** Obtiene el numero de elementos de la tabla
     * @return numero Entero     */
    @Override
    public int getRowCount() 
    {   return listaUsuarios.size(); }

    /*** Obtiene el titulo de la columna seleccionada
     * @return Regresa el titulo de la tabla*/
    @Override
    public String getColumnName(int column) 
    {  return Titulo[column];   }

   
    /*** Obtiene el titulo de la columna seleccionada
     * @return numero Entero     */
    @Override
    public int getColumnCount() 
    {  return Titulo.length;  }

    
    /*** Regresa el valor de la casilla seleccionada
     * @param rowIndex Valor de la fila 
     * @param columnIndex Valor de la colunmna
     * @return Regresa los valores del campo seleccionado   */
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        
        switch (columnIndex) 
        {
            case 0: return listaUsuarios.get(rowIndex).getId_usuario();
            case 1: return listaUsuarios.get(rowIndex).getDni();
            case 2: return listaUsuarios.get(rowIndex).getNombres();
            case 3: return listaUsuarios.get(rowIndex).getApellidos();
            case 4: return listaUsuarios.get(rowIndex).getCorreo();
            default: return null;
        }
    
    } 

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
        switch (columnIndex) {
            case 0: listaUsuarios.get(rowIndex).setId_usuario(String.valueOf(aValue)); 
                    break;
            case 1:
                listaUsuarios.get(rowIndex).setDni(String.valueOf(aValue)); 
                    break;
                    
            case 2:
                listaUsuarios.get(rowIndex).setNombres(String.valueOf(aValue)); 
                    break;
            case 3:
                listaUsuarios.get(rowIndex).setApellidos(String.valueOf(aValue)); 
                    break;
                
            case 4:
                listaUsuarios.get(rowIndex).setCorreo(String.valueOf(aValue)); 
                    break;
            default:
                throw new AssertionError();
        }
        
    }
}

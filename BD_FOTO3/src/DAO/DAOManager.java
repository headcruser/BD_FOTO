
package DAO;

/**Interface que gestiona los Dao existentes
 * @author Headcruser87 */
public interface DAOManager 
{
    /**Obtienene a los usuarios del sistemas
     * @return Regresa una referencia de los usuarios gestionados por
     * la interface.*/
    PersonaDao getUsuarioDao();
}

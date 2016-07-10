
package DAO;

import java.util.List;


/**
 * Interface que contiene los metodos a implementar por las clases posteriores
 * @author Headcruser87
 * @param <T> Indica el tipo de Objeto que van a usar los metodos 
 * @param <K> >Indica el identificador de busqueda
 */
public interface DAO <T,K>
{
   
    void insertar(T a) throws  DAOException;
    void modificar(T a) throws  DAOException;;
    void eliminar(T a) throws  DAOException;;
    List<T> obtenerTodos() throws  DAOException;
    T obtener(K id) throws  DAOException;;
    
}

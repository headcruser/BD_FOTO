
package DAO;

import ModeloBD.Persona;
import java.awt.Image;
import java.io.IOException;

/**
 *Interface que modela el comportamiento de un usuario
 * @see DAO hereda las caracteristicas de la clase DAO
 * @author Headcruser87
 */
public interface FotoDao 
{
            Image getfoto(Persona p_usuario) throws IOException;
}



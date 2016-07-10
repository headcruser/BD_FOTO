
package ModeloBD;
import java.io.FileInputStream;

/**
 *Clase que modela la tabla Foto de la base de datos
 * @author Headcruser87
 */
public class Foto 
{
      private FileInputStream Foto;
      private int longitudBytes;
      
    public Foto(){}
      
    /*** @return the Foto*/
    public FileInputStream getFoto() {
        return Foto;
    }

    /*** @param Foto the Foto to set*/
    public void setFoto(FileInputStream Foto) {
        this.Foto = Foto;
    }

    /*** @return  longitudBytes Regresa el tamaño de la foto*/
    public int getLongitudBytes() {
        return longitudBytes;
    }

    /** @param P_longitudBytes tamaño de la foto */
    public void setLongitudBytes(int P_longitudBytes) {
        this.longitudBytes = P_longitudBytes;
    }
      
} // Fin de la clase 

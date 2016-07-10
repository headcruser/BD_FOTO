
package ModeloBD;

/**Clase que modela a un usuario de la base de datos*/
public class Usuario 
{
    private String idUsuario;
    private String usuario;
    private String passwd;

    public Usuario() { }
    
    /*** @return the idUsuario*/
    public String getIdUsuario() {
        return idUsuario;
    }

    /*** @param idUsuario the idUsuario to set*/
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /*** @return the usuario*/
    public String getUsuario() {
        return usuario;
    }

    /** @param usuario the usuario to set*/
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /*** @return the passwd     */
    public String getPasswd() {
        return passwd;
    }

    /*** @param passwd the passwd to set*/
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
} // Fin de la clase

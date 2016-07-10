package ModeloBD;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Headcruser87
 */
public class Persona 
{
    private String id_usuario;
    private String dni;
    private String Nombres;
    private String Apellidos;
    private String Correo;
    private String Telefono;
    private String Usuario;
    private String Clave;
    private Date Fecha;
    private FileInputStream fis;
    private int longitudBytes;
    private int estado;
    
    public Persona(){}

    public Persona(String id_usuario, String dni, String Nombres, String Apellidos, String Correo) {
        this.id_usuario = id_usuario;
        this.dni = dni;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.Correo = Correo;
    }

    public Persona( String dni, String Nombres, String Apellidos, String Correo, String Telefono, String Usuario, String Clave, Date Fecha, FileInputStream fis) {
        
        this.dni = dni;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.Correo = Correo;
        this.Telefono = Telefono;
        this.Usuario = Usuario;
        this.Clave = Clave;
        this.Fecha = Fecha;
        this.fis = fis;
    }

    public Persona(String dni, String Nombres, String Apellidos, String Correo, String Telefono, String Usuario, String Clave, Date Fecha, FileInputStream fis, int longitudBytes) {
        this.dni = dni;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.Correo = Correo;
        this.Telefono = Telefono;
        this.Usuario = Usuario;
        this.Clave = Clave;
        this.Fecha = Fecha;
        this.fis = fis;
        this.longitudBytes = longitudBytes;
    }

    public Persona(String id_usuario, String dni, String Nombres, String Apellidos, String Correo, String Telefono, String Usuario, String Clave, Date Fecha, FileInputStream fis, int longitudBytes) {
        this.id_usuario = id_usuario;
        this.dni = dni;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.Correo = Correo;
        this.Telefono = Telefono;
        this.Usuario = Usuario;
        this.Clave = Clave;
        this.Fecha = Fecha;
        this.fis = fis;
        this.longitudBytes = longitudBytes;
    }
    

    /**
     * @return the id_usuario
     */
    public String getId_usuario() {
        return id_usuario;
    }

    /**
     * @param id_usuario the id_usuario to set
     */
    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the Nombres
     */
    public String getNombres() {
        return Nombres;
    }

    /**
     * @param Nombres the Nombres to set
     */
    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    /**
     * @return the Apellidos
     */
    public String getApellidos() {
        return Apellidos;
    }

    /**
     * @param Apellidos the Apellidos to set
     */
    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    /**
     * @return the Correo
     */
    public String getCorreo() {
        return Correo;
    }

    /**
     * @param Correo the Correo to set
     */
    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    /**
     * @return the Telefono
     */
    public String getTelefono() {
        return Telefono;
    }

    /**
     * @param Telefono the Telefono to set
     */
    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    /**
     * @return the Persona
     */
    public String getUsuario() {
        return Usuario;
    }

    /**
     * @param Usuario the Persona to set
     */
    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    /**
     * @return the Clave
     */
    public String getClave() {
        return Clave;
    }

    /**
     * @param Clave the Clave to set
     */
    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    /**
     * @return the Fecha
     */
    public String getFecha() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(Fecha);
      
    }

    /**
     * @param Fecha the Fecha to set
     */
    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    /**
     * @return the fis
     */
    public FileInputStream getFis() {
        return fis;
    }

    /**
     * @param fis the fis to set
     */
    public void setFis(FileInputStream fis) {
        this.fis = fis;
    }

    /**
     * @return the longitudBytes
     */
    public int getLongitudBytes() {
        return longitudBytes;
    }

    /**
     * @param longitudBytes the longitudBytes to set
     */
    public void setLongitudBytes(int longitudBytes) {
        this.longitudBytes = longitudBytes;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) 
    {    this.estado = estado; }

    @Override
    public String toString() {
        return  Nombres +" "+ Apellidos ;
    }
    
    
    
}


package GUI;


import DAO.DAOException;
import DAO.DAOManager;
import GUI.Control.C_ConvertirMayuscula;
import GUI.Control.C_Validar;
import ModeloBD.Persona;
import GUI.Reporte.GenerarReportes;
import GUI.SwingModel.TbModeloUsuario;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.toedter.calendar.JDateChooser.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;


/**
 *Formulario que contiene la parte grafica que interactua con el usuario
 * @see JFrame Extiende de la clase JFrame
 * @author Martinez Sierra Daniel
 */
public class UI_GestionUsuario extends javax.swing.JFrame 
{
    /**Almacena la foto que cargue el usuario*/
    private FileInputStream fotoUsuario;
    
    /**Indica el tamaño del archivo del usuario*/
    private int longitudBytes;
    
    /**Indica si es un  un usuario nuevo*/
    boolean EsUsuarioNuevo=true;
    
    /**Almacena el id del usuario*/
    String id;
    
    private DAOManager manager;
    
    private TbModeloUsuario model;
    
    private Persona usuario;
    
    
/******************************************************************************************
 *                                      CONSTRUCTOR DE LA CLASE
 * *****************************************************************************************/    
    
    
  /*** Crea la interfaz grafica para el usuario
     * @param p_manager
     * @throws DAOException Lanza una Excepcion En caso de que la interfaz no se construya adecuanamente */
    public UI_GestionUsuario(DAOManager p_manager) throws DAOException
    {
        initComponents();
        
        manager=p_manager;
        model=new TbModeloUsuario( manager.getUsuarioDao() );
        model.UpdateModel();
        tbListaUsuarios.setModel( model );
        
        m_Validar();
        setLocationRelativeTo(null);
        setResizable(true);
        
        EsUsuarioNuevo=false;
        
   
    }
//*****************************************************************************************
//    
////*****************************************************************************************
  /**Metodo que valida los campos de texto*/
    private void m_Validar()
    {
        //valida el Id
        C_Validar.m_ValidarSoloNumeros(txt_ID);
        C_Validar.m_LimitarCaracteres(txt_ID,8);
        
        //Valida Nombre
        C_Validar.m_ValidarSoloLetras(txt_Nombre);
        C_Validar.m_LimitarCaracteres(txt_Nombre,100);
        
        // valida Apellidos
        C_Validar.m_ValidarSoloLetras(txt_Apellidos);
        C_Validar.m_LimitarCaracteres(txt_Apellidos,100);
        
        //Valida Telefono
        C_Validar.m_ValidarSoloNumeros(txt_Telefono);
        C_Validar.m_LimitarCaracteres(txt_Telefono,10);
        
        //Valida Persona y contraseña
        C_Validar.m_LimitarCaracteres(txt_Usuario,20);
        C_Validar.m_LimitarCaracteres(txt_passwd,50);
        
        //Convierte en mayusculas todo lo que sea tecleado
        txt_Nombre.setDocument(new C_ConvertirMayuscula());
        txt_Apellidos.setDocument(new C_ConvertirMayuscula());
        txtBuscaUsuario.setDocument(new C_ConvertirMayuscula());
        txt_Usuario.setDocument(new C_ConvertirMayuscula());
    }
    
    
    /**Verifica que todos los campos esten completos*/
    public void m_validarIngreso()
    {
        String dni=txt_ID.getText().trim();
        int tam=dni.length();
        String nom=txt_Nombre.getText().trim();
        String ape=txt_Apellidos.getText().trim();
        boolean estado=C_Validar.m_validarEmail(txt_Correo.getText());
        String usr=txt_Usuario.getText().trim();
        String pass=txt_passwd.getText();
        Date fec=jc_Fecha.getDate();
        
        if(tam!=8||nom.isEmpty()||ape.isEmpty()||estado==false
                ||usr.isEmpty()||pass.isEmpty()||fec==null)
            btn_guardar.setEnabled(false);
        
        else
            btn_guardar.setEnabled(true);
    }
//*****************************************************************************************

//*****************************************************************************************       
   
    
    //************************************************************************************
    /**Metodo que carga la foto del usuario*/
    public void m_CargaFoto()
    {
        JFileChooser j=new JFileChooser();
        FileNameExtensionFilter filtro=new FileNameExtensionFilter("JPG & PNG","jpg","png");
        j.setFileFilter(filtro);
        
        int estado=j.showOpenDialog(null);
        if(estado==JFileChooser.APPROVE_OPTION)
        {
            try 
            {
                fotoUsuario=new FileInputStream(j.getSelectedFile());
                longitudBytes=(int)j.getSelectedFile().length();
                try
                {
                    jl_foto.setIcon(null);
                    Image icono=ImageIO.read(j.getSelectedFile())
                                        .getScaledInstance(jl_foto.getWidth(),
                                                         jl_foto.getHeight(), 
                                                         Image.SCALE_DEFAULT);
                    jl_foto.setIcon(new ImageIcon(icono));

                }
                catch(IOException e2)
                {System.out.println("Error al carcar la foto "+e2);}
                
            } catch (Exception e) 
            { System.out.println("Error al cargar el archivo"+e);}
        }
    }
//************************************************************************************

//************************************************************************************
 /**Metodo para limpiar los Campos de texto*/
    public void m_Limpiar()
    {
        txt_ID.setText(null);
        txt_Nombre.setText(null);
        txt_Apellidos.setText(null);
        txt_Telefono.setText(null);
        txt_Correo.setText(null);
        txt_Usuario.setText(null);
        txt_passwd.setText(null);
        jc_Fecha.setDate(null);
        jl_foto.setIcon(null);
        btn_guardar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        txt_ID.requestFocus();
        EsUsuarioNuevo=true;
    }
  //************************************************************************************

    private void m_InsertarUsuario(Persona p_usuario,boolean p_respuesta)
    {
          tbListaUsuarios.setModel(null);
          tbListaUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
          tbListaUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
          tbListaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(0);

    }

    
   

//    
//  ///***********************************************************************************
//    /**Metodo que realiza la busueda del usuario 
//     * @param p_usuario Nombre del usuario a buscar*/
    public void m_BuscarUsuario(String p_usuario)
    {
         tbListaUsuarios.setModel(new TbModeloUsuario( null));
         tbListaUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
         tbListaUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
         tbListaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(0);
        
    }
    ///***********************************************************************************
    
    public void setEditable(boolean editable)
    {
        txt_ID.setEditable(editable);
        txt_ID.requestFocus();
        txt_Nombre.setEditable(editable);
        txt_Apellidos.setEditable(editable);
        txt_Telefono.setEditable(editable);
        txt_Correo.setEditable(editable);
        txt_Usuario.setEditable(editable);
        txt_passwd.setEditable(editable);
        jc_Fecha.setEnabled(editable);
        btn_guardar.setEnabled(editable);
        btn_subirImagen.setEnabled(editable);
       JTPdivisorPrincipal.setEnabledAt(1, !editable);
        EsUsuarioNuevo=editable;
    }
    
    public void cargarDatos()
    {
        if( usuario!=null)
        {
        txt_ID.setText ( usuario.getDni() );
        txt_Nombre.setText ( usuario.getNombres() );
        txt_Apellidos.setText ( usuario.getApellidos() );
        txt_Telefono.setText ( usuario.getTelefono() );
        txt_Correo.setText ( usuario.getCorreo() );
        txt_Usuario.setText ( usuario.getUsuario() );
        txt_passwd.setText ( usuario.getClave() );
        
        jl_foto.setIcon (convertirImagen( usuario , jl_foto ) );
        btn_guardar.setEnabled ( false );
        btn_eliminar.setEnabled ( false );
        txt_ID.requestFocus();
        EsUsuarioNuevo=true;  
        }
        else
            m_Limpiar();
    }
    
     /**Guarda los datos del usuario */
    private void guardarDatos()
    {  
        if( usuario==null)
            usuario=new Persona();
        
        usuario.setId_usuario ( id);
        usuario.setDni(txt_ID.getText().trim());
        usuario.setNombres ( txt_Nombre.getText().trim() );
        usuario.setApellidos ( txt_Apellidos.getText().trim() );
        usuario.setCorreo( txt_Correo.getText().trim() );
        usuario.setTelefono( txt_Telefono.getText().trim() );
        usuario.setUsuario(  txt_Usuario.getText().trim() );
        usuario.setClave( txt_passwd.getText() );
        usuario.setFecha( jc_Fecha.getDate() );
        usuario.setLongitudBytes(longitudBytes);
        usuario.setFis(fotoUsuario); 
        
        try {
            manager.getUsuarioDao().insertar(usuario);
        } catch (DAOException ex) {
            Logger.getLogger(UI_GestionUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //************************************************************************************
    
    public ImageIcon convertirImagen( Persona p_user, JLabel jlfoto)
    {
                ImageIcon foto=null;
                ImageIcon newIcon=null;
                BufferedImage bi;
                Image nueva;
                
                try
                {
                   foto=new ImageIcon( manager.getUsuarioDao().getfoto(p_user.getId_usuario()));
                    nueva=foto.getImage().getScaledInstance(jlfoto.getWidth(), jlfoto.getHeight(), Image.SCALE_SMOOTH);
                    newIcon=new ImageIcon(nueva);   
                }
                catch(IllegalArgumentException  |IOException|NullPointerException err)
                {
                    System.out.println("EL USUARIO  NO TIENE FOTO");
                    newIcon=null;
                }
              return  newIcon;
    } // Fin del metodo 
    
    private Persona getUsuarioSeleccionado() throws DAOException
    {
        String id=( String )tbListaUsuarios.getValueAt( tbListaUsuarios.getSelectedRow() , 0 );
        return manager.getUsuarioDao().obtener(id); 
    }
    
    public void updateModel()
    {
        try {
                model.UpdateModel();
            } catch (DAOException ex) { System.out.println("ERROR: " +ex); }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Opciones = new javax.swing.JPopupMenu();
        GeneraPdf = new javax.swing.JMenuItem();
        JTPdivisorPrincipal = new javax.swing.JTabbedPane();
        PanelRegistro = new javax.swing.JPanel();
        jl_foto = new javax.swing.JLabel();
        jl_id = new javax.swing.JLabel();
        txt_ID = new javax.swing.JTextField();
        jl_nombre = new javax.swing.JLabel();
        txt_Nombre = new javax.swing.JTextField();
        jl_apellidos = new javax.swing.JLabel();
        txt_Apellidos = new javax.swing.JTextField();
        jl_correo = new javax.swing.JLabel();
        txt_Correo = new javax.swing.JTextField();
        jl_telefono = new javax.swing.JLabel();
        txt_Telefono = new javax.swing.JTextField();
        jl_telefono1 = new javax.swing.JLabel();
        txt_Usuario = new javax.swing.JTextField();
        jl_passwd = new javax.swing.JLabel();
        txt_passwd = new javax.swing.JPasswordField();
        btn_subirImagen = new javax.swing.JButton();
        jl_FechaNac = new javax.swing.JLabel();
        btn_nuevo = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        jc_Fecha = new com.toedter.calendar.JDateChooser();
        panelBusqueda = new javax.swing.JPanel();
        txtBuscaUsuario = new javax.swing.JTextField();
        jl_buscar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbListaUsuarios = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        GeneraPdf.setText("GENERAR REPORTE  INDIVIDUAL");
        GeneraPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GeneraPdfActionPerformed(evt);
            }
        });
        Opciones.add(GeneraPdf);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("APLICACION BASICA");

        jl_foto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));

        jl_id.setText("ID");

        txt_ID.setEditable(false);
        txt_ID.setToolTipText("INGRESA UN ID SOLO N");
        txt_ID.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_IDCaretUpdate(evt);
            }
        });

        jl_nombre.setText("NOMBRE");

        txt_Nombre.setEditable(false);
        txt_Nombre.setToolTipText("INGRESA TU NOMBRE");
        txt_Nombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_NombreCaretUpdate(evt);
            }
        });

        jl_apellidos.setText("APELLIDOS");

        txt_Apellidos.setEditable(false);
        txt_Apellidos.setToolTipText("INGRESA TU APELLIDO");
        txt_Apellidos.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_ApellidosCaretUpdate(evt);
            }
        });

        jl_correo.setText("CORREO");

        txt_Correo.setEditable(false);
        txt_Correo.setToolTipText("INGRESA UN CORREO");
        txt_Correo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_CorreoCaretUpdate(evt);
            }
        });

        jl_telefono.setText("TELEFONO");

        txt_Telefono.setEditable(false);
        txt_Telefono.setToolTipText("INGRESA UN TELEFONO");
        txt_Telefono.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_TelefonoCaretUpdate(evt);
            }
        });

        jl_telefono1.setText("USUARIO");

        txt_Usuario.setEditable(false);
        txt_Usuario.setToolTipText("INGRESA UN USUARIO");
        txt_Usuario.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_UsuarioCaretUpdate(evt);
            }
        });

        jl_passwd.setText("CONTRASEÑA");

        txt_passwd.setEditable(false);
        txt_passwd.setToolTipText("INGRESA UNA CONTRASEÑA");

        btn_subirImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Iconos/imageUser.png"))); // NOI18N
        btn_subirImagen.setText("SUBIR IMAGEN");
        btn_subirImagen.setEnabled(false);
        btn_subirImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_subirImagenActionPerformed(evt);
            }
        });

        jl_FechaNac.setText("FECHA DE NACIMIENTO");

        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Iconos/addUser.png"))); // NOI18N
        btn_nuevo.setText("NUEVO");
        btn_nuevo.setToolTipText("INGRESA UN NUEVO USUARIO");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Iconos/deleteUser.png"))); // NOI18N
        btn_eliminar.setText("ELIMINAR");
        btn_eliminar.setToolTipText("ELIMINAR UN USUARIO");
        btn_eliminar.setEnabled(false);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Iconos/saveUser.png"))); // NOI18N
        btn_guardar.setText("GUARDAR");
        btn_guardar.setToolTipText("GUARDAR EL USUARIO");
        btn_guardar.setEnabled(false);
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        jc_Fecha.setToolTipText("SELECCIONA UNA FECHA");
        jc_Fecha.setEnabled(false);

        javax.swing.GroupLayout PanelRegistroLayout = new javax.swing.GroupLayout(PanelRegistro);
        PanelRegistro.setLayout(PanelRegistroLayout);
        PanelRegistroLayout.setHorizontalGroup(
            PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRegistroLayout.createSequentialGroup()
                .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelRegistroLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_subirImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(jl_foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addComponent(jl_FechaNac)
                                .addGap(18, 18, 18)
                                .addComponent(jc_Fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addComponent(jl_id, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addComponent(jl_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addComponent(jl_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txt_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addComponent(jl_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txt_Correo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addComponent(jl_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addComponent(jl_telefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txt_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addComponent(jl_passwd)
                                .addGap(8, 8, 8)
                                .addComponent(txt_passwd, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelRegistroLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btn_guardar)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        PanelRegistroLayout.setVerticalGroup(
            PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelRegistroLayout.createSequentialGroup()
                        .addComponent(jl_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_subirImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelRegistroLayout.createSequentialGroup()
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelRegistroLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jl_id, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_telefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_passwd, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_passwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jc_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_FechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30)
                .addGroup(PanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        JTPdivisorPrincipal.addTab("USUARIOS", PanelRegistro);

        panelBusqueda.setFocusable(false);

        txtBuscaUsuario.setToolTipText("INGRESA EL NOMBRE DE UN USUARIO");
        txtBuscaUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBuscaUsuario.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBuscaUsuarioCaretUpdate(evt);
            }
        });

        jl_buscar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_buscar.setText("BUSCAR");

        tbListaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbListaUsuarios.setToolTipText("MUESTRA LOS USUARIOS DISPONIBLES");
        tbListaUsuarios.setCellSelectionEnabled(true);
        tbListaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbListaUsuarios);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Iconos/PDF.png"))); // NOI18N
        jButton1.setText("GENERAR REPORTE PDF");
        jButton1.setToolTipText("GENERA UN REPORTE DE TODOS LOS USUARIOS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBusquedaLayout = new javax.swing.GroupLayout(panelBusqueda);
        panelBusqueda.setLayout(panelBusquedaLayout);
        panelBusquedaLayout.setHorizontalGroup(
            panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBusquedaLayout.createSequentialGroup()
                        .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelBusquedaLayout.createSequentialGroup()
                                .addComponent(jl_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBusquedaLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(152, 152, 152))))
        );
        panelBusquedaLayout.setVerticalGroup(
            panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_buscar))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        JTPdivisorPrincipal.addTab("LISTA DE USUARIOS", panelBusqueda);

        getContentPane().add(JTPdivisorPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GeneraPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GeneraPdfActionPerformed
                
        int Fila=tbListaUsuarios.getSelectedRow();
        String ID_USUARI0=tbListaUsuarios.getValueAt(Fila, 0).toString();
        
        if(Fila>=0)
            new GenerarReportes().ReporteIndividual(ID_USUARI0);
 
    }//GEN-LAST:event_GeneraPdfActionPerformed

    private void txtBuscaUsuarioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBuscaUsuarioCaretUpdate
        // Realiza la busqueda

        C_Validar.m_ValidarSoloLetras(txtBuscaUsuario);
        String dato=txtBuscaUsuario.getText().trim();

        if(dato.isEmpty())
            updateModel();
        else
        if(!dato.isEmpty())
            m_BuscarUsuario(dato);
    }//GEN-LAST:event_txtBuscaUsuarioCaretUpdate

    private void tbListaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListaUsuariosMouseClicked

        if(evt.getClickCount()==2)
        {
            try 
            {
                boolean seleccionValida=tbListaUsuarios.getSelectedRow()!=2;
                btn_guardar.setEnabled( seleccionValida );
                btn_eliminar.setEnabled( seleccionValida );
                JTPdivisorPrincipal.setSelectedIndex( 0 );
                usuario=getUsuarioSeleccionado();
                cargarDatos();
            } catch (DAOException ex) {
                System.out.println("Error al cargar los usuarios");
            }

        }
    }//GEN-LAST:event_tbListaUsuariosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new GenerarReportes().ReporteUsuario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_IDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_IDCaretUpdate
        // Evento que actualiza el campo de texto
        m_validarIngreso();
    }//GEN-LAST:event_txt_IDCaretUpdate

    private void txt_NombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_NombreCaretUpdate
        // Evento que actualiza el campo de texto
        m_validarIngreso();
    }//GEN-LAST:event_txt_NombreCaretUpdate

    private void txt_ApellidosCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_ApellidosCaretUpdate
        // Evento que actualiza el campo de texto
        m_validarIngreso();
    }//GEN-LAST:event_txt_ApellidosCaretUpdate

    private void txt_CorreoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_CorreoCaretUpdate
        // Evento que actualiza el campo de texto
        m_validarIngreso();
    }//GEN-LAST:event_txt_CorreoCaretUpdate

    private void txt_TelefonoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_TelefonoCaretUpdate
        // Evento que actualiza el campo de texto
        m_validarIngreso();
    }//GEN-LAST:event_txt_TelefonoCaretUpdate

    private void txt_UsuarioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_UsuarioCaretUpdate
        // Evento que actualiza el campo de texto
        m_validarIngreso();
    }//GEN-LAST:event_txt_UsuarioCaretUpdate

    private void btn_subirImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_subirImagenActionPerformed
        //EVENTO QUE CARGA LA FOTO
        m_CargaFoto();
    }//GEN-LAST:event_btn_subirImagenActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        // Limpia los campos de la interfaz
        setEditable(true);
        m_Limpiar();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        // falta implementar
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        //Guarda los datos seleccionados
        guardarDatos();
        m_Limpiar();
        setEditable(false);
        btn_guardar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_nuevo.setEnabled(false);
    }//GEN-LAST:event_btn_guardarActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem GeneraPdf;
    private javax.swing.JTabbedPane JTPdivisorPrincipal;
    private javax.swing.JPopupMenu Opciones;
    private javax.swing.JPanel PanelRegistro;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_subirImagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jc_Fecha;
    private javax.swing.JLabel jl_FechaNac;
    private javax.swing.JLabel jl_apellidos;
    private javax.swing.JLabel jl_buscar;
    private javax.swing.JLabel jl_correo;
    private javax.swing.JLabel jl_foto;
    private javax.swing.JLabel jl_id;
    private javax.swing.JLabel jl_nombre;
    private javax.swing.JLabel jl_passwd;
    private javax.swing.JLabel jl_telefono;
    private javax.swing.JLabel jl_telefono1;
    private javax.swing.JPanel panelBusqueda;
    private javax.swing.JTable tbListaUsuarios;
    private javax.swing.JTextField txtBuscaUsuario;
    private javax.swing.JTextField txt_Apellidos;
    private javax.swing.JTextField txt_Correo;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_Nombre;
    private javax.swing.JTextField txt_Telefono;
    private javax.swing.JTextField txt_Usuario;
    private javax.swing.JPasswordField txt_passwd;
    // End of variables declaration//GEN-END:variables
}

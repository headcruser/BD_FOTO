
package GUI.Control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

/**
 *Clase que maneja las validaciones de los campos de texto
 * @author Martinez Sierra Daniel
 */
public class C_Validar 
{
  
//****************************************************************************************  
    /**Metodo que valida solo letras 
     * @param p_campo Valida el campo de texto seleccionado */
    public static void m_ValidarSoloLetras(JTextField p_campo)
    {
        //Agrega un evento 
        p_campo.addKeyListener(new KeyAdapter() 
        {
            //Evento que detecta cuando se escribe en una caja de texto
            @Override
            public void keyTyped(KeyEvent p_evento)
            {
                //Convierte en un caracter
                char c=p_evento.getKeyChar();
                
               //Obtiene su equivalente en condigo ASCII
                int k=(int)p_evento.getKeyChar();
                
                //Si Corresponde el equivalente con un numero, lo consume y no lo toma en cuenta
                if(Character.isDigit(c)||k==64)
                    p_evento.consume();
            }
        });
    } 
//****************************************************************************************
    
//****************************************************************************************
     /**Metodo que valida solo numeros
     * @param p_campo Valida el campo de texto seleccionado */
    
    public static void m_ValidarSoloNumeros(JTextField p_campo)
    {
         //Agrega un evento 
        p_campo.addKeyListener(new KeyAdapter() 
        {
            //Evento que detecta cuando se escribe en una caja de texto
            @Override
            public void keyTyped(KeyEvent p_evento)
            {
                  //Convierte en un caracter
                char c=p_evento.getKeyChar();
                
                //Si es diferente de algun numero, lo descarta 
                if(!Character.isDigit(c))
                    p_evento.consume();
            }
        });
    }
//****************************************************************************************
    
 //****************************************************************************************
  /**Metodo que limita el numero de caracteres en la caja de texto 
   * @param p_campo Recibe el campo de texto a validar
   * @param p_cantidad Numero de caracteres a eliminar
     */
    public static void m_LimitarCaracteres(JTextField p_campo,int p_cantidad)
    {
        
        p_campo.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyTyped(KeyEvent p_evento)
            {
                char c=p_evento.getKeyChar();
                int tam=p_campo.getText().length();
                if(tam>=p_cantidad)
                    p_evento.consume();
               
            }
        });
    }//****************************************************************************************
    
    
    
    
     private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
 
    public static boolean m_validarEmail(String email) 
    {
 
        // Recibe la expresion regular a analizar
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        //Revisa si la expresion 
        
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }
    
    
    
     public static void m_ConnvertirMayuscula(JTextField p_texto)
    {
       p_texto.addKeyListener(new java.awt.event.KeyAdapter() 
       {
        
        @Override
        public void keyTyped(java.awt.event.KeyEvent evt) 
        {
                Character c = evt.getKeyChar();
                if(Character.isLetter(c)) 
                    evt.setKeyChar(Character.toUpperCase(c));
                
        }
       } );
    }
    
    
}

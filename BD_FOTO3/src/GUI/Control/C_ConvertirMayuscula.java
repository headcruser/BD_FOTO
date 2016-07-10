
package GUI.Control;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *Clase que Convierte en mayuculas las letras
 * @see PlainDocument
 * @author Martinez Sierra Daniel*/
public class C_ConvertirMayuscula extends PlainDocument
{
    
    //SOBRE ESCRIBE EL METODO INSERTAR STRING
     @Override
     public void insertString(int offs, String str, AttributeSet a) throws BadLocationException 
     {   super.insertString(offs, str.toUpperCase(), a);  }
}

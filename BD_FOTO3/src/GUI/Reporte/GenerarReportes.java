package GUI.Reporte;



import DAO.SQL.Sqlconexion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;




/***Clase que genera los reportes pdf
 * @author Martinez Sierra Daniel 
 * @version 1.0
*/
public class GenerarReportes extends Sqlconexion
{
    private Connection con;
    public void ReporteUsuario() throws Exception
    { 
        JasperReport jasperReport;
        JasperPrint print;
        JasperViewer view;
        
        conexion();
      
            try ( InputStream s=new FileInputStream( "src\\GUI\\Reporte\\ReporteUsuario.jasper"))
            {
               try 
                   {
                       jasperReport=(JasperReport)JRLoader.loadObject(s);
                       print = JasperFillManager.fillReport(jasperReport, null, con);
                       view =new JasperViewer(print,false);
                       view.setVisible(true);
                       JasperExportManager.exportReportToPdfFile( print,"src\\Reporte\\reporte.pdf");

                   } catch (JRException e) 
                   {
                       System.out.println("No se guardo el reporte");
                   }
               
              s.close();
            } catch (IOException er) 
            {
                System.out.println("NO SE ENCONTRO EL ARCHIVO");
            }
    } 
    
    
    public void ReporteIndividual(String ID_USUARIO)
    {      
        
      String ubucacion="C:\\Users\\Daniel\\OneDrive\\ARCHIVOS ITC\\SEMESTRE 7\\TALLER DE BASE DE DATOS\\COMPETENCIA 1\\TBD_7 PROYECTO FINAL\\DOCUMENTACION\\BD_ConFoto\\src\\Reporte\\ReporteIndividual.jasper";
      Map parametro=new HashMap();
      parametro.put("ID_USUARIO",Integer.parseInt(ID_USUARIO));
      
      InputStream s=null;
        
            try 
            {
                s=new FileInputStream(ubucacion);
                
            } catch (FileNotFoundException er) 
            {
                System.out.println("NO SE ENCONTRO EL ARCHIVO");
            }
        try 
        {
            JasperReport jasperReport=(JasperReport)JRLoader.loadObject(s);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parametro, con);
            JasperViewer view =new JasperViewer(print,false);
            view.setVisible(true);
            JasperExportManager.exportReportToPdfFile( print,"src\\Reporte\\reporteIndividual.pdf");
            
        } catch (JRException ex) 
        {
            System.out.println("Error al cargar el reporte");
        }
    }
    
    
   private void conexion() throws Exception
   {
            con=getConection();   
   }
  
}

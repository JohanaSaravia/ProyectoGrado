/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kde_salud;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Johana
 */
public class datosFiltrados extends javax.swing.JFrame {

    private ArrayList<Punto> listaPunto;
    private ArrayList<String> listaCampos ;
   // JPanel jpanel = (JPanel) this.getContentPane();          

    //JLabel[] label = new JLabel[listaCampos.size()];    //Declaración del array de etiquetas

   // JTextField[] text = new JTextField[listaCampos.size()];    //Declaración del array de cajas de texto

    //Border border = BorderFactory.createLineBorder(Color.black, 1);

   
    
    public datosFiltrados(ArrayList<Punto> listaPunto, ArrayList<String> listaCampos) {
        this.listaPunto = listaPunto;
        this.listaCampos = listaCampos;
    }
     
                                        
    public void Graficos(){
        for (int i = 0; i < listaPunto.size(); i++) {
            System.out.println(listaPunto.get(i).getLatitud());
        }
    }
            
            
    
   // private void botonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {                                             
      //  for (int i = 0; i < listaPunto.size(); i++) {
        //    System.out.println(listaPunto.get(i).getLatitud());
        //}
        
    //}
}
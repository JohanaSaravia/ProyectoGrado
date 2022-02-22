/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kde_salud;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import metodokde.Capa;
import metodokde.CapaPoligono;
import metodokde.CoordenadaUTM;
import metodokde.PartePoligono;
import metodokde.Poligono;
import metodokde.Utilidades;

/**
 *
 * @author Johana
 */
public class CargarPoligono extends javax.swing.JFrame {

    /**
     * Creates new form CargarPoligono
     */
    private  LinkedHashMap<String, Capa> mapaCapasKML = new LinkedHashMap<>();
    
    public CargarPoligono() {
        initComponents();
        
    }

  


                  
    private void initComponents() {
        this.setResizable(false);
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBuscarArchivo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio");
        setBackground(new java.awt.Color(153, 255, 153));
        setMaximumSize(new java.awt.Dimension(400, 400));
        setMinimumSize(new java.awt.Dimension(400, 400));
        setPreferredSize(new java.awt.Dimension(400, 400));

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 400));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kde_salud/mapa.png"))); // NOI18N

        btnBuscarArchivo.setBackground(new java.awt.Color(255, 51, 51));
        btnBuscarArchivo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnBuscarArchivo.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarArchivo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscarArchivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscarArchivo.setText("Agregar archivo");
        btnBuscarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarArchivoActionPerformed(evt);
            }
        });
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Bienvenido");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Suba la cartografía correspondiente al municipio");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(btnBuscarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(57, 57, 57)
                .addComponent(btnBuscarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                      

        FileReader fr = null;
        BufferedReader br = null;
        
        public void BuscarArchivo(){
        ArrayList<String> listaCampos = new ArrayList<>(); // se almacenaran los campos que contiene cada punto
        ArrayList<Punto> listaPuntos = new ArrayList<>(); // se almacenaran todos los puntos que contiene el archivo kml   
         Scanner entrada = null;
        //Se crea el JFileChooser. Se le indica que la ventana se abra en el directorio actual                    
        JFileChooser fileChooser = new JFileChooser(".");      
        //Se crea el filtro. El primer parámetro es el mensaje que se muestra,
        //el segundo es la extensión de los ficheros que se van a mostrar      
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos java (.kml)", "kml"); 
        //Se le asigna al JFileChooser el filtro
        fileChooser.setFileFilter(filtro);
        //se muestra la ventana
        int valor = fileChooser.showOpenDialog(fileChooser);
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            int posSeparadorAux = ruta.lastIndexOf(File.separator);
            int posPuntoAux = ruta.lastIndexOf(".");

            String rutaBase;
            if (posSeparadorAux >= 0) {
                rutaBase = ruta.substring(0, posSeparadorAux + 1);
            } else {
                rutaBase = File.separator;
            }

            String descCapa;
            if (posPuntoAux > posSeparadorAux) {
                descCapa = ruta.substring(posSeparadorAux + 1, posPuntoAux);
            } else {
                descCapa = ruta.substring(posSeparadorAux + 1);
            }
            Capa capa= null;
            char tipoCapa = 'a';
            try (FileReader fr = new FileReader(ruta);
                BufferedReader br = new BufferedReader(fr)) {
                String lineaCoordenadas = "";
                boolean indEnCoordenadas = false;
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.toLowerCase().contains("<coordinates>")) {
                        lineaCoordenadas = "";
                        indEnCoordenadas = true;
                    }

                    if (indEnCoordenadas) {
                        lineaCoordenadas += linea;
                    }

                    if (linea.toLowerCase().contains("</coordinates>")) {
                        indEnCoordenadas = false;
                    }

                    if (tipoCapa != ' ' && !lineaCoordenadas.equals("") && !indEnCoordenadas) {
                        //Se almacenan los datos en memoria
                        
                        capa = cargarObjetoCapa(capa, tipoCapa, lineaCoordenadas, ruta, descCapa);
                        lineaCoordenadas = "";
                        this.mapaCapasKML.put(ruta, capa);
                    }
                }
            } catch (IOException ex) {
            }finally {
                    CargarReferencia cargarArchivo2 = new CargarReferencia(this.mapaCapasKML);
                    cargarArchivo2.setVisible(true); 
                    this.dispose();

            }      
        }
        } 
        
        
        
    public void habilitarComponentes(boolean habilitar) {
        this.btnBuscarArchivo.setEnabled(true);
        if (!habilitar) {
            this.btnBuscarArchivo.setEnabled(false);
        }
    }
    private void btnBuscarArchivoActionPerformed(java.awt.event.ActionEvent evt) {                                                 
          // TODO add your handling code here:
       CargarPoligono cg = new CargarPoligono();
       cg.BuscarArchivo();
       this.dispose();
    }   

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CargarPoligono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CargarPoligono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CargarPoligono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CargarPoligono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CargarPoligono().setVisible(true);
            }
        });
    }

    private Capa cargarObjetoCapa(Capa capa, char tipoCapa, String texto, String nombreCapa, String descCapa) {
        texto = texto.toLowerCase();
        if (capa == null) {
            switch (tipoCapa) {
                case 'a': //Polígonos
                    capa = new CapaPoligono(nombreCapa, descCapa);
                    break;
            }
        }
        if (capa != null) {
            int posIni;
            int posFin;
            switch (tipoCapa) {
                case 'a': //Polígonos
                    CapaPoligono capaPoligonos = (CapaPoligono) capa;
                    Poligono poligono = new Poligono(capaPoligonos, capaPoligonos.getCantPoligonos());
                    capaPoligonos.addPoligono(poligono);
                    do {
                        posIni = texto.indexOf("<coordinates>");
                        posFin = texto.indexOf("</coordinates>");
                        int posExterno = texto.indexOf("<outerboundaryis>");
                        int posInterno = texto.indexOf("<innerboundaryis>");
                        char tipoParte = ' ';
                        if (posExterno >= 0 && posExterno < posIni) {
                            tipoParte = 'e';
                        } else if (posInterno >= 0 && posInterno < posIni) {
                            tipoParte = 'i';
                        }
                        if (posIni >= 0 && posIni < posFin && tipoParte != ' ') {
                            String[] arrCoordenadas = texto.substring(posIni + 13, posFin).split("\\s");
                            if (arrCoordenadas.length > 0) {
                                PartePoligono partePoligono = new PartePoligono(capaPoligonos, poligono.getCantPartes(), tipoParte);
                                poligono.addParte(partePoligono);
                                for (String valAux : arrCoordenadas) {
                                    String[] arrAux = valAux.split(",");
                                    double latitud;
                                    double longitud;
                                    try {
                                        latitud = Double.parseDouble(arrAux[1]);
                                        longitud = Double.parseDouble(arrAux[0]);
                                    } catch (NumberFormatException ex) {
                                        latitud = Double.NaN;
                                        longitud = Double.NaN;
                                    }

                                    if (!Double.isNaN(latitud) && !Double.isNaN(longitud)) {
                                        Punto punto = new Punto(poligono.getIdCapa(), partePoligono.getCantPuntos(), latitud, longitud);
                                        partePoligono.addPunto(punto);
                                    }
                                }
                            }

                            texto = texto.substring(posFin + 14);
                        } else {
                            break;
                        }
                    } while (posIni >= 0);
                    break;
            }
        }

        return capa;
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBuscarArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration                   
                      
}

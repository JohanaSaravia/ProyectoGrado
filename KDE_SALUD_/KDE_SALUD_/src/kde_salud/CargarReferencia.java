/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kde_salud;

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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import metodokde.Capa;
import metodokde.CapaPuntos;
import metodokde.CoordenadaUTM;
import metodokde.Utilidades;

/**
 *
 * @author Johana
 */
public class CargarReferencia extends javax.swing.JFrame {
    
        private  LinkedHashMap<String, Capa> mapaCapasKML = new LinkedHashMap<>();
       // private ArrayList<Punto> listaLugares = new ArrayList<>();
    
    public CargarReferencia(LinkedHashMap<String, Capa> mapaCapasKML ) {
        this.mapaCapasKML = mapaCapasKML;
        //this.listaLugares = listaLugares;
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
        setTitle("Cargar Puntos de referencia de agua");
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
       
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Suba la cartografía de puntos referencia");

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
            cargarCapa(ruta);
            try {
                File f = new File(ruta);
                entrada = new Scanner(f);
                 
                 int id=0; // el id con el cual se identificara cada punto este será único
                 double latitud=0.0, longitud=0.0; // las coordenadas geograficas correspona<dientes al punto
                 LinkedHashMap<String,String> mapavalores=new LinkedHashMap<>(); // se crea el mapa valores que sera el encargado de guardar los campos y sus valores
                  HashMap<String,String> valoresordenado=new HashMap<>();;
                while (entrada.hasNext()) {
                    //System.out.println(entrada.nextLine());
                    String linea=entrada.nextLine();
                   if(linea.toLowerCase().contains("simplefield")){
                       int posIni = linea.toLowerCase().indexOf("name=");//el tolower convierte en minuscula
                       int posFin = linea.indexOf("\"",(posIni+6));
                       String nombreCampo = linea.substring((posIni+6), posFin);
                       listaCampos.add(nombreCampo);//se agrega el nombre de cada campo
                   
                   }else if(linea.toLowerCase().contains("<placemark>")){
                       id++;
                       latitud=0;// se inicializa la latitud otra vez 
                       longitud=0;// se inicializa la longitud  otra vez 
                       mapavalores=new LinkedHashMap<>();// se inicializa el mapa valores otra vez 
                   }else if(linea.toLowerCase().contains("simpledata")){
                       int posInicial= linea.toLowerCase().indexOf("name=");//el tolower convierte en minuscula
                       
                       int posFinal = linea.toLowerCase().indexOf(">");//la posicion en donde comienzan los valores
                       String llave=linea.substring((posInicial+6), posFinal-1);// el valor guardado
                       int posFinal1= linea.indexOf("</");// la posicion en donde terminan los valores
                       String tipo= linea.substring(posFinal+1, posFinal1);// el valor del contenido 
                       mapavalores.put(llave, tipo);// se agregan ambos valores en el mapa de valores 
                       //System.out.println(mapavalores);
                   }else if(linea.toLowerCase().contains("point")){
                       int iniLongitud = linea.toLowerCase().indexOf("coordinates>");
                       int finLongitud = linea.toLowerCase().indexOf(",");
                       String Longitud = linea.substring(iniLongitud+12, finLongitud);// 
                       longitud= Double.parseDouble(Longitud);
                       int iniLatitud = linea.toLowerCase().indexOf(",");
                       int finLatitud = linea.toLowerCase().indexOf("</");
                       String Latitud = linea.substring(iniLatitud+1, finLatitud);// 
                       latitud= Double.parseDouble(Latitud);
                   }else if(linea.toLowerCase().contains("</placemark>")){
                       //valoresordenado =Mapa(mapavalores);   
                       CoordenadaUTM coordenadaUTM = Utilidades.convertirGeograficasAUTM(latitud, longitud);
                       
                       Punto p =new Punto(mapavalores, latitud, longitud, id, coordenadaUTM);// se crea el nuevo punto que contiene su mapa de valores
                       listaPuntos.add(p);// se agrega el nuevo punto a una lista de puntos
                       
                   }
                   
                }
            } catch (IOException ex) {
               
            } finally {
                    CargarPuntos cargarArchivo2 = new CargarPuntos(this.mapaCapasKML,listaPuntos);
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
       CargarReferencia cg = new CargarReferencia(this.mapaCapasKML);
       cg.BuscarArchivo();
       this.dispose();
    }  

    
    private Capa cargarObjetoCapa(Capa capa, char tipoCapa, String texto, String nombreCapa, String descCapa) {
        texto = texto.toLowerCase();
        if (capa == null) {
            switch (tipoCapa) {
                case 'p': //Puntos
                    capa = new CapaPuntos(nombreCapa, descCapa);
                    break;   
            }
        }
        if (capa != null) {
            int posIni;
            int posFin;
            switch (tipoCapa) {
                
                    case 'p': //Puntos
                    CapaPuntos capaPuntos = (CapaPuntos) capa;
                    posIni = texto.indexOf("<coordinates>");
                    posFin = texto.indexOf("</coordinates>");
                    if (posIni >= 0 && posIni < posFin) {
                        String[] arrAux = texto.substring(posIni + 13, posFin).split(",");
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
                            Punto punto = new Punto(capaPuntos.getIdCapa(), capaPuntos.getCantPunto(), latitud, longitud);
                            capaPuntos.agregarPunto(punto);
                        }
                    }
                    break;                     
            }
        }
        return capa;
    }

    private void cargarCapa(String ruta){
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
            Capa capa = null;
            char tipoCapa = 'p';
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
            }  
        
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBuscarArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration  
}
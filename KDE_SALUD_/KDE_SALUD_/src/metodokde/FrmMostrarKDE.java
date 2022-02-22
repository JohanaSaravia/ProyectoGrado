package metodokde;

import metodokde.FrmPrincipal;
import metodokde.Capa;
import metodokde.CapaPuntos;
import metodokde.CoordenadaUTM;
import metodokde.EntradaGenerica;
import metodokde.FuncionNucleo;
import metodokde.Pixel;
import kde_salud.Punto;
import metodokde.PrCalculoKDE;
import metodokde.Utilidades;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import kde_salud.Punto;

/**
 * Formulario para mostrar resultados de KDE
 *
 * @author Feisar Moreno
 * @date 10/08/2020
 */
public class FrmMostrarKDE extends JInternalFrame {

    private final FrmPrincipal frmPrincipal;
    private final FrmMostrarKDE frmMostrarKDE;
    private final ArrayList<Punto> listaPuntos;
    private LinkedHashMap<String, Capa> mapaCapasKML = new LinkedHashMap<>();
    private double anchoBanda;
    private double resolucion;
    private FuncionNucleo funcionNucleo;
    private Pixel[][] arrPixels;
    private VisorMapa visorMapa;
    private final int cantDivisiones;
    private ArrayList<Punto> listaLugares;
    
    private class AvanceCalculo implements Runnable {

        private boolean controlCorrer = true;
        private int cantPuntosAvance = 0;

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            while (this.controlCorrer) {
                try {
                    Thread.sleep(500);
                    this.mostrarAvance();
                } catch (InterruptedException e) {
                }
            }
        }

        public void setControlCorrer(boolean controlCorrer) {
            this.controlCorrer = controlCorrer;
        }

        private void mostrarAvance() {
            String mensajeAvance;

            this.cantPuntosAvance++;
            this.cantPuntosAvance = this.cantPuntosAvance % 3;
            mensajeAvance = "Processing" + "....".substring(0, this.cantPuntosAvance + 1);
            lblAvance.setText(mensajeAvance);
        }
    }
    
    /**
     * Constructor para el formulario FrmMostrarKDE
     *
     * @param frmPrincipal Formulario principal
     */
    public FrmMostrarKDE(FrmPrincipal frmPrincipal, ArrayList<Punto> listaPuntos,LinkedHashMap<String, Capa> mapaCapasKML,ArrayList<Punto> listaLugares) {
        initComponents();
        
        this.frmPrincipal = frmPrincipal;
        this.frmMostrarKDE = this;
        this.listaPuntos = listaPuntos;
        this.mapaCapasKML = mapaCapasKML;
        this.cantDivisiones = 10;
        this.listaLugares = listaLugares;
        //Se limpia el texto de avance de carga
        this.lblAvance.setVisible(false);
        this.lblAvance.setText(" ");
        mostrarResultados();
        //Se carga el combo de funciones de núcleo
        ArrayList<FuncionNucleo> listaFuncionesNucleo = new ArrayList<>();
        listaFuncionesNucleo.add(new FuncionNucleo(1, "Función Gaussiana"));
        listaFuncionesNucleo.add(new FuncionNucleo(2, "Función de Epanechnikov"));
        listaFuncionesNucleo.add(new FuncionNucleo(3, "Función de Varianza Mínima"));
        listaFuncionesNucleo.add(new FuncionNucleo(4, "Función Uniforme"));
        listaFuncionesNucleo.add(new FuncionNucleo(5, "Función Triangular"));

        this.cmbFuncionNucleo.removeAllItems();
        for (FuncionNucleo funcionNucleoAux : listaFuncionesNucleo) {
            this.cmbFuncionNucleo.addItem(funcionNucleoAux);
        }
        this.cmbFuncionNucleo.setSelectedIndex(-1);
    }
    
    private char obtenerTipoCapa(String linea) {
        linea = linea.toLowerCase();
        if (linea.contains("<point>")) {
            return 'p';
        } else{
        return ' ';
        }
    }
  
    /**
     * Método que muestra los resultados del método KDE.
     */
    public void mostrarResultados() {
        this.addInternalFrameListener(new FrmMostrarKDEListener());

        //Se agrega el lienzo al panel
        this.panContenedor.setLayout(new java.awt.GridLayout(1, 1));
        this.panContenedor.removeAll();
        this.visorMapa = new VisorMapa(this.anchoBanda, this.resolucion, this.mapaCapasKML, this.arrPixels, this.cantDivisiones,this.listaPuntos, this.listaLugares);
        this.panResultados.removeAll();
        this.panResultados.add(visorMapa);
        this.panContenedor.add(this.panResultados);

        this.panResultados.setSize(this.panContenedor.getSize());
        this.visorMapa.setSize(this.panResultados.getSize());
        CapaPoligono capaMapa = null;
         ArrayList<Punto> listaPoligono = hallarPoligono(capaMapa);
    }

    private File crearImagenPNGResultado(String nombreArchivoPNG) throws IOException {
        //Se asignan las dimensiones de la imagen, de acuerdo con el número de pixels del resultado
        int ancho = this.arrPixels[0].length;
        int alto = this.arrPixels.length;

        BufferedImage bufferedImage = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        //La imagen se llena por defecto con una transparencia
        g2d.setColor(new Color(255, 255, 255, 0));
        g2d.fillRect(0, 0, ancho, alto);

        //Se hallan los colores de los pixels y las divisiones
        Color[] arrColores = this.visorMapa.getArrColores();
        double[] arrDivisiones = this.visorMapa.getArrDivisiones();

        //Se pinta cada color del resultado
        for (int i = 0; i < this.arrPixels.length; i++) {
            for (int j = 0; j < this.arrPixels[i].length; j++) {
                Pixel pixel = this.arrPixels[i][j];
                if (pixel.getDensidad() > 0) {
                    //Se determina el valor del pixel de acuerdo a los deciles
                    int decilAux = this.cantDivisiones - 1;
                    for (int k = 0; k < this.cantDivisiones; k++) {
                        if (pixel.getDensidad() <= arrDivisiones[k]) {
                            decilAux = k;
                            break;
                        }
                    }
                    Color colorAux = arrColores[decilAux];
                    g2d.setColor(new Color(colorAux.getRed(), colorAux.getGreen(), colorAux.getBlue(), 192));
                    g2d.fillRect(j, i, 1, 1);
                }
            }
        }

        g2d.dispose();

        //Se crea el archivo PNG
        File archivoPNG = new File(nombreArchivoPNG);
        ImageIO.write(bufferedImage, "png", archivoPNG);

        return archivoPNG;
    }
    
    private File crearArchivoKMLResultado(String nombreArchivoKML, String nombreArchivoPNG) throws IOException {
        //Se hallan las coordenadas de los extremos de la imagen
        double minXAux = this.arrPixels[0][0].getxCentro() - this.resolucion / 2;
        double minYAux = this.arrPixels[this.arrPixels.length - 1][this.arrPixels[0].length - 1].getyCentro() - this.resolucion / 2;
        double maxXAux = this.arrPixels[this.arrPixels.length - 1][this.arrPixels[0].length - 1].getxCentro() + this.resolucion / 2;
        double maxYAux = this.arrPixels[0][0].getyCentro() + this.resolucion / 2;
        
        CapaPuntos capaEventos =  new CapaPuntos(this.listaPuntos);
        CoordenadaUTM utmAux = capaEventos.getListaPuntos().get(0).getCoordenadaUTM();
              
        
        double[] arrAux = Utilidades.convertirUTMAGeograficas(new CoordenadaUTM(minXAux, minYAux, utmAux.getZona(), utmAux.getHemisferio()));
        double latSur = arrAux[0];
        double lonOeste = arrAux[1];
        arrAux = Utilidades.convertirUTMAGeograficas(new CoordenadaUTM(maxXAux, maxYAux, utmAux.getZona(), utmAux.getHemisferio()));
        double latNorte = arrAux[0];
        double lonEste = arrAux[1];
        
        //Se halla el nombre sin ruta del archivo PNG asociado
        int posAux = nombreArchivoPNG.lastIndexOf(File.separator);
        if (posAux >= 0) {
            nombreArchivoPNG = nombreArchivoPNG.substring(posAux + 1);
        }

        File archivoKML = new File(nombreArchivoKML);

        try (PrintWriter pw = new PrintWriter(archivoKML)) {
            //Se encribe el contenido del archivo KML
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">");
            pw.println("    <Document>");
            pw.println("        <name>" + nombreArchivoKML + "</name>");
            pw.println("        <styleUrl>#hideChildrenStyle</styleUrl>");
            pw.println("        <Style id=\"hideChildrenStyle\">");
            pw.println("            <ListStyle id=\"hideChildren\">");
            pw.println("                <listItemType>checkHideChildren</listItemType>");
            pw.println("            </ListStyle>");
            pw.println("        </Style>");
            pw.println("        <Region>");
            pw.println("            <LatLonAltBox>");
            pw.println("                <north>" + latNorte + "</north>");
            pw.println("                <south>" + latSur + "</south>");
            pw.println("                <east>" + lonEste + "</east>");
            pw.println("                <west>" + lonOeste + "</west>");
            pw.println("            </LatLonAltBox>");
            pw.println("            <Lod>");
            pw.println("                <minLodPixels>1</minLodPixels>");
            pw.println("                <maxLodPixels>-1</maxLodPixels>");
            pw.println("            </Lod>");
            pw.println("        </Region>");
            pw.println("        <GroundOverlay>");
            pw.println("            <drawOrder>0</drawOrder>");
            pw.println("            <Icon>");
            pw.println("                <href>" + nombreArchivoPNG + "</href>");
            pw.println("            </Icon>");
            pw.println("            <LatLonBox>");
            pw.println("                <north>" + latNorte + "</north>");
            pw.println("                <south>" + latSur + "</south>");
            pw.println("                <east>" + lonEste + "</east>");
            pw.println("                <west>" + lonOeste + "</west>");
            pw.println("            </LatLonBox>");
            pw.println("        </GroundOverlay>");
            pw.println("    </Document>");
            pw.println("</kml>");
        }

        return archivoKML;
    }

    private boolean validarCamposCalculo() {
 if (this.txtAnchoBanda.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frmPrincipal, "Debe seleccionar un ancho de banda.", "Error", JOptionPane.ERROR_MESSAGE);
            this.txtAnchoBanda.requestFocusInWindow();
            return false;
        } else {
            //Se valida que se haya ingresado un número
            try {
                Double.parseDouble(this.txtAnchoBanda.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this.frmPrincipal, "El ancho de banda debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                this.txtAnchoBanda.requestFocusInWindow();
                return false;
            }
        }

        if (this.cmbFuncionNucleo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this.frmPrincipal, "Debe seleccionar una función de núcleo.", "Error", JOptionPane.ERROR_MESSAGE);
            this.cmbFuncionNucleo.requestFocusInWindow();
            return false;
        }

        if (this.txtResolucion.getText().equals("")) {
            JOptionPane.showMessageDialog(this.frmPrincipal, "Debe seleccionar una resolución.", "Error", JOptionPane.ERROR_MESSAGE);
            this.txtResolucion.requestFocusInWindow();
            return false;
        } else {
            //Se valida que se haya ingresado un número
            try {
                Double.parseDouble(this.txtResolucion.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this.frmPrincipal, "La resolución debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                this.txtResolucion.requestFocusInWindow();
                return false;
            }
        }
        
        return true;
    }
    
    public void habilitarComponentes(boolean habilitar) {
        this.txtAnchoBanda.setEnabled(true);
        this.txtResolucion.setEnabled(true);
        this.cmbFuncionNucleo.setEnabled(true);
        this.chkLeyenda.setEnabled(true);
        this.btnCalcular.setEnabled(true);
        this.btnExportarKDE.setEnabled(true);
        if (!habilitar) {
            this.txtAnchoBanda.setEnabled(false);
            this.txtResolucion.setEnabled(false);
            this.cmbFuncionNucleo.setEnabled(false);
            this.chkLeyenda.setEnabled(false);
            this.btnCalcular.setEnabled(false);
            this.btnExportarKDE.setEnabled(false);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panContenedor = new javax.swing.JPanel();
        panResultados = new javax.swing.JPanel();
        btnExportarKDE = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cmbFuncionNucleo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtAnchoBanda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtResolucion = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        lblAvance = new javax.swing.JLabel();
        chkLeyenda = new javax.swing.JCheckBox();

        setClosable(true);
        setTitle("Kernel Density Estimation - KDE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 195, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );

        panContenedor.setBackground(new java.awt.Color(255, 255, 255));
        panContenedor.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        panResultados.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panResultadosLayout = new javax.swing.GroupLayout(panResultados);
        panResultados.setLayout(panResultadosLayout);
        panResultadosLayout.setHorizontalGroup(
            panResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );
        panResultadosLayout.setVerticalGroup(
            panResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );

        panContenedor.add(panResultados);

        btnExportarKDE.setText("Exportar resultado KDE");
        btnExportarKDE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarKDEActionPerformed(evt);
            }
        });

        jLabel9.setText("Función de núcleo");

        jLabel2.setText("Ancho de banda (metros)");

        txtAnchoBanda.setToolTipText("");

        jLabel6.setText("Resolución (metros)");

        txtResolucion.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(cmbFuncionNucleo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtResolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(50, 177, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtAnchoBanda, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbFuncionNucleo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAnchoBanda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtResolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        lblAvance.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblAvance.setText(".");

        chkLeyenda.setText("Ver leyenda de datos");
        chkLeyenda.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkLeyendaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExportarKDE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCalcular)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAvance, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chkLeyenda)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCalcular)
                            .addComponent(lblAvance))
                        .addGap(26, 26, 26)
                        .addComponent(chkLeyenda)
                        .addGap(34, 34, 34)
                        .addComponent(btnExportarKDE)
                        .addGap(88, 88, 88)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkLeyendaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkLeyendaItemStateChanged
        //Se verifica si se debe mostrar o no la leyenda
        this.visorMapa.setIndVerLeyenda(this.chkLeyenda.isSelected());
        this.visorMapa.repaint();
    }//GEN-LAST:event_chkLeyendaItemStateChanged

    private void btnExportarKDEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarKDEActionPerformed
        if (this.arrPixels == null) {
            JOptionPane.showMessageDialog(this.frmPrincipal, "Aún no se han realizado cálculos del método KDE.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Se carga la ruta base de búsqueda
        String rutaBase = Utilidades.obtenerRutaBase();

        //Se pregunta la ruta en la que se guardará el archivo KMZ
        JFileChooser fileChooser = new JFileChooser();
        if (!rutaBase.equals("")) {
            fileChooser.setCurrentDirectory(new File(rutaBase));
        }
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return "Archivo KMl (*.kml)";
            }
            
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".kmz");
                }
            }
        });
        
        int retornoSel = fileChooser.showSaveDialog(this);
        if (retornoSel == JFileChooser.APPROVE_OPTION) {
            String nombreArchivoPNG = fileChooser.getSelectedFile().getAbsolutePath() + ".png";
            String nombreArchivoKML = fileChooser.getSelectedFile().getAbsolutePath() + ".kml";

            try {
                //Se crea la imagen de resultado
                this.crearImagenPNGResultado(nombreArchivoPNG);

                //Se crea el archivo KML de resultado
                this.crearArchivoKMLResultado(nombreArchivoKML, nombreArchivoPNG);
            } catch (IOException ex) {
            }
        }
    }//GEN-LAST:event_btnExportarKDEActionPerformed

        private  ArrayList<Punto> hallarPoligono (CapaPoligono capa) {
        for (String nombreCapa : mapaCapasKML.keySet()) {
            Capa capaAux = mapaCapasKML.get(nombreCapa);

            if (capaAux.getTipoCapa() == 'a') {
                capa =(CapaPoligono) capaAux;
                break;
            }
        }
        
        Poligono florida = null;
        for(long idPoligono :capa.getMapaPoligonos().keySet() ){
         florida=capa.getMapaPoligonos().get(idPoligono);
         break;
        }
        ArrayList<Punto> listaPoligono =florida.getListaPartes().get(0).getListaPuntos();
        
        return listaPoligono;
        }
    //(xactaul-xinicio)/resolución aplicar funcion techo
    //(yactual-yinicio)/resolución aplicar funcion techo
    public double calculoDensidad(ArrayList<Punto> listaLugares) {
        double xInicio = arrPixels[0][0].getxCentro() - this.resolucion / 2;
        double yInicio = arrPixels[arrPixels.length - 1][0].getyCentro() - this.resolucion / 2;
        int numCuadros = (int) Math.ceil(this.anchoBanda / this.resolucion) + 1;
        double sumaDensidades = 0;
        double cantidadPixels = 0;
        for (Punto piscina : listaLugares) {
            int columnaPiscina = (int) (Math.ceil((piscina.getCoordenadaUTM().getX() - xInicio) / this.resolucion));
            int filaPiscina = (int) (arrPixels.length - Math.ceil((piscina.getCoordenadaUTM().getY() - yInicio) / this.resolucion));
            for (int i = filaPiscina - numCuadros; i <= filaPiscina + numCuadros; i++) {
                for (int j = columnaPiscina - numCuadros; j <= columnaPiscina+ numCuadros; j++) {
                    try {
                        Pixel pixelAux = arrPixels[i][j];
                        double distanciaAux = Math.sqrt(Math.pow(piscina.getCoordenadaUTM().getX() - pixelAux.getxCentro(), 2)
                                + Math.pow(piscina.getCoordenadaUTM().getY() - pixelAux.getyCentro(), 2));
                        if (distanciaAux <= this.anchoBanda) {
                            sumaDensidades += pixelAux.getDensidad();
                            cantidadPixels++;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        //el pixel no existe, no se toma en cuenta
                    }

                }

            }
        }
        double resultado = 0;
        if (cantidadPixels > 0) {
            resultado = sumaDensidades / cantidadPixels;
        }
        return resultado;
    }
    
    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
 //Se valida que se hayan diligenciado todos los campos
        if (this.validarCamposCalculo()) {
            //Se inhabilitan los componentes
            this.habilitarComponentes(false);

            //Se inicia el hilo que muestra el avance del proceso
            final AvanceCalculo tareaCalculo = new AvanceCalculo();
            Thread hiloProcesar = new Thread(tareaCalculo, "Procesando");
            hiloProcesar.start();

            Runnable procesoCarga = new Runnable() {
                @Override
                public void run() {
                    
                    anchoBanda = Double.parseDouble(txtAnchoBanda.getText());
                    resolucion = Double.parseDouble(txtResolucion.getText());
                    funcionNucleo = (FuncionNucleo) cmbFuncionNucleo.getSelectedItem();
                    lblAvance.setVisible(true);
                    //Se llama a la clase que realiza el cálculo
                    PrCalculoKDE prCalculoKDE = new PrCalculoKDE( anchoBanda, resolucion, funcionNucleo, listaPuntos);   
                    //Se realiza el cálculo
                    arrPixels = prCalculoKDE.calcularKDE();
                        
                   
                    tareaCalculo.setControlCorrer(false);
                    lblAvance.setVisible(false);
                    habilitarComponentes(true);
                    

                    if (arrPixels != null && arrPixels.length > 0) {
                        habilitarComponentes(true);
                        mostrarResultados();
                        double[] arrayCorrelacion = calculoCorrelacion();
                        if (arrayCorrelacion[0] < arrayCorrelacion[1]) {
                        JOptionPane.showMessageDialog(null, "Baja infección");
                         System.out.println(arrayCorrelacion);
                        } else if (arrayCorrelacion[0] > arrayCorrelacion[2]) {
                        JOptionPane.showMessageDialog(null, "Alta foco de infección");
               
                    } else {
                    JOptionPane.showMessageDialog(null, "No se relaciona");
                    System.out.println(arrayCorrelacion);
                    }
                    } else {
                        JOptionPane.showMessageDialog(frmPrincipal, "Error en el calculo del método KCE", "KDE", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };

            Thread hiloCarga = new Thread(procesoCarga, "procesarCarga");
            hiloCarga.start();
        }
    }//GEN-LAST:event_btnCalcularActionPerformed

    private double[] calculoCorrelacion() {
        double densidadReal = calculoDensidad(listaLugares);
        ArrayList<Double> listaDensidades = new ArrayList<>();
        listaDensidades.add(densidadReal);
        CapaPoligono capaMapa = null;
         ArrayList<Punto> listaPoligono = hallarPoligono(capaMapa);

        int[] arrPerimetroX = new int[listaPoligono.size()];
        int[] arrPerimetroY = new int[listaPoligono.size()];
        int contador = 0;
        double xMin = Double.POSITIVE_INFINITY, xMax = Double.NEGATIVE_INFINITY, yMin = Double.POSITIVE_INFINITY, yMax = Double.NEGATIVE_INFINITY;
        for (Punto puntoaux : listaPoligono) {
            arrPerimetroX[contador] = (int) (puntoaux.getCoordenadaUTM().getX() * 100);
            arrPerimetroY[contador] = (int) (puntoaux.getCoordenadaUTM().getY() * 100);

            if (puntoaux.getCoordenadaUTM().getX() < xMin) {
                xMin = puntoaux.getCoordenadaUTM().getX();
            }
            if (puntoaux.getCoordenadaUTM().getX() > xMax) {
                xMax = puntoaux.getCoordenadaUTM().getX();
            }
            if (puntoaux.getCoordenadaUTM().getY() < yMin) {
                yMin = puntoaux.getCoordenadaUTM().getY();
            }

            if (puntoaux.getCoordenadaUTM().getY() > yMax) {
                yMax = puntoaux.getCoordenadaUTM().getY();
            }
            contador++;
        }

        Polygon perimetro = new Polygon(arrPerimetroX, arrPerimetroY, listaPoligono.size());

        Random random = new Random();
        for (int i = 0; i < 999; i++) {
            ArrayList<Punto> listaPuntosAux = new ArrayList<>();
            while (listaPuntosAux.size() < listaLugares.size()) {
                double xAleatorio = random.nextDouble() * (xMax - xMin) + xMin;
                double yAleatorio = random.nextDouble() * (yMax - yMin) + yMin;
                boolean contiene = perimetro.contains(xAleatorio * 100, yAleatorio * 100);
                if (contiene) {
                    CoordenadaUTM cUTM = new CoordenadaUTM(xAleatorio, yAleatorio, 0, 'n');
                    Punto puntoAleatorio = new Punto(0, 0);
                    puntoAleatorio.setCoordenadaUTM(cUTM);
                    listaPuntosAux.add(puntoAleatorio);
                }

            }
            double densidadAleatoria = calculoDensidad(listaPuntosAux);
            listaDensidades.add(densidadAleatoria);
        }

        Collections.sort(listaDensidades);

    
        double valor2_5 = listaDensidades.get(25);
        double valor97_5 = listaDensidades.get(974);

        double[] resultado = {densidadReal, valor2_5, valor97_5};
        return resultado;

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnExportarKDE;
    private javax.swing.JCheckBox chkLeyenda;
    private javax.swing.JComboBox cmbFuncionNucleo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAvance;
    private javax.swing.JPanel panContenedor;
    private javax.swing.JPanel panResultados;
    private javax.swing.JTextField txtAnchoBanda;
    private javax.swing.JTextField txtResolucion;
    // End of variables declaration//GEN-END:variables

    private class FrmMostrarKDEListener implements InternalFrameListener {

        @Override
        public void internalFrameOpened(InternalFrameEvent e) {

        }

        @Override
        public void internalFrameClosing(InternalFrameEvent e) {

        }

        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
            frmPrincipal.remove(frmMostrarKDE);
        }

        @Override
        public void internalFrameIconified(InternalFrameEvent e) {

        }

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) {

        }

        @Override
        public void internalFrameActivated(InternalFrameEvent e) {

        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) {

        }

    }
    
    private class ButtonRendererKDE extends JButton implements TableCellRenderer {

        public void setOpaque() {
            super.setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private class ButtonEditorKDE extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private boolean isPushed;
        private final JTable table;
        private final String[] arrRutas;

        public ButtonEditorKDE(JCheckBox checkBox, JTable table, String[] arrRutas) {
            super(checkBox);
            this.table = table;
            this.arrRutas = arrRutas;

            this.button = new JButton();
            this.button.setOpaque(true);
            this.button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (isSelected) {
                this.button.setForeground(table.getSelectionForeground());
                this.button.setBackground(table.getSelectionBackground());
            } else {
                this.button.setForeground(table.getForeground());
                this.button.setBackground(table.getBackground());
            }
            this.label = (value == null) ? "" : value.toString();
            this.button.setText(this.label);
            this.isPushed = true;
            return this.button;
        }


        @Override
        public boolean stopCellEditing() {
            this.isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    
}

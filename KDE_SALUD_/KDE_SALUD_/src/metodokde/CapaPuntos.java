package metodokde;;

import java.util.ArrayList;
import kde_salud.Punto;
import java.util.HashMap;

/**
 * Clase que representa un registro de capa de puntos
 * @author Feisar Moreno
 * @date 30/07/2020
 */
public class CapaPuntos extends Capa {
   // private CapaLineas capaLineas;
    private String fechaCrea;
    private int indProy;
    private double distProy;
    private long idAtributoFecha;
    private long idAtributoHora;
    private ArrayList<Punto> listaPuntos;
    private HashMap<Integer, Punto> mapaPuntos = new HashMap<>();
    private String descCapa;
    
    public CapaPuntos() {
    }

    public CapaPuntos(ArrayList<Punto> listaPuntos) {
        this.listaPuntos = listaPuntos;
    }
 
     public CapaPuntos(String nombreCapa, String descCapa) {
        super();
        this.tipoCapa = 'p';
        this.nombreCapa = nombreCapa;
        this.descCapa = descCapa;
    }
    public String getFechaCrea() {
        return fechaCrea;
    }
    
    public void setFechaCrea(String fechaCrea) {
        this.fechaCrea = fechaCrea;
    }
    
    public int getIndProy() {
        return indProy;
    }
    
    public void setIndProy(int indProy) {
        this.indProy = indProy;
    }
    
    public double getDistProy() {
        return distProy;
    }
    
    public void setDistProy(double distProy) {
        this.distProy = distProy;
    }
    
    public long getIdAtributoFecha() {
        return idAtributoFecha;
    }
    
    public void setIdAtributoFecha(long idAtributoFecha) {
        this.idAtributoFecha = idAtributoFecha;
    }
    
    public long getIdAtributoHora() {
        return idAtributoHora;
    }
    
    public void setIdAtributoHora(long idAtributoHora) {
        this.idAtributoHora = idAtributoHora;
    }

    public ArrayList<Punto> getListaPuntos() {
        return listaPuntos;
    }

    public void setListaPuntos(ArrayList<Punto> listaPuntos) {
        this.listaPuntos = listaPuntos;
    }
    
    
    public void addPunto(Punto punto) {

       this.listaPuntos.add(punto.getId(), punto);
       this.actualizarExtremos(punto.getCoordenadaUTM());
   }
    
    public int getCantPuntos() {
        return this.listaPuntos.size();
    }
    public HashMap<Integer, Punto> getMapaPuntos() {
        return mapaPuntos;
    }

    public void setMapaPuntos(HashMap<Integer, Punto> mapaPuntos) {
        this.mapaPuntos = mapaPuntos;
    }
    
    public void agregarPunto(Punto punto) {
        this.mapaPuntos.put(punto.getId(), punto);
        this.actualizarExtremos(punto.getCoordenadaUTM());
    }
    
    public int getCantPunto() {
        return this.mapaPuntos.size();
    }
}

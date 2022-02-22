/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kde_salud;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import metodokde.CoordenadaUTM;
import metodokde.Utilidades;



/**
 *
 * @author Johana
 */
public class Punto {
    private long idCapa;
    private LinkedHashMap<String,String> mapaDatos;
    private double latitud;
    private double longitud;
    private int id;
    private CoordenadaUTM coordenadaUTM;

    public Punto(LinkedHashMap<String, String> mapaDatos, double latitud, double longitud, int id, CoordenadaUTM coordenadaUTM) {
        
        this.mapaDatos = mapaDatos;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
        this.coordenadaUTM = Utilidades.convertirGeograficasAUTM(latitud, longitud);
    }
    public Punto(double latitud, double longitud ) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.coordenadaUTM = Utilidades.convertirGeograficasAUTM(latitud, longitud);
    }
    public Punto(long idCapa, int id, double latitud, double longitud ) {
        
        this.idCapa = idCapa;
        this.mapaDatos = mapaDatos;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
        this.coordenadaUTM = Utilidades.convertirGeograficasAUTM(latitud, longitud);
    }
    
        public long getIdCapa() {
        return idCapa;
    }
    
    public void setIdCapa(long idCapa) {
        this.idCapa = idCapa;
    }
    public CoordenadaUTM getCoordenadaUTM() {
        return coordenadaUTM;
    }

    public void setCoordenadaUTM(CoordenadaUTM coordenadaUTM) {
        this.coordenadaUTM = coordenadaUTM;
    }

    public HashMap<String, String> getMapaDatos() {
        return mapaDatos;
    }

    public void setMapaDatos(LinkedHashMap<String,String> mapaDatos) {
        this.mapaDatos = mapaDatos;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
//    public void Ordenado(HashMap<String,String> mapa){
//     TreeMap<String, String> ordenado = new TreeMap<>(mapa);
//     ordenado.putAll(mapa);
//    }
//    
}

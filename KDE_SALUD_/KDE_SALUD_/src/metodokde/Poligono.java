/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodokde;

import java.util.ArrayList;

/**
 *
 * @author Johana
 */
public class Poligono {
    private CapaPoligono capaPoligonos;
    private long idPoligono;
    private ArrayList<PartePoligono> listaPartes = new ArrayList<>();
    
    public Poligono() {
    }
    
    public Poligono(CapaPoligono capaPoligonos, long idPoligono) {
        this.capaPoligonos = capaPoligonos;
        this.idPoligono = idPoligono;
    }
    
    public long getIdCapa() {
        return capaPoligonos.getIdCapa();
    }
    
    public long getIdPoligono() {
        return idPoligono;
    }
    
    public void setIdPoligono(long idPoligono) {
        this.idPoligono = idPoligono;
    }
    
    public ArrayList<PartePoligono> getListaPartes() {
        return listaPartes;
    }
    
    public void setListaPartes(ArrayList<PartePoligono> listaPartes) {
        this.listaPartes = listaPartes;
    }
    
    public void addParte(PartePoligono partePoligono) {
        this.listaPartes.add(partePoligono);
    }
    
    public int getCantPartes() {
        return this.listaPartes.size();
    }
    
}
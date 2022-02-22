/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodokde;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Johana
 */

public class CapaPoligono extends Capa {
    private HashMap<Long, Poligono> mapaPoligonos = new HashMap<>();
    
    public CapaPoligono() {
    }
    
    public CapaPoligono(String nombreCapa, String descCapa) {
        super();
        this.tipoCapa = 'a';
        this.nombreCapa = nombreCapa;
        
    }
    
    public HashMap<Long, Poligono> getMapaPoligonos() {
        return mapaPoligonos;
    }
    
    public void setMapaPoligonos(HashMap<Long, Poligono> mapaPoligonos) {
        this.mapaPoligonos = mapaPoligonos;
    }
    
    public void addPoligono(Poligono poligono) {
        this.mapaPoligonos.put(poligono.getIdPoligono(), poligono);
    }
    
    public int getCantPoligonos() {
        return this.mapaPoligonos.size();
    }
}
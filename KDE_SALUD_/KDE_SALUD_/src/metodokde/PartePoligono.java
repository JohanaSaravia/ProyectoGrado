/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodokde;

import java.util.ArrayList;
import kde_salud.Punto;

/**
 *
 * @author Johana
 */
public class PartePoligono {
    private CapaPoligono capaPoligonos;
    private long idParte;
    private char tipoParte;
    private ArrayList<Punto> listaPuntos = new ArrayList<>();
    
    public PartePoligono() {
    }
    
    public PartePoligono(CapaPoligono capaPoligonos, long idParte, char tipoParte) {
        this.capaPoligonos = capaPoligonos;
        this.idParte = idParte;
        this.tipoParte = tipoParte;
    }
    
    public long getIdParte() {
        return idParte;
    }
    
    public void setIdParte(long idParte) {
        this.idParte = idParte;
    }
    
    public char getTipoParte() {
        return tipoParte;
    }
    
    public void setTipoParte(char tipoParte) {
        this.tipoParte = tipoParte;
    }
    
    public ArrayList<Punto> getListaPuntos() {
        return listaPuntos;
    }
    
    public void setListaPuntos(ArrayList<Punto> listaPuntos) {
        this.listaPuntos = listaPuntos;
    }
    
    public void addPunto(Punto punto) {
        this.listaPuntos.add(punto);
        this.capaPoligonos.actualizarExtremos(punto.getCoordenadaUTM());
    }
    
    public int getCantPuntos() {
        return this.listaPuntos.size();
    }
}
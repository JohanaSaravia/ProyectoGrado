package metodokde;


public abstract class Capa {
    private static int contador = 0;
    protected char tipoCapa;
    protected long idCapa;
    protected String nombreCapa;
    protected double minX;
    protected double minY;
    protected double maxX;
    protected double maxY;
    
    protected Capa() {
        idCapa = contador++;
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        maxX = Double.NEGATIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
    }
    
    public char getTipoCapa() {
        return tipoCapa;
    }
    
    public void setTipoCapa(char tipoCapa) {
        this.tipoCapa = tipoCapa;
    }
    
    public long getIdCapa() {
        return idCapa;
    }
    
    public String getNombreCapa() {
        return nombreCapa;
    }
    
    public void setNombreCapa(String nombreCapa) {
        this.nombreCapa = nombreCapa;
    }
    
    

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Capa.contador = contador;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    
    protected void actualizarExtremos(CoordenadaUTM coordenadaUTM) {
        if (coordenadaUTM.getX() < this.minX) {
            this.minX = coordenadaUTM.getX();
        }
        if (coordenadaUTM.getY() < this.minY) {
            this.minY = coordenadaUTM.getY();
        }
        if (coordenadaUTM.getX() > this.maxX) {
            this.maxX = coordenadaUTM.getX();
        }
        if (coordenadaUTM.getY() > this.maxY) {
            this.maxY = coordenadaUTM.getY();
        }
    }
}

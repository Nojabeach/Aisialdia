package modelo;

public class InteresPorDefecto {
    private int idInteres;
    private String nombreInteres;

    public InteresPorDefecto() {
    }

    public InteresPorDefecto(String nombreInteres) {
        this.nombreInteres = nombreInteres;
    }

    public int getIdInteres() {
        return idInteres;
    }

    public void setIdInteres(int idInteres) {
        this.idInteres = idInteres;
    }

    public String getNombreInteres() {
        return nombreInteres;
    }

    public void setNombreInteres(String nombreInteres) {
        this.nombreInteres = nombreInteres;
    }

	@Override
	public String toString() {
		return "InteresPorDefecto [idInteres=" + idInteres + ", nombreInteres=" + nombreInteres + "]";
	}
    
    
}

package clases;

public class Cabecera {
	private int idMensaje;
    private String fecha;
    private String hora;
    private int numeroOperaciones;
    private Double controlSuma;
    
    public Cabecera() {
    	
    }

	public Cabecera(int idMensaje, String fecha, String hora, int numeroOperaciones, Double controlSuma) {
		super();
		this.idMensaje = idMensaje;
		this.fecha = fecha;
		this.hora = hora;
		this.numeroOperaciones = numeroOperaciones;
		this.controlSuma = controlSuma;
	}

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getNumeroOperaciones() {
		return numeroOperaciones;
	}

	public void setNumeroOperaciones(int numeroOperaciones) {
		this.numeroOperaciones = numeroOperaciones;
	}

	public Double getControlSuma() {
		return controlSuma;
	}

	public void setControlSuma(Double controlSuma) {
		this.controlSuma = controlSuma;
	}    

}

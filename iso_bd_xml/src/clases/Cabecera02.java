package clases;

public class Cabecera02 {
	private int idMensaje;
    private String fecha;
    private String hora;
    private int numeroOperaciones;
    private Double controlSuma;
    private String nombreOrdenante;
    private String nifOrdenante;
    private String ibanOrdenante;
    private String bicOrdenante;
    private String identificadorId;
    private String metodoPago;
    private String fechaEjecucion;
    
    public Cabecera02() {
    	
    }
	
	public Cabecera02(int idMensaje, String fecha, String hora, int numeroOperaciones, Double controlSuma,
			String nombreOrdenante, String nifOrdenante, String ibanOrdenante, String bicOrdenante,
			String identificadorId, String metodoPago, String fechaEjecucion) {
		this.idMensaje = idMensaje;
		this.fecha = fecha;
		this.hora = hora;
		this.numeroOperaciones = numeroOperaciones;
		this.controlSuma = controlSuma;
		this.nombreOrdenante = nombreOrdenante;
		this.nifOrdenante = nifOrdenante;
		this.ibanOrdenante = ibanOrdenante;
		this.bicOrdenante = bicOrdenante;
		this.identificadorId = identificadorId;
		this.metodoPago = metodoPago;
		this.fechaEjecucion = fechaEjecucion;
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

	public String getNombreOrdenante() {
		return nombreOrdenante;
	}

	public void setNombreOrdenante(String nombreOrdenante) {
		this.nombreOrdenante = nombreOrdenante;
	}

	public String getNifOrdenante() {
		return nifOrdenante;
	}

	public void setNifOrdenante(String nifOrdenante) {
		this.nifOrdenante = nifOrdenante;
	}

	public String getIdentificadorId() {
		return identificadorId;
	}

	public void setIdentificadorId(String identificadorId) {
		this.identificadorId = identificadorId;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(String fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public String getIbanOrdenante() {
		return ibanOrdenante;
	}

	public void setIbanOrdenante(String ibanOrdenante) {
		this.ibanOrdenante = ibanOrdenante;
	}

	public String getBicOrdenante() {
		return bicOrdenante;
	}

	public void setBicOrdenante(String bicOrdenante) {
		this.bicOrdenante = bicOrdenante;
	}

}

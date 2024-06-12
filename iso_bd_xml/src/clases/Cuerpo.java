package clases;

public class Cuerpo {
	private String identificadorPago;
	private int idMensaje;    
    private String medioPago;
    private String indicadorApunteCuenta;
    private String informacionTipoPago;
    private String fecha;
    private String nombreOrdenante;
    private String nifOrdenante;
    private String cuentaOrdenante;
    private String nombreBeneficiario;
    private String nifBeneficiario;
    private String cuentaBeneficiario;
    private Double controlSuma;
    
    public Cuerpo() {
    	
    }

	public Cuerpo(String identificadorPago, int idMensaje, String medioPago, String indicadorApunteCuenta,
			String informacionTipoPago, String fecha, String nombreOrdenante, String nifOrdenante,
			String cuentaOrdenante, String nombreBeneficiario, String nifBeneficiario, String cuentaBeneficiario,
			Double controlSuma) {
		this.identificadorPago = identificadorPago;
		this.idMensaje = idMensaje;
		this.medioPago = medioPago;
		this.indicadorApunteCuenta = indicadorApunteCuenta;
		this.informacionTipoPago = informacionTipoPago;
		this.fecha = fecha;
		this.nombreOrdenante = nombreOrdenante;
		this.nifOrdenante = nifOrdenante;
		this.cuentaOrdenante = cuentaOrdenante;
		this.nombreBeneficiario = nombreBeneficiario;
		this.nifBeneficiario = nifBeneficiario;
		this.cuentaBeneficiario = cuentaBeneficiario;
		this.controlSuma = controlSuma;
	}

	public Cuerpo(String identificadorPago, String medioPago, String indicadorApunteCuenta, String informacionTipoPago,
			String fecha, String nombreOrdenante, String nifOrdenante, String cuentaOrdenante,
			String nombreBeneficiario, String nifBeneficiario, String cuentaBeneficiario, Double controlSuma) {
		super();
		this.identificadorPago = identificadorPago;
		this.medioPago = medioPago;
		this.indicadorApunteCuenta = indicadorApunteCuenta;
		this.informacionTipoPago = informacionTipoPago;
		this.fecha = fecha;
		this.nombreOrdenante = nombreOrdenante;
		this.nifOrdenante = nifOrdenante;
		this.cuentaOrdenante = cuentaOrdenante;
		this.nombreBeneficiario = nombreBeneficiario;
		this.nifBeneficiario = nifBeneficiario;
		this.cuentaBeneficiario = cuentaBeneficiario;
		this.controlSuma = controlSuma;
	}

	public String getIdentificadorPago() {
		return identificadorPago;
	}

	public void setIdentificadorPago(String identificadorPago) {
		this.identificadorPago = identificadorPago;
	}

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}

	public String getIndicadorApunteCuenta() {
		return indicadorApunteCuenta;
	}

	public void setIndicadorApunteCuenta(String indicadorApunteCuenta) {
		this.indicadorApunteCuenta = indicadorApunteCuenta;
	}

	public String getInformacionTipoPago() {
		return informacionTipoPago;
	}

	public void setInformacionTipoPago(String informacionTipoPago) {
		this.informacionTipoPago = informacionTipoPago;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public String getCuentaOrdenante() {
		return cuentaOrdenante;
	}

	public void setCuentaOrdenante(String cuentaOrdenante) {
		this.cuentaOrdenante = cuentaOrdenante;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getNifBeneficiario() {
		return nifBeneficiario;
	}

	public void setNifBeneficiario(String nifBeneficiario) {
		this.nifBeneficiario = nifBeneficiario;
	}

	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public Double getControlSuma() {
		return controlSuma;
	}

	public void setControlSuma(Double controlSuma) {
		this.controlSuma = controlSuma;
	}    

}

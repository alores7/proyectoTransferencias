package clases;

public class Pago {
	String pagos;
	String normal; 
	String cifOrdenante; 
	String numero; 
	String fechaOperacion;
	String ibanOrdenante; 
	String concepto; 
	String nombreOrdenante; 
	String ibanBeneficiario; 
	Double importe; 
	String nombreBeneficiario; 
	String gastos;
	
	public Pago() {
		
	}

	public Pago(String pagos, String normal, String cifOrdenante, String numero, String fechaOperacion,
			String ibanOrdenante, String concepto, String nombreOrdenante, String ibanBeneficiario, Double importe,
			String nombreBeneficiario, String gastos) {
		this.pagos = pagos;
		this.normal = normal;
		this.cifOrdenante = cifOrdenante;
		this.numero = numero;
		this.fechaOperacion = fechaOperacion;
		this.ibanOrdenante = ibanOrdenante;
		this.concepto = concepto;
		this.nombreOrdenante = nombreOrdenante;
		this.ibanBeneficiario = ibanBeneficiario;
		this.importe = importe;
		this.nombreBeneficiario = nombreBeneficiario;
		this.gastos = gastos;
	}

	public String getPagos() {
		return pagos;
	}

	public void setPagos(String pagos) {
		this.pagos = pagos;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getCifOrdenante() {
		return cifOrdenante;
	}

	public void setCifOrdenante(String cifOrdenante) {
		this.cifOrdenante = cifOrdenante;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getIbanOrdenante() {
		return ibanOrdenante;
	}

	public void setIbanOrdenante(String ibanOrdenante) {
		this.ibanOrdenante = ibanOrdenante;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getNombreOrdenante() {
		return nombreOrdenante;
	}

	public void setNombreOrdenante(String nombreOrdenante) {
		this.nombreOrdenante = nombreOrdenante;
	}

	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getGastos() {
		return gastos;
	}

	public void setGastos(String gastos) {
		this.gastos = gastos;
	}	
	
}

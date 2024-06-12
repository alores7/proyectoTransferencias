package clases;

public class Cuerpo02 {
    private String insrtId;
    private String refrencia;
    private Double importe;
    private String bicBeneficiario;
    private String nombreBeneficiario;
    private String ibanBeneficiario;
    private String concepto;
    
    public Cuerpo02() {
    	
    }

	public Cuerpo02(String insrtId, String refrencia, Double importe, String bicBeneficiario, String nombreBeneficiario,
			String ibanBeneficiario, String concepto) {
		this.insrtId = insrtId;
		this.refrencia = refrencia;
		this.importe = importe;
		this.bicBeneficiario = bicBeneficiario;
		this.nombreBeneficiario = nombreBeneficiario;
		this.ibanBeneficiario = ibanBeneficiario;
		this.concepto = concepto;
	}

	public String getInsrtId() {
		return insrtId;
	}

	public void setInsrtId(String insrtId) {
		this.insrtId = insrtId;
	}

	public String getRefrencia() {
		return refrencia;
	}

	public void setRefrencia(String refrencia) {
		this.refrencia = refrencia;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public String getBicBeneficiario() {
		return bicBeneficiario;
	}

	public void setBicBeneficiario(String bicBeneficiario) {
		this.bicBeneficiario = bicBeneficiario;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}    
    
}

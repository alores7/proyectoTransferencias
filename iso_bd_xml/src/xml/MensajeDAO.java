package xml;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import clases.Cabecera;
import clases.Cabecera02;
import clases.Cuerpo;
import clases.Cuerpo02;
import clases.Pago;

public class MensajeDAO {
	int puerto;
    String user, password, bd;
    Conexion conexion;
    Connection cn;
    
    public MensajeDAO(int puerto, String user, String password, String bd) {
        this.puerto = puerto;
        this.user = user;
        this.password = password;
        this.bd = bd;
        this.conexion = new Conexion();
        this.cn = conexion.conectar(puerto, user, password, bd);
    }

    public Cabecera getCabeceraById(int idMensaje) throws SQLException {
        Cabecera cabecera = null;
        String query = "SELECT * FROM cabecera WHERE idMensaje = ?";
        try (PreparedStatement statement = cn.prepareStatement(query)) {
            statement.setInt(1, idMensaje);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cabecera = new Cabecera();
                cabecera.setIdMensaje(resultSet.getInt("idMensaje"));
                cabecera.setFecha(resultSet.getString("fecha"));
                cabecera.setHora(resultSet.getString("hora"));
                cabecera.setNumeroOperaciones(resultSet.getInt("numeroOperaciones"));
                cabecera.setControlSuma(resultSet.getDouble("controlSuma"));
            }
        }
        return cabecera;
    }
    
    public Cabecera02 obtenerCabeceraPorNIFyFecha(String nif, String fecha) throws SQLException {
    	Cabecera02 cabecera = null;
    	String query = "SELECT nombreOrdenante, nifOrdenante, metodoPago, fecha, ibanOrdenante, bicOrdenante FROM c34 WHERE nifOrdenante = ? AND fecha = ?";
		try (PreparedStatement statement = cn.prepareStatement(query)) {
			statement.setString(1, nif);
			statement.setString(2, fecha);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				cabecera = new Cabecera02();
				cabecera.setNombreOrdenante(resultSet.getString("nombreOrdenante"));
				cabecera.setNifOrdenante(resultSet.getString("nifOrdenante"));
				cabecera.setMetodoPago(resultSet.getString("metodoPago"));
				cabecera.setFechaEjecucion(resultSet.getString("fecha"));
				cabecera.setIbanOrdenante(resultSet.getString("ibanOrdenante"));
				cabecera.setBicOrdenante(resultSet.getString("bicOrdenante"));				
			}    		 
    	}
		return cabecera;
    }

    public List<Cuerpo> getCuerposByIdMensaje(int idMensaje) throws SQLException {
        List<Cuerpo> cuerpos = new ArrayList<>();
        String query = "SELECT * FROM cuerpo WHERE idMensaje = ?";
        try (PreparedStatement statement = cn.prepareStatement(query)) {
            statement.setInt(1, idMensaje);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cuerpo cuerpo = new Cuerpo();
                cuerpo.setIdentificadorPago(resultSet.getString("identificadorPago"));
                cuerpo.setIdMensaje(resultSet.getInt("idMensaje"));
                cuerpo.setMedioPago(resultSet.getString("medioPago"));
                cuerpo.setIndicadorApunteCuenta(resultSet.getString("indicadorApunteCuenta"));
                cuerpo.setInformacionTipoPago(resultSet.getString("informacionTipoPago"));
                cuerpo.setFecha(resultSet.getString("fecha"));
                cuerpo.setNombreOrdenante(resultSet.getString("nombreOrdenante"));
                cuerpo.setNifOrdenante(resultSet.getString("nifOrdenante"));
                cuerpo.setCuentaOrdenante(resultSet.getString("cuentaOrdenante"));
                cuerpo.setNombreBeneficiario(resultSet.getString("nombreBeneficiario"));
                cuerpo.setNifBeneficiario(resultSet.getString("nifBeneficiario"));
                cuerpo.setCuentaBeneficiario("cuentaBeneficiario");
                cuerpo.setControlSuma(resultSet.getDouble("controlSuma"));
                cuerpos.add(cuerpo);
            }
        }
        return cuerpos;
    }
    
    public List<Cuerpo02> leerPagosC34(String nif, String fecha) throws SQLException {
    	List<Cuerpo02> pagos = new ArrayList<>();
        String query = "SELECT referencia, importe, bicBeneficiario, nombreBeneficiario, ibanBeneficiario, concepto FROM c34 WHERE nifOrdenante = ? AND fecha = ?";
        try (PreparedStatement statement = cn.prepareStatement(query)) {
        	statement.setString(1, nif);
        	statement.setString(2, fecha);
        	ResultSet resultSet = statement.executeQuery();
        	while (resultSet.next()) {
        		Cuerpo02 pago = new Cuerpo02();
        		pago.setReferencia(resultSet.getString("referencia"));
        		pago.setImporte(resultSet.getDouble("importe"));
        		pago.setBicBeneficiario(resultSet.getString("bicBeneficiario"));
        		pago.setNombreBeneficiario(resultSet.getString("nombreBeneficiario"));
        		pago.setIbanBeneficiario(resultSet.getString("IbanBeneficiario"));
        		pago.setConcepto(resultSet.getString("concepto"));
        		pagos.add(pago);
        	}        	
        }
        return pagos;
    }
    
    public List<Pago> getPagos() throws SQLException {
        List<Pago> pagos = new ArrayList<>();
        String query = "SELECT * FROM c34_14";
        try (PreparedStatement statement = cn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Pago pago = new Pago();
                pago.setPagos(resultSet.getString("pagos"));
                pago.setNormal(resultSet.getString("normal"));
                pago.setCifOrdenante(resultSet.getString("cifOrdenante"));
                pago.setNumero(resultSet.getString("numero"));
                pago.setFechaOperacion(resultSet.getString("fechaOperacion"));
                pago.setIbanOrdenante(resultSet.getString("ibanOrdenante"));
                pago.setConcepto(resultSet.getString("concepto"));
                pago.setNombreOrdenante(resultSet.getString("nombreOrdenante"));
                pago.setIbanBeneficiario(resultSet.getString("ibanBeneficiario"));
                pago.setImporte(resultSet.getDouble("importe"));
                pago.setNombreBeneficiario(resultSet.getString("nombreBeneficiario"));
                pago.setGastos(resultSet.getString("gastos"));

                pagos.add(pago);
            }
        }
        return pagos;
    }
    
    private static Double calcularControlSuma(List<Cuerpo02> cuerpos) {
        return cuerpos.stream().mapToDouble(Cuerpo02::getImporte).sum();
    }
    
/*    public int generarXML(Cabecera cabecera, List<Cuerpo> cuerpos, String ruta) throws IOException {
        try {
        	FileWriter writer = new FileWriter(ruta + "\\mensaje.xml");
            writer.write("<mensaje>\n");
            writer.write("\t<cabecera>\n");
            writer.write("\t\t<idMensaje>" + cabecera.getIdMensaje() + "</idMensaje>\n");
            writer.write("\t\t<fecha>" + cabecera.getFecha() + "</fecha>\n");
            writer.write("\t\t<hora>" + cabecera.getHora() + "</hora>\n");
            writer.write("\t\t<numeroOperaciones>" + cabecera.getNumeroOperaciones() + "</numeroOperaciones>\n");
            writer.write("\t\t<controlSuma>" + cabecera.getControlSuma() + "</controlSuma>\n");
            writer.write("\t</cabecera>\n");

            writer.write("\t<cuerpo>\n");
            for (Cuerpo cuerpo : cuerpos) {
                writer.write("\t\t<pago>\n");
                writer.write("\t\t\t<identificadorPago>" + cuerpo.getIdentificadorPago() + "</identificadorPago>\n");
                writer.write("\t\t\t<medioPago>" + cuerpo.getMedioPago() + "</medioPago>\n");
                writer.write("\t\t\t<indicadorApunteCuenta>" + cuerpo.getIndicadorApunteCuenta() + "</indicadorApunteCuenta>\n");
                writer.write("\t\t\t<controlSuma>" + cuerpo.getControlSuma() + "</controlSuma>\n");
                writer.write("\t\t\t<fechaEjecucionSolicitada>" + cuerpo.getFechaEjecucionSolicitada() + "</fechaEjecucionSolicitada>\n");
                writer.write("\t\t\t<ordenante>" + cuerpo.getOrdenante() + "</ordenante>\n");
                writer.write("\t\t\t<numeroCuenta>" + cuerpo.getNumeroCuenta() + "</numeroCuenta>\n");
                writer.write("\t\t\t<entidadOrdenante>" + cuerpo.getEntidadOrdenante() + "</entidadOrdenante>\n");
                writer.write("\t\t</pago>\n");
            }
            writer.write("\t</cuerpo>\n");
            writer.write("</mensaje>");
            writer.close();
            
            return 1;
        } catch(IOException e) {
        	return 0;
        }    	
    } */
    
    public int generarXML_iso20022(Cabecera cabecera, List<Cuerpo> cuerpos, String ruta) throws IOException {
        try {
            FileWriter writer = new FileWriter(ruta);
            writer.write("<mensaje>\n");
            writer.write("\t<cabecera>\n");

            LocalDateTime fechaHoraActual = LocalDateTime.now();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String horaFormateada = fechaHoraActual.format(formatter);
            writer.write("\t\t<idMensaje>" + cabecera.getIdMensaje() + "</idMensaje>\n");
            writer.write("\t\t<fecha>" + fechaHoraActual.toLocalDate() + "</fecha>\n");
            writer.write("\t\t<hora>" + horaFormateada + "</hora>\n");

            int numeroOperaciones = cuerpos.size();
            Double sumaControlSuma = 0.0;
            for (Cuerpo cuerpo : cuerpos) {
                sumaControlSuma += cuerpo.getControlSuma();
            }
            String sumaControlSumaFormateada = String.format("%.2f", sumaControlSuma);
            
            writer.write("\t\t<numeroOperaciones>" + numeroOperaciones + "</numeroOperaciones>\n");
            writer.write("\t\t<controlSuma>" + sumaControlSumaFormateada + "</controlSuma>\n");
            writer.write("\t</cabecera>\n");

            writer.write("\t<cuerpo>\n");
            for (Cuerpo cuerpo : cuerpos) {
                writer.write("\t\t<pago>\n");
                writer.write("\t\t\t<identificadorPago>" + cuerpo.getIdentificadorPago() + "</identificadorPago>\n");
                writer.write("\t\t\t<medioPago>" + cuerpo.getMedioPago() + "</medioPago>\n");
                writer.write("\t\t\t<indicadorApunteCuenta>" + cuerpo.getIndicadorApunteCuenta() + "</indicadorApunteCuenta>\n");
                writer.write("\t\t\t<informacionTipoPago>" + cuerpo.getInformacionTipoPago() + "</informacionTipoPago>\n");
                writer.write("\t\t\t<fecha>" + cuerpo.getFecha() + "</fecha>\n");
                writer.write("\t\t\t<nombreOrdenante>" + cuerpo.getNombreOrdenante() + "</nombreOrdenante>\n");
                writer.write("\t\t\t<nifOrdenante>" + cuerpo.getNifOrdenante() + "</nifOrdenante>\n");
                writer.write("\t\t\t<cuentaOrdenante>" + cuerpo.getCuentaOrdenante() + "</cuentaOrdenante>\n");
                writer.write("\t\t\t<nombreBeneficiario>" + cuerpo.getNombreBeneficiario() + "</nombreBeneficiario>\n");
                writer.write("\t\t\t<nifBeneficiario>" + cuerpo.getNifBeneficiario() + "</nifBeneficiario>\n");
                writer.write("\t\t\t<cuentaBeneficiario>" + cuerpo.getCuentaBeneficiario() + "</cuentaBeneficiario>\n");
                writer.write("\t\t\t<controlSuma>" + cuerpo.getControlSuma() + "</controlSuma>\n");
                writer.write("\t\t</pago>\n");
            }
            writer.write("\t</cuerpo>\n");
            writer.write("</mensaje>");
            writer.close();

            return 1;
        } catch(IOException e) {
            return 0;
        }
    }
    
    public int generarCSV_c34_14(List<Pago> pagos, String ruta) {
    	try {
    		Writer writer = new OutputStreamWriter(new FileOutputStream(ruta), StandardCharsets.UTF_8);
    	// FileWriter writer = new FileWriter(ruta);

            for (Pago pago : pagos) {
                writer.write(pago.getPagos() + ";");
                writer.write(pago.getNormal() + ";");
                writer.write(pago.getCifOrdenante() + ";");
                writer.write(pago.getNumero() + ";");
                writer.write(";");
                writer.write(pago.getFechaOperacion() + ";");
                writer.write(pago.getIbanOrdenante() + ";");
                writer.write(pago.getConcepto() + ";");
                writer.write(pago.getNombreOrdenante() + ";");
                writer.write(";");
                writer.write(pago.getIbanBeneficiario() + ";");
                writer.write(pago.getImporte() + ";");
                writer.write(pago.getNombreBeneficiario() + ";");
                writer.write(pago.getGastos() + ";");
                writer.write(";");
                writer.write(pago.getConcepto() + "\n");
                
            }

            writer.close();
            return 1;
        } catch (IOException e) {
        	e.printStackTrace();
        	return 0;            
        }    	
    }
    
	public int generarXML_cuaderno34(Cabecera02 cabecera, List<Cuerpo02> pagos, String destino) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destino), "UTF-8"))) {
            //	(Writer writer = new FileWriter(destino))
            	Random random = new Random();
                int numeroAleatorio = random.nextInt(99999 - 1 + 1) + 1;
                String numeroFormateado = String.format("%05d", numeroAleatorio);
            	
                cabecera.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                cabecera.setHora(new SimpleDateFormat("HH:mm:ss").format(new Date()));
                cabecera.setNumeroOperaciones(pagos.size());
                cabecera.setControlSuma(calcularControlSuma(pagos));                
                
            	writer.write("<CstmrCdtTrfInitn>\n");
            	
                // CABECERA
                writer.write("<GrpHdr>\n");
                writer.write("\t<MsgId>" + numeroFormateado + "</MsgId>\n");
                writer.write("\t<CreDtTm>" + cabecera.getFecha() + "T" + cabecera.getHora() + "</CreDtTm>\n");
                writer.write("\t<NbOfTxs>" + cabecera.getNumeroOperaciones() + "</NbOfTxs>\n");
                String controlSumaFormateado = String.format(Locale.US, "%.2f", cabecera.getControlSuma());
                writer.write("\t<CtrlSum>" + controlSumaFormateado + "</CtrlSum>\n");
                writer.write("\t\t<InitgPty>\n");
                writer.write("\t\t\t<Nm>" + cabecera.getNombreOrdenante() + "</Nm>\n");
                writer.write("\t\t\t<Id>\n");
                writer.write("\t\t\t\t<OrgId>\n");
                writer.write("\t\t\t\t\t<Othr>\n");
                writer.write("\t\t\t\t\t\t<Id>" + cabecera.getNifOrdenante() + "</Id>\n");
                writer.write("\t\t\t\t\t</Othr>\n");
                writer.write("\t\t\t\t</OrgId>\n");
                writer.write("\t\t\t</Id>\n");
                writer.write("\t\t</InitgPty>\n");
                writer.write("</GrpHdr>\n");

                // CUERPO
                writer.write("<PmtInf>\n");
                writer.write("\t\t<PmtInfId>SCT</PmtInfId>\n");
                writer.write("\t\t<PmtMtd>" + cabecera.getMetodoPago() + "</PmtMtd>\n");
                writer.write("\t\t<BtchBookg>true</BtchBookg>\n");
                writer.write("\t<NbOfTxs>" + cabecera.getNumeroOperaciones() + "</NbOfTxs>\n");
                writer.write("\t<CtrlSum>" + controlSumaFormateado + "</CtrlSum>\n");
                writer.write("\t\t\t<PmtTpInf>\n");
                writer.write("\t\t\t\t<SvcLvl>\n");
                writer.write("\t\t\t\t\t<Cd>SEPA</Cd>\n");
                writer.write("\t\t\t\t</SvcLvl>\n");
                writer.write("\t\t\t\t<CtgyPurp>\n");
                writer.write("\t\t\t\t\t<Cd>CASH</Cd>\n");
                writer.write("\t\t\t\t</CtgyPurp>\n");
                writer.write("\t\t\t</PmtTpInf>\n");
                writer.write("\t\t\t<ReqdExctnDt>" + cabecera.getFechaEjecucion() + "</ReqdExctnDt>\n");
                writer.write("\t\t\t<Dbtr>\n");
                writer.write("\t\t\t\t<Nm>" + cabecera.getNombreOrdenante() + "</Nm>\n");
                writer.write("\t\t\t\t<Id>\n");
                writer.write("\t\t\t\t\t<OrgId>\n");
                writer.write("\t\t\t\t\t\t<Othr>\n");
                writer.write("\t\t\t\t\t\t\t<Id>" + cabecera.getNifOrdenante() + "</Id>\n");                
                writer.write("\t\t\t\t\t\t</Othr>\n");
                writer.write("\t\t\t\t\t</OrgId>\n");
                writer.write("\t\t\t\t</Id>\n");
                writer.write("\t\t\t</Dbtr>\n");
                writer.write("\t\t\t<DbtrAcct>\n");
                writer.write("\t\t\t\t<Id>\n");
                writer.write("\t\t\t\t\t<IBAN>" + cabecera.getIbanOrdenante() + "</IBAN>\n");
                writer.write("\t\t\t\t</Id>\n");
                writer.write("\t\t\t</DbtrAcct>\n");
                writer.write("\t\t\t<DbtrAgt>\n");
                writer.write("\t\t\t\t<FinInstnId>\n");
                writer.write("\t\t\t\t\t<BIC>" + cabecera.getBicOrdenante() + "</BIC>\n");
                writer.write("\t\t\t\t</FinInstnId>\n");                
                writer.write("\t\t\t</DbtrAgt>\n");
                for (Cuerpo02 pago : pagos) {
                    writer.write("\t\t\t<CdtTrfTxInf>\n");
                    writer.write("\t\t\t\t<PmtId>\n");
                    writer.write("\t\t\t\t\t<InstrId>" + pago.getReferencia() + "</InstrId>\n");
                    writer.write("\t\t\t\t\t<EndToEndId>" + pago.getReferencia() + "</EndToEndId>\n");
                    writer.write("\t\t\t\t</PmtId>\n");
                    writer.write("\t\t\t\t<Amt>\n");
                    String importeFormateado = String.format(Locale.US, "%.2f", pago.getImporte());
                    writer.write("\t\t\t\t\t<InstdAmt Ccy=\"EUR\">" + importeFormateado + "</InstdAmt>\n");
                    writer.write("\t\t\t\t</Amt>\n");
                    writer.write("\t\t\t\t<CdtrAgt>\n");
                    writer.write("\t\t\t\t\t<FinInstnId>\n");
                    writer.write("\t\t\t\t\t\t<BIC>" + pago.getBicBeneficiario() + "</BIC>\n");
                    writer.write("\t\t\t\t\t</FinInstnId>\n");                
                    writer.write("\t\t\t\t</CdtrAgt>\n");
                    writer.write("\t\t\t\t<Cdtr>\n");
                    writer.write("\t\t\t\t\t<Nm>" + pago.getNombreBeneficiario() + "</Nm>\n");
                    writer.write("\t\t\t\t</Cdtr>\n");
                    writer.write("\t\t\t\t<CdtrAcct>\n");
                    writer.write("\t\t\t\t\t<Id>\n");
                    writer.write("\t\t\t\t\t\t<IBAN>" + pago.getIbanBeneficiario() + "</IBAN>\n");
                    writer.write("\t\t\t\t\t</Id>\n");                
                    writer.write("\t\t\t\t</CdtrAcct>\n");
                    writer.write("\t\t\t\t<Purp>\n");
                    writer.write("\t\t\t\t\t<Cd>CASH</Cd>\n");
                    writer.write("\t\t\t\t</Purp>\n");
                    writer.write("\t\t\t\t<RmtInf>\n");
                    writer.write("\t\t\t\t\t<Ustrd>" + pago.getConcepto() + "</Ustrd>\n");
                    writer.write("\t\t\t\t</RmtInf>\n");
                    writer.write("\t\t\t</CdtTrfTxInf>\n");
                }
                writer.write("</PmtInf>");
                
                writer.write("</CstmrCdtTrfInitn>");
                return 1;
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
    }
}


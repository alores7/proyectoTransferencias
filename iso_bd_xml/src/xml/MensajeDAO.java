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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    
/*    public int generarXML_cuaderno34(Cabecera02 cabecera, List<Cuerpo02> cuerpos, String ruta, String nombreOrd, String nifOrd) throws IOException {
        try {
            FileWriter writer = new FileWriter(ruta);
            writer.write("<Mensaje>\n");
            writer.write("\t<Cabecera>\n");

            LocalDateTime fechaHoraActual = LocalDateTime.now();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String horaFormateada = fechaHoraActual.format(formatter);
            writer.write("\t\t<IdMensaje>" + cabecera.getIdMensaje() + "</IdMensaje>\n");
            writer.write("\t\t<Fecha>" + fechaHoraActual.toLocalDate() + "</Fecha>\n");
            writer.write("\t\t<Hora>" + horaFormateada + "</Hora>\n");

            int numeroOperaciones = cuerpos.size();
            Double sumaControlSuma = 0.0;
            for (Cuerpo02 cuerpo : cuerpos) {
                sumaControlSuma += cuerpo.getImporte();
            }
            String sumaControlSumaFormateada = String.format("%.2f", sumaControlSuma);
            
            writer.write("\t\t<NumeroOperaciones>" + numeroOperaciones + "</NumeroOperaciones>\n");
            writer.write("\t\t<ControlSuma>" + sumaControlSumaFormateada + "</ControlSuma>\n");
            writer.write("\t\t<IniciadorPago>\n");
            writer.write("\t\t<Nombre>" + nombreOrd + "</Nombre>\n");
            writer.write("\t\t<NIF>" + nifOrd + "</NIF>\n");
            writer.write("\t\t</IniciadorPago>\n");
            writer.write("\t</Cabecera>\n");

            writer.write("\t<CstmrCdtTrfInitn>\n");
            for (Cuerpo02 cuerpo : cuerpos) {
                writer.write("\t\t<PmtInf>\n");
                writer.write("\t\t\t<IdentificadorPago>" + cuerpo.getIdentificadorPago() + "</IdentificadorPago>\n");
                writer.write("\t\t\t<MetodoPago>" + cuerpo.getMetodoPago() + "</MetodoPago>\n");
                writer.write("\t\t\t<PmtTpInf>\n");
                writer.write("\t\t\t\t<SvcLvl>\n");
                writer.write("\t\t\t\t\t<Cd>" + cuerpo.getCd() + "</Cd>\n");	
                writer.write("\t\t\t\t</SvcLvl>\n");
                writer.write("\t\t\t</PmtTpInf>\n");
                writer.write("\t\t\t<Fecha>" + cuerpo.getFecha() + "</Fecha>\n");
                writer.write("\t\t\t<Dbtr>\n");
                writer.write("\t\t\t\t<Nm>" + cuerpo.getNombreOrdenante() + "</Nm>\n");
                writer.write("\t\t\t</Dbtr>\n");
                writer.write("\t\t\t<DbtrAcct>\n");
                writer.write("\t\t\t\t<Id>\n");
                writer.write("\t\t\t\t\t<IBAN>" + cuerpo.getIbanOrdenante() + "</IBAN>\n");
                writer.write("\t\t\t\t</Id>\n");
                writer.write("\t\t\t</DbtrAcct>\n");
                writer.write("\t\t\t<DbtrAgt>\n");
                writer.write("\t\t\t\t<FinInstnId>\n");
                writer.write("\t\t\t\t\t<BIC>" + cuerpo.getBicOrdenante() + "</BIC>\n");
                writer.write("\t\t\t\t</FinInstnId>\n");                
                writer.write("\t\t\t</DbtrAgt>\n");
                writer.write("\t\t\t<ChrgBr>" + cuerpo.getClausulaGastos() + "</ChrgBr>\n");
                writer.write("\t\t\t<CdtTrfTxInf>\n");
                writer.write("\t\t\t\t<PmtId>\n");
                writer.write("\t\t\t\t\t<EndToEndId>" + cuerpo.getRefrencia() + "</EndToEndId>\n");
                writer.write("\t\t\t\t</PmtId>\n");
                writer.write("\t\t\t\t<Amt>\n");
                writer.write("\t\t\t\t\t<InstdAmt>" + cuerpo.getImporte() + "</InstdAmt>\n");
                writer.write("\t\t\t\t</Amt>\n");
                writer.write("\t\t\t\t<UltmtDbtr>\n");
                writer.write("\t\t\t\t\t<Nm>" + cuerpo.getNombreUltimoOrdenante() + "</Nm>\n");
                writer.write("\t\t\t\t</UltmtDbtr>\n");
                writer.write("\t\t\t\t<CdtrAgt>\n");
                writer.write("\t\t\t\t\t<FinInstnId>\n");
                writer.write("\t\t\t\t\t\t<BIC>" + cuerpo.getBicBeneficiario() + "</BIC>\n");
                writer.write("\t\t\t\t\t</FinInstnId>\n");                
                writer.write("\t\t\t\t</CdtrAgt>\n");
                writer.write("\t\t\t\t<Cdtr>\n");
                writer.write("\t\t\t\t\t<Nm>" + cuerpo.getNombreBeneficiario() + "</Nm>\n");
                writer.write("\t\t\t\t</Cdtr>\n");
                writer.write("\t\t\t\t<CdtrAcct>\n");
                writer.write("\t\t\t\t\t<Id>\n");
                writer.write("\t\t\t\t\t\t<IBAN>" + cuerpo.getIbanBeneficiario() + "</IBAN>\n");
                writer.write("\t\t\t\t\t</Id>\n");                
                writer.write("\t\t\t\t</CdtrAcct>\n");
                writer.write("\t\t\t\t<RmtInf>\n");
                writer.write("\t\t\t\t\t<Ustrd>" + cuerpo.getConcepto() + "</Ustrd>\n");
                writer.write("\t\t\t\t</RmtInf>\n");             
                writer.write("\t\t\t</CdtTrfTxInf>\n");    
                writer.write("\t\t</PmtInf>\n");
            }
            writer.write("\t</CstmrCdtTrfInitn>\n");
            writer.write("</Mensaje>");
            writer.close();

            return 1;
        } catch(IOException e) {
            return 0;
        }	
    } */

}

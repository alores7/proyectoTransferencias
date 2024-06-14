package xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import clases.Cabecera;
import clases.Cabecera02;
import clases.Cuerpo;
import clases.Cuerpo02;

public class GeneradorXMLdesdeExcel {
	
	public static void convertirCSVaXML(String origen, String destino) {
        Cabecera cabecera = generarCabecera(origen);
        List<Cuerpo> cuerpos = leerCuerpos(origen);
        generarXML(cabecera, cuerpos, destino);
        System.out.println("Generado");
    }
	
	public static void convertirCSVaXML_cuaderno34(String origen, String destino, String nmOrdenante, 
			String nifOrd, String iban, String bic, String metodoPago, String fechaEj) {
        Cabecera02 cabecera = generarCabecera02(origen);
        List<Cuerpo02> cuerpos = leerCuerpos02(origen);
        generarXML_cuaderno34(cabecera, cuerpos, destino, nmOrdenante, nifOrd, iban, bic, metodoPago, fechaEj);
        System.out.println("Generado");
    }

    private static Cabecera generarCabecera(String origen) {
        Cabecera cabecera = new Cabecera();
        cabecera.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        cabecera.setHora(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        List<Cuerpo> cuerpos = leerCuerpos(origen);
        cabecera.setNumeroOperaciones(cuerpos.size());
        cabecera.setControlSuma(calcularControlSuma(cuerpos));
        return cabecera;
    }
    
    private static Cabecera02 generarCabecera02(String origen) {
        Cabecera02 cabecera = new Cabecera02();
        cabecera.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        cabecera.setHora(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        List<Cuerpo02> cuerpos = leerCuerpos02(origen);
        cabecera.setNumeroOperaciones(cuerpos.size());
        cabecera.setControlSuma(calcularControlSuma02(cuerpos));
        return cabecera;
    }
    
    private static List<Cuerpo02> leerCuerpos02(String origen) {
        List<Cuerpo02> cuerpos = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(origen), "UTF-8"))) {
        //	(BufferedReader br = new BufferedReader(new FileReader(origen)))
            cuerpos = br.lines().skip(1) // saltar la cabecera del CSV
                    .map(linea -> {
                        String[] datos = linea.split(";");
                        Cuerpo02 cuerpo = new Cuerpo02();
                        // Asignar datos del CSV a los campos de Cuerpo
                        // Suponiendo que los datos están en el mismo orden que los campos en la clase Cuerpo.java
                        cuerpo.setInsrtId(datos[0]);
                        cuerpo.setReferencia(datos[1]);
                        String importeStr = datos[2].replace(",", ".");
                        cuerpo.setImporte(Double.parseDouble(importeStr));
                        cuerpo.setBicBeneficiario(datos[3]);
                        cuerpo.setNombreBeneficiario(datos[4]);
                        cuerpo.setIbanBeneficiario(datos[5]);
                        cuerpo.setConcepto(datos[6]);
                        return cuerpo;
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cuerpos;
    }

    private static List<Cuerpo> leerCuerpos(String origen) {
        List<Cuerpo> cuerpos = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(origen), "UTF-8"))) {
        //	(BufferedReader br = new BufferedReader(new FileReader(origen)))
            cuerpos = br.lines().skip(1) // saltar la cabecera del CSV
                    .map(linea -> {
                        String[] datos = linea.split(";");
                        Cuerpo cuerpo = new Cuerpo();
                        // Asignar datos del CSV a los campos de Cuerpo
                        // Suponiendo que los datos están en el mismo orden que los campos en la clase Cuerpo.java
                        cuerpo.setIdentificadorPago(datos[0]);
                        cuerpo.setMedioPago(datos[1]);
                        cuerpo.setIndicadorApunteCuenta(datos[2]);
                        cuerpo.setInformacionTipoPago(datos[3]);
                        cuerpo.setFecha(datos[4]);
                        cuerpo.setNombreOrdenante(datos[5]);
                        cuerpo.setNifOrdenante(datos[6]);
                        cuerpo.setCuentaOrdenante(datos[7]);
                        cuerpo.setNombreBeneficiario(datos[8]);
                        cuerpo.setNifBeneficiario(datos[9]);
                        cuerpo.setCuentaBeneficiario(datos[10]);
                        String controlSumaStr = datos[11].replace(',', '.'); // Reemplazar comas por puntos
                        cuerpo.setControlSuma(Double.parseDouble(controlSumaStr));
                        return cuerpo;
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cuerpos;
    }

    private static Double calcularControlSuma(List<Cuerpo> cuerpos) {
        return cuerpos.stream().mapToDouble(Cuerpo::getControlSuma).sum();
    }
    
    private static Double calcularControlSuma02(List<Cuerpo02> cuerpos) {
        return cuerpos.stream().mapToDouble(Cuerpo02::getImporte).sum();
    }

    private static void generarXML(Cabecera cabecera, List<Cuerpo> cuerpos, String destino) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destino), "UTF-8"))) {
        //	(Writer writer = new FileWriter(destino))
        	writer.write("<mensaje>\n");
        	
            // CABECERA
            writer.write("<cabecera>\n");
            writer.write("\t<fecha>" + cabecera.getFecha() + "</fecha>\n");
            writer.write("\t<hora>" + cabecera.getHora() + "</hora>\n");
            writer.write("\t<numeroOperaciones>" + cabecera.getNumeroOperaciones() + "</numeroOperaciones>\n");
            String controlSumaFormateado = String.format("%.2f", cabecera.getControlSuma());
            writer.write("\t<controlSuma>" + controlSumaFormateado + "</controlSuma>\n");
            writer.write("</cabecera>\n");

            // CUERPO
            writer.write("<cuerpo>\n");
            for (Cuerpo cuerpo : cuerpos) {
                writer.write("\t<pago>\n");
                writer.write("\t\t<identificadorPago>" + cuerpo.getIdentificadorPago() + "</identificadorPago>\n");
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
                writer.write("\t\t<controlSuma>" + cuerpo.getControlSuma() + "</controlSuma>\n");
                writer.write("\t</pago>\n");
            }
            writer.write("</cuerpo>");
            
            writer.write("</mensaje>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void generarXML_cuaderno34(Cabecera02 cabecera, List<Cuerpo02> cuerpos, String destino,
    		String nmOrdenante, String nifOrd, String iban, String bic, String metodoPago, String fechaEj) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destino), "UTF-8"))) {
        //	(Writer writer = new FileWriter(destino))
        	Random random = new Random();
            int numeroAleatorio = random.nextInt(99999 - 1 + 1) + 1;
            String numeroFormateado = String.format("%05d", numeroAleatorio);
        	
        	writer.write("<CstmrCdtTrfInitn>\n");
        	
            // CABECERA
            writer.write("<GrpHdr>\n");
            writer.write("\t<MsgId>" + numeroFormateado + "</MsgId>\n");
            writer.write("\t<CreDtTm>" + cabecera.getFecha() + "T" + cabecera.getHora() + "</CreDtTm>\n");
            writer.write("\t<NbOfTxs>" + cabecera.getNumeroOperaciones() + "</NbOfTxs>\n");
            String controlSumaFormateado = String.format("%.2f", cabecera.getControlSuma());
            writer.write("\t<CtrlSum>" + controlSumaFormateado + "</CtrlSum>\n");
            writer.write("\t\t<InitgPty>\n");
            writer.write("\t\t\t<Nm>" + nmOrdenante + "</Nm>\n");
            writer.write("\t\t\t<Id>\n");
            writer.write("\t\t\t\t<OrgId>\n");
            writer.write("\t\t\t\t\t<Othr>\n");
            writer.write("\t\t\t\t\t\t<Id>" + nifOrd + "</Id>\n");
            writer.write("\t\t\t\t\t</Othr>\n");
            writer.write("\t\t\t\t</OrgId>\n");
            writer.write("\t\t\t</Id>\n");
            writer.write("\t\t</InitgPty>\n");
            writer.write("</GrpHdr>\n");

            // CUERPO
            writer.write("<PmtInf>\n");
            writer.write("\t\t<PmtInfId>SCT</PmtInfId>\n");
            writer.write("\t\t<PmtMtd>" + metodoPago + "</PmtMtd>\n");
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
            writer.write("\t\t\t<ReqdExctnDt>" + fechaEj + "</ReqdExctnDt>\n");
            writer.write("\t\t\t<Dbtr>\n");
            writer.write("\t\t\t\t<Nm>" + nmOrdenante + "</Nm>\n");
            writer.write("\t\t\t\t<Id>\n");
            writer.write("\t\t\t\t\t<OrgId>\n");
            writer.write("\t\t\t\t\t\t<Othr>\n");
            writer.write("\t\t\t\t\t\t\t<Id>" + nifOrd + "</Id>\n");                
            writer.write("\t\t\t\t\t\t</Othr>\n");
            writer.write("\t\t\t\t\t</OrgId>\n");
            writer.write("\t\t\t\t</Id>\n");
            writer.write("\t\t\t</Dbtr>\n");
            writer.write("\t\t\t<DbtrAcct>\n");
            writer.write("\t\t\t\t<Id>\n");
            writer.write("\t\t\t\t\t<IBAN>" + iban + "</IBAN>\n");
            writer.write("\t\t\t\t</Id>\n");
            writer.write("\t\t\t</DbtrAcct>\n");
            writer.write("\t\t\t<DbtrAgt>\n");
            writer.write("\t\t\t\t<FinInstnId>\n");
            writer.write("\t\t\t\t\t<BIC>" + bic + "</BIC>\n");
            writer.write("\t\t\t\t</FinInstnId>\n");                
            writer.write("\t\t\t</DbtrAgt>\n");
            for (Cuerpo02 cuerpo : cuerpos) {
                writer.write("\t\t\t<CdtTrfTxInf>\n");
                writer.write("\t\t\t\t<PmtId>\n");
                writer.write("\t\t\t\t\t<InstrId>" + cuerpo.getInsrtId() + "</InstrId>\n");
                writer.write("\t\t\t\t\t<EndToEndId>" + cuerpo.getReferencia() + "</EndToEndId>\n");
                writer.write("\t\t\t\t</PmtId>\n");
                writer.write("\t\t\t\t<Amt>\n");
                writer.write("\t\t\t\t\t<InstdAmt Ccy=\"EUR\">" + cuerpo.getImporte() + "</InstdAmt>\n");
                writer.write("\t\t\t\t</Amt>\n");
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
                writer.write("\t\t\t\t<Purp>\n");
                writer.write("\t\t\t\t\t<Cd>CASH</Cd>\n");
                writer.write("\t\t\t\t</Purp>\n");
                writer.write("\t\t\t\t<RmtInf>\n");
                writer.write("\t\t\t\t\t<Ustrd>" + cuerpo.getConcepto() + "</Ustrd>\n");
                writer.write("\t\t\t\t</RmtInf>\n");
                writer.write("\t\t\t</CdtTrfTxInf>\n");
            }
            writer.write("</PmtInf>");
            
            writer.write("</CstmrCdtTrfInitn>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

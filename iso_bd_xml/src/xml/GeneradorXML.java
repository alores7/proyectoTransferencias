package xml;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import clases.Cabecera;
import clases.Cuerpo;

public class GeneradorXML {
    
    public void generarXML(Cabecera cabecera, List<Cuerpo> cuerpos) throws IOException {
    /*    FileWriter writer = new FileWriter("mensaje.xml");
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
            writer.write("\t\t\t<fechaEjecucionSolicitada>" + cuerpo.getFechaEjecucionSolicitada() + "</fechaEjecucionSolicitada>\n");
            writer.write("\t\t\t<empresaOrdenante>" + cuerpo.getEmpresaOrdenante() + "</empresaOrdenante>\n");
            writer.write("\t\t</pago>\n");
        }
        writer.write("\t</cuerpo>\n");
        writer.write("</mensaje>");
        writer.close(); */
    }
}

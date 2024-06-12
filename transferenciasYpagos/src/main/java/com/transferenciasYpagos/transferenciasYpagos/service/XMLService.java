package com.transferenciasYpagos.transferenciasYpagos.service;

import com.transferenciasYpagos.transferenciasYpagos.models.PagoISO20022;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class XMLService {

    @Autowired
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(XMLService.class.getName());

    @Transactional
    public void exportDataToXML(String filePath, String idMensaje) {
        logger.info("Iniciando exportación de datos a XML");

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Crear el elemento raíz
            Element mensajeElement = document.createElement("mensaje");
            document.appendChild(mensajeElement);

            // Crear la cabecera
            Element cabeceraElement = document.createElement("cabecera");
            mensajeElement.appendChild(cabeceraElement);

            // Obtener la fecha y hora actual
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String fechaActual = dateFormat.format(currentDate);
            String horaActual = timeFormat.format(currentDate);

            // Contador para el número de pagos y suma de controlSuma
            int numeroOperaciones = 0;
            double sumaControlSuma = 0.0;

            // Consulta para obtener los datos de la tabla
            Query query = entityManager.createQuery("SELECT p FROM PagoISO20022 p");
            List<PagoISO20022> resultList = query.getResultList();

            for (PagoISO20022 pago : resultList) {
                // Crear elemento pago
                Element pagoElement = document.createElement("pago");
                mensajeElement.appendChild(pagoElement);

                // Agregar datos del pago
                agregarElemento(document, pagoElement, "identificadorPago", pago.getIdentificadorPago());
                agregarElemento(document, pagoElement, "medioPago", pago.getMedioPago());
                agregarElemento(document, pagoElement, "indicadorApunteCuenta", pago.getIndicadorApunteCuenta());
                agregarElemento(document, pagoElement, "informacionTipoPago", pago.getInformacionTipoPago());
                agregarElemento(document, pagoElement, "fecha", pago.getFecha());
                agregarElemento(document, pagoElement, "nombreOrdenante", pago.getNombreOrdenante());
                agregarElemento(document, pagoElement, "nifOrdenante", pago.getNifOrdenante());
                agregarElemento(document, pagoElement, "cuentaOrdenante", pago.getCuentaOrdenante());
                agregarElemento(document, pagoElement, "nombreBeneficiario", pago.getNombreBeneficiario());
                agregarElemento(document, pagoElement, "nifBeneficiario", pago.getNifBeneficiario());
                agregarElemento(document, pagoElement, "cuentaBeneficiario", pago.getCuentaBeneficiario());
                agregarElemento(document, pagoElement, "controlSuma", String.valueOf(pago.getControlSuma()));

                // Incrementar el contador y sumar controlSuma
                numeroOperaciones++;
                sumaControlSuma += pago.getControlSuma();
            }

            // Agregar datos a la cabecera
            agregarElemento(document, cabeceraElement, "idMensaje", idMensaje);
            agregarElemento(document, cabeceraElement, "fecha", fechaActual);
            agregarElemento(document, cabeceraElement, "hora", horaActual);
            agregarElemento(document, cabeceraElement, "numeroOperaciones", String.valueOf(numeroOperaciones));
            agregarElemento(document, cabeceraElement, "controlSuma", String.valueOf(sumaControlSuma));

            // Escribir el contenido XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            logger.info("Datos exportados exitosamente a " + filePath);
        } catch (ParserConfigurationException | TransformerException ex) {
            logger.severe("Error al escribir en el archivo XML: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            logger.severe("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exportToXMLfromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int numeroOperaciones = 0;
            double controlSumaTotal = 0.0;

            // Inicializamos el documento XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Creamos los elementos principales del XML
            Element mensajeElement = doc.createElement("mensaje");
            doc.appendChild(mensajeElement);

            Element cabeceraElement = doc.createElement("cabecera");
            mensajeElement.appendChild(cabeceraElement);

            Element cuerpoElement = doc.createElement("cuerpo");
            mensajeElement.appendChild(cuerpoElement);

            // Cabecera
            Element idMensajeElement = doc.createElement("idMensaje");
            idMensajeElement.appendChild(doc.createTextNode("1"));
            cabeceraElement.appendChild(idMensajeElement);

            Element fechaElement = doc.createElement("fecha");
            fechaElement.appendChild(doc.createTextNode(LocalDate.now().toString()));
            cabeceraElement.appendChild(fechaElement);

            Element horaElement = doc.createElement("hora");
            horaElement.appendChild(doc.createTextNode(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));
            cabeceraElement.appendChild(horaElement);

            // Procesamos cada línea del CSV
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length == 12) {
                    Element pagoElement = doc.createElement("pago");
                    cuerpoElement.appendChild(pagoElement);

                    agregarElemento(doc, pagoElement, "identificadorPago", fields[0]);
                    agregarElemento(doc, pagoElement, "nombre", fields[1]);
                    agregarElemento(doc, pagoElement, "direccion", fields[2]);
                    agregarElemento(doc, pagoElement, "cp", fields[3]);
                    agregarElemento(doc, pagoElement, "poblacion", fields[4]);
                    agregarElemento(doc, pagoElement, "provincia", fields[5]);
                    agregarElemento(doc, pagoElement, "nif", fields[6]);
                    agregarElemento(doc, pagoElement, "ccc", fields[7]);
                    agregarElemento(doc, pagoElement, "iban", fields[8]);
                    agregarElemento(doc, pagoElement, "bic", fields[9]);
                    agregarElemento(doc, pagoElement, "controlSuma", fields[10]);

                    numeroOperaciones++;
                    controlSumaTotal += Double.parseDouble(fields[10].replace(",", "."));
                }
            }

            Element numeroOperacionesElement = doc.createElement("numeroOperaciones");
            numeroOperacionesElement.appendChild(doc.createTextNode(String.valueOf(numeroOperaciones)));
            cabeceraElement.appendChild(numeroOperacionesElement);

            Element controlSumaElement = doc.createElement("controlSuma");
            controlSumaElement.appendChild(doc.createTextNode(String.format("%.2f", controlSumaTotal)));
            cabeceraElement.appendChild(controlSumaElement);

            // Guardamos el documento XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter("output.xml"));
            transformer.transform(source, result);

            System.out.println("Archivo XML creado exitosamente.");

        } catch (IOException | ParserConfigurationException | javax.xml.transform.TransformerException e) {
            e.printStackTrace();
        }
    }

    private void agregarElemento(Document document, Element parentElement, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.appendChild(document.createTextNode(textContent));
        parentElement.appendChild(element);
    }
}

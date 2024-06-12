package com.transferenciasYpagos.transferenciasYpagos.service;

import com.opencsv.*;
import com.transferenciasYpagos.transferenciasYpagos.models.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class CsvService {

    @Autowired
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(CsvService.class.getName());

    @Transactional
    public void exportDataToCSV(String filePath) {
        logger.info("Iniciando exportación de datos a CSV");

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath), ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            // Consulta para obtener los datos de la tabla
            Query query = entityManager.createQuery("SELECT e.pagos, e.normal, e.cifOrdenante, e.numero, e.fechaOperacion, e.ibanOrdenante, e.concepto, e.nombreOrdenante, e.ibanBeneficiario, e.importe, e.nombreBeneficiario, e.gastos FROM Pago e");
            List<Object[]> resultList = query.getResultList();

            logger.info("Número de registros obtenidos: " + resultList.size());

            // Escribir los datos en el archivo CSV
            for (Object[] row : resultList) {
                String[] rowData = new String[row.length];
                for (int i = 0; i < row.length; i++) {
                    rowData[i] = row[i] != null ? row[i].toString() : "";
                }
                writer.writeNext(rowData);
            }

            logger.info("Datos exportados exitosamente a " + filePath);
        } catch (IOException ex) {
            logger.severe("Error al escribir en el archivo CSV: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            logger.severe("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void importDataFromCSV(String filePath) {
        logger.info("Iniciando importación de datos desde CSV");

        try {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .build();

            CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                    .withCSVParser(parser)
                    .build();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Pago pago = new Pago();
                pago.setId(generateId());
                pago.setPagos(nextLine[0]);
                pago.setNormal(nextLine[1]);
                pago.setCifOrdenante(nextLine[2]);
                pago.setNumero(nextLine[3]);
                pago.setFechaOperacion(nextLine[4]);
                pago.setIbanOrdenante(nextLine[5]);
                pago.setConcepto(nextLine[6]);
                pago.setNombreOrdenante(nextLine[7]);
                pago.setIbanBeneficiario(nextLine[8]);
                pago.setImporte(Double.parseDouble(nextLine[9]));
                pago.setNombreBeneficiario(nextLine[10]);
                pago.setGastos(nextLine[11]);

                entityManager.persist(pago);
            }

            logger.info("Datos importados exitosamente desde " + filePath);
        } catch (IOException ex) {
            logger.severe("Error al leer el archivo CSV: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            logger.severe("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String generateId() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int minLength = 3;
        int maxLength = 5;
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }
        return sb.toString();
    }
}

package com.transferenciasYpagos.transferenciasYpagos.service;

import com.opencsv.CSVWriter;
import com.transferenciasYpagos.transferenciasYpagos.models.Pago;
import com.transferenciasYpagos.transferenciasYpagos.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public void exportPagosToCSV(String filePath) throws IOException {
        List<Pago> pagos = pagoRepository.findAll();
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Write the header
            String[] header = {"ID", "Pagos", "Normal", "CIF Ordenante", "Numero", "Fecha Operacion", "IBAN Ordenante", "Concepto", "Nombre Ordenante", "IBAN Beneficiario", "Importe", "Nombre Beneficiario", "Gastos"};
            writer.writeNext(header);

            // Write the data
            for (Pago pago : pagos) {
                String[] data = {
                        String.valueOf(pago.getId()),
                        pago.getPagos(),
                        pago.getNormal(),
                        pago.getCifOrdenante(),
                        pago.getNumero(),
                        pago.getFechaOperacion(),
                        pago.getIbanOrdenante(),
                        pago.getConcepto(),
                        pago.getNombreOrdenante(),
                        pago.getIbanBeneficiario(),
                        String.valueOf(pago.getImporte()),
                        pago.getNombreBeneficiario(),
                        pago.getGastos()
                };
                writer.writeNext(data);
            }
        }
    }

}

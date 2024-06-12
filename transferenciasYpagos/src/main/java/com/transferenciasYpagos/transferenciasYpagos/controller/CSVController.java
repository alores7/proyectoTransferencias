package com.transferenciasYpagos.transferenciasYpagos.controller;

import com.transferenciasYpagos.transferenciasYpagos.service.CsvService;
import com.transferenciasYpagos.transferenciasYpagos.service.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class CSVController {

    @Autowired
    private CsvService csvService;
    @Autowired
    private XMLService xmlService;

    @GetMapping({"","/"})
    public String index() {
        return "index";
    }

    @GetMapping("/c34_14_export")
    public String exportCSVPage() {
        return "export";
    }

    @GetMapping("/c34_14_import")
    public String importCSVPage() {
        return "import";
    }

    @GetMapping("/genXML")
    public String genXMLPage() {
        return "genXMLfromBD";
    }

    @GetMapping("/genXMLdesdeCSV")
    public String genXMLdesdeCSVPage() {
        return "genXMLfromCSV";
    }

    @GetMapping("/c34_14_export/export")
    public ResponseEntity<String> exportCSV(@RequestParam("saveLocation") String saveLocation) {
        try {
            // Construir la ruta completa del archivo CSV
            String filePath = Paths.get(saveLocation).toAbsolutePath().toString();
            csvService.exportDataToCSV(filePath);
            return ResponseEntity.ok("Archivo CSV creado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el archivo CSV.");
        }
    }

    @GetMapping("/genXML/generar")
    public ResponseEntity<String> generateXMLFromDatabase(@RequestParam("saveLocation") String saveLocation) {
        try {
            String idMensaje = "1";
            String filePath = Paths.get(saveLocation).toAbsolutePath().toString();
            xmlService.exportDataToXML(filePath, idMensaje);
            return ResponseEntity.ok("Archivo XML creado exitosamente desde la base de datos.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el archivo XML desde la base de datos.");
        }
    }

    @GetMapping("/genXMLdesdeCSV/generar")
    public ResponseEntity<String> generateXMLFromCSV(@RequestParam("saveLocation") String saveLocation) {
        try {
            String filePath = Paths.get(saveLocation).toAbsolutePath().toString();
            xmlService.exportToXMLfromCSV(saveLocation);
            return ResponseEntity.ok("Archivo XML creado exitosamente a partir del archivo CSV.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el archivo XML desde el archivo CSV.");
        }
    }

    @PostMapping("/c34_14_import/import")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Archivo vacío.");
            }

            // Definir la ruta donde se guardará el archivo
            String tempDirectory = "C:/path/to/temp/";
            File directory = new File(tempDirectory);

            // Crear la ruta si no existe
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Guardar el archivo temporalmente
            String filePath = tempDirectory + file.getOriginalFilename();
            File destFile = new File(filePath);
            file.transferTo(destFile);

            // Importar los datos del archivo CSV
            csvService.importDataFromCSV(filePath);

            return ResponseEntity.ok("Datos importados exitosamente desde el archivo CSV.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el archivo.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al importar los datos desde el archivo CSV.");
        }
    }
}

package com.htmltopdf.HTMLtoPDF;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.nio.file.Files;

@Controller
public class PdfController {

    private final PdfGenerator pdfGenerator;

    @Autowired
    public PdfController(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping("/convert")
    public void convertHtmlToPdf(HttpServletResponse response) throws Exception {
        String htmlFilePath = "templates/input.html";
        String outputPath = "OutputPdf.pdf"; // Output file path

        pdfGenerator.convertHtmlToPdf(htmlFilePath, outputPath);

        File pdfFile = new File(outputPath);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFile.getName());
        response.setContentLength((int) pdfFile.length());

        Files.copy(pdfFile.toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    }
}
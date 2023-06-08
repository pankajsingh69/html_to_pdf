package com.htmltopdf.HTMLtoPDF;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class PdfGenerator {

    public void convertHtmlToPdf(String htmlFilePath, String outputPath) throws Exception {
        try (InputStream input = new ClassPathResource(htmlFilePath).getInputStream();
             OutputStream output = new FileOutputStream(outputPath)) {

            File htmlFile = File.createTempFile("temp", ".html");
            FileUtils.copyInputStreamToFile(input, htmlFile);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(htmlFile);
            renderer.layout();
            renderer.createPDF(output);
            renderer.finishPDF();

            htmlFile.delete();
        }
    }
}

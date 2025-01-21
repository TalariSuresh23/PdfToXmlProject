package com.example.pdfconverter;

import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfXml {

    public static void main(String[] args) {
        String inputFilePath = "C:\\PdfToXml\\input.pdf"; // Corrected file path
        String outputFilePath = "C:\\XmlToPdf\\output.xml";

        try {
            File pdfFile = new File(inputFilePath);
            if (!pdfFile.exists()) {
                throw new FileNotFoundException("Input PDF file not found: " + inputFilePath);
            }

            PDDocument document = PDDocument.load(pdfFile);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(document);

            String xmlContent = convertToXML(pdfText);

            File outputFile = new File(outputFilePath);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write(xmlContent);
            }

            System.out.println("PDF converted to XML successfully. Output saved at: " + outputFilePath);

            document.close();
        } catch (Exception e) {
            System.err.println("Error occurred while converting PDF to XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String convertToXML(String textContent) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<document>\n");
        xmlBuilder.append("  <content>\n");
        xmlBuilder.append("    ").append(escapeXML(textContent)).append("\n");
        xmlBuilder.append("  </content>\n");
        xmlBuilder.append("</document>");
        return xmlBuilder.toString();
    }

    private static String escapeXML(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}

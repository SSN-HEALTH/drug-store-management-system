package com.ssnhealthcare.drugstore.report.service.implementation;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class ReportFileGenerator {

    private static final String PROJECT_TITLE = "SSN Healthcare Drug Store Management";

    public static byte[] generatePdf(String title, List<String> headers, List<List<String>> rows,
                                     LocalDate fromDate, LocalDate toDate) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdf);

        // Project title
        document.add(new Paragraph(PROJECT_TITLE).setBold().setFontSize(16));
        document.add(new Paragraph("From: " + fromDate + "   To: " + toDate).setItalic());
        document.add(new Paragraph("\n"));

        // Report title
        document.add(new Paragraph(title).setBold());

        Table table = new Table(UnitValue.createPercentArray(headers.size()));
        headers.forEach(h -> table.addHeaderCell(new Cell().add(new Paragraph(h))));
        for (List<String> row : rows) {
            for (String cellValue : row) {
                table.addCell(new Cell().add(new Paragraph(cellValue)));
            }
        }

        document.add(table);
        document.add(new Paragraph("\n"));
        document.close();
        return baos.toByteArray();
    }

    public static byte[] generateCsv(String title, List<String> headers, List<List<String>> rows,
                                     LocalDate fromDate, LocalDate toDate) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
        CSVWriter writer = new CSVWriter(osw);

        // Project title and date range
        writer.writeNext(new String[]{"--- " + PROJECT_TITLE + " ---"});
        writer.writeNext(new String[]{"From: " + fromDate + "   To: " + toDate});
        writer.writeNext(new String[]{""});

        writer.writeNext(new String[]{"--- " + title + " ---"});
        writer.writeNext(headers.toArray(new String[0]));
        for (List<String> row : rows) writer.writeNext(row.toArray(new String[0]));
        writer.writeNext(new String[]{""});
        writer.close();
        return baos.toByteArray();
    }

    public static byte[] generateTxt(String title, List<String> headers, List<List<String>> rows,
                                     LocalDate fromDate, LocalDate toDate) {
        StringBuilder sb = new StringBuilder();

        // Project title and date range
        sb.append("=== ").append(PROJECT_TITLE).append(" ===\n");
        sb.append("From: ").append(fromDate).append("   To: ").append(toDate).append("\n\n");

        sb.append("=== ").append(title).append(" ===\n");
        for (String h : headers) sb.append(h).append(" | ");
        sb.setLength(sb.length() - 3);
        sb.append("\n").append("-".repeat(sb.length())).append("\n");
        for (List<String> row : rows) {
            for (String cell : row) sb.append(cell).append(" | ");
            sb.setLength(sb.length() - 3);
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}

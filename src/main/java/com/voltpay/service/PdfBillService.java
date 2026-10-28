package com.voltpay.service;

import com.voltpay.model.Bill;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfBillService {
    public byte[] createPdf(Bill b) {
        try (PDDocument document = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
                var title = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                var font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                cs.beginText(); cs.setFont(title, 20); cs.newLineAtOffset(60, 750);
                cs.showText("VoltPay - Electricity Bill"); cs.endText();
                List<String> lines = List.of(
                    "ELECTRICITY BILL STATEMENT",
                    "Bill ID: " + b.getId(),
                    "Customer ID: " + b.getCustomer().getCustomerCode(),
                    "Customer name: " + safe(b.getCustomer().getName()),
                    "Mobile: " + safe(b.getCustomer().getMobile()),
                    "Address: " + safe(b.getCustomer().getAddress()),
                    "Meter number: " + safe(b.getCustomer().getMeterNumber()),
                    "Connection type: " + b.getCustomer().getCategory(),
                    "Billing period: " + b.getBillingPeriod(),
                    "Previous reading: " + b.getPreviousReading() + " kWh",
                    "Current reading: " + b.getCurrentReading() + " kWh",
                    "Units consumed: " + b.getUnits() + " kWh",
                    String.format(java.util.Locale.US, "Rate per unit: Rs. %.2f", b.getRate()),
                    String.format(java.util.Locale.US, "Energy charges: Rs. %.2f", b.getEnergyCharge()),
                    String.format(java.util.Locale.US, "Fixed charge: Rs. %.2f", b.getFixedCharge()),
                    String.format(java.util.Locale.US, "TOTAL AMOUNT: Rs. %.2f", b.getTotalAmount()),
                    "Due date: " + b.getDueDate(),
                    "Payment status: " + b.getStatus(),
                    "",
                    "Demo project statement. Not an official utility invoice."
                );
                float y = 710;
                for (int i = 0; i < lines.size(); i++) {
                    cs.beginText(); cs.setFont(i == 0 ? title : font, i == 0 ? 13 : 11);
                    cs.newLineAtOffset(60, y); cs.showText(lines.get(i)); cs.endText();
                    y -= i == 0 ? 28 : 23;
                }
            }
            document.save(out);
            return out.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not generate PDF bill.", ex);
        }
    }
    private String safe(String value) {
        if (value == null) return "-";
        return value.replaceAll("[^\\\\x20-\\\\x7E]", "?");
    }
}

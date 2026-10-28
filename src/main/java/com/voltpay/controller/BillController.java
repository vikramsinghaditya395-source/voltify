package com.voltpay.controller;

import com.voltpay.dto.CreateBillRequest;
import com.voltpay.model.Bill;
import com.voltpay.service.BillService;
import com.voltpay.service.PdfBillService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BillController {
    private final BillService bills;
    private final PdfBillService pdfs;
    public BillController(BillService bills, PdfBillService pdfs) { this.bills = bills; this.pdfs = pdfs; }

    @PostMapping("/bills") public Bill create(@Valid @RequestBody CreateBillRequest request) { return bills.create(request); }
    @GetMapping("/customers/{code}/bills") public List<Bill> customerBills(@PathVariable String code) { return bills.forCustomer(code); }
    @GetMapping("/bills/{id}") public Bill get(@PathVariable Long id) { return bills.get(id); }
    @PostMapping("/bills/{id}/pay") public Bill pay(@PathVariable Long id) { return bills.pay(id); }

    @GetMapping(value="/bills/{id}/pdf", produces="application/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        Bill bill = bills.get(id);
        byte[] pdf = pdfs.createPdf(bill);
        String filename = "VoltPay_Bill_" + bill.getCustomer().getCustomerCode() + "_" + bill.getBillingPeriod() + ".pdf";
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(filename).build().toString())
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }
}

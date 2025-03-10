package com.gereach.ledger.controller;

import com.gereach.ledger.bean.po.Invoice;
import com.gereach.ledger.bean.web.InvoiceRequest;
import com.gereach.ledger.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * 新增发票记录
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')") // 仅允许普通用户操作
    public ResponseEntity<?> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        Invoice invoice = invoiceService.createInvoice(request);
        return ResponseEntity.ok(invoice); // 返回完整发票信息（按需简化字段）
    }
}

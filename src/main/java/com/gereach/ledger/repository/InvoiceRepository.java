package com.gereach.ledger.repository;

import com.gereach.ledger.bean.po.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // 检查发票号是否存在（可选）
    boolean existsByInvoiceNumber(String invoiceNumber);
}

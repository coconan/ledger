package com.gereach.ledger.service;

import com.gereach.ledger.bean.po.Invoice;
import com.gereach.ledger.bean.po.User;
import com.gereach.ledger.bean.web.InvoiceRequest;
import com.gereach.ledger.exception.DuplicateInvoiceNumberException;
import com.gereach.ledger.exception.UserNotFoundException;
import com.gereach.ledger.repository.InvoiceRepository;
import com.gereach.ledger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 创建发票记录（需用户登录）
     */
    @Transactional
    public Invoice createInvoice(InvoiceRequest request) {
        // 1. 获取当前登录用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("用户不存在"));

        // 2. 检查发票号是否重复（可选）
        if (invoiceRepository.existsByInvoiceNumber(request.getInvoiceNumber())) {
            throw new DuplicateInvoiceNumberException("发票号已存在");
        }

        // 3. 构建并保存发票
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setAmount(request.getAmount());
        invoice.setCreator(creator);

        return invoiceRepository.save(invoice);
    }
}

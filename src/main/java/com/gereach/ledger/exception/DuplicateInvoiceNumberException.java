package com.gereach.ledger.exception;

public class DuplicateInvoiceNumberException extends RuntimeException {
    public DuplicateInvoiceNumberException(String message) {
        super(message);
    }
}

package com.gereach.ledger.bean.web;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceRequest {

    @NotBlank(message = "发票号不能为空")
    @Size(max = 50, message = "发票号长度不能超过50个字符")
    private String invoiceNumber;

    @NotNull(message = "发票日期不能为空")
    private LocalDate invoiceDate;

    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.01", message = "金额必须大于0")
    private BigDecimal amount;

    // 其他可选字段（根据业务需求扩展）
    // private String description;
}

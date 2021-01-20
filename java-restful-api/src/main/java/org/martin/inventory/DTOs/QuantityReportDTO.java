package org.martin.inventory.DTOs;

import java.util.Date;

public class QuantityReportDTO {

    private Date date;
    private long quantity;

    public QuantityReportDTO(Date date, long quantity) {
        this.date = date;
        this.quantity = quantity;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public long getQuantity() { return quantity; }

    public void setQuantity(long quantity) { this.quantity = quantity; }
}

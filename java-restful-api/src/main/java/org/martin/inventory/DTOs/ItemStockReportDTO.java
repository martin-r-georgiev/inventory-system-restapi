package org.martin.inventory.DTOs;

public class ItemStockReportDTO {
    private long itemsInStock;
    private long itemsOutOfStock;

    public ItemStockReportDTO(long itemsInStock, long itemsOutOfStock) {
        this.itemsInStock = itemsInStock;
        this.itemsOutOfStock = itemsOutOfStock;
    }

    public long getItemsInStock() { return itemsInStock; }

    public void setItemsInStock(int itemsInStock) { this.itemsInStock = itemsInStock; }

    public long getItemsOutOfStock() { return itemsOutOfStock; }

    public void setItemsOutOfStock(int itemsOutOfStock) { this.itemsOutOfStock = itemsOutOfStock; }
}

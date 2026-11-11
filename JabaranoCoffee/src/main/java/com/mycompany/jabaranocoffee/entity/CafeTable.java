package com.mycompany.jabaranocoffee.entity;

/**
 * Merepresentasikan data meja di Jabarano Coffee.
 *
 * @author rangga
 */
public class CafeTable {

    // ----------------------------------------------------------------
    // Atribut
    // ----------------------------------------------------------------
    private int    id;
    private String tableNumber;
    private int    capacity;
    private String status;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public CafeTable() {}

    public CafeTable(int id, String tableNumber, int capacity, String status) {
        this.id          = id;
        this.tableNumber = tableNumber;
        this.capacity    = capacity;
        this.status      = status;
    }

    // ----------------------------------------------------------------
    // Getter
    // ----------------------------------------------------------------
    public int    getId()          { return id; }
    public String getTableNumber() { return tableNumber; }
    public int    getCapacity()    { return capacity; }
    public String getStatus()      { return status; }

    // ----------------------------------------------------------------
    // Setter
    // ----------------------------------------------------------------
    public void setId(int id)                      { this.id = id; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }
    public void setCapacity(int capacity)          { this.capacity = capacity; }
    public void setStatus(String status)           { this.status = status; }

    // ----------------------------------------------------------------
    // Output
    // ----------------------------------------------------------------
    @Override
    public String toString() {
        return tableNumber + " (Kapasitas: " + capacity + ", Status: " + status + ")";
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jabaranocoffee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author kayla
 */

/*
 * Merepresentasikan satu entri antrean pelanggan Jabarano Coffee.
 */
public class QueueEntry {

    // ----------------------------------------------------------------
    // Atribut
    // ----------------------------------------------------------------
    private int           id;
    private String        queueNumber;
    private String        accessCode;
    private String        customerName;
    private int           partySize;
    private Integer       tableId;        // NULL selama WAITING
    private String        status;
    private LocalDateTime joinedAt;
    private LocalDateTime calledAt;       // NULL selama WAITING
    private LocalDate     queueDate;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public QueueEntry() {}

    public QueueEntry(int id, String queueNumber, String accessCode,
                      String customerName, int partySize, Integer tableId,
                      String status, LocalDateTime joinedAt,
                      LocalDateTime calledAt, LocalDate queueDate) {
        this.id           = id;
        this.queueNumber  = queueNumber;
        this.accessCode   = accessCode;
        this.customerName = customerName;
        this.partySize    = partySize;
        this.tableId      = tableId;
        this.status       = status;
        this.joinedAt     = joinedAt;
        this.calledAt     = calledAt;
        this.queueDate    = queueDate;
    }

    // ----------------------------------------------------------------
    // Getter
    // ----------------------------------------------------------------
    public int getId()                   { return id; }
    public String getQueueNumber()       { return queueNumber; }
    public String getAccessCode()        { return accessCode; }
    public String getCustomerName()      { return customerName; }
    public int getPartySize()            { return partySize; }
    public Integer getTableId()          { return tableId; }
    public String getStatus()            { return status; }
    public LocalDateTime getJoinedAt()   { return joinedAt; }
    public LocalDateTime getCalledAt()   { return calledAt; }
    public LocalDate getQueueDate()      { return queueDate; }

    // ----------------------------------------------------------------
    // Setter
    // ----------------------------------------------------------------
    public void setId(int id)                            { this.id = id; }
    public void setQueueNumber(String queueNumber)        { this.queueNumber = queueNumber; }
    public void setAccessCode(String accessCode)          { this.accessCode = accessCode; }
    public void setCustomerName(String customerName)      { this.customerName = customerName; }
    public void setPartySize(int partySize)               { this.partySize = partySize; }
    public void setTableId(Integer tableId)               { this.tableId = tableId; }
    public void setStatus(String status)                  { this.status = status; }
    public void setJoinedAt(LocalDateTime joinedAt)       { this.joinedAt = joinedAt; }
    public void setCalledAt(LocalDateTime calledAt)       { this.calledAt = calledAt; }
    public void setQueueDate(LocalDate queueDate)         { this.queueDate = queueDate; }

    // ----------------------------------------------------------------
    // Output
    // ----------------------------------------------------------------
    @Override
    public String toString() {
        return "QueueEntry{id=" + id +
               ", queueNumber='" + queueNumber + "'" +
               ", customer='" + customerName + "'" +
               ", status='" + status + "'}";
    }
}
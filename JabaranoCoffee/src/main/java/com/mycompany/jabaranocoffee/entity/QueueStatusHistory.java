/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jabaranocoffee.entity;

import java.time.LocalDateTime;

/**
 *
 * @author Shafira Nailah
 */
public class QueueStatusHistory {
    private int id;
    private int queueId;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime changedAt;
    
    public QueueStatusHistory(){}
    
    public QueueStatusHistory(int id, int queueId, String oldStatus,
                            String newstatus, LocalDateTime changeAt){
        this.id = id;
        this.queueId = queueId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
    }
    
    public int getId(){
        return id;
    }
    
    public int getQueueId(){
        return queueId;
    }
    
    public String getOldStatus(){
        return oldStatus;
    }
    
    public String getNewStatus(){
        return newStatus;
    }
    
    public LocalDateTime getChangedAt(){
        return changedAt;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
    
    @Override
    public String toString(){
        return "QueueStatusHistory{queueId=" + queueId +
                "," + oldStatus + "->" + newStatus + ", changedAt=" + changedAt + "}"; 
    }  
}

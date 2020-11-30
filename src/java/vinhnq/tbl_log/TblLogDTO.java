/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_log;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class TblLogDTO implements Serializable {
    private int logId;
    private String userId;
    private String productId;
    private Timestamp date;

    public TblLogDTO() {
    }

    public TblLogDTO(int logId, String userId, String productId, Timestamp date) {
        this.logId = logId;
        this.userId = userId;
        this.productId = productId;
        this.date = date;
    }
    
    
    /**
     * @return the logId
     */
    public int getLogId() {
        return logId;
    }

    /**
     * @param logId the logId to set
     */
    public void setLogId(int logId) {
        this.logId = logId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_product_status;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class TblProductStatusDTO implements Serializable {
    private String statusId;
    private String statusName;

    public TblProductStatusDTO() {
    }

    public TblProductStatusDTO(String statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    
    /**
     * @return the statusId
     */
    public String getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    
}

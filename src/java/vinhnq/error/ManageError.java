/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.error;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class ManageError implements Serializable {
    private String createExpiredErr;
    private String duplicateIdErr;

    /**
     * @return the CreateExpiredErr
     */
    public String getCreateExpiredErr() {
        return createExpiredErr;
    }

    /**
     * @param createExpiredErr the CreateExpiredErr to set
     */
    public void setCreateExpiredErr(String createExpiredErr) {
        this.createExpiredErr = createExpiredErr;
    }

    /**
     * @return the duplicateIdErr
     */
    public String getDuplicateIdErr() {
        return duplicateIdErr;
    }

    /**
     * @param duplicateIdErr the duplicateIdErr to set
     */
    public void setDuplicateIdErr(String duplicateIdErr) {
        this.duplicateIdErr = duplicateIdErr;
    }
    
    
}

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
public class SearchPriceError implements Serializable {
    private String upperIsLessThanLowerErr;

    /**
     * @return the UpperIsLessThanLowerErr
     */
    public String getUpperIsLessThanLowerErr() {
        return upperIsLessThanLowerErr;
    }

    /**
     * @param UpperIsLessThanLowerErr the UpperIsLessThanLowerErr to set
     */
    public void setUpperIsLessThanLowerErr(String UpperIsLessThanLowerErr) {
        this.upperIsLessThanLowerErr = UpperIsLessThanLowerErr;
    }
}

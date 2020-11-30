/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class QuantityError implements Serializable {
    private List<String> listOfErr = new ArrayList<>();

    /**
     * @return the listOfErr
     */
    public List<String> getListOfErr() {
        return listOfErr;
    }

    /**
     * @param listOfErr the listOfErr to set
     */
    public void setListOfErr(List<String> listOfErr) {
        this.listOfErr = listOfErr;
    }
    
    
}

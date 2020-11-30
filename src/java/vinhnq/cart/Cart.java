/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import vinhnq.tbl_product.TblProductDTO;

/**
 *
 * @author Admin
 */
public class Cart implements Serializable {
    private Map<TblProductDTO, Integer> compartment;

    /**
     * @return the compartment
     */
    public Map<TblProductDTO, Integer> getCompartment() {
        return compartment;
    }

    /**
     * @param compartment the compartment to set
     */
    public void setCompartment(Map<TblProductDTO, Integer> compartment) {
        this.compartment = compartment;
    }
    
    public void addNewProduct(TblProductDTO productDTO) {
        if (compartment == null) {
            compartment = new HashMap<>();
        }
        
        int quantity = 1;
        if (compartment.containsKey(productDTO)) {
            quantity = compartment.get(productDTO) + 1;           
        }
        
        compartment.put(productDTO, quantity);
    }
    
    public void removeProduct(TblProductDTO productDTO) {
        if (compartment != null) {
            compartment.remove(productDTO);
        }
        
        if (compartment.isEmpty()) {
            compartment = null;
        }
    }
    
    public double getTotalPrice() {
        double total = 0;
        for (TblProductDTO productDTO : compartment.keySet()) {
            total += productDTO.getPrice() * compartment.get(productDTO);
        }
        return total;
    }
}

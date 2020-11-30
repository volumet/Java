/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_order_detail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import vinhnq.cart.Cart;
import vinhnq.helpers.DBHelpers;
import vinhnq.tbl_product.TblProductDAO;
import vinhnq.tbl_product.TblProductDTO;

/**
 *
 * @author Admin
 */
public class TblOrderDetailDAO implements Serializable {
    
    public boolean addToOrderDetail(Cart cart, String orderId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        int result = 0;
        boolean flag = true;

        try {
            con.setAutoCommit(false);

            String sql = "INSERT INTO tblOrderDetail(detailId, orderId, productId, quantity, price) VALUES(NEWID(),?,?,?,?)";
            stm = con.prepareStatement(sql);
            for (TblProductDTO productDTO : cart.getCompartment().keySet()) {
                stm.setString(1, orderId);
                stm.setString(2, productDTO.getProductId());
                stm.setInt(3, cart.getCompartment().get(productDTO));
                stm.setDouble(4, productDTO.getPrice());
                result = stm.executeUpdate();
                if (result == 0) {
                    flag = false;
                }
            }
            if (flag == false) {
                con.rollback();
            }
            else {
                con.commit();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return flag;
    }

    
    public void loadOrderDetail(String orderId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
 
        try {
            String sql = "SELECT productId, quantity, price FROM tblOrderDetail WHERE orderId=? ORDER BY productId ASC";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderId);
            rs = stm.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("productId");
                TblProductDAO productDAO = new TblProductDAO();
                String productName = productDAO.getProductName(productId);
                
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                
                TblOrderDetailDTO orderDetailDTO = new TblOrderDetailDTO(null, orderId, productId, quantity, price);
                if (listOfOrderDetail == null) {
                    listOfOrderDetail = new HashMap<>();
                }
                listOfOrderDetail.put(orderDetailDTO, productName);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    private Map<TblOrderDetailDTO, String> listOfOrderDetail;

    /**
     * @return the listOfOrderDetail
     */
    public Map<TblOrderDetailDTO, String> getListOfOrderDetail() {
        return listOfOrderDetail;
    }
    
}

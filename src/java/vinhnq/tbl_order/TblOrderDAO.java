/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import vinhnq.helpers.DBHelpers;

/**
 *
 * @author Admin
 */
public class TblOrderDAO implements Serializable {
    public String createOrder(String userId, double total, Timestamp date, String address, String paymentId, String name, String phone) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String result = null;
        try {
            String sql = "SELECT NEWID() AS orderId";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getString("orderId");
            }
            
            sql = "INSERT INTO tblOrder(orderId, userId, total, date, address, paymentId, name, phone) VALUES (?,?,?,?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, result);
            stm.setString(2, userId);
            stm.setDouble(3, total);
            stm.setTimestamp(4, date);
            stm.setString(5, address);
            stm.setString(6, paymentId);
            stm.setString(7, name);
            stm.setString(8, phone);

            stm.executeUpdate();
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
        return result;
    }
    
    public TblOrderDTO getOrder(String orderId, String userId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblOrderDTO orderDTO = null;
        try {
            String sql = "SELECT total, date, address, paymentId, phone, name FROM tblOrder WHERE orderId=? AND userId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderId);
            stm.setString(2, userId);
            rs = stm.executeQuery();
            if (rs.next()) {
                double total = rs.getDouble("total");
                Timestamp date = rs.getTimestamp("date");
                String address = rs.getString("address");
                String paymentId = rs.getString("paymentId");
                String phone = rs.getString("phone");
                String name = rs.getString("name");
                orderDTO = new TblOrderDTO(orderId, userId, total, date, address, paymentId, phone, name);
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
        return orderDTO;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_payment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import vinhnq.helpers.DBHelpers;

/**
 *
 * @author Admin
 */
public class TblPaymentDAO implements Serializable {
    public String searchNameById(String paymentId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String name = null;
        try {
            String sql = "SELECT paymentName FROM tblPayment WHERE paymentId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, paymentId);
            rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString("paymentName");
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
        return name;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_account_status;

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
public class TblAccountStatusDAO implements Serializable {
    public String getAccountStatusName(String statusId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT statusName FROM tblAccountStatus WHERE statusId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, statusId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String statusName = rs.getString("statusName");
                return statusName;
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
        return null;
    }
}

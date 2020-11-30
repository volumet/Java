/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_role;

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
public class TblRoleDAO implements Serializable {
    public String getRole(String roleId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT roleName FROM tblRole WHERE roleId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, roleId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String roleName = rs.getString("roleName");
                return roleName;
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

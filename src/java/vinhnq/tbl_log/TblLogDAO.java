/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_log;

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
public class TblLogDAO implements Serializable {
    public void writeToLog(TblLogDTO logDTO) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        try {
            String sql = "INSERT INTO tblLog(logId, userId, productId, date) VALUES(?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, logDTO.getLogId());
            stm.setString(2, logDTO.getUserId());
            stm.setString(3, logDTO.getProductId());
            stm.setTimestamp(4, logDTO.getDate());
            stm.executeUpdate();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public int getMaxLogId() throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        int maxLogId = 0;
        try {
            String sql = "SELECT MAX(CAST(logId AS INT)) AS maxLogId FROM tblLog";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                maxLogId = rs.getInt("maxLogId");
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
        return maxLogId;
    }
}

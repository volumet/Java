/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_product_status;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import vinhnq.helpers.DBHelpers;

/**
 *
 * @author Admin
 */
public class TblProductStatusDAO implements Serializable {
    private List<TblProductStatusDTO> listOfProductStatus;

    /**
     * @return the listOfProductStatus
     */
    public List<TblProductStatusDTO> getListOfProductStatus() {
        return listOfProductStatus;
    }
    
    public void loadListOfProductStatus() throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT statusId, statusName FROM tblProductStatus";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String statusId = rs.getString("statusId");
                String statusName = rs.getString("statusName");
                TblProductStatusDTO productStatusDTO = new TblProductStatusDTO(statusId, statusName);
                if (listOfProductStatus == null) {
                    listOfProductStatus = new ArrayList<>();
                }
                listOfProductStatus.add(productStatusDTO);
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
}

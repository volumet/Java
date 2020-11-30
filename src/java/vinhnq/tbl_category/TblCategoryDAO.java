/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_category;

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
public class TblCategoryDAO implements Serializable {
    private List<TblCategoryDTO> listOfCategory;

    /**
     * @return the listOfCategory
     */
    public List<TblCategoryDTO> getListOfCategory() {
        return listOfCategory;
    }
    
    public void loadListOfCategory() throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT categoryId, categoryName FROM tblCategory";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String categoryId = rs.getString("categoryId");
                String categorName = rs.getString("categoryName");
                TblCategoryDTO categoryDTO = new TblCategoryDTO(categoryId, categorName);
                if (listOfCategory == null) {
                    listOfCategory = new ArrayList<>();
                }
                listOfCategory.add(categoryDTO);
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

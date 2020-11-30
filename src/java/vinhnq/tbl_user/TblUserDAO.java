/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_user;

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
public class TblUserDAO implements Serializable {
    public TblUserDTO loginAccount(String username, String password) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT name, statusId, roleId FROM tblUser WHERE userId=? AND password=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String statusId = rs.getString("statusId");
                String roleId = rs.getString("roleId");
                TblUserDTO userDTO = new TblUserDTO(username, name, statusId, roleId);
                return userDTO;
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
    
    public TblUserDTO loginByGmail(String email) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT name, statusId, roleId FROM tblUser WHERE userId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String statusId = rs.getString("statusId");
                String roleId = rs.getString("roleId");
                TblUserDTO userDTO = new TblUserDTO(email, name, statusId, roleId);
                return userDTO;
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
    
    
    public String searchNameById(String username) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String name = null;
        try {
            String sql = "SELECT name FROM tblUser WHERE userId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
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
    
    private List<TblUserDTO> listUser;
    public void loadUser() throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String name = null;
        try {
            String sql = "SELECT userId, name, statusId, roleId FROM tblUser";
            stm = con.prepareStatement(sql);

            rs = stm.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("userId");
                String uname = rs.getString("name");
                String statusId = rs.getString("statusId");
                String roleId = rs.getString("roleId");
                
                TblUserDTO myUser = new TblUserDTO(userId, uname, statusId, roleId);
                if (listUser == null) {
                    listUser = new ArrayList<>();
                }
                listUser.add(myUser);
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

    /**
     * @return the listUser
     */
    public List<TblUserDTO> getListUser() {
        return listUser;
    }
}

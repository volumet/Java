/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_user;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class TblUserDTO implements Serializable {
    private String userId;
    private String name;
    private String password;
    private String statusId;
    private String roleId;

    public TblUserDTO() {
    }

    public TblUserDTO(String userId, String name, String password, String statusId, String roleId) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.statusId = statusId;
        this.roleId = roleId;
    }
    
    public TblUserDTO(String userId, String name, String statusId, String roleId) {
        this.userId = userId;
        this.name = name;
        this.statusId = statusId;
        this.roleId = roleId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the statusId
     */
    public String getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    
}

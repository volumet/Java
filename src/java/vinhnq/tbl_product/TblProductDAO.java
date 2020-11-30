/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.tbl_product;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;
import javax.naming.NamingException;
import vinhnq.cart.Cart;
import vinhnq.helpers.DBHelpers;

/**
 *
 * @author Admin
 */
public class TblProductDAO implements Serializable {

    private List<TblProductDTO> listOfProducts;

    /**
     * @return the listOfProduct
     */
    public List<TblProductDTO> getListOfProducts() {
        return listOfProducts;
    }

    public void loadAllProducts(int productPerPage, Date today) throws NamingException, SQLException, FileNotFoundException, IOException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT productId, name, price, quantity, categoryId, statusId, image, createDate, expiredDate "
                    + "FROM tblProduct "
                    + "WHERE quantity>? AND statusId='1' AND CAST(expiredDate AS DATE)>=? "
                    + "ORDER BY name ASC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            stm = con.prepareStatement(sql);
            stm.setInt(1, 0);
            stm.setDate(2, today);
            stm.setInt(3, 0);
            stm.setInt(4, productPerPage);
            rs = stm.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("productId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String categoryId = rs.getString("categoryId");
                String statusId = rs.getString("statusId");

                //Get image
                String imagePath = rs.getString("image");
                File file = new File(imagePath);
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", output);
                output.close();
                String image = Base64.getEncoder().encodeToString(output.toByteArray());
                //-----------------------------------------------

                Date createDate = rs.getDate("createDate");
                Date expiredDate = rs.getDate("expiredDate");
                TblProductDTO productDTO = new TblProductDTO(productId, name, price, quantity, image, categoryId, statusId, createDate, expiredDate);

                if (listOfProducts == null) {
                    listOfProducts = new ArrayList<>();
                }
                listOfProducts.add(productDTO);
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

    public int getTotalProduct() throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(productId) AS total "
                    + "FROM tblProduct";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                return total;
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
        return 0;
    }

    public void loadPagingAllProducts(int productPerPage, int thisPage) throws NamingException, SQLException, IOException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT productId, name, price, quantity, categoryId, statusId, image, createDate, expiredDate "
                    + "FROM tblProduct "
                    + "WHERE quantity>? AND statusId='1' "
                    + "ORDER BY createDate DESC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            stm = con.prepareStatement(sql);
            stm.setInt(1, 0);
            stm.setInt(2, productPerPage * (thisPage - 1));
            stm.setInt(3, productPerPage);
            rs = stm.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("productId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String categoryId = rs.getString("categoryId");
                String statusId = rs.getString("statusId");
                //Get image
                String imagePath = rs.getString("image");
                File file = new File(imagePath);
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", output);
                output.close();
                String image = Base64.getEncoder().encodeToString(output.toByteArray());
                //-----------------------------------------------
                Date createDate = rs.getDate("createDate");
                Date expiredDate = rs.getDate("expiredDate");
                TblProductDTO productDTO = new TblProductDTO(productId, name, price, quantity, image, categoryId, statusId, createDate, expiredDate);
                if (listOfProducts == null) {
                    listOfProducts = new ArrayList<>();
                }
                listOfProducts.add(productDTO);
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

    public void searchPagingProducts(int productPerPage, int thisPage, String searchValue, Date today) throws NamingException, SQLException, IOException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT productId, name, price, quantity, categoryId, statusId, image, createDate, expiredDate "
                    + "FROM tblProduct "
                    + "WHERE name LIKE ? AND statusId='1' AND quantity>? AND CAST(expiredDate AS DATE)>=? "
                    + "ORDER BY createDate DESC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchValue + "%");
            stm.setInt(2, 0);
            stm.setDate(3, today);
            stm.setInt(4, productPerPage * (thisPage - 1));
            stm.setInt(5, productPerPage);
            rs = stm.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("productId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String categoryId = rs.getString("categoryId");
                String statusId = rs.getString("statusId");
                //Get image
                String imagePath = rs.getString("image");
                File file = new File(imagePath);
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", output);
                String image = Base64.getEncoder().encodeToString(output.toByteArray());
                //-----------------------------------------------
                Date createDate = rs.getDate("createDate");
                Date expiredDate = rs.getDate("expiredDate");
                TblProductDTO productDTO = new TblProductDTO(productId, name, price, quantity, image, categoryId, statusId, createDate, expiredDate);
                if (listOfProducts == null) {
                    listOfProducts = new ArrayList<>();
                }
                listOfProducts.add(productDTO);
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

    public void searchByPrice(int productPerPage, int thisPage, double searchLowerPrice, double searchUpperPrice, Date today) throws NamingException, SQLException, IOException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT productId, name, price, quantity, categoryId, statusId, image, createDate, expiredDate "
                    + "FROM tblProduct "
                    + "WHERE price>=? AND price<=? AND statusId='1' AND quantity>? AND CAST(expiredDate AS DATE)>=? "
                    + "ORDER BY createDate DESC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            stm = con.prepareStatement(sql);
            stm.setDouble(1, searchLowerPrice);
            stm.setDouble(2, searchUpperPrice);
            stm.setInt(3, 0);
            stm.setDate(4, today);
            stm.setInt(5, productPerPage * (thisPage - 1));
            stm.setInt(6, productPerPage);
            rs = stm.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("productId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String categoryId = rs.getString("categoryId");
                String statusId = rs.getString("statusId");
                //Get image
                String imagePath = rs.getString("image");
                File file = new File(imagePath);
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", output);
                String image = Base64.getEncoder().encodeToString(output.toByteArray());
                //-----------------------------------------------
                Date createDate = rs.getDate("createDate");
                Date expiredDate = rs.getDate("expiredDate");
                TblProductDTO productDTO = new TblProductDTO(productId, name, price, quantity, image, categoryId, statusId, createDate, expiredDate);
                if (listOfProducts == null) {
                    listOfProducts = new ArrayList<>();
                }
                listOfProducts.add(productDTO);
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

    public void searchByCategory(int productPerPage, int thisPage, String searchCategoryId, Date today) throws NamingException, SQLException, IOException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT productId, name, price, quantity, statusId, image, createDate, expiredDate "
                    + "FROM tblProduct "
                    + "WHERE categoryId=? AND statusId='1' AND quantity>? AND CAST(expiredDate AS DATE)>=? "
                    + "ORDER BY createDate DESC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            stm = con.prepareStatement(sql);
            stm.setString(1, searchCategoryId);
            stm.setInt(2, 0);
            stm.setDate(3, today);
            stm.setInt(4, productPerPage * (thisPage - 1));
            stm.setInt(5, productPerPage);
            rs = stm.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("productId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                String statusId = rs.getString("statusId");
                //Get image
                String imagePath = rs.getString("image");
                File file = new File(imagePath);
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", output);
                String image = Base64.getEncoder().encodeToString(output.toByteArray());
                //-----------------------------------------------
                Date createDate = rs.getDate("createDate");
                Date expiredDate = rs.getDate("expiredDate");
                TblProductDTO productDTO = new TblProductDTO(productId, name, price, quantity, image, searchCategoryId, statusId, createDate, expiredDate);
                if (listOfProducts == null) {
                    listOfProducts = new ArrayList<>();
                }
                listOfProducts.add(productDTO);
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

    public int updateProductWithImage(TblProductDTO productDTO) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        int result;
        try {
            String sql = "UPDATE tblProduct "
                    + "SET name=?, price=?, quantity=?, categoryId=?, statusId=?, image=?, createDate=?, expiredDate=? "
                    + "WHERE productId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, productDTO.getName());
            stm.setDouble(2, productDTO.getPrice());
            stm.setInt(3, productDTO.getQuantity());
            stm.setString(4, productDTO.getCategoryId());
            stm.setString(5, productDTO.getStatusId());
            stm.setString(6, productDTO.getImage());
            stm.setDate(7, productDTO.getCreateDate());
            stm.setDate(8, productDTO.getExpiredDate());
            stm.setString(9, productDTO.getProductId());

            result = stm.executeUpdate();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public int updateProductWithoutImage(TblProductDTO productDTO) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        int result;

        try {
            String sql = "UPDATE tblProduct "
                    + "SET name=?, price=?, quantity=?, categoryId=?, statusId=?, createDate=?, expiredDate=? "
                    + "WHERE productId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, productDTO.getName());
            stm.setDouble(2, productDTO.getPrice());
            stm.setInt(3, productDTO.getQuantity());
            stm.setString(4, productDTO.getCategoryId());
            stm.setString(5, productDTO.getStatusId());
            stm.setDate(6, productDTO.getCreateDate());
            stm.setDate(7, productDTO.getExpiredDate());
            stm.setString(8, productDTO.getProductId());

            result = stm.executeUpdate();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public int getMaxProductId() throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT MAX(CAST(productId AS INT)) AS total FROM tblProduct";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                return total;
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
        return 0;
    }

    public void createProduct(TblProductDTO productDTO) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;

        try {
            String sql = "INSERT INTO tblProduct(productId, name, price, quantity, categoryId, statusId, image, createDate, expiredDate) "
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, productDTO.getProductId());
            stm.setString(2, productDTO.getName());
            stm.setDouble(3, productDTO.getPrice());
            stm.setInt(4, productDTO.getQuantity());
            stm.setString(5, productDTO.getCategoryId());
            stm.setString(6, productDTO.getStatusId());
            stm.setString(7, productDTO.getImage());
            stm.setDate(8, productDTO.getCreateDate());
            stm.setDate(9, productDTO.getExpiredDate());

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

    public TblProductDTO searchProductByIdWithoutImage(String productId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblProductDTO productDTO = null;
        try {
            String sql = "SELECT name, price FROM tblProduct WHERE productId=? AND statusId='1'";
            stm = con.prepareStatement(sql);
            stm.setString(1, productId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                productDTO = new TblProductDTO(productId, name, price, 0, null, null, null, null, null);
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
        return productDTO;
    }

    public int getQuantity(String productId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        int quantity = 0;
        try {
            String sql = "SELECT quantity FROM tblProduct WHERE productId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, productId);
            rs = stm.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("quantity");

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
        return quantity;
    }

    public void reduceQuantity(Cart cart) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);

            for (TblProductDTO productDTO : cart.getCompartment().keySet()) {
                String sql = "SELECT quantity FROM tblProduct WHERE productId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, productDTO.getProductId());
                rs = stm.executeQuery();
                if (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    sql = "UPDATE tblProduct SET quantity=? WHERE productId=?";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, quantity - cart.getCompartment().get(productDTO));
                    stm.setString(2, productDTO.getProductId());
                    stm.addBatch();
                    stm.executeBatch();
                }
            }
            con.commit();
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
    
    public String getProductName(String productId) throws NamingException, SQLException {
        Connection con = DBHelpers.makeConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String name = null;
        try {
            String sql = "SELECT name FROM tblProduct WHERE productId=?";
            stm = con.prepareStatement(sql);
            stm.setString(1, productId);
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.log4j.LogManager;
import vinhnq.error.ManageError;
import vinhnq.helpers.ConvertInputStreamToByteArrayHelpers;
import vinhnq.tbl_category.TblCategoryDAO;
import vinhnq.tbl_category.TblCategoryDTO;
import vinhnq.tbl_product.TblProductDAO;
import vinhnq.tbl_product.TblProductDTO;
import vinhnq.tbl_product_status.TblProductStatusDAO;
import vinhnq.tbl_product_status.TblProductStatusDTO;

/**
 *
 * @author Admin
 */
@MultipartConfig(fileSizeThreshold = 16177215, maxFileSize = 16177215)
public class CreateProductServlet extends HttpServlet {

    private final org.apache.log4j.Logger logger = LogManager.getLogger(CreateProductServlet.class);
    private final String MANAGE = "ManagePage";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String productId = request.getParameter("txtProductId");
        String productName = request.getParameter("txtName");
        double price = Double.valueOf(request.getParameter("txtPrice"));
        int quantity = Integer.valueOf(request.getParameter("txtQuantity"));
        Date createDate = Date.valueOf(request.getParameter("txtCreateDate"));
        Date expiredDate = Date.valueOf(request.getParameter("txtExpiredDate"));
        String statusId = request.getParameter("txtStatus");
        String categoryId = request.getParameter("cbCategory");
        String strThisPage = request.getParameter("txtPage");
        String url = MANAGE;
        //Get photo
        Part filePart = request.getPart("photo"); //Get photo by Part
        InputStream photo = filePart.getInputStream(); //Convert part to input stream
        if (filePart.getSize() <= 0) { //Check file part size if it's <=0 (no picture was uploaded)
            photo = null; //set input stream null
        }

        //Create error object
        ManageError error = new ManageError();
        //----------------------------

        TblProductDAO productDAO = new TblProductDAO();
        TblProductDTO productDTO = null;
        int thisPage = 1;
        if (strThisPage != null && !strThisPage.isEmpty()) {
            thisPage = Integer.valueOf(strThisPage);
        }
        try {
            if (createDate.compareTo(expiredDate) <= 0) {

                if (photo != null) {
                    LocalDate time = LocalDate.now();
                    String imageName = productId + time.toString();
                    byte[] buffer = ConvertInputStreamToByteArrayHelpers.convertISToByteArr(photo);
                    //close inputstream
                    photo.close();
                    String path = "F:\\Java\\Image Folder\\" + imageName + ".png";

                    productDTO = new TblProductDTO(productId, productName, price, quantity, path, categoryId, statusId, createDate, expiredDate);
                    productDAO.createProduct(productDTO);

                    //If create success then write image to folder (else exception)
                    File writeTo = new File(path);
                    try (OutputStream outStream = new FileOutputStream(writeTo)) {
                        outStream.write(buffer);
                    }
                    
                } else {
                    productDTO = new TblProductDTO(productId, productName, price, quantity, null, categoryId, statusId, createDate, expiredDate);
                    productDAO.createProduct(productDTO);
                }

            } else {
                error.setCreateExpiredErr("Create date must be before expired date");
                request.setAttribute("CREATE_ERROR", error);
            }
        } catch (NamingException e) {
            logger.error("CreateProductServlet MainTryCatch NamingException " + e.getMessage());
        } catch (SQLException e) {
            error.setDuplicateIdErr("Duplicate id");
            request.setAttribute("CREATE_ERROR", error);
            logger.error("CreateProductServlet MainTryCatch SQLException " + e.getMessage());
        } finally {
            try {
                int productPerPage = 20;
                productDAO.loadPagingAllProducts(productPerPage, thisPage);
                List<TblProductDTO> listOfProducts = productDAO.getListOfProducts();
                request.setAttribute("LIST_PRODUCTS", listOfProducts);

                TblCategoryDAO categoryDAO = new TblCategoryDAO();
                categoryDAO.loadListOfCategory();
                List<TblCategoryDTO> listOfCategory = categoryDAO.getListOfCategory();
                request.setAttribute("LIST_CATEGORY", listOfCategory);

                TblProductStatusDAO productStatusDAO = new TblProductStatusDAO();
                productStatusDAO.loadListOfProductStatus();
                List<TblProductStatusDTO> listOfProductStatus = productStatusDAO.getListOfProductStatus();
                request.setAttribute("LIST_STATUS", listOfProductStatus);

                int paging;
                int total = productDAO.getTotalProduct();
                if (total % productPerPage == 0) {
                    paging = total / productPerPage;
                } else {
                    paging = total / productPerPage + 1;
                }

                request.setAttribute("PAGING", paging);
            } catch (IOException e) {
                logger.error("CreateProductServlet SubTryCatch IOException " + e.getMessage());
            } catch (SQLException e) {
                logger.error("CreateProductServlet SubTryCatch SQLException " + e.getMessage());
            } catch (NamingException e) {
                logger.error("CreateProductServlet SubTryCatch NamingException " + e.getMessage());
            }

            ServletContext context = request.getServletContext();
            Map<String, String> listMap
                    = (Map<String, String>) context.getAttribute("MAP");

            RequestDispatcher rd = request.getRequestDispatcher(listMap.get(url));
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

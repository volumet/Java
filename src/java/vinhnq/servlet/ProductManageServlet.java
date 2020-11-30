/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
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
public class ProductManageServlet extends HttpServlet {

    private final org.apache.log4j.Logger logger = LogManager.getLogger(ProductManageServlet.class);
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
        String strThisPage = request.getParameter("txtPage");
        String url = MANAGE;
        try {
            TblProductDAO productDAO = new TblProductDAO();
            int productPerPage = 20;
            int thisPage = 1;
            if (strThisPage != null) {
                thisPage = Integer.valueOf(strThisPage);
            }
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
        } catch (NamingException e) {
            logger.error("ProductManageServlet NamingException " + e.getMessage());
        } catch (SQLException e) {
            logger.error("ProductManageServlet SQLException " + e.getMessage());
        } finally {
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

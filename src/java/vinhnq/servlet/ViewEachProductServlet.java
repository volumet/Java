/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import vinhnq.error.SearchPriceError;
import vinhnq.tbl_category.TblCategoryDAO;
import vinhnq.tbl_category.TblCategoryDTO;
import vinhnq.tbl_product.TblProductDAO;
import vinhnq.tbl_product.TblProductDTO;

/**
 *
 * @author Admin
 */
public class ViewEachProductServlet extends HttpServlet {

    private final org.apache.log4j.Logger logger = LogManager.getLogger(ViewEachProductServlet.class);
    private final String STORE_FULL = "MainStore";

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
        int thisPage = Integer.valueOf(request.getParameter("txtPage"));
        String searchValue = request.getParameter("txtSearchValue");
        String strLowerPrice = request.getParameter("txtLowerPrice");
        String strUpperPrice = request.getParameter("txtUpperPrice");
        double searchLowerPrice = 0;
        if (strLowerPrice != null && !strLowerPrice.isEmpty()) {
            searchLowerPrice = Double.valueOf(strLowerPrice);
        }
        double searchUpperPrice = 0;
        if (strUpperPrice != null && !strUpperPrice.isEmpty()) {
            searchUpperPrice = Double.valueOf(strUpperPrice);
        } 
        String searchCategoryId = request.getParameter("cbCategory");

        String url = STORE_FULL;
        Date today = Date.valueOf(LocalDate.now());
        try {
            int productPerPage = 20;

            TblProductDAO productDAO = new TblProductDAO();

            if (searchValue != null && !searchValue.equals(" ")) {
                productDAO.searchPagingProducts(productPerPage, thisPage, searchValue, today);
            } else {
                if (searchLowerPrice > 0 && (searchUpperPrice >= searchLowerPrice)) {
                    productDAO.searchByPrice(productPerPage, thisPage, searchLowerPrice, searchUpperPrice, today);
                } else {
                    if (searchUpperPrice < searchLowerPrice) {
                        SearchPriceError searchPriceError = new SearchPriceError();
                        searchPriceError.setUpperIsLessThanLowerErr("Upper price limit must more than lower price limit");
                        productDAO.loadPagingAllProducts(productPerPage, thisPage);
                        request.setAttribute("PRICE_ERROR", searchPriceError);
                    } else {
                        if (searchCategoryId != null) {
                            productDAO.searchByCategory(productPerPage, thisPage, searchCategoryId, today);
                        } else {
                            productDAO.loadPagingAllProducts(productPerPage, thisPage);
                        }

                    }
                }
            }

            List<TblProductDTO> listOfProducts = productDAO.getListOfProducts();
            request.setAttribute("LIST_PRODUCTS", listOfProducts);

            TblCategoryDAO categoryDAO = new TblCategoryDAO();
            categoryDAO.loadListOfCategory();
            List<TblCategoryDTO> listOfCategory = categoryDAO.getListOfCategory();
            request.setAttribute("LIST_CATEGORY", listOfCategory);

            int paging;
            int total = productDAO.getTotalProduct();
            if (total % productPerPage == 0) {
                paging = total / productPerPage;
            } else {
                paging = total / productPerPage + 1;
            }

            request.setAttribute("PAGING", paging);

        } catch (NamingException e) {
            logger.error("ViewEachProductServlet NamingException " + e.getMessage());
        } catch (SQLException e) {
            logger.error("ViewEachProductServlet SQLException " + e.getMessage());
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

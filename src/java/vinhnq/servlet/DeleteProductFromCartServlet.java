/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import vinhnq.cart.Cart;
import vinhnq.tbl_product.TblProductDAO;
import vinhnq.tbl_product.TblProductDTO;

/**
 *
 * @author Admin
 */
public class DeleteProductFromCartServlet extends HttpServlet {

    private final org.apache.log4j.Logger logger = LogManager.getLogger(DeleteProductFromCartServlet.class);
    private final String CART_PAGE = "ViewCart";
    private final String DEFAULT_PAGE = "ViewAllProduct";

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
        String url = CART_PAGE;
        try {
            TblProductDAO productDAO = new TblProductDAO();
            TblProductDTO productDTO = productDAO.searchProductByIdWithoutImage(productId);

            if (productDTO != null) {
                HttpSession session = request.getSession(true);
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart == null) {
                    cart = new Cart();
                }
                cart.removeProduct(productDTO);
                if (cart.getCompartment() == null) {
                    cart = null;
                }
                session.setAttribute("CART", cart);
            } else {
                url = DEFAULT_PAGE;
            }
        } catch (NamingException e) {
            logger.error("DeleteProductFromCartServlet NamingException " + e.getMessage());
        } catch (SQLException e) {
            logger.error("DeleteProductFromCartServlet SQLException " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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

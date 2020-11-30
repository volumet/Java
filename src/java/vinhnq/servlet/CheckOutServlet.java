/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import vinhnq.error.QuantityError;
import vinhnq.tbl_category.TblCategoryDAO;
import vinhnq.tbl_category.TblCategoryDTO;
import vinhnq.tbl_order.TblOrderDAO;
import vinhnq.tbl_order_detail.TblOrderDetailDAO;
import vinhnq.tbl_product.TblProductDAO;
import vinhnq.tbl_product.TblProductDTO;
import vinhnq.tbl_user.TblUserDTO;

/**
 *
 * @author Admin
 */
public class CheckOutServlet extends HttpServlet {

    private final org.apache.log4j.Logger logger = LogManager.getLogger(CheckOutServlet.class);
    private final String CART_PAGE = "CartPage";
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
        String[] quantityList = request.getParameterValues("txtQuantity");
        String address = request.getParameter("txtAddress");
        String phone = request.getParameter("phone");
        String name = request.getParameter("txtName");
        PrintWriter out = response.getWriter();
        String url = CART_PAGE;
        try {
            HttpSession session = request.getSession(false);
            Cart cart = (Cart) session.getAttribute("CART");

            if (cart != null) {
                if (cart.getCompartment() != null) {
                    int count = 0;
                    Set<TblProductDTO> keySet = cart.getCompartment().keySet();
                    for (TblProductDTO productDTO : keySet) {
                        cart.getCompartment().put(productDTO, Integer.valueOf(quantityList[count]));
                        count++;
                    }
                    TblProductDAO productDAO = new TblProductDAO();
                    QuantityError quantityError = new QuantityError();
                    boolean foundErr = false;
                    double total = 0;
                    Timestamp date = Timestamp.valueOf(LocalDateTime.now());
                    for (TblProductDTO productDTO : cart.getCompartment().keySet()) {
                        total += productDTO.getPrice() * cart.getCompartment().get(productDTO);
                        if (cart.getCompartment().get(productDTO) > productDAO.getQuantity(productDTO.getProductId())) {
                            foundErr = true;
                            quantityError.getListOfErr().add("Product " + productDTO.getName() + " is not enough or out of stock");
                            request.setAttribute("QUANTITY_ERR", quantityError);
                        }
                    }

                    if (foundErr == false) {
                        TblOrderDAO orderDAO = new TblOrderDAO();
                        String userId = "guest";
                        TblUserDTO account = (TblUserDTO) session.getAttribute("USER");
                        if (account != null) {
                            userId = account.getUserId();
                        }
                        String orderId = orderDAO.createOrder(userId, total, date, address, "1", name, phone);
                        if (orderId != null) {
                            TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                            boolean result = orderDetailDAO.addToOrderDetail(cart, orderId);
                            if (result == true) {
                                productDAO.reduceQuantity(cart);
                                session.setAttribute("CART", null);

                                request.setAttribute("TRACKING", orderId);
                            }
                        }
                    }
                }
            } else {
                url = DEFAULT_PAGE;
            }
            TblCategoryDAO categoryDAO = new TblCategoryDAO();
            categoryDAO.loadListOfCategory();
            List<TblCategoryDTO> listOfCategory = categoryDAO.getListOfCategory();
            request.setAttribute("LIST_CATEGORY", listOfCategory);
        } catch (NamingException e) {
            logger.error("CheckOutServlet NamingException " + e.getMessage());
        } catch (SQLException e) {
            logger.error("CheckOutServlet SQLException " + e.getMessage());
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

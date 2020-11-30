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
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import vinhnq.tbl_category.TblCategoryDAO;
import vinhnq.tbl_category.TblCategoryDTO;
import vinhnq.tbl_order.TblOrderDAO;
import vinhnq.tbl_order.TblOrderDTO;
import vinhnq.tbl_order_detail.TblOrderDetailDAO;
import vinhnq.tbl_order_detail.TblOrderDetailDTO;
import vinhnq.tbl_payment.TblPaymentDAO;
import vinhnq.tbl_user.TblUserDAO;
import vinhnq.tbl_user.TblUserDTO;

/**
 *
 * @author Admin
 */
public class TrackingServlet extends HttpServlet {

    private final org.apache.log4j.Logger logger = LogManager.getLogger(TrackingServlet.class);
    public final String TRACKING = "TrackingPage";

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
        String trackingId = request.getParameter("txtTrackingId");
        String url = TRACKING;
        try {
            HttpSession session = request.getSession(false);
            String userId = null;
            if (session != null) {
                TblUserDTO userDTO = (TblUserDTO) session.getAttribute("USER");
                if (userDTO != null) {
                    userId = userDTO.getUserId();
                }
            }
            
            TblOrderDAO orderDAO = new TblOrderDAO();
            TblOrderDTO orderDTO = orderDAO.getOrder(trackingId, userId);
            request.setAttribute("ORDER", orderDTO);

            if (orderDTO != null) {
                if (orderDTO.getName() == null) {
                    TblUserDAO userDAO = new TblUserDAO();
                    String name = userDAO.searchNameById(orderDTO.getUserId());
                    request.setAttribute("LOGGED_NAME", name);
                }

                if (orderDTO.getPaymentId() != null) {
                    TblPaymentDAO paymentDAO = new TblPaymentDAO();
                    String paymentName = paymentDAO.searchNameById(orderDTO.getPaymentId());
                    request.setAttribute("PAYMENT_METHOD", paymentName);
                }

                TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                orderDetailDAO.loadOrderDetail(trackingId);
                Map<TblOrderDetailDTO, String> listOfOrderDetail = orderDetailDAO.getListOfOrderDetail();
                request.setAttribute("ORDER_DETAIL_MAP", listOfOrderDetail);
            }
            TblCategoryDAO categoryDAO = new TblCategoryDAO();
            categoryDAO.loadListOfCategory();
            List<TblCategoryDTO> listOfCategory = categoryDAO.getListOfCategory();
            request.setAttribute("LIST_CATEGORY", listOfCategory);
        } catch (NamingException e) {
            logger.error("TrackingServlet NamingException " + e.getMessage());
        } catch (SQLException e) {
            logger.error("TrackingServlet SQLException " + e.getMessage());
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

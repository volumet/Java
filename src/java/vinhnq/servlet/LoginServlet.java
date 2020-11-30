/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import static vinhnq.helpers.EncryptionHelpers.getSHA;
import static vinhnq.helpers.EncryptionHelpers.toHexString;
import vinhnq.tbl_account_status.TblAccountStatusDAO;
import vinhnq.tbl_role.TblRoleDAO;
import vinhnq.tbl_user.TblUserDAO;
import vinhnq.tbl_user.TblUserDTO;

/**
 *
 * @author Admin
 */
public class LoginServlet extends HttpServlet {

    private final org.apache.log4j.Logger logger = LogManager.getLogger(LoginServlet.class);
    private final String VIEW_ALL_PRODUCT = "ViewAllProduct";
    private final String LOGIN_FAILED = "LoginFailed";

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
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String url = LOGIN_FAILED;
        try {
            //Encrypt password
            String encryptedPassword = toHexString(getSHA(password));

            //Check login
            TblUserDAO userDAO = new TblUserDAO();
            TblUserDTO userDTO = userDAO.loginAccount(username, encryptedPassword);

            if (userDTO != null) {
                //Check account status
                TblAccountStatusDAO accountStatusDAO = new TblAccountStatusDAO();
                String accountStatus = accountStatusDAO.getAccountStatusName(userDTO.getStatusId());
                if (accountStatus.equals("Active")) {
                    //Clear pre login session
                    HttpSession session = request.getSession(false);
                    session.invalidate();
                    
                    
                    session = request.getSession(true);
                    
                    //Put account to session
                    session.setAttribute("USER", userDTO);
                    url = VIEW_ALL_PRODUCT;
                    
                    //Put role to session
                    TblRoleDAO roleDAO = new TblRoleDAO();
                    String accountRole = roleDAO.getRole(userDTO.getRoleId());
                    session.setAttribute("USER_ROLE", accountRole);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("\nLoginServlet NoSuchAlgorithmException " + e.getMessage());
        } catch (NamingException e) {
            logger.error("\nLoginServlet NamingException " + e.getMessage());
        } catch (SQLException e) {
            logger.error("\nLoginServlet SQLException " + e.getMessage());
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

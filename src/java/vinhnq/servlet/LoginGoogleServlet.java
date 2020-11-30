/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.servlet;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import vinhnq.helpers.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.security.GeneralSecurityException;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import vinhnq.tbl_account_status.TblAccountStatusDAO;
import vinhnq.tbl_role.TblRoleDAO;
import vinhnq.tbl_user.TblUserDAO;
import vinhnq.tbl_user.TblUserDTO;

/**
 *
 * @author Admin
 */
public class LoginGoogleServlet extends HttpServlet {

    private final String LOGIN_FAILED = "LoginFailed";
    private final String VIEW_ALL_PRODUCT = "ViewAllProduct";
    private final org.apache.log4j.Logger logger = LogManager.getLogger(LoginGoogleServlet.class);

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
        String url = LOGIN_FAILED;
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList("10916029103-mccqfubuoigvgbv3olt86p8agu8sk9lm.apps.googleusercontent.com"))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

            String idTokenString = request.getParameter("id_token");
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Get profile information from payload
                String email = payload.getEmail();
                TblUserDAO userDAO = new TblUserDAO();
                TblUserDTO userDTO = userDAO.loginByGmail(email);
                if (userDTO != null) {
                    TblAccountStatusDAO accountStatusDAO = new TblAccountStatusDAO();
                    String accountStatus = accountStatusDAO.getAccountStatusName(userDTO.getStatusId());
                    if (accountStatus.equals("Other")) {
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

            }
        } catch (IOException e) {
            logger.error("LoginGoogleServlet IOException " + e.getMessage());
        } catch (GeneralSecurityException e) {
            logger.error("LoginGoogleServlet GeneralSecurityException " + e.getMessage());
        } catch (NamingException e) {
            logger.error("LoginGoogleServlet NamingException " + e.getMessage());
        } catch (SQLException e) {
            logger.error("LoginGoogleServlet SQLException " + e.getMessage());
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDEV425_HW4;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.derby.jdbc.ClientDataSource;

public class ShowAccount extends HttpServlet {

    private HttpSession session;
    private int user_id;
    private String Cardholdername;
    private String CardType;
    private String ServiceCode;
    private String CardNumber;
    private int CAV_CCV2;
    private java.sql.Date expiredate;
    private String FullTrackData;
    private String PIN;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        session = request.getSession(true);
        if (session.getAttribute("UMUCUserEmail") == null) {
            response.sendRedirect("login.jsp");
        } else {
            getData();

            request.setAttribute("Cardholdername", Cardholdername);
            request.setAttribute("CardType", CardType);
            request.setAttribute("ServiceCode", ServiceCode);
            request.setAttribute("CardNumber", CardNumber);
            request.setAttribute("CAV_CCV2", CAV_CCV2);
            request.setAttribute("expiredate", expiredate);
            request.setAttribute("FullTrackData", FullTrackData);
            request.setAttribute("PIN", PIN);

            request.getRequestDispatcher("account.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    // Adjusted getData method to use Prepared Statements
    public void getData() {

        try {
            ClientDataSource ds = new ClientDataSource();
            ds.setDatabaseName("SDEV425");
            ds.setServerName("localhost");
            ds.setPortNumber(1527);

            ds.setDataSourceName("jdbc:derby");

            Connection conn = ds.getConnection();

            // Use of prepared statements
            String sql = "SELECT * FROM customeraccount WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (Integer) session.getAttribute("UMUCUserID"));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                user_id = rs.getInt("user_id");
                Cardholdername = rs.getString("Cardholdername");
                CardType = rs.getString("Cardtype");
                ServiceCode = rs.getString("ServiceCode");
                CardNumber = rs.getString("CardNumber");
                CAV_CCV2 = rs.getInt("CAV_CCV2");
                expiredate = rs.getDate("expiredate");
                FullTrackData = rs.getString("FullTrackData");
                PIN = rs.getString("PIN");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

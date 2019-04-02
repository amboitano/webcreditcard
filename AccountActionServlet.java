
package servlets;

import business.CreditCard;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acmor
 */
public class AccountActionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String URL = "/CardTrans.jsp";
        String msg = "";
        CreditCard card;
        String path = getServletContext().getRealPath("/WEB-INF/" + "\\");
        
        String action = request.getParameter("actiontype");
        card = (CreditCard) request.getSession().getAttribute("card");
        Object cc =  request.getSession().getAttribute("card");
        
        if (card == null && !action.equalsIgnoreCase("NEW") && !action.equalsIgnoreCase("EXISTING")) {
            msg = "No Account active. <br>";          
        }
        else {
            if (action.equalsIgnoreCase("NEW")) {
                card = new CreditCard(path);
                if (card.getErrorStatus()) {
                    msg += "Account Open Error: " + card.getErrorMessage() + "<br>";   
                } else  {
                    msg += card.getActionMsg() + "<br>";  
                }
            }
            if (action.equalsIgnoreCase("history")) {
                URL = "/History.jsp";
            }
            if (action.equalsIgnoreCase("CHARGE")){
                double d = Double.parseDouble(request.getParameter("cAmt"));
                String desc = request.getParameter("cDesc");
                card.setCharge(d, desc);
            }
            
            if (action.equalsIgnoreCase("PAYMENT")){
                double pmt = Double.parseDouble(request.getParameter("pAmt"));
                card.setPayment(pmt);
            }
            
            if (action.equalsIgnoreCase("INCREASE")){
                double inc = Double.parseDouble(request.getParameter("cIncrease"));
                card.setCreditIncrease(inc);
            }
            if (action.equalsIgnoreCase("INTEREST")){
                double pmt = Double.parseDouble(request.getParameter("iRate"));
                card.setInterestCharge(pmt);
            }
            
            
            request.getSession().setAttribute("card", card);
            Cookie acct = new Cookie("acct",String.valueOf(card.getAccountId()));
            acct.setMaxAge(60*2); //how lng the cookie will last on client machine
            acct.setPath("/");
            response.addCookie(acct);
        }
        request.setAttribute("msg", msg);
        
        RequestDispatcher disp = getServletContext().getRequestDispatcher(URL);
        disp.forward(request, response);
        
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

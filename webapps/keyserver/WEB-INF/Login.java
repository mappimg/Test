import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Login extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
    doGet(request, response);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{

    response.setContentType("text/html; charset=Shift_JIS");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(true);
    Object status = session.getAttribute("status");

    if (status != null){
      out.println((String)status);
      out.println("<p>Certification Failed</p>");
      out.println("<p>Please enter your UserID and Password again.</p>");
      session.setAttribute("status", null);
    }

    out.println("<div class=\"pos-block-main-view\">");
    out.println("<form method=\"POST\" action=\"/keyserver/login_check\" name=\"loginform\">");
    out.println("<table>");
    out.println("<tr>");
    out.println("<td>UserID</td>");
    out.println("<td><input type=\"text\" name=\"user\" size=\"32\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>Password</td>");
    out.println("<td><input type=\"password\" name=\"pass\" size=\"32\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td><input type=\"submit\" value=\"login\"></td>");
    out.println("<td><input type=\"reset\" value=\"reset\"></td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("</form>");
    out.println("</dev>");
  }
}

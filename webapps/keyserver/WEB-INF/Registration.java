import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

// ユーザを追加する
// Admin限定機能


public class Registration extends HttpServlet 
{
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
     doAction(request, response);
  }


  public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
  {
     doAction(request, response);
  }


  public void doAction(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
    HttpSession session = request.getSession(true);
    if(session.getAttribute("login") == null){
        response.sendRedirect("/keyserver/login");
        return;
    }

    response.setContentType("text/html; charset=Shift_JIS");
    PrintWriter out = response.getWriter();
    String msg = (String)session.getAttribute("message");
    String userid = request.getParameter("user");
    String name = request.getParameter("name");
    String p1 = request.getParameter("pass1");
    String p2 = request.getParameter("pass2");
    String adminflg = request.getParameter("adminflg");
    String webflg = request.getParameter("webflg");

    if(CheckInput(userid, name, p1, p2, out))
    {
	if(Update(userid, name, p1, adminflg=="1", webflg=="1", session))
	{
		session.setAttribute("message", "Registration successfull");
		response.sendRedirect("menu");
	}
	else
	{
		//session.getAttribute("message", "Registration Error");
		response.sendRedirect("user_registration");
	}
    }
 

    out.println("<html>");
    out.println("<head>");
    out.println("<title>Registration</title>");
    out.println("</head>");
    out.println("<body>");
    
    if(msg != null)
    {
        out.println(msg);
        session.setAttribute("message", null);
    }

    PrintScreen(out);
  }

  protected Boolean CheckInput(String userid, String name, String p1, String p2, PrintWriter out)
  {
	if(userid == null || userid.length() <= 0){
		//out.println("ERROR-A");
		return false;
	}
	if(name == null || name.length() <= 0){
		//out.println("ERROR-B");
		return false;
	}
	if(p1 == null || p1.length() <= 0){
		//out.println("ERROR-C");
		return false;
	}
	if(p2 == null || p2.length() <= 0){
		//out.println("ERROR-D");
		return false;
	}
	if(!p1.equals(p2)){
		//out.println("ERROR-E");
		return false;
	}
	return true;
  }

  protected void PrintScreen(PrintWriter out)
  {
    out.println("<form method=\"POST\" action=\"user_registration\" name=\"doit\">");
    out.println("<table>");
    out.println("<tr>");
    out.println("<td>UserID</td>");
    out.println("<td><input type=\"text\" name=\"user\" size=\"32\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<tr>");
    out.println("<td>UserName</td>");
    out.println("<td><input type=\"text\" name=\"name\" size=\"32\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>Password</td>");
    out.println("<td><input type=\"password\" name=\"pass1\" size=\"32\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>Password again</td>");
    out.println("<td><input type=\"password\" name=\"pass2\" size=\"32\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>Administrator</td>");
    out.println("<td><input type=\"checkbox\" name=\"adminflg\" size=\"32\" value=\"1\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>WebController</td>");
    out.println("<td><input type=\"checkbox\" name=\"webflg\" size=\"32\" value=\"1\"></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td><input type=\"submit\" value=\"OK\"></td>");
    out.println("<td><input type=\"reset\" value=\"reset\"></td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }


  protected Boolean Update(String userid, String name, String password, Boolean adminflg, Boolean webcontroller, HttpSession session)
  {
    Statement statement = null;
    Connection connection = null;
    String msg = "";
    try
    {
    	Class.forName("org.sqlite.JDBC");
    	connection = DriverManager.getConnection("jdbc:sqlite:/database/edison.db");
    	statement = connection.createStatement();
    	String sql = "Insert into User values('"+ userid + "','"+ password +"','"+name+"'," + (adminflg ? "1" : "0") + "," + (webcontroller ? "1" : "0") + ",'','','','','', 0);";
	msg = sql;
    	statement.executeUpdate(sql);
    }
    catch(Exception e)
    {
       session.setAttribute("message", "[UPDATE] "+e.toString() + "\r\n" + msg);
       return false;
    }
    finally
    {
	try
	{
	  if(statement != null)
	    statement.close();
	}
	catch(Exception e2)
	{
	}
	try
	{
	  if(connection != null)
	    connection.close();
	}
	catch(Exception e2)
	{
	}
    }
    return true;
  }
}

import java.io.*;
import java.net.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class History extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws IOException{
    HttpSession session = request.getSession(true);
    if(session.getAttribute("login") == null){
        response.sendRedirect("/keyserver/login");
        return;
    }
    PrintWriter out = response.getWriter();
    try
    {
    StringBuffer sb = new StringBuffer();
    Class.forName("org.sqlite.JDBC");
    Connection connection = DriverManager.getConnection("jdbc:sqlite:/home/root/ServerAP/edison.db");
    Statement statement = connection.createStatement();
    String sql = "select * from History;";
    ResultSet rs = statement.executeQuery(sql);

    // HTML HEADER
    sb.append("<html><head><title>History</title></head>\r\n");
    // BODY
    sb.append("<body> <form name=\"myfrm\" action=\"\" method=\"post\">  <style type=\"text/css\">.table2 th { background-color: #cccccc;}</style> \r\n <table class = \"table2\" border=1>");
    sb.append("<tr><th>DeviceID</th>");
    sb.append("<th>SystemID</th>");
    sb.append("<th>DeviceType</th>");
    sb.append("<th>Value</th>");
    sb.append("<th>CreateDate</th>");

    while(rs.next())
    {
        sb.append("<tr>");
        for(int i = 0; i < 12; i++)
        {
          sb.append("<td>" + rs.getString(i+1) + "</td>");
        }
        sb.append("</tr>");
    }
    sb.append("</form></body></html>");

    response.setContentType("text/html");
    out.println(sb.toString());
    connection.close();
    }
    catch(Exception e)
    {
    out.println("<html><head><title>ERROR WORLD!</title></head><body><h1>"+e.toString()+"</h1></body></html>");
    }
  }
}

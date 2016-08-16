import java.io.*;
import java.net.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class DoorManager extends HttpServlet {
  String jsConfirm = "<SCRIPT language=\"JavaScript\">" +
					 "function move_post(frm){" +
					 "frm.confirmed.value = event.srcElement.name;"+
					 "frm.submit();}</SCRIPT>\r\n";
  

  public void Process(HttpServletRequest request, HttpServletResponse response)
  throws IOException
  {
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
      Connection connection = DriverManager.getConnection("jdbc:sqlite:/database/edison.db");
      Statement statement = connection.createStatement();

      String sql = "select * from Door;";
      ResultSet rs = statement.executeQuery(sql);

      // HTML HEADER
      sb.append("<html><head><title>Door</title></head>\r\n");
      // JAVASCRIPT
      sb.append(jsConfirm);
      // BODY
      sb.append("<body> <style type=\"text/css\">.table2 th { background-color: #cccccc;}</style> \r\n <table class = \"table2\" border=1>");
      sb.append("<form name=\"myfrm\" method=\"post\">");
      sb.append("DO GET<br/>");
      sb.append("<tr><th>&nbsp;</th>");
      sb.append("<th>DoorNum</th>");
      sb.append("<th>Authority</th>");
      sb.append("<th>DoorName</th>");
      sb.append("<th>Door</th>");
      sb.append("<th>Lock</th>");
      sb.append("<th>Bolt</th>");
      sb.append("<th>AutoCloseTimeout1</th>");
      sb.append("<th>AutoCloseTimeout2</th>");
      sb.append("<th>CreateDate</th>");
      sb.append("<th>UpdateDate</th>");
      sb.append("<th>DeleteFlg</th>");
    
      String[] oc = new String[] {"Open", "Close"};
      String radio = request.getParameter("radiobutton");
      while(rs.next())
      {
        sb.append("<tr>");
        for(int i = 0; i < 12; i++)
        {
            if(i == 0)
            {
                // RadioButton
		if(radio != null && radio.equals(rs.getString(1)))
	                sb.append("<td><input type=\"radio\" name=\"radiobutton\" value=\""+rs.getString(1)+"\" checked=\"checked\"></td>");
		else
	                sb.append("<td><input type=\"radio\" name=\"radiobutton\" value=\""+rs.getString(1)+"\"></td>");
			
            }
            else if(i == 4 || i == 5 || i == 6)
            {
                Boolean b = rs.getBoolean(i);
                sb.append("<td>" + (b ? oc[1] : oc[0]) + "</td>");
            }
            else
            {
                sb.append("<td>" + rs.getString(i) + "</td>");
            }
        }
        sb.append("</tr>");
      }

      sb.append("<br/>");
      sb.append("<input type=\"submit\" name=\"CHECK\" value=\"CHECK\">&nbsp;");
      sb.append("<input type=\"submit\" name=\"OPEN\" value=\"OPEN\">&nbsp;");
      sb.append("<input type=\"submit\" name=\"CLOSE\" value=\"CLOSE\"><br/>");
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
 
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws IOException{
    Process(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws IOException{
    byte act = 0;
    String radio = request.getParameter("radiobutton");
    if(request.getParameter("CHECK") != null)
    {
        act = Packet.CMDTYPE_CHECK;
    }
    else if(request.getParameter("OPEN") != null)
    {
        act = Packet.CMDTYPE_OPEN;
    }
    else if(request.getParameter("CLOSE") != null)
    {
        act = Packet.CMDTYPE_CLOSE;
    }

    if(act != 0 && radio != null )
    {
	InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 22226);
	Packet p = new Packet();
        p.DoorCommand(act, Byte.parseByte(radio),0, addr);
    }

    Process(request, response);
  }
}

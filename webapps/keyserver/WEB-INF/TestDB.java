import java.io.*;
import java.net.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class TestDB extends HttpServlet {
  String jsConfirm = "<SCRIPT language=\"JavaScript\">" +
					 "function move_post(frm){" +
					 "frm.confirmed.value = event.srcElement.name;"+
					 "frm.submit();}</SCRIPT>\r\n";
  
  private void Confirm(PrintWriter out, String deviceID)
  {
    
  }  
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws IOException{
    PrintWriter out = response.getWriter();
    try
    {
    

    StringBuffer sb = new StringBuffer();
    Class.forName("org.sqlite.JDBC");
    Connection connection = DriverManager.getConnection("jdbc:sqlite:/database/edison.db");
    Statement statement = connection.createStatement();

    String confirmed = request.getParameter("confirmed");
    if(confirmed != null && !confirmed.equals(""))
    {
      String update = "update Device set State = 1 where DeviceID = '" + confirmed + "';"; 
      statement.executeUpdate(update);
    }

    String sql = "select * from Device;";
    ResultSet rs = statement.executeQuery(sql);
    
    // HTML HEADER
    sb.append("<html><head><title>Devices</title></head>\r\n");
    // JAVASCRIPT
    sb.append(jsConfirm);
    // BODY
    sb.append("<body> <style type=\"text/css\">.table2 th { background-color: #cccccc;}</style> \r\n <table class = \"table2\" border=1>");
    sb.append("<tr><th>DeviceID</th>");
    sb.append("<th>SystemID</th>");
    sb.append("<th>DeviceType</th>");
    sb.append("<th>DeviceName</th>");
    sb.append("<th>IPAddr</th>");
    sb.append("<th>Port</th>");
    sb.append("<th>Authority</th>");
    sb.append("<th>State</th>");
    sb.append("<th>Interval</th>");
    sb.append("<th>PollingRetryCount</th>");
    sb.append("<th>CurrentValue</th>");
    sb.append("<th>CreateDate</th>");
    sb.append("<th>UpdateDate</th></tr>");


    while(rs.next())
    {
        sb.append("<tr>");
        for(int i = 0; i < 13; i++)
        {
	    /*
   	    // TODO DevType Check
	    if(i == 0 && rs.getString(8).equals("2")) // State 
	    {
		// to SendCommand
    		sb.append("<form method=\"POST\" action=\"send_command\" name=\"submit_form\"><td>");
    		sb.append(" <input type=\"hidden\" name=\"devid\" value=\"" + rs.getString(1) +"\">");
    		sb.append(" <input type=\"hidden\" name=\"addr\" value=\""+ rs.getString(5) +"\">");
    		sb.append(" <input type=\"hidden\" name=\"port\" value=\""+ rs.getString(6) +"\">");
    		sb.append(" <input type=\"hidden\" name=\"serverid\" value=\""+ rs.getString(2) +"\">");
    		sb.append(" <a href=\"javascript:document.submit_form.submit()\">"+ rs.getString(1) +"</a></td></form>");		
            }
            */
            if(i == 7 && rs.getString(8).equals("0")) // Status
            {
		// Confirm Button
                sb.append("<form name=\"myfrm\" method=\"get\">");
                sb.append("<br/><input type=\"hidden\" name=\"confirmed\" value=\"\"><br/>");
            	sb.append("<td>" + "<INPUT type=\"button\" name=\"" + rs.getString(1) + "\" value=\"Confirm\" onclick=\"move_post(myfrm);\">");
                sb.append("</form>");
            }
            else
            {
                sb.append("<td>" + rs.getString(i+1) + "</td>");
            }
        }
        sb.append("</tr>");
    }

    sb.append("</body></html>");


 

    response.setContentType("text/html");
    out.println(sb.toString());
    //out.println("<html><head><title>HELLO WORLD!</title></head><body><h1>"+str+"</h1></body></html>");
    connection.close();
    }
    catch(Exception e)
    {
    out.println("<html><head><title>ERROR WORLD!</title></head><body><h1>"+e.toString()+"</h1></body></html>");
    }
  }
}

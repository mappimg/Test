import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

// ユーザを追加する
// Admin限定機能



public class UserEdit extends HttpServlet 
{
  String changeJS = "function cmb_change(){ obj = document.doit.namelist; index = obj.selectedIndex; href = \"/keyserver/user_edit?userid=\"+obj.options[index].value; location.href = href;}";

  Statement statement = null;
  Connection connection = null;

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

    try
    {
      Class.forName("org.sqlite.JDBC");
      this.connection = DriverManager.getConnection("jdbc:sqlite:/database/edison.db");
      this.statement = connection.createStatement();
    }
    catch(Exception e)
    {
	session.setAttribute("message", e.toString());
	return;
    }
        
    response.setContentType("text/html; charset=Shift_JIS");
    PrintWriter out = response.getWriter();
    String msg = (String)session.getAttribute("message");
    String target_userid = "admin";
    if(request.getParameter("userid") != null)
	target_userid = request.getParameter("userid");
    String name = request.getParameter("namelist");
    String c1 = request.getParameter("c1");
    String c2 = request.getParameter("c2");
    String c3 = request.getParameter("c3");
    String c4 = request.getParameter("c4");
    String adminflg = request.getParameter("adminflg");
    String webflg = request.getParameter("webflg");
    String[] auth = request.getParameterValues("door");

    if(CheckInput(name))
    {
	if(Update(target_userid, name, c1, c2, c3, c4, auth,adminflg != null, webflg != null, session))
	{
		session.setAttribute("message", "Update Successfull<br/>");
		response.sendRedirect("user_edit");
		return;
	}
	else
	{
		response.sendRedirect("user_edit");
		return;
	}
    }
 

    out.println("<html>");
    out.println("<head>");
    out.println("<script type=\"text/javascript\">");
    out.println(changeJS);
    out.println("</script>");
    out.println("<title>ユーザ情報編集</title>");
    out.println("</head>");

    out.println("<body>");
    
    if(msg != null)
    {
        out.println(msg);
        session.setAttribute("message", null);
    }

    PrintScreen(out, session, target_userid);

    try{
     this.statement.close();
    }catch(Exception e){
    }
    try{
     this.connection.close();
    }catch(Exception e){
    }
  }

  protected Boolean CheckInput(String name)
  {
	if(name == null || name.length() <= 0){
		return false;
	}
	return true;
  }

  private HashMap<Integer, Boolean> StrToHashMap(String str){
    String[] list = str.split(",", 0);
    HashMap<Integer, Boolean> hash = new HashMap<Integer, Boolean>();
    for(int i = 0; i < list.length; i++){
	if(list[i].trim().length() > 0)
	 hash.put(Integer.parseInt(list[i]), true);
    }
    return hash;
  }

  private String ListToStr(String[] list)
  {
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < list.length; i++){
      if(i > 0)
	sb.append(",");
      sb.append(list[i]);
    }
    return sb.toString();
  }

  protected void PrintScreen(PrintWriter out, HttpSession session, String userid)
  {
    try
    {
      // DBに登録されているユーザのリストを取得する
      String q = "select UserID, AccountName from User where deleteflg = 0;";
      ResultSet rs = statement.executeQuery(q);
      List<String> namelist = new ArrayList<String>();
      List<String> idlist = new ArrayList<String>();
      while(rs.next())
      {
	  idlist.add(rs.getString("UserID"));
  	  namelist.add(rs.getString("AccountName"));
      }
      this.statement.close();
      this.statement = this.connection.createStatement();

      // 選択されたユーザの情報を取得する
      q = "select * from User where userid = '" + userid + "' and deleteflg = 0;";
      rs = this.statement.executeQuery(q);
      rs.next();

      String admin_checked = rs.getBoolean("adminflg") ? "checked=checked" : "";
      String webc_checked = rs.getBoolean("webcontrollerflg") ? "checked=checked" : "";

      out.println("<form ng-controller=\"MyViewController\">");
      out.println("<table>");
      out.println("<tr>");
      out.println("<td>ユーザID<td>");
      out.println("<td><select name=\"userid_select\" id=\"userid_select\" ng-model=\"selectedItem\" ng-change=\"userid_change\">");
      for(int i = 0; i < namelist.size(); i++)
      {
	out.println("<option value=\"" + idlist.get(i) + "\""+ (userid.equals(idlist.get(i)) ? "selected" : "") + ">" + namelist.get(i) + "</option>\r\n");
      }
      out.println("</select></td></tr>");
      out.println("<tr>"); 
      out.println("<td>ユーザ名</td>");
      out.println("<td><input type=\"text\" name=\"name\" ng-model=\"username\" size=\"32\" value=\""+ rs.getString("AccountName") + "\" ng-model=\"text\"></td>");      
      out.println("</tr>");

      out.println("<tr>");
      out.println("<td>カードID1</td>");
      out.println("<td><input type=\"text\" name=\"c1\" size=\"40\" ng-model=\"c1\" value=\"\"" + rs.getString("CardID1") + "\"></td>");
      out.println("</tr>");

      out.println("<tr>");
      out.println("<td>カードID2</td>");
      out.println("<td><input type=\"text\" name=\"c2\" size=\"40\" ng-model=\"c2\" value=\"\""+ rs.getString("CardID2") + "\"></td>");
      out.println("</tr>");
      
      out.println("<tr>");
      out.println("<td>カードID3</td>");
      out.println("<td><input type=\"text\" name=\"c3\" size=\"40\" ng-model=\"c3\" value=\"\""+ rs.getString("CardID3") + "\"></td>");
      out.println("</tr>");
      
      out.println("<tr>");
      out.println("<td>カードID4</td>");
      out.println("<td><input type=\"text\" name=\"c4\" size=\"40\" ng-model=\"c4\" value=\"\""+ rs.getString("CardID4") + "\"></td>");
      out.println("</tr>");
    
      out.println("<tr>");
      out.println("<td>Administrator</td>");
      out.println("<td><input type=\"checkbox\"  size=\"32\" ng-model=\"adminflg\"></td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td>WebController</td>");
      out.println("<td><input type=\"checkbox\"  size=\"32\" ng-model=\"webflg\"></td>");
      out.println("</tr>");
      out.println("<tr><td>&nbsp;</td></tr>");
      
      // UserのDoorに対する権限のリストを取得
      HashMap<Integer, Boolean> hash  = StrToHashMap(rs.getString("DoorAuth"));
      this.statement.close();
      this.statement = this.connection.createStatement();

      // Doorの情報を取得
      q = "select * from Door where deleteflg = 0;";
      rs = statement.executeQuery(q);
      while(rs.next()){
	String num = rs.getString("DoorNum");
	String name = "door"+num;
	String doorname = rs.getString("DoorName");
        String checked =  ";"+name+"='"+num+"'";
        if(hash.containsKey(new Integer(rs.getInt("DoorNum"))))
      		out.println("<tr><td><input type=\"checkbox\" size=\"32\" ng-model=\""+name+"\" ng-init=\"boxarr_add('"+name+"','" + num + "')"+checked+"\" ng-true-value=\"'"+num+"'\" ng-false-value=\"'NO'\"/>" + doorname +"</td></tr>");
	else
      		out.println("<tr><td><input type=\"checkbox\" size=\"32\" ng-model=\""+name+"\" ng-init=\"boxarr_add('"+name+"','" + num + "')\" ng-true-value=\""+num+"\" ng-false-value=\"no\">" + doorname +"</td></tr>");
      }
      this.statement.close();
      this.connection.close();
      out.println("<tr><td>&nbsp;</td></tr>");
      out.println("<tr><td>&nbsp;</td></tr>");
      out.println("<tr><td><button ng-click=\"onclick_useredit_update()\">更新</button></td>");
      out.println("<td><button ng-click=\"onclick_useredit_reset()\">リセット</button></td>");
      out.println("</tr>");
      out.println("</table>");
      out.println("</form>");
    }
    catch(Exception e)
    {
	session.setAttribute("message", "[PRINT] "+e.toString());
    }
  }


  protected Boolean Update(String userid, String name, String c1, String c2, String c3, String c4, String[] auth,  Boolean adminflg, Boolean webcontroller, HttpSession session)
  {
    String msg = "";
    boolean result = false;
    try
    {
	String str_auth = ListToStr(auth);
	String sql = "Update User set AccountName = '" + name + "', CardID1='"+ c1 + "',CardID2='" + c2 + "',CardID3='" + c3 + "', CardID4='" + c4 +"', DoorAuth = '"+ str_auth + "', adminflg=" + (adminflg ? "1" : "0") + ", webcontrollerflg=" + (webcontroller ? "1" : "0") + " where UserID = '" + userid + "';";
	msg = sql;
    	this.statement.executeUpdate(sql);
	result = true;
    }
    catch(Exception e)
    {
       session.setAttribute("message", "[UPDATE] "+e.toString() + "\r\n" + msg);
    }
    finally
    {
	try
	{
	  if(statement != null)
	    this.statement.close();
	}
	catch(Exception e2)
	{
	}
	try
	{
	  if(this.connection != null)
	    this.connection.close();
	}
	catch(Exception e2)
	{
	}
    }
    return result;
  }
}

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginCheck extends HttpServlet 
{
  Connection connection = null;
  Statement statement = null;
  ResultSet rs = null;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
    response.setContentType("text/html; charset=Shift_JIS");
    PrintWriter out = response.getWriter();

    // ユーザIDとパスワードを取得
    String user = request.getParameter("user");
    String pass = request.getParameter("pass");

    // セッション取得
    HttpSession session = request.getSession(true);

    // ユーザIDとパスワードが正確な組み合わせである場合、
    // そのユーザ情報を取得する
    try
    {
      Class.forName("org.sqlite.JDBC");
      this.connection = DriverManager.getConnection("jdbc:sqlite:/database/edison.db");
      this.statement = connection.createStatement();
      this.rs = authUser(user, pass,session);
    
      if (rs != null)
      {
        try
        {
           // ResultSetに結果がある場合、指定したユーザIDとパスワードの組み合わせが見つかったと判断
           session.setAttribute("login", "OK");
           session.setAttribute("userName", (String)rs.getString("AccountName"));

           // admin flgが立っている場合、sessionにadmin flgを追加する
           if(rs.getBoolean("AdminFlg"))
	     session.setAttribute("admin", "true");
           else
	     session.setAttribute("admin", "false");
           // web controller flgが立っている場合、sessionにweb controller flgを追加する
           if(rs.getBoolean("WebControllerFlg"))
	     session.setAttribute("webcontroller", "true");
           else
	     session.setAttribute("webcontroller", "false");
           // Menuへ遷移
           String target = "/keyserver/index.html#/menu";
           response.sendRedirect(target);
        }
        catch(Exception ex)
        {
          session.setAttribute("status", ex.toString());
          response.sendRedirect("/keyserver/index.html#/login");
        }
      }
      else
      {
        response.sendRedirect("/keyserver/index.html#/login");
      }
    }
    catch(Exception ex2)
    {
    }
    finally
    {
        try
        {
                if(this.statement != null)
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
  }

  // 指定したユーザIDとパスワードからユーザを見つける
  protected ResultSet authUser(String user, String pass, HttpSession session){
    if (user == null || user.length() == 0 || pass == null || pass.length() == 0){
       session.setAttribute("status", "Error1");
       return null;
    }
    ResultSet rs = null;
    try
    {
    	String sql = "select AccountName, AdminFlg, WebControllerFlg from User where UserID = '" + user + "' and Password = '" + pass +"';";
    	rs = this.statement.executeQuery(sql);
  	rs.next();
    }
    catch(Exception e)
    {
       session.setAttribute("status", e.toString());
    }
    return rs;
  }
}

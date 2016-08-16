import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Menu extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{

    response.setContentType("text/html; charset=Shift_JIS");
    PrintWriter out = response.getWriter();

    HttpSession session = null;
    session = request.getSession(true);

    String admin = (String)session.getAttribute("admin");
    String msg = (String)session.getAttribute("message");
    
    out.println("<div class=\"pos-block-main-view\">");
    out.println("<h1>Menu</h1>");
    if(msg != null)
    {
	out.println(msg + "<br/><br/>");
	session.setAttribute("message", null);
    }

    out.println("<form ng-controller=\"MyViewController\">");
    out.println("<button ng-click=\"onclick_todoorlist()\">ドア一覧</button><br/>");
    out.println("<button ng-click=\"onclick_tohistory()\">履歴</button><br/>");

    if(admin == "true")
    {
	out.println("<button ng-click=\"onclick_tocardreaders()\">カードリーダ</button><br/>");
	out.println("<button ng-click=\"onclick_toregistration()\">ユーザ登録</button><br/>");
	out.println("<button ng-click=\"onclick_touser_edit()\">ユーザ編集</button><br/>");
    }
    out.println("<br/>");
    out.println("<button ng-click=\"onclick_logout()\">ログアウト</button>");
    out.println("</form>");
    out.println("</dev>");
  }
}

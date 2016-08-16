import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings({"rawtypes", "serial","unchecked"})
public class Central extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public Central() {
        super();
    }

    private String Action(HttpServletRequest request, HttpServletResponse response, HttpSession session, String order)
    {
	if(order == null)
		return "{\"result\" : \"\"}";

	if(order.equals("logout"))
	{
            // セッションを削除してログアウト
            session.invalidate();
            return "{\"result\" : \"OK\"}";
	}
	else if(order.equals("checksession"))
	{
	    // loginされているかをチェック
	    if(session.getAttribute("login") != null)
            	return "{\"result\" : \"OK\"}";
	}
        return "{\"result\" : \"\"}";
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	response.setContentType("text/html; charset=Shift_JIS");
	PrintWriter out = response.getWriter();
    	HttpSession session = request.getSession(true);
    	String order = (String)request.getParameter("order");
	
	String result = Action(request, response, session, order);
        PrintWriter pw = response.getWriter();
        pw.println(result);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

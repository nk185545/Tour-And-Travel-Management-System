package com.antat;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mysql.cj.protocol.Resultset;
@WebServlet("/PayNowServlet")
public class PayNowServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String pac=request.getParameter("id3");
	HttpSession session=request.getSession();
	String pname=(String) session.getAttribute("usess");
	String pdate=request.getParameter("tdate");
	int padult=Integer.parseInt(request.getParameter("noa"));
	int pchild=Integer.parseInt(request.getParameter("noc"));
	String ptravel=request.getParameter("t");
	String url="jdbc:mysql://127.0.0.1:3306/nishi";
	String un="root";
	String pwd="nishi@1425";
	String str=request.getParameter("id1");
	PrintWriter out=response.getWriter();
	try {
		Class.forName("com.mysql.jdbc.Driver") ;
	} catch (ClassNotFoundException e1) {
		e1.printStackTrace();
	}
	Connection con = null ;
	try {
		con = DriverManager.getConnection(url, un, pwd) ;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	PreparedStatement pst=null;
		String query="insert into BookingDetails values(?,?,?,?,?)";
	try {
		 pst=con.prepareStatement(query);
		pst.setString(1,pname);
		pst.setString(2,pac);
		pst.setInt(3,padult);
		pst.setInt(4,pchild);
		pst.setString(5,pdate);
		int cnt=pst.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement st=null;
	String query2="select * from AddTourPackage where packageid='"+pac+"'";
	try {
		 st=con.createStatement();
		 ResultSet rs=st.executeQuery(query2);
		 rs.next();
     int total=0;
     total=total+rs.getInt("stayamt")+rs.getInt("foodamt");
     if(ptravel.equals("Bus"))
     {
    	total=total+rs.getInt("busamt");
     }
     if(ptravel.equals("Train"))
     {
    	total=total+rs.getInt("trainamt");
     }
     if(ptravel.equals("AirLine"))
     {
    	total=total+rs.getInt("airamt");
     }
     total=total*padult+total*(pchild/2);
     request.getRequestDispatcher("header1.html").include(request, response);
     out.print("<br><br>");
     out.print("<center><table border='1' style='width:500px;'>");
	out.print("<tr style='background-color:grey;color:white'><td>Pay Online</td></tr>");
     out.print("<tr><td>Total Payable Amount:  "+total+"</td></tr>");
     out.print("<form action='LastPage	Servlet'>");
     out.print("<tr><td>Debit Card Number:  <input type='text' ></td></tr>");
     out.print("<tr><td>Enter CVV:&emsp;&emsp; <input type='text' ></td></tr>");
     out.print("</table>");
     out.print("<input type='submit' value='Pay Now'>");
     out.print("</form>");
     out.print("<br>");
    
     out.print("</center>");
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
try {
	pst.close();
	st.close();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
try {
	con.close();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}

}



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class MyServlet1
 */
@WebServlet("/MyServlet1")
public class MyServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter wri = response.getWriter();
		 
		//what the document looks like  (print out)
		wri.println("<!DOCTYPE html>");
		wri.println("<html>");
		wri.println("<body>");
		wri.println("<h1>Welcome to your Address Book!</h1>");
		wri.println("<form>");
		wri.println("Address: <input type= text name= address required/> </br>");
		wri.println("Name: <input type= text name= fmlname required/> </br>");
		wri.println("Email: <input type= text name= email required/> </br>");
		wri.println("Phone: <input type= text name= phone required/> </br>");
		wri.println("<input type= submit value= Add />");
		wri.println("</form>");
		wri.println("</body>");
		wri.println("</html>");
		
		
		PreparedStatement state = null;
	//	PreparedStatement state4 = null;
		Connection con = null;
	    String cname = null;
	    String address = null;
	    String email = null;
	    String phone = null;
	    
	    try
	    {
	    	Class.forName("com.mysql.jdbc.Driver");
	    }
	    catch(ClassNotFoundException e)
	    {
	    	
	    }
		//getting and inserting user info into table
		try
		{
		cname = request.getParameter("fmlname");
		address = request.getParameter("address");
		email = request.getParameter("email");
		phone = request.getParameter("phone"); 
			
		String link = "jdbc:mysql://skhn.ddns.net:3306/myDBPro1?verifyServerCertificate=false&useSSL=true"; //need to check that url
	    String username = "hello";
		String password = "pass";
		con = (Connection) DriverManager.getConnection( link , username, password); 
		state = (PreparedStatement) con.prepareStatement("INSERT INTO myAddressBook1 (NAME,ADDRESS,EMAIL,PHONE) VALUES(?,?,?,?)");
				state.setString(1,cname);
				state.setString(2,address);
				state.setString(3,email);
				state.setString(4,phone);
				state.execute();
					wri.println("<!DOCTYPE html>");
					wri.println("<html>");
					wri.println("<body>");
					wri.println("<br>New Contact Added! </br>");
					wri.println("<a>The contact name is </a>" + cname + ".");
					wri.println("<a>The address is </a>" + address + ".");
					wri.println("<a>The email is </a>" + email + ".");
					wri.println("<a>The phone number is </a>" + phone + ".<br><br><br>");
					wri.println("</body>");
					wri.println("</html>");
			
		
		
		con.close();
		
		}
		catch(SQLException err ) 
		{
			//System.out.println( err.getMessage( ) );
			
			if(err.getMessage().startsWith("Duplicate entry"))
			{
				wri.println("<!DOCTYPE html>");
				wri.println("<html>");
				wri.println("<body>");
				wri.println("<a> This contact already exists! </a>");
				wri.println("</body>");
				wri.println("</html>");
			}
		
		}
		//being able to view user information
		wri.println("<!DOCTYPE html>");
		wri.println("<html>");
		wri.println("<body>");
		wri.println("<form>");
		wri.println("<br>View a contact! </br>");
		wri.println("Enter the name: <input type= text name= identify required /> </br>");
		wri.println("<input type= submit value= View />");
		wri.println("</form>");
		wri.println("</body>");
		wri.println("</html>");
	
		PreparedStatement state1 = null;
		Connection con1 = null;
		String vname = null;
		try
		{
		vname = request.getParameter("identify");
		
			
		String link1 = "jdbc:mysql://skhn.ddns.net:3306/myDBPro1?verifyServerCertificate=false&useSSL=true"; //need to check that url
	    String username1 = "hello";
		String password1 = "pass";
		con1 = (Connection) DriverManager.getConnection( link1 , username1, password1); 	
		state1 = (PreparedStatement) con1.prepareStatement("SELECT * FROM myAddressBook1 WHERE NAME = ?");
		
		state1.setString(1, vname);
		
		ResultSet result = state1.executeQuery();
		
		String v = null;
			if(result.next())
			{
			v = result.getString("NAME");
			String a = result.getString("ADDRESS");
			String e = result.getString("EMAIL");
			String p = result.getString("PHONE");
		
			wri.println("<!DOCTYPE html>");
			wri.println("<html>");
			wri.println("<body>");
			wri.println("<br>The contact you wanted to see! </br>");
			wri.println("<a>The name is </a>" + v + ".");
			wri.println("<a>The address is </a>" + a + ".");
			wri.println("<a>The email is </a>" + e + ".");
			wri.println("<a>The phone number is </a>" + p + ".<br><br><br>");
			wri.println("</body>");
			wri.println("</html>");
			}
			else
			{
				if(vname != v)
				{
				wri.println("<!DOCTYPE html>");
				wri.println("<html>");
				wri.println("<body>");
				wri.println("<a> This contact doesn't exist! </a>");
				wri.println("</body>");
				wri.println("</html>");	}
			}
		
		
		
		//putting data given from user into the database.....insert statement	
		con1.close();
		}
		catch(SQLException err ) 
		{
		System.out.println( err.getMessage( ) );
		}
		
		//being able to delete your contact
		wri.println("<!DOCTYPE html>");
		wri.println("<html>");
		wri.println("<body>");
		wri.println("<form>");
		wri.println("<br>Delete your contact! </br>");
		wri.println("Enter the name: <input type= text name= delete required /> </br>");
		wri.println("<input type= submit value= Delete />");
		wri.println("</form>");
		wri.println("</body>");
		wri.println("</html>");
	
		PreparedStatement state2 = null;
		PreparedStatement state3 = null;
		Connection con2 = null;
		String dname = null;
		try
		{
		dname = request.getParameter("delete");
		
			
		String link2 = "jdbc:mysql://skhn.ddns.net:3306/myDBPro1?verifyServerCertificate=false&useSSL=true"; //need to check that url
	    String username2 = "hello";
		String password2 = "pass";
		con2 = (Connection) DriverManager.getConnection( link2 , username2, password2); 	
		state3 = (PreparedStatement) con2.prepareStatement("SELECT * FROM myAddressBook1 WHERE NAME = '"+dname+"'");
		ResultSet result12 = state3.executeQuery();
		String nmt = null;
		if(result12.next())
		{
		nmt = result12.getString("NAME");
		state2 = (PreparedStatement) con2.prepareStatement("DELETE FROM myAddressBook1 WHERE NAME = ?");
		state2.setString(1, dname);
		
		state2.executeUpdate();
		
			
			wri.println("<!DOCTYPE html>");
			wri.println("<html>");
			wri.println("<body>");
			wri.println("<br>Deleted contact is .... </br>");
			wri.println(dname);
			wri.println("</body>");
			wri.println("</html>");
		
		}
		else
		{
			if(dname != nmt)
			{
			wri.println("<!DOCTYPE html>");
			wri.println("<html>");
			wri.println("<body>");
			wri.println("<a> This contact doesn't exist so it can't be deleted! </a>");
			wri.println("</body>");
			wri.println("</html>");	}
		}
		
		//putting data given from user into the database.....insert statement	
		con2.close();
		}
		catch(SQLException err ) 
		{
		System.out.println( err.getMessage( ) );
		} 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

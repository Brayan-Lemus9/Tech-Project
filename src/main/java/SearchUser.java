import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchUser")
public class SearchUser extends HttpServlet {
   private static final long serialVersionUID = 1L;


   public SearchUser() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String userName = request.getParameter("username");
	   String password = request.getParameter("password");
       int flag = 0;

	   
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;


            String selectSQL = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
          
         
            
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String theUserName = rs.getString("USERNAME").trim();
                String thePassword = rs.getString("PASSWORD").trim();
                
                if (theUserName.contains(userName) && thePassword.contains(password)) 
                {
                	RequestDispatcher ds = request.getRequestDispatcher("/home.html");
                	ds.include(request, response);
                	flag = 1;
                }
                else 
                {
                	RequestDispatcher ds = request.getRequestDispatcher("/Error-Message.html");
                	ds.include(request, response);
                }
               
             }
            
         
      
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
        	 if (flag == 0) {
        	 RequestDispatcher ds = request.getRequestDispatcher("/Error-Message.html");
         	ds.include(request, response);
        	 }
        	 else 
        	 {
            if (preparedStatement != null )
               preparedStatement.close();
        	 }
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
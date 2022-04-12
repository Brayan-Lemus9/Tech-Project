/**
 * @file Registration.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Registration() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userName = request.getParameter("username");
      String password = request.getParameter("password");
      
      if (userName.isEmpty() && password.isEmpty() || userName.isEmpty() || password.isEmpty()) 
      {
    	  RequestDispatcher ds = request.getRequestDispatcher("/Error-Message.html");
      	  ds.include(request, response);
      }
      else 
      {

      Connection connection = null;
      String insertSql = " INSERT INTO USER (USERNAME, PASSWORD, SPANISH_LEVEL, JAPANESE_LEVEL) values ( ?, ?,1,1)";

      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, userName);
         preparedStmt.setString(2, password);
         preparedStmt.execute();
         connection.close();
         RequestDispatcher ds = request.getRequestDispatcher("/Validation-Message.html");
     	 ds.include(request, response);
      } catch (Exception e) {
         e.printStackTrace();
      }

     
   }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
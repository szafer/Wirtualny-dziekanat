package pl.edu.us.server.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		// Redirect call to another url
		response.sendRedirect("localhost/uczelnia/start.php");
	}

}

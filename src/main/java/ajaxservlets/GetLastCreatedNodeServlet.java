package ajaxservlets;

import database.DAOController;
import database.NodeDAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "GetLastCreatedNode",
        urlPatterns = "/getlastcreatednode"
)
public class GetLastCreatedNodeServlet extends HttpServlet {
    NodeDAO dao;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write("{ \"id\" : " + dao.getLastCreatedNode() + " }");
    }

    public void init(ServletConfig config) throws ServletException {
        super.init();
        dao = DAOController.getDao();
    }
}
package ajaxservlets;

import database.DAOController;
import database.Node;
import database.NodeDAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "RenameNode",
        urlPatterns = "/renamenode"
)
public class RenameNodeServlet extends HttpServlet {
    NodeDAO dao;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        Node node = dao.get(id);
        node.setName(name);
        dao.update(node);
    }

    public void init(ServletConfig config) throws ServletException {
        super.init();
        dao = DAOController.getDao();
    }
}
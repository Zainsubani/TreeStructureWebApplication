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
        name = "MoveNode",
        urlPatterns = "/movenode"
)
public class MoveNodeServlet extends HttpServlet {
    NodeDAO dao;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));
        Long newParentId = Long.parseLong(request.getParameter("new_parent_id"));
        Node node = dao.get(id);
        Node oldParent = node.getParentNode();
        oldParent.deleteChildren(node);
        Node newParent = dao.get(newParentId);
        newParent.appendChild(node);
    }

    public void init(ServletConfig config) throws ServletException {
        super.init();
        dao = DAOController.getDao();
    }
}
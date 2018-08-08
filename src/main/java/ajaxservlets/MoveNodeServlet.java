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
        Node node = dao.get(id);
        Node oldParent = node.getParentNode();
        if (oldParent!=null) {
            oldParent.deleteChildren(node);
        } else {
            dao.deleteRootNode(node);
        }
        String newId = request.getParameter("new_parent_id");
        if (!newId.equals("#")){
            Long newParentId = Long.parseLong(newId);
            Node newParent = dao.get(newParentId);
            newParent.appendChild(node);
        } else {
            node.setParentNode(null);
            dao.setRootNode(node);
        }
        dao.save(node);
    }

    public void init(ServletConfig config) throws ServletException {
        super.init();
        dao = DAOController.getDao();
    }
}
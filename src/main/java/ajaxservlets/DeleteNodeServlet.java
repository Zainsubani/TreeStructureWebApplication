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

@WebServlet(
        name = "DeleteNode",
        urlPatterns = "/deletenode"
)
public class DeleteNodeServlet extends HttpServlet {
    NodeDAO dao;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        Node nodeToDelete = dao.get(id);
        if (nodeToDelete.getParentNode()==null){
            dao.deleteRootNode(nodeToDelete);
        } else {
            Node parentNode = nodeToDelete.getParentNode();
            parentNode.deleteChildren(nodeToDelete);
        }
        dao.delete(nodeToDelete);
    }

    public void init(ServletConfig config) throws ServletException {
        super.init();
        dao = DAOController.getDao();
    }
}
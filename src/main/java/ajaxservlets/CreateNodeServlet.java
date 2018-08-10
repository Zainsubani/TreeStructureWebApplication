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
import java.io.PrintWriter;

@WebServlet(
        name = "CreateNode",
        urlPatterns = "/createnode"
)
public class CreateNodeServlet extends HttpServlet {
    NodeDAO dao;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nodeName = request.getParameter("name");
        Long parentNodeId = Long.parseLong(request.getParameter("parent"));
        Node parentNode = dao.get(parentNodeId);
        Node nodeToSave = new Node(nodeName, parentNode);
        nodeToSave.setParentNode(parentNode);
        long id = (Long) dao.save(nodeToSave);
        PrintWriter writer = response.getWriter();
        writer.write("{ \"id\" : " + id + " }");
    }

    public void init(ServletConfig config) throws ServletException {
        super.init();
        dao = DAOController.getDao();
    }
}
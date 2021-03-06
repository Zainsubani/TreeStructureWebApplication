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
import java.util.Iterator;
import java.util.Set;

@WebServlet(
        name = "GetNodeChildren",
        urlPatterns = "/getnodechildren"
)
public class GetNodeChildrenServlet extends HttpServlet {
    NodeDAO dao;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Thread.sleep(2000);
            // this pause is a part of project requirements
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json; charset=utf-8");
        String id = request.getParameter("id");
        Set<Node> childrenNodes;
        if (id.equals("root")){
           childrenNodes = dao.getRootNodes();
        } else {
            Long nodeId = Long.parseLong(id);
            Node parent = dao.get(nodeId);
            childrenNodes = parent.getChildrenNodes();
        }
        PrintWriter writer = response.getWriter();
        if (childrenNodes.isEmpty()){
            writer.write("[]");
            return;
        }
        writer.write("[\n");
        Iterator<Node> iter = childrenNodes.iterator();
        writer.write(iter.next().toString());
        while (iter.hasNext()) {
            writer.write(" ," + iter.next());
        }
        writer.write("\n]");
    }

    public void init(ServletConfig config) throws ServletException {
        super.init();
        dao = DAOController.getDao();
    }
}

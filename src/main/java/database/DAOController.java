package database;

public class DAOController {
    private static NodeDAO dao = new NodeDAO();

    public static NodeDAO getDao(){
        return dao;
    }
}


package domain;

import java.sql.SQLException;
import java.util.List;
import repository.RepositoryController;


public class DomeinController {

    private RepositoryController repoController;
    
    public DomeinController(){
        repoController = new RepositoryController();
    }
    
    public List<Continent> getAllContinents() throws SQLException
    {
        return repoController.getAllContinents();
    }
    
    
}

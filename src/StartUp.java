import domain.ContinentenBeheer;
import gui.ContinentControllerPanel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StartUp extends Application {

    @Override
    public void start(Stage stage) throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=HOGENT1415_11";   //database specific url.
        String user = "sa";
        String password = "root";

        Connection connection
                = DriverManager.getConnection(url, user, password);

        Statement statement = connection.createStatement();
        String sql = "select * from Continents";
        ResultSet result = statement.executeQuery(sql);
        String name="";
        while(result.next()) {

            name = result.getString("name");
            System.out.println(name);
        }
        
        

        ContinentenBeheer continentenBeheer = new ContinentenBeheer();
        Scene scene = new Scene(new ContinentControllerPanel(continentenBeheer));
        stage.setTitle("Werelddelen");
        stage.setScene(scene);

        // The stage will not get smaller than its preferred (initial) size.
        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.show();
        //connection.close();
    }

    public static void main(String... args) {
        Application.launch(StartUp.class, args);
    }
}


import gui.ClassListPanel;
import gui.ClassListViewPanel;
import gui.GlobalFrame;
import gui.LocationControllerPanel;
import gui.LocationWizardController;
import gui.MainPanel;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import repository.RepositoryController;

public class StartUp extends Application {

    
    @Override
    public void start(Stage stage) throws SQLException{
        Scene scene = new Scene(new GlobalFrame());
        
        stage.setTitle("Aardrijkskunde");
        stage.setScene(scene);

        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.show();
    }

    public static void main(String... args) {
        Application.launch(StartUp.class, args);
    }
}

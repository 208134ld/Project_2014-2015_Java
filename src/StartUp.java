
import gui.ClassListPanel;
import gui.ClassListViewPanel;
import gui.GlobalFrame;
import gui.LocationControllerPanel;
import gui.LocationWizardController;
import gui.MainPanel;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import repository.RepositoryController;

public class StartUp extends Application {

    
    @Override
    public void start(Stage stage) throws SQLException{
        Scene scene = new Scene(new GlobalFrame());
        
        stage.setTitle("Aardrijkskunde");
        stage.setScene(scene);
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(bounds.getWidth());
            stage.setMinHeight(bounds.getHeight());
            //stage.setX();
            //stage.setY();
        });
        stage.show();
    }

    public static void main(String... args) {
        Application.launch(StartUp.class, args);
    }
}

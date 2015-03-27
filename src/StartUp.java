
import domain.ContinentenBeheer;
import gui.ContinentControllerPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StartUp extends Application {

    @Override
    public void start(Stage stage) {
        
     
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
    }

    public static void main(String... args) {
        Application.launch(StartUp.class, args);
    }
}

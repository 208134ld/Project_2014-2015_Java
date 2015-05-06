
import controllers.GlobalFrame;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StartUp extends Application {

    @Override
    public void start(Stage stage) throws SQLException, IOException {
        Scene scene = new Scene(new GlobalFrame());
        
        stage.setTitle("Aardrijkskunde");
        stage.setScene(scene);
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(500);
            stage.setMinHeight(730);
        });
        stage.show();
        
        double x = bounds.getMinX() + (bounds.getWidth() - stage.getWidth()) / 2.0;
        double y = bounds.getMinY() + (bounds.getHeight() - stage.getHeight()) / 2.0;

        stage.setX(x);
        stage.setY(y);
    }

    public static void main(String... args) {
        Application.launch(StartUp.class, args);
    }
}

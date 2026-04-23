import controllers.DataAnalyzerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class DataAnalyzerApp extends Application {
	
	@Override public void start(final Stage primaryStage) {
		
		// TODO: Setup the primaryStage here.
		
		final var loader = new FXMLLoader(DataAnalyzerApp.class.getResource("/fxml/data-analyzer.fxml"));
		try { primaryStage.setScene(loader.load()); }
		catch (IOException e) { throw new RuntimeException(e); }
		
		// TODO: Do whatever you might need with the controller.
		final DataAnalyzerController controller = loader.getController();
		
		// TODO: Once you are ready to start testing your app with your GUI, uncomment this line below.
		primaryStage.show();
	}
	
}

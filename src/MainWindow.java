import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.text.*;

public class MainWindow extends Application{
	
	Stage window;
	Scene scene1, scene2;
	ArrayList<Destination> ordered = new ArrayList<Destination>();
	String route = "";
	Label results = new Label ("fff");
	
	public static void main (String[]args){
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        
        //Title of the application
        Label label1 = new Label ("ISAK Shuttle Service");
        Label label2 = new Label ("Please select the destinations that you want to visit.");
        
        //Destinations
        CheckBox seizanso = new CheckBox("Seizanso");
        CheckBox khs = new CheckBox("Karuizawa High School");
        CheckBox kjh = new CheckBox("Karuizawa Junior High School");
        CheckBox ses = new CheckBox("Seibu Elementary School");
        CheckBox ces = new CheckBox("Chubu Elementary School");
        CheckBox tes = new CheckBox("Tobu Elementary School");
        
        Button firstButton = new Button("Get the route");
        firstButton.setOnAction(e -> handleOptions(seizanso, khs, kjh, ses, ces, tes));

        //First Scene
        VBox first = new VBox(20);
        first.setPadding(new Insets(20, 20, 20, 20));
        first.getChildren().addAll(label1, label2, seizanso, khs, kjh, ses, ces, tes, firstButton);
        scene1 = new Scene(first, 750, 500);
        
        Button secondButton = new Button("Go back");
        secondButton.setOnAction(e -> window.setScene(scene1));
        
        Text sign = new Text(100, 50, "This is the shortest route:");
        
 
        sign.setFont(new Font(12));
        
        //Second Scene
        VBox second = new VBox(20);
        second.setPadding(new Insets(20, 20, 20, 20));
        second.getChildren().addAll(sign, results, secondButton);
        scene2 = new Scene(second, 750, 500);
 
        window.setScene(scene1);
        window.setTitle("ISAK Shuttle Service");
        window.show();
    }
	
	public void handleOptions(CheckBox seizanso, CheckBox khs, CheckBox kjh, CheckBox ses, CheckBox ces, CheckBox tes){
		route = "";
		
		ArrayList<Destination> selected = new ArrayList<Destination>();
		selected.add(ShortestCombination.getAllDestinations().get(0));
		
		if(seizanso.isSelected()){
			selected.add(ShortestCombination.getAllDestinations().get(1));
		}
		
		if(khs.isSelected()){
			selected.add(ShortestCombination.getAllDestinations().get(2));
		}
		
		if(kjh.isSelected()){
			selected.add(ShortestCombination.getAllDestinations().get(3));
		}
		
		if(ses.isSelected()){
			selected.add(ShortestCombination.getAllDestinations().get(4));
		}
		
		if(ces.isSelected()){
			selected.add(ShortestCombination.getAllDestinations().get(5));
		}
		
		if(tes.isSelected()){
			selected.add(ShortestCombination.getAllDestinations().get(6));
		}
		
		if (seizanso.isSelected() == false && khs.isSelected() == false && kjh.isSelected() == false && ses.isSelected() == false && ces.isSelected() == false && tes.isSelected() == false){
			results.setText("No destinations chosen");
		} else {
			ShortestCombination routes = new ShortestCombination();
			double [][] distanceMatrix = routes.individualDistances(selected);
			ordered = routes.findShortestCombination(distanceMatrix, selected);
			for (int i = 0; i < ordered.size(); i++){
				if (i == ordered.size() - 1){
					route += (i+1) + ". " + ordered.get(i).getName();
				}else{
					route += (i+1) + ". " + ordered.get(i).getName() + "\n";
				}
	        }
			results.setText(route);
		}
		window.setScene(scene2);
	}
}
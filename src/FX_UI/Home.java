package FX_UI;



import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Home extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
	Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); 
    primaryStage.setTitle("Bible study");
    primaryStage.setX(screenBounds.getMinX());
    primaryStage.setY(screenBounds.getMinX());
    //primaryStage.setHeight(screenBounds.getHeight());
    //primaryStage.setWidth(screenBounds.getWidth());
    //primaryStage.setWidth(200);
    Group root = new Group();
    
    
    Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    // create a grid pane
    BorderPane border = new BorderPane();
    border.setId("main-border");
    VBox vbox = new VBox();
    HBox hbox = new HBox();
    VBox vboxButtons = new VBox();
    vbox.setPrefSize(450, 700);
    hbox.setPrefSize(450, 700);
    vboxButtons.setPrefSize(170,170);
    border.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
    
    ObservableList<String> names = FXCollections.observableArrayList("steve","marv","issa","hayk");
    ListView<String> lst = new ListView<>(names);
    
    //*********Text Area*************
    TextArea txtActualVerse = new TextArea("actual verse");
    TextArea txtCommentary = new TextArea("comments");
    TextArea txtRefedVerse = new TextArea("Referenced verses");
    txtActualVerse.setPrefSize(400, 200);
    txtCommentary.setPrefSize(400, 200);
    txtRefedVerse.setPrefSize(400, 200);
    
    //*********Init ListViews***********
    ListView<String> lstBooks = new ListView<>();
    ListView<String> lstVerses = new ListView<>();
    ListView<String> lstRef = new ListView<>();
    lstBooks.setPrefSize(150, 400);
    lstVerses.setPrefSize(150, 400);
    lstRef.setPrefSize(150, 100);
    
    //********Buttons*********

	Button btnAddVerse = new Button("add verse");
	Button btnAddComment = new Button("add comment");
	Button btnAddRef = new Button("add reference");
	
	
    btnAddVerse.setPrefSize(170, 50);
    btnAddComment.setPrefSize(170, 50);
    btnAddRef.setPrefSize(170, 50);
    
    final ListProps BOOK_LIST = BookList.getInstant();
    ListProps verseList = new VerseList(lstVerses,txtActualVerse, txtCommentary);
    ListProps refedVerses = new RefedVerses(lstRef, txtRefedVerse);
    BOOK_LIST.run();
    verseList.run();
    refedVerses.run();
    
    lst.setPrefWidth(150);
    lst.setPrefHeight(150);
    
    
    lst.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			System.out.println("selection changed "+arg1+" "+arg2);
		}
    });

    //**********Landscaping************
    hbox.getChildren().addAll(lstBooks,lstVerses,lstRef);
    vbox.getChildren().addAll(txtActualVerse,txtCommentary,txtRefedVerse);
    vboxButtons.getChildren().addAll(btnAddVerse,btnAddComment,btnAddRef);
    hbox.setSpacing(4);
    vbox.setSpacing(4);
    vbox.setPadding(new Insets(5));
    vboxButtons.setSpacing(10);
    border.setLeft(hbox);
    border.setCenter(vbox);
    border.setRight(vboxButtons);
   
    root.getChildren().add(border);
    
    primaryStage.sizeToScene();
    primaryStage.setScene(scene);
    scene.getStylesheets().add("FX_UI/MainStyles.css");
    
    primaryStage.show();
  }

}


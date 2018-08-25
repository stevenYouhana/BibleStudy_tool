package FX_UI;




import javax.swing.JTextField;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Home extends Application {
	Utility.Log p = new Utility.Log(); 
	
	final static String ch_PHOLDER = "ch";
	final static String vnum_PHOLDER = "verse number";
	final static String version_PHOLDER = "version";
	final static String actV_PHOLDER = "verse";
	final static String coment_PHOLDER = "notes";
	final static String search_PHOLDER = "Search";
	final static String ref_PHOLDER = "References";
	static TextInputControl[] txtProps;
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
    VBox vboxLists = new VBox();
    HBox hboxTexts = new HBox();
    VBox vboxButtons = new VBox();
    VBox hboxCrud = new VBox();
    VBox hboxTxtFlds = new VBox();
    
    vboxLists.setPrefSize(450, 700);
    hboxTexts.setPrefSize(450, 700);
    vboxButtons.setPrefSize(170,170);
    hboxTxtFlds.setPrefSize(170,170);
    border.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
    
    ObservableList<String> names = FXCollections.observableArrayList("steve","marv","issa","hayk");
    ListView<String> lst = new ListView<>(names);
    
    //*********Text Field************
	TextField txtCh = new TextField(ch_PHOLDER);
	TextField txtVNum = new TextField(vnum_PHOLDER);
	TextField txtVersion = new TextField(version_PHOLDER);
	TextField txtSearch = new TextField(search_PHOLDER+"...");
    txtCh.setPrefSize(50, 40);
    txtVNum.setPrefSize(100, 40);
    txtVersion.setPrefSize(100, 40);
    txtSearch.setPrefSize(100, 40);
    
    txtCh.setId(ch_PHOLDER);
    txtVNum.setId(vnum_PHOLDER);
    txtVersion.setId(version_PHOLDER);
    txtSearch.setId(search_PHOLDER);
    
    //*********Text Area*************
    TextArea txtActualVerse = new TextArea(actV_PHOLDER+"...");
    TextArea txtCommentary = new TextArea(coment_PHOLDER+"...");
    TextArea txtRefedVerse = new TextArea(ref_PHOLDER+"...");
    txtActualVerse.setPrefSize(300, 170);
    txtCommentary.setPrefSize(300, 170);
    txtRefedVerse.setPrefSize(300, 170);
    
    txtActualVerse.setId(actV_PHOLDER);
    txtCommentary.setId(coment_PHOLDER);
    txtRefedVerse.setId(ref_PHOLDER);
    
    //*********Manage all input(/output) fields
    	txtProps = new TextInputControl[] {
    		txtCh, txtVNum,txtVersion,txtSearch, txtActualVerse,
    		txtCommentary,txtRefedVerse
    		};
    for(TextInputControl input : txtProps) {
    	TextProp textProp = new TextProp(input);
    		p.p("Home: "+input.getId());
        textProp.run();
    }
    
    //*********Init ListViews***********
    ListView<String> lstBooks = new ListView<>();
    ListView<String> lstVerses = new ListView<>();
    ListView<String> lstRef = new ListView<>();
    lstBooks.setPrefSize(150, 400);
    lstVerses.setPrefSize(150, 400);
    lstRef.setPrefSize(150, 400);
    
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
    hboxTexts.getChildren().addAll(lstBooks,lstVerses,lstRef);
    vboxLists.getChildren().addAll(txtActualVerse,txtCommentary,txtRefedVerse);
    vboxButtons.getChildren().addAll(btnAddVerse,btnAddComment,btnAddRef,txtSearch);
    hboxTxtFlds.getChildren().addAll(txtCh, txtVNum, txtVersion);
    hboxCrud.getChildren().addAll(hboxTxtFlds);
    hboxCrud.getChildren().addAll(vboxButtons);
    hboxTexts.setSpacing(4);
    vboxLists.setSpacing(4);
    vboxLists.setPadding(new Insets(5));
    hboxTxtFlds.setSpacing(5);
    vboxButtons.setSpacing(10);
    hboxCrud.setSpacing(10);
    border.setLeft(hboxTexts);
    border.setCenter(vboxLists);
    border.setRight(hboxCrud);
    root.getChildren().add(border);
    
    primaryStage.sizeToScene();
    primaryStage.setScene(scene);
    scene.getStylesheets().add("FX_UI/MainStyles.css");
    primaryStage.setResizable(false);
    primaryStage.show();
  }

}


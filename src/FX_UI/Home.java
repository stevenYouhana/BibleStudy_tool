package FX_UI;


import Study.Search;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import javafx.stage.Popup;

public class Home extends Application implements Runnable {
	protected static SearchView searchView;
	public static Home.SearchView getHome_instant() {
		if(searchView != null) return searchView;
		return null;
	}
	Utility.Log p = new Utility.Log(); 
	Rectangle2D screenBounds;
	ButtonProp addVerseProp;
	ButtonProp addNoteProp;
	ButtonProp addRefProp;
	final static String CH = "ch";
	final static String VNUM = "verse number";
	final static String VERSION = "version";
	final static String ACT_V = "verse";
	final static String CMNT = "notes";
	final static String SEARCH = "search";
	final static String REF = "references";
	static TextInputControl[] txtProps;
	Popup searchPopUp;
	
	VBox vboxLists;
    HBox hboxTexts;
    VBox vboxButtons;
    VBox hboxCrud;
    VBox hboxTxtFlds;
    
	
	TextField txtCh;
	TextField txtVNum;
	TextField txtVersion;
	TextField txtSearch;
	

    TextArea txtActualVerse;
    TextArea txtCommentary;
    TextArea txtRefedVerse;
    
	
    public void initializer() {
	    	txtCh = new TextField();
	    	vboxLists = new VBox();
	    hboxTexts = new HBox();
	    vboxButtons = new VBox();
	    hboxCrud = new VBox();
	    hboxTxtFlds = new VBox();
	    
		
	    txtCh = new 	TextField();
	    	txtVNum = new 	TextField();
	    	txtVersion = new 	TextField();
	    	txtSearch = new 	TextField();
	    	
	
	    //*********Text Area*************
	    txtActualVerse = new TextArea();
	    txtCommentary = new TextArea();
	    txtRefedVerse = new TextArea();
	    
	    screenBounds = Screen.getPrimary().getVisualBounds();
    }
	
    @Override
	public void run() {
		Application.launch();
	
    }
    
    public class SearchView implements Runnable {
	    	Stage stage;
	    	ListView<String> list = new ListView<>();
	    	ObservableList<String> outcomes = FXCollections.observableArrayList();
//	    	Search searchEngine;
	    	Search searchEngine;
	    
	    	public SearchView(Stage stage) {
	    		this.stage = stage;
	    		searchView = this;
	    	}
	    	public void getSearchPopup() {
	    		searchPopUp.show(stage);
	    	}
	    	public SearchView() {
	    	}
	    public void showPop() {
	    		searchPopUp.show(stage);
	    }
	    public Thread lookForFoundVerses() {
	    		return new Thread( () -> {
	    			searchEngine = new Search(txtSearch);
	    			p.p("lookout Thread");
	    			while(true) {
	    				try {
	    					synchronized(searchEngine) {
	    						searchEngine.wait();
	    					}
	    				} catch(InterruptedException ie) {
	    					p.p("lookOut inturptd "+ie);
	    				}
	    				p.p("lookOut notified");
//	    				p.p("and found: "+Search.foundVerses);
	    				Platform.runLater(() -> {
	    					searchPopUp.show(stage);
	    					outcomes.clear();
	    					outcomes.addAll(searchEngine.getFoundVerses());
	    				});
	    				
	    			}
	    		});
	    }
	    //Run this on a different thread?
		public void run() {
			list.setItems(outcomes);
			p.p("search view run!");
			searchPopUp = new Popup();
			Button btnClose = new Button("close");
		    BorderPane border = new BorderPane();
		    VBox vbox = new VBox();
		    vbox.getChildren().add(list);
		    border.setLeft(vbox);
		    border.setBottom(btnClose);
		    searchPopUp.getContent().add(border);
		    searchPopUp.setOpacity(0.9);
		    searchPopUp.setHideOnEscape(true);
		    searchPopUp.hideOnEscapeProperty();
		    searchPopUp.setAnchorX(screenBounds.getMaxX()-10);
		    searchPopUp.setAnchorY(screenBounds.getMaxY()-10);
		    //searchPopUp.show(stage);
		    btnClose.setOnAction(e -> {
		    		searchPopUp.hide();
		    });
		    lookForFoundVerses().start();
		}
    }
    
    
    
  @Override
  public void start(Stage primaryStage) {
	initializer();
    primaryStage.setTitle("Bible study");
    primaryStage.setX(screenBounds.getMinX());
    primaryStage.setY(screenBounds.getMinX());
    //primaryStage.setHeight(screenBounds.getHeight());
    //primaryStage.setWidth(screenBounds.getWidth());
    //primaryStage.setWidth(200);
    Group root = new Group();
    
    
    
    Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()-100);
    // create a grid pane
    BorderPane border = new BorderPane();
    border.setId("main-border");
    
    vboxLists.setPrefSize(350, 700);
    hboxTexts.setPrefSize(600, 700);
    vboxButtons.setPrefSize(170,170);
    hboxTxtFlds.setPrefSize(170,170);
    border.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight()-100);
    
    
    txtCh.setPrefSize(50, 40);
    txtVNum.setPrefSize(100, 40);
    txtVersion.setPrefSize(100, 40);
    txtSearch.setPrefSize(100, 40);
    
    txtCh.setId(CH);
    txtVNum.setId(VNUM);
    txtVersion.setId(VERSION);
    txtSearch.setId(SEARCH);
    
       
    txtActualVerse.setPrefSize(260, 170);
    txtCommentary.setPrefSize(260, 170);
    txtRefedVerse.setPrefSize(260, 170);
    
    txtActualVerse.setId(ACT_V);
    txtCommentary.setId(CMNT);
    txtRefedVerse.setId(REF);
    
    	txtProps = new TextInputControl[] {
    		txtCh, txtVNum,txtVersion, txtSearch, txtActualVerse,
    		txtCommentary,txtRefedVerse
    		};
    for(TextInputControl input : txtProps) {
    	TextProp textProp = new TextProp(input);
        textProp.run();
    }
    
    ListView<String> lstBooks = new ListView<>();
    ListView<String> lstVerses = new ListView<>();
    ListView<String> lstRef = new ListView<>();

    lstBooks.setPrefSize(100, 400);
    lstVerses.setPrefSize(150, 400);
    lstRef.setPrefSize(150, 400);
    

	Button btnAddVerse = new Button("Add verse");
	Button btnAddComment = new Button("Add noted");
	Button btnAddRef = new Button("Add reference");
	addVerseProp = new AddVerse(btnAddVerse);
	addNoteProp = new AddNote(btnAddComment);
	addRefProp = new AddRef(btnAddRef);
	addVerseProp.prepThis().start();
	
    btnAddVerse.setPrefSize(170, 50);
    btnAddComment.setPrefSize(170, 50);
    btnAddRef.setPrefSize(170, 50);
    
    final ListProps BOOK_LIST = BookList.getInstant();
    ListProps verseList = new VerseList(lstVerses,txtActualVerse, txtCommentary);
    ListProps refedVerses = new RefedVerses(lstRef, txtRefedVerse);
   
    
    BOOK_LIST.run();
    verseList.run();
    refedVerses.run();

    lstBooks = BOOK_LIST.getListing();
    lstVerses = verseList.getListing();
        
    hboxTexts.getChildren().addAll(lstBooks,lstVerses,lstRef);
    vboxLists.getChildren().addAll(txtActualVerse,txtCommentary,txtRefedVerse);
    vboxButtons.getChildren().addAll(btnAddVerse,btnAddComment,btnAddRef);
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
    border.setBottom(txtSearch);
    root.getChildren().add(border);
    
    primaryStage.sizeToScene();
    primaryStage.setScene(scene);
    scene.getStylesheets().add("FX_UI/MainStyles.css");
    primaryStage.setResizable(false);
    primaryStage.show();

    searchView = new SearchView(primaryStage);
    searchView.run();
  }


}


package FX_UI;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.LinkedList;

import javafx.scene.control.TextArea;

public class TextProp implements Runnable {
	Utility.Log p = new Utility.Log(); 
	TextField txtField;
	TextArea txtArea;
	
	String id = null;
	private static LinkedList<TextProp> props = new LinkedList<>();

	public TextProp(TextInputControl input) {
		if(input instanceof TextField) {
			this.txtField = new TextField();
			this.txtField = (TextField) input;
			id = txtField.getId();
			props.add(this);
		}
		else if(input instanceof TextArea) {
			this.txtArea = new TextArea();
			this.txtArea = (TextArea) input;
			id = txtArea.getId();
			props.add(this);
		}
		else return;
	}
	private void managePlaceHolders() {
		props.forEach(obj -> {
			switch(obj.id) {
			case "verse": obj.setPlaceHolder(Home.actV_PHOLDER);
			break;
			
			case "notes": obj.setPlaceHolder(Home.coment_PHOLDER);
			break;
			
			case "references": obj.setPlaceHolder(Home.ref_PHOLDER);
			break;
			
			case "ch": obj.setPlaceHolder(Home.ch_PHOLDER);
			break;
			
			case "verse number": obj.setPlaceHolder(Home.vnum_PHOLDER);
			break;
			
			case "version": obj.setPlaceHolder(Home.version_PHOLDER);
			break;
			//	txtSearch
			default: obj.setPlaceHolder(Home.search_PHOLDER);
			break;
			}
		});
	}
	private void setPlaceHolder(String def) {
		try {
			if(txtField != null) {
				txtField.setPromptText(def);
			}
			else if(txtArea != null) {
				txtArea.setPromptText(def);
			}
		}
		catch(NullPointerException npe) {
			p.p("ERR setPlaceHolder(str): "+npe);
		}

	}
	@Override
	public void run() {
		if(props.size() == Home.txtProps.length) {
			managePlaceHolders();
		}
	}
}

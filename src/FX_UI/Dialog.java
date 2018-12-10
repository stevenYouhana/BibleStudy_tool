package FX_UI;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Optional;

import Utility.Log;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class Dialog {
	static Utility.Log p = new Utility.Log();
	Alert alert = null;
	String title = null;
	String content = null;
	Optional<ButtonType> result = null;
	ButtonType btnUpdate = new ButtonType("Update", ButtonData.APPLY);
	ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	
	private ButtonType selection = null;
	
	public Dialog(String title, String content) {
		this.title = title;
		this.content = content;
	}
			
	public void confirmation() {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.getButtonTypes().setAll(btnUpdate, btnCancel);
		this.result = alert.showAndWait();
		selection = result.get() == btnUpdate? btnUpdate: btnCancel;
	}
	public ButtonType getSelectionMade() {
		return selection != null? selection: null;
	}
	public void closeDialog() {
		this.alert.close();
	}
	
	public static void done(String title, String content, int timeMS) {
		class Tray {
			Utility.Log p = new Utility.Log();
			Timer timer = new Timer();
			Alert alertDone = new Alert(AlertType.INFORMATION);
			//TimerTask task = new TimerScheduleDelay();
			public void displayInfo() {
				p.p("Timer init");
				alertDone.setTitle(title);
				alertDone.setContentText(content);
				alertDone.getDialogPane().setId("alert-done");
				alertDone.getDialogPane().getStylesheets().add(
						   getClass().getResource("MainStyles.css").toExternalForm());
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						p.p("TIMER ON");
						Platform.runLater( () -> alertDone.close());
					}
				}, 1000);
				p.p("TIMER OFF");
				alertDone.show();
			}
		}
		Tray tray = new Tray();
		tray.displayInfo();
	}
	

}

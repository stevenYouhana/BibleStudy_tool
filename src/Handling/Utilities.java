package Handling;

import java.util.ArrayList;

import Utility.Log;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Cursor;
//import sun.plugin2.uitoolkit.DragContext;

public class Utilities {
		static Log p = new Log();
		public static void disable_buttons(ArrayList<Button> buttons) {
			p.p("disable_buttons");
			for (Button button : buttons) {
				p.p("disable buttons");
				button.setDisable(true);
				//button.setDisable(true);
			}
		}
		
		public static void enable_buttons(ArrayList<Button> buttons) {
			p.p("");
			for (Button button : buttons) {
				p.p("enable buttons");
				button.setDisable(false);
			}
		}
//		private Node makeDraggable(final Node node) {
//		    final DragContext dragContext = new DragContext();
//		    final Node wrapGroup = node;
//
//		    wrapGroup.addEventFilter(
//		            MouseEvent.MOUSE_PRESSED,
//		            new EventHandler<MouseEvent>() {
//		                public void handle(final MouseEvent mouseEvent) {
//
//		                        dragContext.mouseAnchorX = mouseEvent.getX();
//		                        dragContext.mouseAnchorY = mouseEvent.getY();
//		                        dragContext.initialTranslateX =
//		                                stage.getX();
//		                        dragContext.initialTranslateY =
//		                                stage.getY();
//		                    }
//
//		            });
//
//		    wrapGroup.addEventFilter(
//		            MouseEvent.MOUSE_DRAGGED,
//		            new EventHandler<MouseEvent>() {
//		                public void handle(final MouseEvent mouseEvent) {
//
//		                        stage.setX(
//		                                dragContext.initialTranslateX
//		                                        + mouseEvent.getX()
//		                                        - dragContext.mouseAnchorX);
//		                        stage.setY(
//		                                dragContext.initialTranslateY
//		                                        + mouseEvent.getY()
//		                                        - dragContext.mouseAnchorY);
//		                    dragContext.initialTranslateX =
//		                            stage.getX();
//		                    dragContext.initialTranslateY =
//		                            stage.getY();
//
//		                }
//		            });
//
//		    return node;
//		}

		/** holder structure for drag delta amounts */
		private static class Delta { double x, y; }

		/** makes a stage draggable using a given node */
		public static void makeDraggable(final Stage stage, final Node byNode) {
		    final Delta dragDelta = new Delta();
		    byNode.setOnMousePressed(new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent mouseEvent) {
		            // record a delta distance for the drag and drop operation.
		            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
		            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
		            byNode.setCursor(Cursor.MOVE);
		        }
		    });
		    byNode.setOnMouseReleased(new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent mouseEvent) {
		            byNode.setCursor(Cursor.HAND);
		        }
		    });
		    byNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent mouseEvent) {
		            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
		            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
		        }
		    });
		    byNode.setOnMouseEntered(new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent mouseEvent) {
		            if (!mouseEvent.isPrimaryButtonDown()) {
		                byNode.setCursor(Cursor.HAND);
		            }
		        }
		    });
		    byNode.setOnMouseExited(new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent mouseEvent) {
		            if (!mouseEvent.isPrimaryButtonDown()) {
		                byNode.setCursor(Cursor.DEFAULT);
		            }
		        }
		    });
		}
}

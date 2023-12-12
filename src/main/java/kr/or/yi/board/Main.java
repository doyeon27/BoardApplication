//package kr.or.yi.board;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import org.w3c.dom.events.MouseEvent;
//
//import java.io.IOException;
//
//
//
//public class Main extends Application {
//
//    private double x = 0;
//    private double y = 0;
//
//
//    @Override
//    public void start(Stage stage) throws IOException {
//
//        try{
//            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//            Scene scene = new Scene(root, 600, 400);  // 전 에는 900, 600
////            scene.getStylesheets().add(getClass().getResource("application.css").toString()); // 라운드블럭
//            stage.setScene(scene);
//            stage.setResizable(false); // 창 크기 고정
//            root.setOnMousePressed((MouseEvent event) ->{
//                x = event.getScreenX();
//                y = event.getScreenY();
//            });
//
//
//            root.setOnMouseDragged((MouseEvent event) -> {
//                stage.setX(event.getScreenX() -x);
//                stage.setY(event.getScreenX() -y);
//            });
//
//            root.setOnMouseReleased((MouseEvent event) -> {
//                stage.setOpacity(1);
//            });
//
//            stage.initStyle(StageStyle.TRANSPARENT);
//            stage.show();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}

package kr.or.yi.board;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

            Scene scene = new Scene(root, 600, 400); // 전 에는 900, 600
            stage.setScene(scene);
            stage.setResizable(false); // 창 크기 고정
            root.setOnMousePressed((MouseEvent event) ->{
                x= event.getSceneX();
                y= event.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent event) ->{
                stage.setX(event.getScreenX() -x);
                stage.setY(event.getScreenY() -y);
                stage.setOpacity(0.8); //

            });
            root.setOnMouseReleased((MouseEvent evnet) ->{
                stage.setOpacity(1);
            });
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}
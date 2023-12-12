package kr.or.yi.board.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtil {
    private static SceneUtil instance;

    Stage stage;
    Scene scene;
    Parent root;
    FXMLLoader loader;

    private SceneUtil() {
    }

    public static SceneUtil getInstance() {
        if (instance == null) {
            instance = new SceneUtil();
        }
        return instance;
    }

    public void switchScene(MouseEvent event, String fxml) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchScene(ActionEvent event, String fxml) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene(MouseEvent event, String fxml, Parent root) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene(ActionEvent event, String fxml, Parent root) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Object getController(String fxml) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        return loader.getController();
    }


    // 종료 버튼, 액션 이벤트
    public void close(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("프로그램 종료");
        alert.setHeaderText("프로그램을 종료 하시겠습니까?");
        alert.setContentText("프로그램이 종료 됩니다.");

        System.out.println("프로그램 종료합니다");

        if (alert.showAndWait().get() == ButtonType.OK){  // showAndWait 이거를 이용해서 버튼 누를지 말지 정하라는 코드
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // 이벤트 발생한 곳 ㅇㄷ? 찾고 나서
            stage.close();
        }

    }

    // 종료 버튼, 마우스 이벤트
    public void close(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("프로그램 종료");
        alert.setHeaderText("프로그램을 종료 하시겠습니까?");
        alert.setContentText("프로그램이 종료 됩니다.");

        System.out.println("프로그램 종료합니다");

        if (alert.showAndWait().get() == ButtonType.OK){  // showAndWait 이거를 이용해서 버튼 누를지 말지 정하라는 코드
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); // 이벤트 발생한 곳 ㅇㄷ? 찾고 나서
            stage.close();
        }

    }

    public void close(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("프로그램 종료");
        alert.setHeaderText("프로그램을 종료 하시겠습니까?");
        alert.setContentText("프로그램이 종료 됩니다.");

        System.out.println("프로그램 종료합니다");

        if (alert.showAndWait().get() == ButtonType.OK){  // showAndWait 이거를 이용해서 버튼 누를지 말지 정하라는 코드
            stage.close();
        }

    }


    // get set 만듬, root , load
    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }
}

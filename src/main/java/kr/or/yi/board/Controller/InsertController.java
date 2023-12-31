//package kr.or.yi.board.Controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import kr.or.yi.board.DTO.Board;
//import kr.or.yi.board.Main;
//import kr.or.yi.board.Service.BoardService;
//import kr.or.yi.board.Service.BoardServiceImpl;
//import kr.or.yi.board.Utils.SceneUtil;
//
//import java.io.File;
//import java.io.IOException;
//
//public class InsertController {
//
//    @FXML
//    private TextField tfTitle;
//    @FXML
//    private TextField tfWriter;
//    @FXML
//    private TextArea taContent;
//
//    @FXML
//    private FileChooser fileChooser;
//    @FXML
//    private TextField tfFilePath;
//    @FXML
//    private ImageView imageView;
//
//    private Image image;
//    private File file;
//
//    Stage stage;
//    Scene scene;
//    Parent root;
//
//    FXMLLoader loader;
//
//    private BoardService boardService = new BoardServiceImpl();
//
//    public void insert(ActionEvent event) throws IOException {
//        Board board = new Board(tfTitle.getText(), taContent.getText(), tfWriter.getText());
//        int result = boardService.insert(board);
//        if (result > 0){
//            System.out.println("글 쓰기 완료");
//            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
//        }
//    }
//
//    public void moveToList(ActionEvent event) throws IOException {
//        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
//    }
//
//    public void fileUpload(ActionEvent event) {
//        fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".git"),
//                new FileChooser.ExtensionFilter("Text Files", ".txt"),
//                new FileChooser.ExtensionFilter("Audio File", ".wav", ".mp3", ".aac"),
//                new FileChooser.ExtensionFilter("All Files", "*.*")
//        );
//        file = fileChooser.showOpenDialog(stage);
//        System.out.println(file);
//        if(file !=null){
//            tfFilePath.setText(file.getAbsolutePath());
//            image = new Image(file.toURI().toString(), 211, 271, true, true);
//            imageView.setImage(image);
//        }
//
//    }
//}

package kr.or.yi.board.Controller;

import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kr.or.yi.board.DTO.Board;
import kr.or.yi.board.Main;
import kr.or.yi.board.Service.BoardService;
import kr.or.yi.board.Service.BoardServiceImpl;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class InsertController {

    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfWriter;
    @FXML
    private TextArea taContent;
    @FXML
    private FileChooser fileChooser;
    @FXML
    private TextField tfFilePath;
    @FXML
    private ImageView imageView;

    private Image image;
    private File file;

    Stage stage;
    Scene scene;
    Parent root;

    FXMLLoader loader;

    private BoardService boardService = new BoardServiceImpl();

    public void insert(ActionEvent event) throws IOException, SQLException {
        Board board = new Board(tfTitle.getText(), tfWriter.getText(), taContent.getText(), file);
        int result = boardService.insert(board);
        if (result > 0) {
            System.out.println("글쓰기 완료");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }
    }


    public void moveToList(ActionEvent event) throws IOException {

        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());

    }

    public void fileUpload(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.git"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Audio File", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        file = fileChooser.showOpenDialog(stage);
        System.out.println(file);
        if(file !=null){
            tfFilePath.setText(file.getAbsolutePath());
            image = new Image(file.toURI().toString(), 211,271, true, true);
            imageView.setImage(image);
        }

    }
}

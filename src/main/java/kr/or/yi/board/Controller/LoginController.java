package kr.or.yi.board.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import kr.or.yi.board.DTO.User;
import kr.or.yi.board.Service.UserService;
import kr.or.yi.board.Service.UserServiceImpl;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    // 이거 꼭 해 줘야 함.
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfPw;

    private UserService userService = new UserServiceImpl();

    public void login(ActionEvent event) throws IOException {
        System.out.println(tfID.getText());
        System.out.println(tfPw.getText());

        if (tfID.getText().isEmpty() || tfPw.getText().isEmpty()) {
            errorIdPw();
        } else {
            User user = userService.select(tfID.getText());
            if (user.getUserid().isEmpty()) {
                isIdEmpty();
            } else {
//                if (user.getPassword() == tfPw.getText()){
                if (!Objects.equals(user.getPassword(), tfPw.getText())) {
                    errorPW();
                } else {
                    loginSuccess();
                    SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
                }
            }
        }
    }


    // 최대한 줄인 코드
//    public void login(ActionEvent event) throws IOException {
//        String inputID = tfID.getText();
//        String inputPW = tfPw.getText();
//
//        if (inputID.isEmpty() || inputPW.isEmpty()) {
//            showAlert("다시 확인하세요.", "ID와 PW를 입력하세요.", Alert.AlertType.ERROR);
//            return;
//        }
//
//        User user = userService.select(inputID);
//
//        if (user.getUserid().isEmpty()) {
//            showAlert("다시 확인하세요.", "사용자 계정이 없습니다.", Alert.AlertType.ERROR);
//        } else if (user.getPassword().equals(inputPW)) {
//            showAlert("정보 확인.", "로그인 성공", Alert.AlertType.INFORMATION);
//            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
//
//        } else {
//            showAlert("다시 확인하세요.", "비밀번호가 일치하지 않습니다.", Alert.AlertType.ERROR);
//        }
//    }
//
//    private void showAlert(String title, String content, Alert.AlertType alertType) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }


    /////////////////////////////////////////////////////////////////////

    public void close() {
        System.out.println("로그인 페이지를 종료합니다.");
        System.exit(0);
    }

    public void errorIdPw(){
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("다시 확안하십시오.");
        alert.setHeaderText(null);
        alert.setContentText("ID와 PW를 입력하세요.");
        alert.showAndWait();
    }

    public void isIdEmpty(){
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("다시 확안하십시오.");
        alert.setHeaderText(null);
        alert.setContentText("사용자 계정이 없습니다.");
        alert.showAndWait();
    }

    public void loginSuccess(){
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("정보확인.");
        alert.setHeaderText(null);
        alert.setContentText("로그인 성공");
        alert.showAndWait();
    }

    public void errorPW(){
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("다시 확안하십시오.");
        alert.setHeaderText(null);
        alert.setContentText("비밀번호가 일치 하지 않습니다.");
        alert.showAndWait();
    }
}

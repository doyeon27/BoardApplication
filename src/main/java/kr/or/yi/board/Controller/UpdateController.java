package kr.or.yi.board.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kr.or.yi.board.DTO.Board;
import kr.or.yi.board.Service.BoardService;
import kr.or.yi.board.Service.BoardServiceImpl;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.IOException;

public class UpdateController {
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfWriter;
    @FXML
    private TextArea taContent;

    private BoardService boardService = new BoardServiceImpl();
    int boardNo;

    public void read(int boardNo){
        this.boardNo = boardNo;
        Board board = boardService.select(boardNo);
        tfTitle.setText(board.getTitle());
        tfWriter.setText(board.getWriter());
        taContent.setText(board.getContent());
    }

    public void update(ActionEvent event) throws IOException {
        Board board = new Board(tfTitle.getText(), taContent.getText(), tfWriter.getText());
        board.setBoard_no(boardNo);
        int result = boardService.update(board);
        if(result > 0){
            System.out.println("글 수정 완료");

            // close 에 있는 종료? 그걸 여기에 당겨써서 글수정 하시겠어요? 출력되게 하면 좋음


            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }
    }

    public void moveToList(ActionEvent event) throws IOException{
        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
    }

    public void delete(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("게시글 삭제");
        alert.setHeaderText("게시글을 삭제 하시겠습니까?");
        alert.setContentText("게시글을 삭제 후 복구가 불가능 합니다.");

        int result = 0;
        if (alert.showAndWait().get() == ButtonType.OK){  // showAndWait 이거를 이용해서 버튼 누를지 말지 정하라는 코드
            result = boardService.delete(boardNo);
        }
        if (result > 0){
            System.out.println("삭제 완료");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }

    }





}

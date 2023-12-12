package kr.or.yi.board;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import kr.or.yi.board.Controller.ReadController;
import kr.or.yi.board.Controller.UI;
import kr.or.yi.board.Controller.UpdateController;
import kr.or.yi.board.DTO.Board;
import kr.or.yi.board.Service.BoardService;
import kr.or.yi.board.Service.BoardServiceImpl;
import kr.or.yi.board.Utils.SceneUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Board> boardTableView;
    @FXML
    private TableColumn<Board, Integer> colBoardNo;
    @FXML
    private TableColumn<Board, String> colTitle;
    @FXML
    private TableColumn<Board, String> colWriter;
    @FXML
    private TableColumn<Board, String> colRegDate;
    @FXML
    private TableColumn<Board, String> colUpdate;
    @FXML
    private TableColumn<Board, CheckBox> colCbDelete;
    @FXML
    private CheckBox cbAll;

    Stage stage;
    Scene scene;
    Parent root;

    FXMLLoader loader; // 글쓰기 버튼을 누르면 화면이 바껴야 하기 때문에 이렇게 하나 만들어놓음

    BoardService boardService = new BoardServiceImpl();   // db 불러온다
    @FXML
    private ImageView imageView;
    @FXML
    private Button btnClose;


    // 메인 목록 번호 관
    @FXML
    private Pagination pagination;

    private int totalCount = 0;
    private int pageIndex = 0;
    private int pageSize = 10;
    private int totalPage;

    int boardNo;
    private final ChangeListener<Number> paginationChangeListener = ((observableValue, oldValue, newValue) -> changePage());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colBoardNo.setCellValueFactory(new PropertyValueFactory<>("Board_no")); //
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title")); //
        colWriter.setCellValueFactory(new PropertyValueFactory<>("Writer")); //
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("Reg_date")); //
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("Upd_date")); //
        colCbDelete.setCellValueFactory(new PropertyValueFactory<>("CbDelete")); //

        // 지우는거 ?
//        boardTableView.setItems(list);
//        cbAll.setSelected(false);


//        try {
//            totalCount = boardService.totalListCount();
//            totalPage = (totalCount == 0 ? 1 : totalCount);
//            totalPage = totalCount / pageSize;
//            totalPage = totalCount % pageSize > 0 ? ++totalPage : totalPage;
//
//        }catch (SQLException e){
//            throw  new RuntimeException(e);
//        }

        totalCount = boardService.totalListCount();
        totalCount = (totalCount == 0 ? 1 : totalCount);
        totalPage = totalCount / pageSize;
        totalPage = totalCount % pageSize > 0 ? ++totalPage : totalPage;

        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(pageSize);
        pagination.currentPageIndexProperty().addListener(paginationChangeListener);

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer integer) {

                System.out.println("페이지 버튼클릭->" + pagination.getCurrentPageIndex());
                pageListAll(pagination.getCurrentPageIndex());
                return new Label(String.format("현재의 페이지 : %d", pagination.getCurrentPageIndex()));
            }
        });


        cbAll.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CheckBox checkBox = (CheckBox) mouseEvent.getSource();
                boolean checkAll = checkBox.isSelected();
                boardTableView.getItems().forEach((board -> {
                    board.getCbDelete().setSelected(checkAll);
                }));
            }
        });

        boardTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && boardTableView.getSelectionModel().getSelectedItem() != null){
                    int boardNo = boardTableView.getSelectionModel().getSelectedItem().getBoard_no(); // 번호나 게시물을 클릭 해서 가져 옴. boardNo
                    try{
                        ReadController readController = (ReadController) SceneUtil.getInstance().getController(UI.READ.getPath());
                        readController.read(boardNo);
                        Parent root = SceneUtil.getInstance().getRoot();
                        SceneUtil.getInstance().switchScene(mouseEvent, UI.READ.getPath(), root);
                    }catch (IOException ignored){
                        ignored.printStackTrace();
                    }
                }

            }
        });
    }


    public void moveToInsert(ActionEvent event) throws IOException {
        System.out.println("글쓰기 화면이동");
        SceneUtil.getInstance().switchScene(event, UI.INSERT.getPath());
    }

    public void close(ActionEvent event){
        SceneUtil.getInstance().close(event);
    }

    public void deleteSelected(ActionEvent event){
        boardTableView.getItems().forEach((board -> {
            CheckBox cbDelete = board.getCbDelete();
            boolean checked = cbDelete.isSelected();
            if (checked){
                int boardNo = board.getBoard_no();
                boardService.delete(boardNo);  // 지워지게 하는
            }
        }));
        initialize(null,null);
    }

    private void changePage(){
        System.out.println("현재 페이지는 : " + pagination.getCurrentPageIndex());
    }

    public void pageListAll(int pageIndex){

        List<Board> boardList;
        boardList = boardService.pageList(pageIndex);
        System.out.println(boardList);
        totalCount = boardList.size();
//        System.out.println("11111 ->"+ totalCount + "--" + pageIndex);

        ObservableList<Board> list = FXCollections.observableArrayList(boardList);
        colBoardNo.setCellValueFactory(new PropertyValueFactory<>("Board_no")); //
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title")); //
        colWriter.setCellValueFactory(new PropertyValueFactory<>("Writer")); //
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("Reg_date")); //
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("Upd_date")); //
        colCbDelete.setCellValueFactory(new PropertyValueFactory<>("CbDelete")); //


        boardTableView.setItems(list);
        cbAll.setSelected(false);

        cbAll.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CheckBox checkBox = (CheckBox) mouseEvent.getSource();
                boolean checkAll = checkBox.isSelected();
                boardTableView.getItems().forEach((board -> {
                    board.getCbDelete().setSelected(checkAll);
                }));
            }
        });
    }

    /////////////////////////////////////////////////////
    private double x =0;
    private double y =0;
    @FXML
    private AnchorPane rootPane;


    @FXML
    public void handleMouseClicked(MouseEvent event){
        Platform.runLater(() ->{
           rootPane = (AnchorPane) event.getTarget();
           stage = (Stage) rootPane.getScene().getWindow();
           rootPane.setOnMousePressed((event1) -> {
               x = event1.getSceneX();
               y = event1.getSceneY();
           });
           rootPane.setOnMouseDragged((event1) -> {
               stage.setX(event1.getScreenX() - x);
               stage.setY(event1.getScreenY() - y);
               stage.setOpacity(0.8f); // 창 투명화
           });
           rootPane.setOnDragDone((event1) -> {
               stage = (Stage) rootPane.getScene().getWindow();
               stage.setOpacity(1.0f);
           });
           rootPane.setOnMouseReleased((event1 -> {
               stage = (Stage) rootPane.getScene().getWindow();
               stage.setOpacity(1.0f);
           }));
        });
    }

    public void moveToUpdate(ActionEvent event) throws IOException {

        boardNo = boardTableView.getSelectionModel().getSelectedItem().getBoard_no();

        UpdateController updateController = (UpdateController) SceneUtil.getInstance().getController(UI.UPDATE.getPath());
        updateController.read(boardNo);
        Parent root = SceneUtil.getInstance().getRoot();
        SceneUtil.getInstance().switchScene(event, UI.UPDATE.getPath(), root);
    }
}


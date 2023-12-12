package kr.or.yi.board.DTO;

import javafx.scene.control.CheckBox;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
// DB 테이블이랑 이거랑 일치 시켜야함 . 나중에 get set 써서 머를 해야함
public class Board {


    // DB 테이블이랑 이거랑 일치 시켜야함 . 나중에 get set 써서 머를 해야함
    private int board_no;
    private String title;
    private String writer;
    private String content;
    private String reg_date;
    private String upd_date;
    
    // 사진 관련 
    private File file;
    private InputStream isfile;

    // 게시판 목록에서 글 번호 옆에 체크박스 지우기
    private CheckBox cbDelete;


    public Board(){

        this("제목없음", "내용없음", "글쓴이 없음");

    }

    // 글 입력 받으면 생성자 함수 ~~?
    public Board(String title, String content, String writer){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.cbDelete = new CheckBox(); // 인스턴스 생성
    }

    public Board(String title, String content, String writer, File file){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.file = file;
        this.cbDelete = new CheckBox(); // 인스턴스 생성
    }

    public int getBoard_no() {
        return board_no;
    }



    public void setBoard_no(int board_no) {
        this.board_no = board_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getUpd_date() {
        return upd_date;
    }

    public void setUpd_date(String upd_date) {
        this.upd_date = upd_date;
    }


    // 체크박스 get set
    public CheckBox getCbDelete() {
        return cbDelete;
    }

    // File get set


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // 사진 get set
    public InputStream getIsfile() {
        return isfile;
    }

    public void setIsfile(InputStream isfile) {
        this.isfile = isfile;
    }






    public void setCbDelete(CheckBox cbDelete) {
        this.cbDelete = cbDelete;
    }

    @Override
    public String toString() {
        return "Board{" +
                "board_no=" + board_no +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", reg_date=" + reg_date +
                ", upd_date=" + upd_date +
                '}';
    }
}

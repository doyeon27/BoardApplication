package kr.or.yi.board.DAO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.yi.board.DTO.Board;

import java.io.*;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class BoardDAO extends JDBCConnection {
    SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private FileInputStream fis; // 전역 변수
    private Image image; // 전역 변수


    public int insert(Board board) {     // 메서드 추가
        int result = 0;
        String sql = "INSERT INTO Board(title, writer, content, file)" // 쿼리문 작성 해줘야함, 나중에 업데이트문 할때 추가하면 됨
                + "VALUES(?,?,?,?) "; // 나중에 ? 에 작성해주면 됨

        Savepoint savepoint = null;

        try {
            savepoint = con.setSavepoint("insertSavePoint");
            psmt = con.prepareStatement(sql); // 위의 커리문을 '?' <- 여기에 넣겠다
            psmt.setString(1, board.getTitle()); // 메인메뉴의 것을 여기 객체로 만든다 (get set 만든거 참조)
            psmt.setString(2, board.getWriter()); // 메인메뉴의 것을 여기 객체로 만든다 (get set 만든거 참조)
            psmt.setString(3, board.getContent()); // 메인메뉴의 것을 여기 객체로 만든다 (get set 만든거 참조)


            if (board.getIsfile() != null) {
                fis = new FileInputStream(board.getFile());
                psmt.setBinaryStream(4, (InputStream) fis, (int) board.getFile().length());

            }else {

                psmt.setBinaryStream(4,null);

            }


            result = psmt.executeUpdate(); // DB에 집어넣을거임

            if (result > 0) {
                con.commit(); // commit 되면 정식적으로 DB 연동 됨
            }
        } catch (SQLException | FileNotFoundException e) { // 인셉션이 발생할 수 있음
            try {
                con.rollback(savepoint); // 인셉션이 발생해서 실패 할 경우 롤백 해라. 시작하기 전 으로 (데이터의 안정성을 위해)
            } catch (SQLException e1) {
                e1.printStackTrace(); // 어떤 오류가 난지 확인하기 위해서 이거씀
            }
            System.out.println("게시글 등록 시, 예외 발생");
            e.printStackTrace();
        }

        return result;
    }

    // DB 소스를 자바에서 (조회)
    public List<Board> selectList() {
        LinkedList<Board> boardList = new LinkedList<>();
        String sql = " SELECT * "
                + " FROM Board "
                + " ORDER BY reg_date DESC";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) { // while문 돌면서 next result값 나옴
                Board board = new Board();  // 게시판에서 담을 객체하나 생성
                board.setBoard_no(rs.getInt("board_no"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setContent(rs.getString("content"));
                board.setReg_date(dataFormat.format(rs.getTimestamp("reg_date")));
                board.setUpd_date(dataFormat.format(rs.getTimestamp("upd_date")));
                boardList.add(board); // 위에 rs 한걸 여기에 넣음
            }
        } catch (Exception e) {
            System.out.println("게시글 목록 조회 시 예외 발생");
            e.printStackTrace(); // 이유 설명
        }

        return boardList; // Main에 boardList에 담김
    }


    public Board select(int board_no) {

        Board board = new Board();
        String sql = " SELECT * "
                + " FROM Board "
                + " WHERE board_no = ? ";
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, board_no);
            rs = psmt.executeQuery();

            if (rs.next()) {
                board.setBoard_no(rs.getInt("board_no"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setContent(rs.getString("content"));
                board.setReg_date(dataFormat.format(rs.getTimestamp("reg_date")));
                board.setUpd_date(dataFormat.format(rs.getTimestamp("upd_date")));
                InputStream is = rs.getBinaryStream("file");
                if (is != null ){
                    board.setIsfile(is);
                    is.close();
                }
//                is.close();
//                board.setIsfile(is);
//                OutputStream os = new FileOutputStream("photo.jpg");
//                byte[] fileBuffer = new byte[1024];
//                int size = 0;
//                while ((size = is.read(fileBuffer)) != -1) {
//                    os.write(fileBuffer, 0, size);
//                }
//                os.close();
//                is.close();
//                File file = new File("file:photo.jpg");
//                board.setFile(file);

            } else {
                System.out.println(board_no + "번 게시물은 존재 하지 않습니다.");
                board = null;
            }
        } catch (Exception e) {
            System.out.println("게시물 조회 시 예외 발생");
            e.printStackTrace();
        }

        return board;

    }

    public int update(Board board) {
        int result = 0;
        String sql = " UPDATE Board"
                + " SET title = ? "
                + " ,writer =  ? "
                + ", content = ? "
                + ", upd_date = now() "
                + " WHERE board_no = ? ";
        Savepoint savepoint = null;

        try {
            savepoint = con.setSavepoint("UpdateSavePoint");
            psmt = con.prepareStatement(sql);
            psmt.setString(1, board.getTitle());
            psmt.setString(2, board.getWriter());
            psmt.setString(3, board.getContent());
            psmt.setInt(4, board.getBoard_no());

            result = psmt.executeUpdate();

            if (result > 0) {
                con.commit();
            }
        } catch (SQLException e) {
            try {
                con.rollback(savepoint);
            } catch (SQLException e1) {
                System.out.println("게시글 수정 시 예외 발생");
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    public int delete(int board_no) {
        int result = 0;

        String sql = " DELETE FROM Board "
                + " WHERE board_no = ? ";
        Savepoint savepoint = null;
        try {
            savepoint = con.setSavepoint("DeleteSavePoint");
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, board_no);

            result = psmt.executeUpdate();

            if (result > 0) {
                con.commit();
            }
        } catch (SQLException e) {
            try {
                con.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("게시글 삭제 시 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 게시물이 몇개인지 알아보는 메소드
    public int selectTotalCount() {
        String sql = "SELECT count(*)"
                + " FROM Board"
                + " ORDER BY reg_date DESC";

        int count = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            System.out.println("총 게시물 : " + count);
        } catch (Exception e) {
            System.out.println("게시물 카운터 조회시 예외 발생");
            e.printStackTrace();
        }
        return count;
    }

    public List<Board> selectPageList(int pageNo) {
        LinkedList<Board> boardsList = new LinkedList<>();
        String sql = "SELECT * "
                + " FROM Board "
                + " ORDER BY reg_date DESC limit 10 offset ? ";
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, pageNo * 10);
            rs = psmt.executeQuery();
            while (rs.next()) {
                Board board = new Board();
                board.setBoard_no(rs.getInt("board_no"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setContent(rs.getString("content"));
                board.setReg_date(dataFormat.format(rs.getTimestamp("reg_date")));
                board.setUpd_date(dataFormat.format(rs.getTimestamp("upd_date")));
                boardsList.add(board);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return boardsList;
    }
}

package kr.or.yi.board.DAO;

import java.sql.*;

public class JDBCConnection {
    public Connection con; // 멤버 변수. DB 연동 해주게 하는 커넥션
    public Statement stmt; // 멤버 변수
    public PreparedStatement psmt; // 멤버 변수
    public ResultSet rs; // DB 에서 쿼리를 보내면 응답이 담기는 곳 . 결과 담김


    public JDBCConnection(){  // 기본 생성자
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");   // 연동한 jdbc Driver를 로딩시켜버림(임포트 같은)
            String url = "jdbc:mysql://localhost:3306/Board?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
            String id = "root";
            String pw = "107duseo!";

            con = DriverManager.getConnection(url, id, pw);   // DB 접속 , con <- DB연결 관리하는 놈
            con.setAutoCommit(false); // 명령했을때만 넘어감 -> 이거 Commit
            System.out.println("DB 연결 성공");
        }catch (Exception e){
            System.out.println("DB 연결 실패");
            e.printStackTrace(); // 왜 에러난지 알수있게 하는거
        }
    }


}

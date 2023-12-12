package kr.or.yi.board.DTO;

// DB 테이블 이름
public class User {

    // DB 테이블 에서 컬럼 이름 똑같이
    private int uid;
    private String userid;
    private String username;
    private String password;




    // DB 컬럼을 get set 해줘야 함
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 실행 시켜봐야 하기 때문(값이 제대로 오는지) to String() 해줘야 함
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    // 생성자

    public User(){
        this("제목없음", "내용없음", "글쓴이 없음");
    }

    public User(String userid, String username, String password) {
        this.userid = userid;
        this.username = username;
        this.password = password;
    }
    public User(int uid, String userid, String username, String password) {
        this.uid = uid;
        this.userid = userid;
        this.username = username;
        this.password = password;
    }

}

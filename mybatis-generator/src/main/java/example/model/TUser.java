package example.model;

public class TUser {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String rickName;

    /**
     * 0：保密、1：女性、2：男性
     */
    private Integer sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRickName() {
        return rickName;
    }

    public void setRickName(String rickName) {
        this.rickName = rickName == null ? null : rickName.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
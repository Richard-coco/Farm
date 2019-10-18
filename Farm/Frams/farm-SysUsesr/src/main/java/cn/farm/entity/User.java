package cn.farm.entity;

import lombok.Data;

@Data
public class User {
    private Integer userid;

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", postcode='" + postcode + '\'' +
                ", photo='" + photo + '\'' +
                ", introduction='" + introduction + '\'' +
                ", state=" + state +
                '}';
    }

    private String password;

    private Integer role;

    private String name;

    private String nickname;

    private String phone;

    private String email;

    private Integer address;

    private String postcode;

    private String photo;

    private String introduction;

    private Integer state;


}
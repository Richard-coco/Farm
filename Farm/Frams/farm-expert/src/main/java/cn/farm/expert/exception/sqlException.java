package cn.farm.expert.exception;

import java.sql.SQLException;

public class sqlException extends SQLException {

    public sqlException(String Message){
         super(Message);
        }
}

package cn.farm.login.dto;

public class Code {
    //以下为测试代码，随机生成验证码
    private static int newcode;

    public static int getNewcode() {
        return newcode;
    }

    public static void setNewcode() {
        newcode = (int) (Math.random() * 9999) + 1000;  //每次调用生成一位四位数的随机数
    }
}

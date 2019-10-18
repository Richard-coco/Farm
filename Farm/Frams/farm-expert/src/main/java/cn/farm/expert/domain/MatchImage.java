package cn.farm.expert.domain;

public class MatchImage {

    private int  matchId;

    private int imageId;

    private String matchinfo;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getMatchinfo() {
        return matchinfo;
    }

    public void setMatchinfo(String matchinfo) {
        this.matchinfo = matchinfo;
    }

    @Override
    public String toString() {
        return "MatchImage{" +
                "matchId=" + matchId +
                ", imageId=" + imageId +
                ", matchinfo='" + matchinfo + '\'' +
                '}';
    }
}

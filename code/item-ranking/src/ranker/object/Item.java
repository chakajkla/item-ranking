package ranker.object;

public class Item {

    private final String id;
    private String categoryId;
    private double clicks;
    private double impressions;
    private double cpc;

    private double score;
    private double groupScore;

    private double ctrScore;
    private double cpcScore;
    private double invScore;
    private double profitScore;
    private double ppvScore;

    public Item(String _id) {
        this.id = _id;
    }

    public void setcategoryId(String _categoryId) {
        this.categoryId = _categoryId;
    }

    public void setClicks(double _clicks) {
        this.clicks = _clicks;
    }

    public void setImpressions(double _impressions) {
        this.impressions = _impressions;
    }

    public void setCpc(double _cpc) {
        this.cpc = _cpc;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public double getClicks() {
        return clicks;
    }

    public double getImpressions() {
        return impressions;
    }

    public double getCpc() {
        return cpc;
    }

    public String getId() {
        return id;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public double getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(double groupScore) {
        this.groupScore = groupScore;
    }

    public double getCtrScore() {
        return ctrScore;
    }

    public void setCtrScore(double ctrScore) {
        this.ctrScore = ctrScore;
    }

    public double getCpcScore() {
        return cpcScore;
    }

    public void setCpcScore(double cpcScore) {
        this.cpcScore = cpcScore;
    }

    public double getInvScore() {
        return invScore;
    }

    public void setInvScore(double invScore) {
        this.invScore = invScore;
    }

    public double getProfitScore() {
        return profitScore;
    }

    public void setProfitScore(double profitScore) {
        this.profitScore = profitScore;
    }

    public double getPpvScore() {
        return ppvScore;
    }

    public void setPpvScore(double ppvScore) {
        this.ppvScore = ppvScore;
    }
}

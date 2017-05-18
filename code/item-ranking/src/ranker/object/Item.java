package ranker.object;

public class Item {

    private final String id;
    private String categoryId;
    private double clicks;
    private double impressions;
    private double cpc;

    private double score;

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
}

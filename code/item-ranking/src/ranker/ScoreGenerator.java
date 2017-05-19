package ranker;

import ranker.io.CSVUtil;
import ranker.object.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreGenerator {

    public static void main(String[] args) {
        generate();
    }

    public static void generate() {

        ArrayList<Item> items = CSVUtil.readItems();

        Map<String, ArrayList<Item>> categoryMap = new HashMap<>();

        items.forEach(item -> {
            if (!categoryMap.containsKey(item.getCategoryId())) {
                categoryMap.put(item.getCategoryId(), new ArrayList<>());
            }
            categoryMap.get(item.getCategoryId()).add(item);
        });

        categoryMap.forEach((k, v) -> {


            v.forEach(item -> {
                double score = calculateScore(item, v, items);
                item.setScore(score);
            });

            double categoryScore = calculateScore(v, "score");

            v.forEach(item -> {

                double finalScore = (item.getScore() + categoryScore)  / 2 ;

                item.setScore(finalScore);
                item.setGroupScore(categoryScore);
            });

            System.out.println("Category : " + k + ", size = " + v.size() +
                    " catScore = " + categoryScore +
                    " ctrScore = " + calculateScore(v, "ctr") +
                    " invScore = " + calculateScore(v, "inv") +
                    " cpcScore = " + calculateScore(v, "cpc") +
                    " profitScore = " + calculateScore(v, "profit") +
                    " ppvScore = " + calculateScore(v, "ppv")
            );

        });

        CSVUtil.writeItems(categoryMap);
    }

    private static double calculateScore(ArrayList<Item> v, String type) {
        double sumScore = 0;

        for (Item item : v) {
            if (type.equals("score"))
                sumScore += item.getScore();
            else if (type.equals("ctr"))
                sumScore += item.getCtrScore();
            else if (type.equals("inv"))
                sumScore += item.getInvScore();
            else if (type.equals("cpc"))
                sumScore += item.getCpcScore();
            else if (type.equals("profit"))
                sumScore += item.getProfitScore();
            else if (type.equals("ppv"))
                sumScore += item.getPpvScore();
        }

        return sumScore / (double) v.size();

    }

    private static double calculateScore(Item item, ArrayList<Item> group, ArrayList<Item> allItems) {

        double ctr = FeatureExtractor.getCTR(item);
        double inv = FeatureExtractor.getInv(item);
        double cpc = FeatureExtractor.getNormalizedCPC(item, group, allItems);
        double profit = FeatureExtractor.getNormalizedProfit(item, group, allItems);
        double ppv = FeatureExtractor.getNormalizedProfitPerView(item, group, allItems);

        item.setCtrScore(ctr);
        item.setInvScore(inv);
        item.setCpcScore(cpc);
        item.setProfitScore(profit);
        item.setPpvScore(ppv);

        double featureScore = (ctr + inv + cpc + profit + ppv);

        return featureScore / FeatureExtractor.NUM_FEATURES;

    }

}

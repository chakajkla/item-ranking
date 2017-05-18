package ranker;

import ranker.io.CSVUtil;
import ranker.object.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreGenerator {

    public static void main(String[] args){
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
                double score = calculateScore(item, v);
                item.setScore(score);
            });

            double categoryScore = calculateScore(v);
            v.forEach(item -> {
                item.setScore((item.getScore() + categoryScore) / 2);
            });

            System.out.println("Category : " + k + ", size = " + v.size() + " catScore = "+categoryScore);

        });

        CSVUtil.writeItems(categoryMap);
    }

    private static double calculateScore(ArrayList<Item> v) {
        double sumScore = 0;

        for (Item item : v) {
            sumScore += item.getScore();
        }

        return sumScore / (double) v.size();

    }

    private static double calculateScore(Item item, ArrayList<Item> group) {

        double featureScore = (FeatureExtractor.getCTR(item) + FeatureExtractor.getInv(item) + FeatureExtractor.getNormalizedCPC(item, group)
                + FeatureExtractor.getNormalizedProfit(item, group));

        return featureScore / FeatureExtractor.NUM_FEATURES;

    }

}

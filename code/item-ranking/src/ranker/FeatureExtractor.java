package ranker;

import ranker.object.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeatureExtractor {

    public static final double NUM_FEATURES = 5;

    public static Map<String, Double> cacheMap = new HashMap<>();

    /***
     * 1.CTR (clicks/impressions)
     */
    public static double getCTR(Item item) {

        if (item.getImpressions() == 0)
            return 0;

        return item.getClicks() / item.getImpressions();
    }

    /***
     * 2.Normalized cpc (within category)
     * @param item
     * @param group
     * @return
     */
    public static double getNormalizedCPC(Item item, ArrayList<Item> group, ArrayList<Item> allItems) {

        if (group.size() == 1) {
            return 1;
        }

        //within category
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Item it : group) {
            max = Math.max(max, it.getCpc());
            min = Math.min(min, it.getCpc());
        }

        double within = (item.getCpc() - min) / (max - min);

        //across category
        String maxKey = "cpc_max";
        String minKey = "cpc_min";

        if (cacheMap.containsKey(maxKey) && cacheMap.containsKey(minKey)) {
            max = cacheMap.get(maxKey);
            min = cacheMap.get(minKey);
        } else {
            max = Double.MIN_VALUE;
            min = Double.MAX_VALUE;
            for (Item it : allItems) {
                max = Math.max(max, it.getCpc());
                min = Math.min(min, it.getCpc());
            }
            cacheMap.put(maxKey, max);
            cacheMap.put(minKey, min);
        }

        double across = (item.getCpc() - min) / (max - min);

        return (within + across) / 2;
    }

    /***
     *
     3.If click = 0(clicks + 1 / impressions), 1 otherwise - for punishing item with zero clicks that
     was shown a lot and no clicks
     */

    public static double getInv(Item item) {

        if (item.getClicks() > 0) {
            return 1;
        }

        if (item.getImpressions() == 0) {
            return 0;
        }

        return (item.getClicks() + 1) / item.getImpressions();
    }

    /***
     * 4.Normalized profits per view = (cpc * clicks) / impressions (within category)
     */
    public static double getNormalizedProfitPerView(Item item, ArrayList<Item> group, ArrayList<Item> allItems) {

        if (group.size() == 1) {
            if (getProfitPerView(item) != 0) {
                return 1;
            }
            return 0;
        }

        //within category
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Item it : group) {
            max = Math.max(max, getProfitPerView(it));
            min = Math.min(min, getProfitPerView(it));
        }

        double within = (getProfitPerView(item) - min) / (max - min);

        //across category
        String maxKey = "ppv_max";
        String minKey = "ppv_min";

        if (cacheMap.containsKey(maxKey) && cacheMap.containsKey(minKey)) {
            max = cacheMap.get(maxKey);
            min = cacheMap.get(minKey);
        } else {
            max = Double.MIN_VALUE;
            min = Double.MAX_VALUE;
            for (Item it : allItems) {
                max = Math.max(max, getProfitPerView(it));
                min = Math.min(min, getProfitPerView(it));
            }
            cacheMap.put(maxKey, max);
            cacheMap.put(minKey, min);
        }


        double across = (getProfitPerView(item) - min) / (max - min);

        return (within + across) / 2;
    }

    public static double getProfitPerView(Item item) {
        if (item.getImpressions() == 0) {
            return 0;
        }
        return (item.getCpc() * item.getClicks()) / item.getImpressions();
    }

    public static double getNormalizedProfit(Item item, ArrayList<Item> group, ArrayList<Item> allItems) {

        if (group.size() == 1) {
            if (getProfit(item) != 0) {
                return 1;
            }
            return 0;
        }

        //within category
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Item it : group) {
            max = Math.max(max, getProfit(it));
            min = Math.min(min, getProfit(it));
        }

        double within = (getProfit(item) - min) / (max - min);

        //across category
        String maxKey = "profit_max";
        String minKey = "profit_min";

        if (cacheMap.containsKey(maxKey) && cacheMap.containsKey(minKey)) {
            max = cacheMap.get(maxKey);
            min = cacheMap.get(minKey);
        } else {
            max = Double.MIN_VALUE;
            min = Double.MAX_VALUE;
            for (Item it : allItems) {
                max = Math.max(max, getProfit(it));
                min = Math.min(min, getProfit(it));
            }
            cacheMap.put(maxKey, max);
            cacheMap.put(minKey, min);
        }

        double across = (getProfit(item) - min) / (max - min);

        return (within + across) / 2;
    }

    public static double getProfit(Item item) {
        if (item.getImpressions() == 0) {
            return 0;
        }
        return (item.getCpc() * item.getClicks());
    }

}

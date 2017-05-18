package ranker;

import ranker.object.Item;

import java.util.ArrayList;

public class FeatureExtractor {

    public static final double NUM_FEATURES = 4;

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
    public static double getNormalizedCPC(Item item, ArrayList<Item> group) {

        if(group.size() == 1){
            return 1;
        }

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Item it : group) {
            max = Math.max(max, it.getCpc());
            min = Math.min(min, it.getCpc());
        }

        return (item.getCpc() - min) / (max - min);
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
    public static double getNormalizedProfit(Item item, ArrayList<Item> group) {

        if(group.size() == 1){
            return 1;
        }

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Item it : group) {
            double prof = getProfit(it);
            max = Math.max(max, prof);
            min = Math.min(min, prof);
        }

        return (getProfit(item) - min) / (max - min);
    }

    public static double getProfit(Item item) {
        if (item.getImpressions() == 0) {
            return 0;
        }
        return (item.getCpc() * item.getClicks()) / item.getImpressions();
    }

}

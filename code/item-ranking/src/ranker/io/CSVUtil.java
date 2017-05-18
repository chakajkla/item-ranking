package ranker.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

import ranker.object.Item;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;

public class CSVUtil {

    @Test
    public void testReadItems() {
        assertTrue(readItems().size() == 10000);
    }


    public static ArrayList<Item> readItems() {
        Path FILE_PATH = Paths.get("data", "product_scoring.csv");
        List<String> itemStrings = new ArrayList<>();
        try (Stream<String> stream = Files.lines(FILE_PATH)) {

            itemStrings = stream.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        itemStrings.remove(0); // remove header

        ArrayList<Item> items = new ArrayList<>();

        itemStrings.forEach(item -> {
            String[] sp = item.split(",");
            Item i = new Item(sp[0]);
            i.setcategoryId(sp[1]);
            i.setCpc(Double.parseDouble(sp[2]));
            i.setClicks(Double.parseDouble(sp[3]));
            if (sp.length == 5) {
                i.setImpressions(Double.parseDouble(sp[4]));
            }

            items.add(i);
        });

        return items;
    }

    public static void writeItems(Map<String, ArrayList<Item>> categoryMap) {


        File f = new File("data/product_scoring_scored.csv");
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path FILE_PATH = Paths.get("data", "product_scoring_scored.csv");


        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write("product_id;category_id;cpc;clicks;views;score;groupscore\n"); //header

            categoryMap.forEach((k, v) -> {

                v.forEach(item -> {
                    try {

                        writer.write(item.getId() + ";" +
                                item.getCategoryId() + ";" +
                                item.getCpc() + ";" +
                                item.getClicks() + ";" +
                                item.getImpressions() + ";" +
                                item.getScore() + ";" +
                                item.getGroupScore()+"\n"); //data

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

package com.gildedrose;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GildedRoseGoldenMasterTest {
    private static final String[] NAMES = {
        "+5 Dexterity Vest",
        "Aged Brie",
        "Sulfuras, Hand of Ragnaros",
        "Backstage passes to a TAFKAL80ETC concert",
        "Elixir of the Mongoose"
    };

    @Test
    void goldenMasterLegacyTest() {

        for (String name : NAMES) {
            for (int sellIn = -3; sellIn <= 15; sellIn++) {
                for (int quality = 0; quality <= 50; quality++) {
                    Item oldItem = new Item(name, sellIn, quality);
                    Item newItem = new Item(name, sellIn, quality);

                    new GildedRoseLegacy(new Item[] { oldItem }).updateQuality();
                    new GildedRose(new Item[] { newItem }).updateQuality();

                    String caseCheck = name + " / sellIn=" + sellIn + " / quality=" + quality;
                    assertEquals(oldItem.sellIn, newItem.sellIn, caseCheck);
                    assertEquals(oldItem.quality, newItem.quality, caseCheck);
                }
            }
        }

    }
}

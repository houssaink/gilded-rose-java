package com.gildedrose;

import java.util.Locale;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_TAFKA_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String CONJURED = "conjured";

    public void updateQuality() {
        for (Item item : items) {
            if (item.name.equals(SULFURAS)) {
                continue;
            }
            item.sellIn = item.sellIn - 1;
            if (item.name.equals(BACKSTAGE_TAFKA_CONCERT)) {
                if (item.sellIn >= 0) {
                    this.upgradeQualityConcert(item);
                } else {
                    item.quality = 0;
                }

            } else if (item.name.equals(AGED_BRIE)) {
                this.upgradeQuality(item);
            } else {
                this.degradeQuality(item);
            }

        }
    }


    private void upgradeQuality(Item item) {
        int qualityGained = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(50, item.quality + qualityGained);

    }

    private void degradeQuality(Item item) {
        int qualityLost = item.name.toLowerCase(Locale.ROOT).contains(CONJURED)
            ? (item.sellIn < 0 ? 4 : 2)
            : (item.sellIn < 0 ? 2 : 1);
        item.quality = Math.max(0, item.quality - qualityLost);
    }


    private void upgradeQualityConcert(Item item) {
        int qualityGained = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
        item.quality = Math.min(50, item.quality + qualityGained);
    }
}

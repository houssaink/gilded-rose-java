package com.gildedrose;

import java.util.Locale;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
                continue;
            }
            if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (item.sellIn > 0) {
                    this.manageConcert(item);
                } else {
                    item.quality = 0;
                }
                item.sellIn = item.sellIn - 1;
            } else {
                item.sellIn = item.sellIn - 1;
                if (item.name.equals("Aged Brie")) {
                    this.upgradeItem(item);
                } else {
                    this.degradeItem(item);
                }
            }
        }
    }


    private void upgradeItem(Item item) {
        int qualityGained = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(50, item.quality + qualityGained);

    }

    private void degradeItem(Item item) {
        int qualityLost = item.name.toLowerCase(Locale.ROOT).contains("conjured")
            ? (item.sellIn < 0 ? 4 : 2)
            : (item.sellIn < 0 ? 2 : 1);
        item.quality = Math.max(0, item.quality - qualityLost);
    }


    private void manageConcert(Item item) {
        int upgradeQuality = item.sellIn < 6 ? 3 : item.sellIn < 11 ? 2 : 1;
        item.quality = Math.min(50, item.quality + upgradeQuality);
    }
}

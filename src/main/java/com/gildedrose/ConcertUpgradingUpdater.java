package com.gildedrose;

public class ConcertUpgradingUpdater implements ItemUpdater {

    @Override
    public void updateItem(Item item) {
        item.sellIn = item.sellIn - 1;
        if (item.sellIn < 0) {
            item.quality = 0;
            return;
        }
        int qualityGained = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
        item.quality = Math.min(50, item.quality + qualityGained);
    }
}

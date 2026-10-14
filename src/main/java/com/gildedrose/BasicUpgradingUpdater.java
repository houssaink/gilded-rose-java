package com.gildedrose;

public class BasicUpgradingUpdater implements ItemUpdater {
    private final int base;
    private final int multiplier;

    public BasicUpgradingUpdater(int base, int multiplier) {
        this.base = base;
        this.multiplier = multiplier;
    }

    @Override
    public void updateItem(Item item) {
        item.sellIn = item.sellIn - 1;
        int qualityGained = item.sellIn < 0 ? base * multiplier : base;
        item.quality = Math.min(50, item.quality + qualityGained);
    }
}

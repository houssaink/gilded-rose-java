package com.gildedrose;

public class BasicDegradingUpdater implements ItemUpdater {

    private final int base;
    private final int multiplier;

    public BasicDegradingUpdater(int base, int multiplier) {
        this.base = base;
        this.multiplier = multiplier;
    }

    @Override
    public void updateItem(Item item) {
        item.sellIn = item.sellIn - 1;
        int qualityLost = item.sellIn < 0 ? multiplier * base : base;
        item.quality = Math.max(0, item.quality - qualityLost);
    }
}

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

    private static final ItemUpdater STANDARD_UPDATER = new BasicDegradingUpdater(1, 2);
    private static final ItemUpdater CONJURED_UPDATER = new BasicDegradingUpdater(2, 2);
    private static final ItemUpdater AGED_BRIE_UPDATER = new BasicUpgradingUpdater(1, 2);
    private static final ItemUpdater BACKSTAGE_CONCERT_UPDATER = new ConcertUpgradingUpdater();
    private static final ItemUpdater SULFURAS_UPDATER = new NoOperationUpdater();


    public void updateQuality() {
        for (Item item : items) {
            this.updaterFor(item.name).updateItem(item);
        }
    }

    private ItemUpdater updaterFor(String name) {
       return switch (name) {
           case SULFURAS -> SULFURAS_UPDATER;
           case AGED_BRIE -> AGED_BRIE_UPDATER;
           case BACKSTAGE_TAFKA_CONCERT -> BACKSTAGE_CONCERT_UPDATER;
           default ->
                name.toLowerCase(Locale.ROOT).contains(CONJURED)
                    ? CONJURED_UPDATER
                    :  STANDARD_UPDATER;
        };

    }

}

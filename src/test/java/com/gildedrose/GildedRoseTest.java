package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void specialItemMustNotBeEdited() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 1, 80);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(1, item.sellIn);
        assertEquals(80, item.quality);
    }


    @Test
    void itemLosesOneQualityPointsBeforeExpiration() {
        Item item = new Item("Mana Cake", 5, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(4, item.sellIn);
        assertEquals(39, item.quality);
    }
    @Test
    void itemLosesTwoQualityPointsAfterExpiration() {
        Item item = new Item("Mana Cake", -1, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-2, item.sellIn);
        assertEquals(38, item.quality);
    }
    @Test
    void itemLastDayOfSellIn() {
        Item item = new Item("Mana Cake", 0, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-1, item.sellIn);
        assertEquals(38, item.quality);
    }
    @Test
    void itemQualityNeverGoesBelowZero() {
        Item item = new Item("Mana Cake", -2 , 1);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-3, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void conjuredItemLosesTwoQualityPointsBeforeExpiration() {
        Item item = new Item("Conjured Mana Cake", 5, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(4, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void conjuredItemLosesFourQualityPointsAfterExpiration() {
        Item item = new Item("Conjured Mana Cake", 0, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-1, item.sellIn);
        assertEquals(36, item.quality);
    }

    @Test
    void conjuredItemLastDayOfSellIn() {
        Item item = new Item("Conjured Mana Cake", 1, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(0, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void conjuredItemQualityNeverGoesBelowZero() {
        Item item = new Item("Conjured Mana Cake", -2 , 1);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-3, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void itemBeingRecognizedAsConjured() {
        Item item = new Item("The conjured demon sword", 5, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(4, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void itemBeingRecognizedAsConjuredCAP() {
        Item item = new Item("The CONJURED demon sword", 5, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(4, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void backstagePassQualityNeverExceedsFifty() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(9, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePassQualityDropsToZeroOnConcertDay() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }


    @Test
    void emptyItems() {
        GildedRose app = new GildedRose(new Item[] {});

        app.updateQuality();

        assertEquals(0, app.items.length);
    }

    @Test
    void agedBrieIncreasesOneQualityPointBeforeExpiration() {
        Item item = new Item("Aged Brie", 5, 10);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(4, item.sellIn);
        assertEquals(11, item.quality);
    }

    @Test
    void agedBrieIncreasesTwoQualityPointsAfterExpiration() {
        Item item = new Item("Aged Brie", 0, 10);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-1, item.sellIn);
        assertEquals(12, item.quality);
    }

    @Test
    void agedBrieQualityNeverExceedsFifty() {
        Item item = new Item("Aged Brie", -1, 49);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-2, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePassIncreasesOneQualityPointWhenConcertIsElevenDaysAway() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(10, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePassIncreasesOneQualityPointWhenConcertIsFarAway() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(14, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePassIncreasesTwoQualityPointsWhenConcertIsTenDaysAway() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(9, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePassIncreasesTwoQualityPointsWhenConcertIsSixDaysAway() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 20);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(5, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePassIncreasesThreeQualityPointsWhenConcertIsFiveDaysAway() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(4, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePassIncreasesThreeQualityPointsWhenConcertIsOneDayAway() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 20);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(0, item.sellIn);
        assertEquals(23, item.quality);
    }


    @Test
    void backstagePassQualityStaysAtZeroAfterConcert() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 40);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();

        assertEquals(-2, item.sellIn);
        assertEquals(0, item.quality);
    }

}

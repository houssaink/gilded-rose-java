package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_TAFKA_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    private static final String AGED_BRIE = "Aged Brie";
    private static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";
    private static final String DEXTERITY_VEST = "+5 Dexterity Vest";

    private Item updateOnce(String name, int sellIn, int quality) {
        Item item = new Item(name, sellIn, quality);
        GildedRose app = new GildedRose(new Item[] { item });
        app.updateQuality();
        return item;
    }

    @Test
    void specialItemMustNotBeEdited() {
        Item item = updateOnce(SULFURAS, 1, 80);

        assertEquals(1, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    void itemLosesOneQualityPointsBeforeExpiration() {
        Item item = updateOnce(DEXTERITY_VEST, 5, 40);

        assertEquals(4, item.sellIn);
        assertEquals(39, item.quality);
    }

    @Test
    void itemLosesTwoQualityPointsAfterExpiration() {
        Item item = updateOnce(DEXTERITY_VEST, -1, 40);

        assertEquals(-2, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void itemLastDayOfSellIn() {
        Item item = updateOnce(DEXTERITY_VEST, 0, 40);

        assertEquals(-1, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void itemQualityNeverGoesBelowZero() {
        Item item = updateOnce(DEXTERITY_VEST, -2, 1);

        assertEquals(-3, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void conjuredItemLosesTwoQualityPointsBeforeExpiration() {
        Item item = updateOnce(CONJURED_MANA_CAKE, 5, 40);

        assertEquals(4, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void conjuredItemLosesFourQualityPointsAfterExpiration() {
        Item item = updateOnce(CONJURED_MANA_CAKE, 0, 40);

        assertEquals(-1, item.sellIn);
        assertEquals(36, item.quality);
    }

    @Test
    void conjuredItemLastDayOfSellIn() {
        Item item = updateOnce(CONJURED_MANA_CAKE, 1, 40);

        assertEquals(0, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void conjuredItemQualityNeverGoesBelowZero() {
        Item item = updateOnce(CONJURED_MANA_CAKE, -2, 1);

        assertEquals(-3, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void itemBeingRecognizedAsConjured() {
        Item item = updateOnce("The conjured demon sword", 5, 40);

        assertEquals(4, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void itemBeingRecognizedAsConjuredCAP() {
        Item item = updateOnce("The CONJURED demon sword", 5, 40);

        assertEquals(4, item.sellIn);
        assertEquals(38, item.quality);
    }

    @Test
    void backstagePassQualityNeverExceedsFifty() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 10, 49);

        assertEquals(9, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePassQualityDropsToZeroOnConcertDay() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 0, 40);

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
        Item item = updateOnce(AGED_BRIE, 5, 10);

        assertEquals(4, item.sellIn);
        assertEquals(11, item.quality);
    }

    @Test
    void agedBrieIncreasesTwoQualityPointsAfterExpiration() {
        Item item = updateOnce(AGED_BRIE, 0, 10);

        assertEquals(-1, item.sellIn);
        assertEquals(12, item.quality);
    }

    @Test
    void agedBrieQualityNeverExceedsFifty() {
        Item item = updateOnce(AGED_BRIE, -1, 49);

        assertEquals(-2, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePassIncreasesOneQualityPointWhenConcertIsElevenDaysAway() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 11, 20);

        assertEquals(10, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePassIncreasesOneQualityPointWhenConcertIsFarAway() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 15, 20);

        assertEquals(14, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePassIncreasesTwoQualityPointsWhenConcertIsTenDaysAway() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 10, 20);

        assertEquals(9, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePassIncreasesTwoQualityPointsWhenConcertIsSixDaysAway() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 6, 20);

        assertEquals(5, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePassIncreasesThreeQualityPointsWhenConcertIsFiveDaysAway() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 5, 20);

        assertEquals(4, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePassIncreasesThreeQualityPointsWhenConcertIsOneDayAway() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 1, 20);

        assertEquals(0, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePassQualityStaysAtZeroAfterConcert() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, -1, 40);

        assertEquals(-2, item.sellIn);
        assertEquals(0, item.quality);
    }


    @Test
    void sulfurasNeverChangesEvenWithNegativeSellIn() {
        Item item = updateOnce(SULFURAS, -1, 80);

        assertEquals(-1, item.sellIn);
        assertEquals(80, item.quality);
    }


    @Test
    void itemWithZeroQualityStaysAtZeroBeforeExpiration() {
        Item item = updateOnce(DEXTERITY_VEST, 5, 0);

        assertEquals(4, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void itemWithZeroQualityStaysAtZeroAfterExpiration() {
        Item item = updateOnce(DEXTERITY_VEST, -1, 0);

        assertEquals(-2, item.sellIn);
        assertEquals(0, item.quality);
    }


    @Test
    void conjuredItemQualityNeverGoesBelowZeroBeforeExpiration() {
        Item item = updateOnce(CONJURED_MANA_CAKE, 5, 1);

        assertEquals(4, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void conjuredItemQualityNeverGoesBelowZeroAfterExpiration() {
        Item item = updateOnce(CONJURED_MANA_CAKE, -1, 3);

        assertEquals(-2, item.sellIn);
        assertEquals(0, item.quality);
    }


    @Test
    void agedBrieAtFiftyStaysAtFiftyBeforeExpiration() {
        Item item = updateOnce(AGED_BRIE, 5, 50);

        assertEquals(4, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void agedBrieAtFiftyStaysAtFiftyAfterExpiration() {
        Item item = updateOnce(AGED_BRIE, -1, 50);

        assertEquals(-2, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void agedBrieIsCappedAtFiftyWhenTheDoubleGainWouldExceedIt() {
        // 49 + 2 = 51, doit rester à 50
        Item item = updateOnce(AGED_BRIE, 0, 49);

        assertEquals(-1, item.sellIn);
        assertEquals(50, item.quality);
    }


    @Test
    void backstagePassAtFiftyStaysAtFiftyWhenGainIsThree() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 5, 50);

        assertEquals(4, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePassIsCappedAtFiftyWhenGainIsThree() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 5, 48);

        assertEquals(4, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePassIsCappedAtFiftyWhenGainIsTwo() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 8, 49);

        assertEquals(7, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePassOnLastDayBeforeConcertStillGainsThree() {
        Item item = updateOnce(BACKSTAGE_TAFKA_CONCERT, 1, 30);

        assertEquals(0, item.sellIn);
        assertEquals(33, item.quality);
    }


    @Test
    void eachItemIsUpdatedIndependently() {
        Item brie = new Item(AGED_BRIE, 5, 10);
        Item sulfuras = new Item(SULFURAS, 0, 80);
        Item vest = new Item(DEXTERITY_VEST, 5, 20);
        Item conjured = new Item(CONJURED_MANA_CAKE, 5, 20);
        GildedRose app = new GildedRose(new Item[] { brie, sulfuras, vest, conjured });

        app.updateQuality();

        assertEquals(11, brie.quality);
        assertEquals(80, sulfuras.quality);
        assertEquals(19, vest.quality);
        assertEquals(18, conjured.quality);
    }

    @Test
    void itemsAreUpdatedAgainOnTheFollowingDay() {
        Item item = new Item(DEXTERITY_VEST, 1, 10);
        GildedRose app = new GildedRose(new Item[] { item });

        app.updateQuality();   // jour 1 : sellIn 0, qualité 9
        app.updateQuality();   // jour 2 : sellIn -1, qualité 7 (perte doublée)

        assertEquals(-1, item.sellIn);
        assertEquals(7, item.quality);
    }
}

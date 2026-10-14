# Gilded Rose starting position in Java

Refactoring of the Gilded Rose legacy code, plus the new **Conjured** items feature (quality degrades twice as fast as normal items).

## Branches

| Branch | Approach | Java |
|---|---|---|
| `main` | Simple and readable: one class, small named methods, no nested `if` | 8 |
| `strategy-pattern` | Exploration: one `ItemUpdater` per behavior (Strategy pattern) | 17+ |

`main` is the main deliverable. `strategy-pattern` is an alternative showing how the code could evolve if many more item types appeared. Both pass the same tests.

> This branch requires Java 17 or higher (arrow-style `switch`). `main` keeps the original Java 8 setup.

## How to run the tests

```
./gradlew cleanTest test
```

## What is tested

- **Unit tests** (`GildedRoseTest`): one test per business rule and edge case (quality never below 0 or above 50, quality drop after the sell-by date, Sulfuras never changing, backstage pass thresholds, Conjured items, several items at once). A small `updateOnce` helper removes the repetition.
- **Golden master** (`GildedRoseGoldenMasterTest`): compares the original implementation (`GildedRoseLegacy`, kept in the test sources) with the refactored one on every combination of name, `sellIn` and quality. It proves the refactoring did not change the existing behavior. Conjured items are excluded because the legacy code does not handle them.

## Design choices

- **Methods and classes are named after the behavior, not the item** (`upgrade`, `degrade`, ...), so another item with the same rules can reuse them.
- **`sellIn` is decremented once, before the quality is computed**, for every item except Sulfuras. The backstage pass thresholds are shifted accordingly (`< 10` and `< 5` instead of `< 11` and `< 6`).
- **Conjured items are detected with a case-insensitive `contains("conjured")`**, so names like `Conjured Mana Cake` or `The conjured demon sword` both work.
- **`Item` is not modified**, as required by the exercise.

## Strategy branch details

- `ItemUpdater` defines a single method, `updateItem(Item item)`.
- `BasicDegradingUpdater(base, multiplier)` serves standard and Conjured items (`(1, 2)` and `(2, 2)`).
- `BasicUpgradingUpdater(base, multiplier)` serves Aged Brie.
- `ConcertUpgradingUpdater` handles the backstage pass thresholds.
- `NoOperationUpdater` handles Sulfuras.
- `GildedRose.updaterFor` picks the right updater from the item name.

Known limit: adding a new item name still means editing `updaterFor`. A `Map` from name to updater, or updaters declaring which items they support, would remove that step. It was left out to keep the code simple for four item types.

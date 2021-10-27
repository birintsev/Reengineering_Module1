package birintsev;

import java.util.Random;
import java.util.stream.IntStream;
import static birintsev.TestConstants.CART_CAPACITY_VALID_MAX;
import static birintsev.TestConstants.ITEM_PRICE_VALID_MAX;
import static birintsev.TestConstants.ITEM_QUANTITY_VALID_MAX;
import static birintsev.TestConstants.ITEM_QUANTITY_VALID_MIN;
import static birintsev.TestConstants.ITEM_TITLE_LENGTH_VALID_MAX;
import static birintsev.TestConstants.TOLERANCE;
import static birintsev.TestConstants.ITEM_PRICE_VALID_MIN;

public class RandomTestDataUtils {

    static final Random RANDOM = new Random();

    private RandomTestDataUtils() {
        // This class is not expected to be instantiated.
    }

    static Item randomValidItem() {
        return new Item(randomValidItemTitle(), randomValidItemPrice(), randomValidItemsQuantity(), randomItemType());
    }

    /**
     * Creates a random string of specified length
     */
    static String randomStringOfLength(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        IntStream.range(0, length).forEach(integer -> stringBuilder.append((char) integer));
        return stringBuilder.toString();
    }

    static Item.Type randomItemType() {
        return Item.Type.values()[RANDOM.nextInt(Item.Type.values().length)];
    }

    static String randomInvalidItemTitleLong() {
        return randomStringOfLength(ITEM_TITLE_LENGTH_VALID_MAX + 1);
    }

    static int randomInt(int fromInclusive, int toExclusive) {
        return RANDOM.ints(fromInclusive, toExclusive).findFirst().getAsInt();
    }

    static double randomDouble(double fromInclusive, double toExclusive) {
        return RANDOM.doubles(fromInclusive, toExclusive).findFirst().getAsDouble();
    }

    static String randomValidItemTitle() {
        return randomStringOfLength(randomInt(1, ITEM_TITLE_LENGTH_VALID_MAX + 1));
    }

    static double randomValidItemPrice() {
        return randomDouble(ITEM_PRICE_VALID_MIN, ITEM_PRICE_VALID_MAX);
    }

    static int randomValidItemsQuantity() {
        return randomInt(1, ITEM_QUANTITY_VALID_MAX + 1);
    }

    static double randomInvalidNegativeItemPrice() {
        double randomPrice = RANDOM.nextDouble() * RANDOM.nextInt();
        return randomPrice < 0 ? randomPrice : -1 * randomPrice;
    }

    static double randomInvalidItemPriceLarge() {
        return RANDOM.doubles(ITEM_PRICE_VALID_MAX + TOLERANCE, Double.MAX_VALUE)
            .findFirst()
            .getAsDouble();
    }

    static int randomInvalidItemsQuantityMin() {
        return ITEM_QUANTITY_VALID_MIN - Math.max(1, Math.abs(RANDOM.nextInt()));
    }

    static int randomInvalidItemsQuantityMax() {
        return ITEM_QUANTITY_VALID_MAX + Math.max(1, Math.abs(RANDOM.nextInt()));
    }

    static int randomValidCartCapacity() {
        return randomInt(1, CART_CAPACITY_VALID_MAX + 1);
    }

    static int randomGreaterThanMaxCartCapacity() {
        return randomInt(CART_CAPACITY_VALID_MAX + 1, Integer.MAX_VALUE);
    }
}

package birintsev;

import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShoppingCartTest {

    private static final double TOLERANCE = Double.MIN_VALUE;

    private static final Random RANDOM = new Random();

    private static final int ITEM_TITLE_MAX_VALID_LENGTH = 32;

    private static final int ITEMS_MIN_VALID_QUANTITY = 1;

    private static final int ITEMS_MAX_VALID_QUANTITY = 1000;

    private static final double ITEMS_MAX_VALID_PRICE = 1000 - TOLERANCE;

    private static final double VALID_MIN_ITEMS_PRICE = 0 + TOLERANCE;

    private static final int CART_MAX_ITEMS_CAPACITY = 99;

    @Test
    void addItem_emptyTitleIllegalArgumentException() {
        Item item = validItem();
        item.setTitle(stringOfLength(0));
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_longTitleIllegalArgumentException() {
        Item item = validItem();
        item.setTitle(invalidLongRandomTitle());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_negativePriceIllegalArgumentException() {
        Item item = validItem();
        item.setPrice(invalidRandomNegativeItemPrice());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_largePriceIllegalArgumentException() {
        Item item = validItem();
        item.setPrice(invalidRandomLargeItemPrice());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemPriceIllegalArgumentException() {
        Item item = validItem();
        item.setPrice(invalidRandomLargeItemPrice());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemPriceZeroIllegalArgumentException() {
        Item item = validItem();
        item.setPrice(0);
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemPriceMinInvalidValueIllegalArgumentException() {
        Item item = validItem();
        item.setPrice(minInvalidItemsPrice());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemQuantityMinThanInvalidValueIllegalArgumentException() {
        Item item = validItem();
        item.setQuantity(invalidRandomItemsQuantityMin());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemQuantityMaxThanInvalidValueIllegalArgumentException() {
        Item item = validItem();
        item.setQuantity(invalidRandomItemsQuantityMax());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_validItemToEmptyCart() {
        addItemToCart(emptyCart(), validItem());
    }

    @Test
    void cartCapacityTest() {
        ShoppingCart shoppingCart = emptyCart();
        RANDOM.ints(randomValidCartCapacity()).forEach(itemIndex -> addItemToCart(shoppingCart, validItem()));
    }

    @Test
    void indexOutOfBoundsExceptionOnMaxCapacity() {
        ShoppingCart shoppingCart = emptyCart();
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> RANDOM.ints(CART_MAX_ITEMS_CAPACITY + 1)
                .forEach(itemIndex -> addItemToCart(shoppingCart, validItem()))
        );
    }

    @Test
    void indexOutOfBoundsExceptionOnGreaterThanMaxCapacity() {
        ShoppingCart shoppingCart = emptyCart();
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> RANDOM.ints(randomGreaterThanMaxCartCapacity())
                .forEach(itemIndex -> addItemToCart(shoppingCart, validItem()))
        );
    }

    private void addItemToEmptyCartAssertThrowsIllegalArgumentException(Item item) {
        assertThrows(
            IllegalArgumentException.class,
            () -> addItemToCart(emptyCart(), item)
        );
    }

    private static Item validItem() {
        return new Item(validRandomItemTitle(), validRandomItemPrice(), validRandomItemsQuantity(), randomItemType());
    }

    private static ShoppingCart emptyCart() {
        return new ShoppingCart();
    }

    /**
     * Creates a random string of specified length
     */
    private static String stringOfLength(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        IntStream.range(0, length).forEach(integer -> stringBuilder.append((char) integer));
        return stringBuilder.toString();
    }

    private static Item.Type randomItemType() {
        return Item.Type.values()[RANDOM.nextInt(Item.Type.values().length)];
    }

    private static String invalidLongRandomTitle() {
        return stringOfLength(ITEM_TITLE_MAX_VALID_LENGTH + 1);
    }

    private static int randomInt(int fromInclusive, int toExclusive) {
        return RANDOM.ints(fromInclusive, toExclusive).findFirst().getAsInt();
    }

    private static double randomDouble(double fromInclusive, double toExclusive) {
        return RANDOM.doubles(fromInclusive, toExclusive).findFirst().getAsDouble();
    }

    private static String validRandomItemTitle() {
        return stringOfLength(randomInt(1, ITEM_TITLE_MAX_VALID_LENGTH + 1));
    }

    private static double validRandomItemPrice() {
        return randomDouble(VALID_MIN_ITEMS_PRICE, ITEMS_MAX_VALID_PRICE + 1);
    }

    private static int validRandomItemsQuantity() {
        return randomInt(1, ITEMS_MAX_VALID_QUANTITY + 1);
    }

    private static double invalidRandomNegativeItemPrice() {
        double randomPrice = RANDOM.nextDouble() * RANDOM.nextInt();
        return randomPrice < 0 ? randomPrice : -1 * randomPrice;
    }

    private static double invalidRandomLargeItemPrice() {
        return RANDOM.doubles(ITEMS_MAX_VALID_PRICE + TOLERANCE, Double.MAX_VALUE)
            .findFirst()
            .getAsDouble();
    }

    private static double minInvalidItemsPrice() {
        return ITEMS_MAX_VALID_PRICE + TOLERANCE;
    }

    private static int invalidRandomItemsQuantityMin() {
        return ITEMS_MIN_VALID_QUANTITY - Math.max(1, Math.abs(RANDOM.nextInt()));
    }

    private static int invalidRandomItemsQuantityMax() {
        return ITEMS_MAX_VALID_QUANTITY + Math.max(1, Math.abs(RANDOM.nextInt()));
    }

    private static void addItemToCart(ShoppingCart cart, Item item) {
        cart.addItem(item.getTitle(), item.getPrice(), item.getQuantity(), item.getType());
    }

    private static int randomValidCartCapacity() {
        return randomInt(1, CART_MAX_ITEMS_CAPACITY + 1);
    }

    private static int randomGreaterThanMaxCartCapacity() {
        return randomInt(CART_MAX_ITEMS_CAPACITY + 1, Integer.MAX_VALUE);
    }
}

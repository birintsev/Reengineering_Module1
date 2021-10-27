package birintsev;

import org.junit.jupiter.api.Test;
import java.util.stream.IntStream;
import static birintsev.RandomTestDataUtils.randomGreaterThanMaxCartCapacity;
import static birintsev.RandomTestDataUtils.randomInvalidItemPriceLarge;
import static birintsev.RandomTestDataUtils.randomInvalidItemTitleLong;
import static birintsev.RandomTestDataUtils.randomInvalidItemsQuantityMax;
import static birintsev.RandomTestDataUtils.randomInvalidItemsQuantityMin;
import static birintsev.RandomTestDataUtils.randomInvalidNegativeItemPrice;
import static birintsev.RandomTestDataUtils.randomStringOfLength;
import static birintsev.RandomTestDataUtils.randomValidCartCapacity;
import static birintsev.RandomTestDataUtils.randomValidItem;
import static birintsev.TestConstants.CART_CAPACITY_VALID_MAX;
import static birintsev.TestConstants.ITEM_PRICE_INVALID_MIN;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShoppingCartAddItemTest {

    @Test
    void addItem_emptyTitleIllegalArgumentException() {
        Item item = randomValidItem();
        item.setTitle(randomStringOfLength(0));
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_longTitleIllegalArgumentException() {
        Item item = randomValidItem();
        item.setTitle(randomInvalidItemTitleLong());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_negativePriceIllegalArgumentException() {
        Item item = randomValidItem();
        item.setPrice(randomInvalidNegativeItemPrice());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_largePriceIllegalArgumentException() {
        Item item = randomValidItem();
        item.setPrice(randomInvalidItemPriceLarge());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemPriceIllegalArgumentException() {
        Item item = randomValidItem();
        item.setPrice(randomInvalidItemPriceLarge());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemPriceZeroIllegalArgumentException() {
        Item item = randomValidItem();
        item.setPrice(0);
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemPriceMinInvalidValueIllegalArgumentException() {
        Item item = randomValidItem();
        item.setPrice(ITEM_PRICE_INVALID_MIN);
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemQuantityMinThanInvalidValueIllegalArgumentException() {
        Item item = randomValidItem();
        item.setQuantity(randomInvalidItemsQuantityMin());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_itemQuantityMaxThanInvalidValueIllegalArgumentException() {
        Item item = randomValidItem();
        item.setQuantity(randomInvalidItemsQuantityMax());
        addItemToEmptyCartAssertThrowsIllegalArgumentException(item);
    }

    @Test
    void addItem_validItemToEmptyCart() {
        addItemToCart(emptyCart(), randomValidItem());
    }

    @Test
    void cartCapacityTest() {
        ShoppingCart shoppingCart = emptyCart();
        int cartCapacity = randomValidCartCapacity();
        IntStream.range(0, randomValidCartCapacity()).forEach(i -> addItemToCart(shoppingCart, randomValidItem()));
    }

    @Test
    void indexOutOfBoundsExceptionOnMaxCapacity() {
        ShoppingCart shoppingCart = emptyCart();
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> IntStream.range(0, CART_CAPACITY_VALID_MAX + 1)
                .forEach(itemIndex -> addItemToCart(shoppingCart, randomValidItem()))
        );
    }

    @Test
    void indexOutOfBoundsExceptionOnGreaterThanMaxCapacity() {
        ShoppingCart shoppingCart = emptyCart();
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> IntStream.range(0, randomGreaterThanMaxCartCapacity())
                .forEach(itemIndex -> addItemToCart(shoppingCart, randomValidItem()))
        );
    }

    private static void addItemToEmptyCartAssertThrowsIllegalArgumentException(Item item) {
        assertThrows(
            IllegalArgumentException.class,
            () -> addItemToCart(emptyCart(), item)
        );
    }

    private static void addItemToCart(ShoppingCart cart, Item item) {
        cart.addItem(item.getTitle(), item.getPrice(), item.getQuantity(), item.getType());
    }

    private static ShoppingCart emptyCart() {
        return new ShoppingCart();
    }
}

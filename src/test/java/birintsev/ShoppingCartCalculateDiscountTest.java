package birintsev;

import org.junit.jupiter.api.Test;
import static birintsev.RandomTestDataUtils.randomInt;
import static birintsev.RandomTestDataUtils.randomValidItem;
import static birintsev.ShoppingCart.calculateDiscount;
import static birintsev.TestConstants.BULK_DISCOUNT;
import static birintsev.TestConstants.MAX_TOTAL_DISCOUNT;
import static birintsev.TestConstants.TYPE_DISCOUNT_BULK_DISCOUNT;
import static birintsev.TestConstants.TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT;
import static birintsev.TestConstants.TYPE_DISCOUNT_DISCOUNT;
import static birintsev.TestConstants.ITEMS_BUY_TO_GET_BULK_DISCOUNT;
import static birintsev.TestConstants.TYPE_DISCOUNT_MAX_DISCOUNT;
import static birintsev.TestConstants.TYPE_REGULAR_DISCOUNT;
import static birintsev.TestConstants.TYPE_SALE_DISCOUNT;
import static birintsev.TestConstants.TYPE_SECOND_DISCOUNT;
import static com.google.common.truth.Truth.assertWithMessage;

public class ShoppingCartCalculateDiscountTest {

    @Test
    void calculateDiscount_regularItemNoDiscount() {
        Item item = randomValidItem();
        item.setQuantity(1);
        item.setType(Item.Type.REGULAR);

        int discount = calculateDiscount(item);

        assertWithMessage("Regular items do not have discount.")
            .that(discount)
            .isEqualTo(TYPE_REGULAR_DISCOUNT);
    }

    @Test
    void calculateDiscount_secondItems() {
        Item item = randomValidItem();
        item.setType(Item.Type.SECOND);
        item.setQuantity(quantityWithoutBulkDiscount());

        int discount = calculateDiscount(item);

        assertWithMessage("%s items should have %s% discount.", Item.Type.SECOND, TYPE_SECOND_DISCOUNT)
            .that(discount)
            .isEqualTo(TYPE_SECOND_DISCOUNT);
    }

    @Test
    void calculateDiscount_discountItemsWithoutDiscount() {
        Item item = randomValidItem();
        item.setType(Item.Type.DISCOUNT);
        item.setQuantity(quantityItemsOfDiscountTypeWithoutDiscounts());

        int discount = calculateDiscount(item);

        assertWithMessage(
            "%s items of quantity %s should have %s discount",
                Item.Type.DISCOUNT,
                item.getQuantity(),
            TYPE_DISCOUNT_DISCOUNT
        )
            .that(discount)
            .isEqualTo(TYPE_DISCOUNT_DISCOUNT);
    }

    @Test
    void calculateDiscount_discountItemsWithDiscount() {
        Item item = randomValidItem();
        item.setType(Item.Type.DISCOUNT);
        item.setQuantity(TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT);

        int discount = calculateDiscount(item);

        assertWithMessage(
            "%s items of quantity %s should have %s discount",
            Item.Type.DISCOUNT,
            item.getQuantity(),
            TYPE_DISCOUNT_DISCOUNT
        )
            .that(discount)
            .isEqualTo(TYPE_DISCOUNT_DISCOUNT + TYPE_DISCOUNT_BULK_DISCOUNT);
    }

    @Test
    void calculateDiscount_discountItemsWithAlmostNextDiscount() {
        Item item = randomValidItem();
        item.setType(Item.Type.DISCOUNT);
        item.setQuantity(quantityItemsOfDiscountTypeWithAlmostNextDiscount());

        int discount = calculateDiscount(item);
        int expectedDiscount = TYPE_DISCOUNT_DISCOUNT
            + (item.getQuantity() / TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT) * TYPE_DISCOUNT_BULK_DISCOUNT;

        assertWithMessage(
            "%s items of quantity %s should have %s discount",
            Item.Type.DISCOUNT,
            item.getQuantity(),
            expectedDiscount
        )
            .that(discount)
            .isEqualTo(expectedDiscount);
    }

    @Test
    void calculateDiscount_discountItemsWithDiscountOverflow() {
        Item item = randomValidItem();
        item.setType(Item.Type.DISCOUNT);
        item.setQuantity(typeDiscountItemsNumberToGetDiscountOverflow());

        int discount = calculateDiscount(item);

        assertWithMessage(
            "%s items of quantity %s should have %s discount",
            Item.Type.DISCOUNT,
            item.getQuantity(),
            TYPE_DISCOUNT_MAX_DISCOUNT
        )
            .that(discount)
            .isEqualTo(TYPE_DISCOUNT_MAX_DISCOUNT);
    }

    @Test
    void calculateDiscount_saleItems() {
        Item item = randomValidItem();
        item.setType(Item.Type.SALE);

        int discount = calculateDiscount(item);

        assertWithMessage(
            "%s items of quantity %s should have %s discount",
            Item.Type.SALE,
            item.getQuantity(),
            discountForItemsSale()
        )
            .that(discount)
            .isEqualTo(discountForItemsSale());
    }

    @Test
    void calculateDiscount_bulkDiscountTypeRegular() {
        final int quantity = (int) (2.5 * ITEMS_BUY_TO_GET_BULK_DISCOUNT);
        Item item = randomValidItem();
        item.setType(Item.Type.REGULAR);
        item.setQuantity(quantity);

        int discount = calculateDiscount(item);
        int expectedDiscount = Math.min(
            TYPE_REGULAR_DISCOUNT + (item.getQuantity() / ITEMS_BUY_TO_GET_BULK_DISCOUNT) * BULK_DISCOUNT,
            MAX_TOTAL_DISCOUNT
        );

        assertWithMessage(
            "%s items of quantity %s should have %s discount",
            Item.Type.REGULAR,
            item.getQuantity(),
            TYPE_DISCOUNT_DISCOUNT
        )
            .that(discount)
            .isEqualTo(expectedDiscount);
    }

    @Test
    void calculateDiscount_bulkDiscountTypeSecond() {
        final int quantity = (int) (3.85 * ITEMS_BUY_TO_GET_BULK_DISCOUNT);
        Item item = randomValidItem();
        item.setType(Item.Type.SECOND);
        item.setQuantity(quantity);

        int discount = calculateDiscount(item);
        int expectedDiscount = Math.min(
            TYPE_SECOND_DISCOUNT + (item.getQuantity() / ITEMS_BUY_TO_GET_BULK_DISCOUNT) * BULK_DISCOUNT,
            MAX_TOTAL_DISCOUNT
        );

        assertWithMessage(
            "%s items of quantity %s should have %s discount",
            Item.Type.SECOND,
            item.getQuantity(),
            expectedDiscount
        )
            .that(discount)
            .isEqualTo(expectedDiscount);
    }

    @Test
    void calculateDiscount_bulkDiscountTypeDiscount() {
        final int quantity = (int) (1.05 * ITEMS_BUY_TO_GET_BULK_DISCOUNT);
        Item item = randomValidItem();
        item.setType(Item.Type.DISCOUNT);
        item.setQuantity(quantity);

        int discount = calculateDiscount(item);
        int expectedDiscount = Math.min(
            Math.min(TYPE_DISCOUNT_DISCOUNT + (item.getQuantity() / TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT) * TYPE_DISCOUNT_BULK_DISCOUNT, TYPE_DISCOUNT_MAX_DISCOUNT) + (item.getQuantity() / ITEMS_BUY_TO_GET_BULK_DISCOUNT) * BULK_DISCOUNT,
            MAX_TOTAL_DISCOUNT
        );

        assertWithMessage(
            "%s items of quantity %s should have %s discount",
            Item.Type.DISCOUNT,
            item.getQuantity(),
            expectedDiscount
        )
            .that(discount)
            .isEqualTo(expectedDiscount);
    }

    private int discountForItemsSale() {
        return Math.min(TYPE_SALE_DISCOUNT, MAX_TOTAL_DISCOUNT);
    }

    private int quantityWithoutBulkDiscount() {
        return randomInt(2, ITEMS_BUY_TO_GET_BULK_DISCOUNT);
    }

    private int quantityItemsOfDiscountTypeWithoutDiscounts() {
        return randomInt(1, TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT);
    }

    private int quantityItemsOfDiscountTypeWithAlmostNextDiscount() {
        return randomInt(
            TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT + 1,
            2 * TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT
        );
    }

    private int typeDiscountItemsNumberToGetDiscountOverflow() {
        return ((TYPE_DISCOUNT_MAX_DISCOUNT - TYPE_DISCOUNT_DISCOUNT) / TYPE_DISCOUNT_BULK_DISCOUNT)
            * (TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT + 1);
    }
}

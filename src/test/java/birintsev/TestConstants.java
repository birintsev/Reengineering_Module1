package birintsev;

class TestConstants {

    static final double TOLERANCE = Double.MIN_VALUE;

    static final int ITEM_TITLE_LENGTH_VALID_MAX = 32;

    static final int ITEM_QUANTITY_VALID_MIN = 1;

    static final int ITEM_QUANTITY_VALID_MAX = 1000;

    static final double ITEM_PRICE_VALID_MAX = 1000 - TOLERANCE;

    static final double ITEM_PRICE_VALID_MIN = 0.01;

    static final double ITEM_PRICE_INVALID_MIN = ITEM_PRICE_VALID_MAX + TOLERANCE;

    static final int CART_CAPACITY_VALID_MAX = 99;

    static final int ITEMS_BUY_TO_GET_BULK_DISCOUNT = 100;

    static final int TYPE_DISCOUNT_BUY_TO_GET_TYPE_DISCOUNT_BULK_DISCOUNT = 10;

    static final int TYPE_REGULAR_DISCOUNT = 0;

    static final int TYPE_SECOND_DISCOUNT = 50;

    static final int TYPE_DISCOUNT_DISCOUNT = 10;

    static final int TYPE_DISCOUNT_BULK_DISCOUNT = 10;

    static final int TYPE_DISCOUNT_MAX_DISCOUNT = 50;

    static final int TYPE_SALE_DISCOUNT = 90;

    static final int MAX_TOTAL_DISCOUNT = 80;

    static final int BULK_DISCOUNT = 10;

    private TestConstants() {
        // This class is not expected to be instantiated.
    }
}

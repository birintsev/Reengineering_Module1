package birintsev;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    public enum Type { SECOND, REGULAR, SALE, DISCOUNT };
    private String title;
    private double price;
    private int quantity;
    private Type type;
}

package PDP.task1.valueobjects;

/**
 * Value Object representing a Product identifier.
 * Immutable and validated on creation.
 */
public class ProductID {
    private final String id;

    /**
     * Creates a new ProductID
     * @param id The identifier string (must not be null or empty)
     */
    public ProductID(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID productID = (ProductID) o;
        return id.equals(productID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

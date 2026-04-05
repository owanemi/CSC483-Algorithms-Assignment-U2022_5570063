package com.csc483.assignment1;

import com.csc483.assignment1.search.Product;
import com.csc483.assignment1.search.SearchEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SearchEngineTest {

    private SearchEngine engine;
    private Product[]    sorted;

    @BeforeEach
    void setUp() {
        engine = new SearchEngine();
        sorted = new Product[]{
            new Product(10, "Alpha",   "Laptop",  999.00, 5),
            new Product(20, "Beta",    "Phone",   499.00, 10),
            new Product(30, "Gamma",   "Tablet",  299.00, 20),
            new Product(40, "Delta",   "TV",     1299.00, 3),
            new Product(50, "Epsilon", "Camera",  799.00, 7),
        };
        engine.buildNameIndex(sorted);
    }

    // ------------------------------------------------------------------ Product
    @Test
    void product_constructorStoresAllFields() {
        Product p = new Product(1, "Test", "Cat", 9.99, 3);
        assertEquals(1,      p.getProductId());
        assertEquals("Test", p.getProductName());
        assertEquals("Cat",  p.getCategory());
        assertEquals(9.99,   p.getPrice(), 0.001);
        assertEquals(3,      p.getStockQuantity());
    }

    @Test
    void product_rejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "", "Cat", 9.99, 0));
    }

    @Test
    void product_rejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "X", "Cat", -1.0, 0));
    }

    @Test
    void product_rejectsNegativeStock() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "X", "Cat", 1.0, -1));
    }

    @Test
    void product_equalityByIdOnly() {
        Product a = new Product(5, "A", "Cat", 1.0, 1);
        Product b = new Product(5, "B", "Cat", 2.0, 2);
        assertEquals(a, b);
    }

    // ------------------------------------------------------------------ Sequential search
    @Test
    void sequentialSearch_findsExistingProduct() {
        Product result = engine.sequentialSearchById(sorted, 30);
        assertNotNull(result);
        assertEquals("Gamma", result.getProductName());
    }

    @Test
    void sequentialSearch_returnsNullWhenMissing() {
        assertNull(engine.sequentialSearchById(sorted, 99));
    }

    @Test
    void sequentialSearch_findsFirst() {
        assertNotNull(engine.sequentialSearchById(sorted, 10));
    }

    @Test
    void sequentialSearch_findsLast() {
        assertNotNull(engine.sequentialSearchById(sorted, 50));
    }

    @Test
    void sequentialSearch_nullArrayReturnsNull() {
        assertNull(engine.sequentialSearchById(null, 10));
    }

    @Test
    void sequentialSearch_emptyArrayReturnsNull() {
        assertNull(engine.sequentialSearchById(new Product[0], 10));
    }

    // ------------------------------------------------------------------ Binary search
    @Test
    void binarySearch_findsMiddleElement() {
        Product result = engine.binarySearchById(sorted, 30);
        assertNotNull(result);
        assertEquals(30, result.getProductId());
    }

    @Test
    void binarySearch_findsFirstElement() {
        assertNotNull(engine.binarySearchById(sorted, 10));
    }

    @Test
    void binarySearch_findsLastElement() {
        assertNotNull(engine.binarySearchById(sorted, 50));
    }

    @Test
    void binarySearch_returnsNullWhenMissing() {
        assertNull(engine.binarySearchById(sorted, 99));
    }

    @Test
    void binarySearch_nullArrayReturnsNull() {
        assertNull(engine.binarySearchById(null, 10));
    }

    @Test
    void binarySearch_emptyArrayReturnsNull() {
        assertNull(engine.binarySearchById(new Product[0], 10));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30, 40, 50})
    void binarySearch_findsAllProducts(int id) {
        assertNotNull(engine.binarySearchById(sorted, id));
    }

    // Sequential and binary return the same result for all IDs
    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30, 40, 50, 99})
    void binaryAndSequentialAgree(int id) {
        Product seq = engine.sequentialSearchById(sorted, id);
        Product bin = engine.binarySearchById(sorted, id);
        assertEquals(seq, bin);
    }

    // ------------------------------------------------------------------ Name search
    @Test
    void searchByName_caseInsensitive() {
        assertNotNull(engine.searchByName(sorted, "ALPHA"));
        assertNotNull(engine.searchByName(sorted, "alpha"));
        assertNotNull(engine.searchByName(sorted, "Alpha"));
    }

    @Test
    void searchByName_returnsNullWhenMissing() {
        assertNull(engine.searchByName(sorted, "Nonexistent"));
    }

    @Test
    void searchByName_nullTargetReturnsNull() {
        assertNull(engine.searchByName(sorted, null));
    }

    // ------------------------------------------------------------------ Hybrid search
    @Test
    void hybridSearch_findsProduct() {
        assertNotNull(engine.hybridSearchByName("beta"));
    }

    @Test
    void hybridSearch_caseInsensitive() {
        assertNotNull(engine.hybridSearchByName("GAMMA"));
    }

    @Test
    void hybridSearch_returnsNullWhenMissing() {
        assertNull(engine.hybridSearchByName("unknown_product"));
    }

    @Test
    void hybridSearch_nullReturnsNull() {
        assertNull(engine.hybridSearchByName(null));
    }

    // ------------------------------------------------------------------ addProduct
    @Test
    void addProduct_maintainsSortedOrder() {
        Product newProd = new Product(25, "NewItem", "Laptop", 199.0, 5);
        Product[] result = engine.addProduct(sorted, newProd);

        assertEquals(sorted.length + 1, result.length);

        // Verify sorted order is maintained
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i].getProductId() <= result[i + 1].getProductId(),
                    "Array not sorted at index " + i);
        }
    }

    @Test
    void addProduct_insertAtBeginning() {
        Product newProd = new Product(1, "First", "Laptop", 9.99, 1);
        Product[] result = engine.addProduct(sorted, newProd);
        assertEquals(1, result[0].getProductId());
    }

    @Test
    void addProduct_insertAtEnd() {
        Product newProd = new Product(999, "Last", "Laptop", 9.99, 1);
        Product[] result = engine.addProduct(sorted, newProd);
        assertEquals(999, result[result.length - 1].getProductId());
    }

    @Test
    void addProduct_updatesNameIndex() {
        Product newProd = new Product(25, "UniqueNameXYZ", "Laptop", 199.0, 5);
        engine.addProduct(sorted, newProd);
        assertNotNull(engine.hybridSearchByName("UniqueNameXYZ"));
    }

    @Test
    void addProduct_nullProductThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> engine.addProduct(sorted, null));
    }

    @Test
    void addProduct_nullArrayTreatedAsEmpty() {
        Product newProd = new Product(1, "Solo", "Cat", 1.0, 1);
        Product[] result = engine.addProduct(null, newProd);
        assertEquals(1, result.length);
        assertEquals(1, result[0].getProductId());
    }
}

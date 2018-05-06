package dao;

import entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类对Product类进行增删改查
 */
public class ProductDao {
    /**
     * 模拟数据
     */
    private ArrayList<Product> products;

    private ProductDao() {
        products = new ArrayList<Product>();
        for (int i = 0; i < 5; i++) {
            products.add(new Product(String.valueOf(i), "笔记本" + i, "LN001" + i, 34.0 + i));
        }
    }

    private static final class SingleHolder {
        private static final ProductDao instance = new ProductDao();
    }

    public static ProductDao getInstance() {
        return SingleHolder.instance;
    }

    /**
     * 查找所有的商品
     */
    public List<Product> findAll() {
        return products;
    }

    /**
     * 根据商品编号查询出对应的商品
     *
     * @param id 商品编号
     */
    public Product findById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
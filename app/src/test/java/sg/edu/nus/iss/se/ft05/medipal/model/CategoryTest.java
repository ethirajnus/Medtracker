package sg.edu.nus.iss.se.ft05.medipal.model;

import org.junit.Test;

import sg.edu.nus.iss.se.ft05.medipal.domain.Category;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Ashish Katre on 3/20/2017.
 */
public class CategoryTest {

    Category category;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testCategory() {

        category = new Category();
        category.setId(10);
        category.setCategoryName("name");
        category.setDescription("description");
        category.setCode("NAM");
        category.setRemind(true);

        assertEquals(category.getId(), 10);
        assertEquals(category.getCategoryName(), "name");
        assertEquals(category.getDescription(), "description");
        assertEquals(category.getCode(), "NAM");
        assertEquals(category.getRemind(), true);
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testCategoryFull() {

        category = new Category("name", "NAM","description", true);

        assertNotNull(category);
        assertEquals(category.getCategoryName(), "name");
        assertEquals(category.getDescription(), "description");
        assertEquals(category.getCode(), "NAM");
        assertEquals(category.getRemind(), true);
    }
}

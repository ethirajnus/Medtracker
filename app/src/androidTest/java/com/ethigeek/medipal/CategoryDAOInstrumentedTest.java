package com.ethigeek.medipal;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ethigeek.medipal.dao.CategoryDAOImpl;
import com.ethigeek.medipal.daoutils.DBHelper;
import com.ethigeek.medipal.domain.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 * @author Ethiraj Srinivasan
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CategoryDAOInstrumentedTest {


    private CategoryDAOImpl categoryDAOImpl;
    private Category category1;
    private Category category2;
    private Category category7;

    @Before
    public void setUp() {

        categoryDAOImpl = new CategoryDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = categoryDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            categoryDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.CATEGORY_KEY_ID)));
            cursor.moveToNext();
        }

        category1 = new Category("name1", "NM1", "description1", true);

        categoryDAOImpl.insert(category1);

        category2 = new Category("name2", "NM2", "description2", true);

        categoryDAOImpl.insert(category2);

        categoryDAOImpl.insert(new Category("name3", "NM3", "description3", true));

        categoryDAOImpl.insert(new Category("name4", "NM4", "description4", true));

        category7 = new Category("name5", "NM5", "description5", true);
        category7.setId(3);
    }

    @After
    public void finish() {

        Cursor cursor = categoryDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            categoryDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.CATEGORY_KEY_ID)));
            cursor.moveToNext();
        }
        categoryDAOImpl.close();
    }

    @Test
    public void testPreConditions() {

        assertNotNull(categoryDAOImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor c = categoryDAOImpl.findAll();

        assertNotNull(c);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            Category category = new Category();
            category.setId(c.getInt(c.getColumnIndex(DBHelper.CATEGORY_KEY_ID)));
            category.setCategoryName((c.getString(c.getColumnIndex(DBHelper.CATEGORY_KEY_CATEGORY))));
            category.setCode(c.getString(c.getColumnIndex(DBHelper.CATEGORY_KEY_CODE)));
            category.setDescription(c.getString(c.getColumnIndex(DBHelper.CATEGORY_KEY_DESCRIPTION)));
            category.setRemind(c.getInt(c.getColumnIndex(DBHelper.CATEGORY_KEY_REMIND)) == 1);

            assertNotNull(category.getId());

            switch (category.getId()) {

                case 1:
                    assertNotNull(category.getCategoryName());
                    assertEquals(category1.getCategoryName(), category.getCategoryName());
                    assertNotNull(category.getDescription());
                    assertEquals(category1.getDescription(), category.getDescription());
                    assertNotNull(category.getCode());
                    assertEquals(category1.getCode(), category.getCode());
                    assertNotNull(category.getRemind());
                    assertEquals(category1.getRemind(), category.getRemind());
                    break;

                case 2:
                    assertNotNull(category.getCategoryName());
                    assertEquals(category2.getCategoryName(), category.getCategoryName());
                    assertNotNull(category.getDescription());
                    assertEquals(category2.getDescription(), category.getDescription());
                    assertNotNull(category.getCode());
                    assertEquals(category2.getCode(), category.getCode());
                    assertNotNull(category.getRemind());
                    assertEquals(category2.getRemind(), category.getRemind());
                    break;
            }

            c.moveToNext();
        }
    }

    @Test
    public void testFindByField() throws Exception {

        findByIdTesting(1, category1);
    }

    private void findByIdTesting(int id, Category categoryTest) {

        Category category = categoryDAOImpl.findByField(DBHelper.CATEGORY_KEY_ID, id);

        assertNotNull(category);

        assertNotNull(category.getCategoryName());
        assertEquals(categoryTest.getCategoryName(), category.getCategoryName());
        assertNotNull(category.getDescription());
        assertEquals(categoryTest.getDescription(), category.getDescription());
        assertNotNull(category.getCode());
        assertEquals(categoryTest.getCode(), category.getCode());
        assertNotNull(category.getRemind());
        assertEquals(categoryTest.getRemind(), category.getRemind());
    }

    @Test
    public void testDelete() throws Exception {

        int num = categoryDAOImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = categoryDAOImpl.update(category7);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, category7);
    }

    @Test
    public void testFetchAllCategoriesWithId() throws Exception {

        Cursor cursor = categoryDAOImpl.fetchAllCategoriesWithId();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.CATEGORY_KEY_ID));
            String category = cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_KEY_CATEGORY));

            assertNotNull(id);

            switch (id) {

                case 1:
                    assertNotNull(category);
                    assertEquals(category1.getCategoryName(), category);
                    break;

                case 2:
                    assertNotNull(category);
                    assertEquals(category2.getCategoryName(), category);
                    break;
            }

            cursor.moveToNext();
        }
    }
}

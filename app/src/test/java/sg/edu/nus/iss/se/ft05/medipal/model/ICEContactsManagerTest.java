package sg.edu.nus.iss.se.ft05.medipal.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAOImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

/**
 * Created by e0146812 on 3/20/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ICEContactsManagerTest {

    public ICEContactsManager iceContactsManager;

//    @Mock
//    ICEContactsDAO iceContactsDAO;

//    @BeforeClass
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }

  /*  @Mock
    Context context;*/

/*    @Mock
    Cursor cursor;*/

    /**
     * Method to test getters and setters
     */
    @Test
    public void testContacts() {

       // iceContactsDAO = new ICEContactsDAOImpl(context);

        /*when(iceContactsDAO.findAll())
                .thenReturn(cursor);*/

    /*    when(new ICEContactsDAOImpl(context))
                .thenReturn(iceContactsDAO);*/

    iceContactsManager = new ICEContactsManager();

        Assert.assertNotNull(iceContactsManager);
    }
}
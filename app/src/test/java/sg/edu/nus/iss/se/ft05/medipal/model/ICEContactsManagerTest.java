package sg.edu.nus.iss.se.ft05.medipal.model;


import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import sg.edu.nus.iss.se.ft05.medipal.activities.ICEAdditionActivity;
import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.managers.ICEContactsManager;

import org.mockito.Mock;


/**
 * Created by e0146812 on 3/20/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ICEContactsManagerTest {

    public ICEContactsManager iceContactsManager;

//
//
//    @Mock
//    Context mcontext;

    @Before
    public void initMocks() {

        iceContactsManager = new ICEContactsManager();


        //iceContactsDAO = new ICEContactsDAOImpl(mcontext);
    }

    @Mock
    ICEAdditionActivity ice;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testContacts() {

        // iceContactsDAO = new ICEContactsDAOImpl(context);

        /*when(iceContactsDAO.findAll())
                .thenReturn(cursor);*/

        ICEAdditionActivity ice = Mockito.mock(ICEAdditionActivity.class);


        Context context = ice.getApplicationContext();

        ICEContactsDAOImpl miceContactsDAO = new ICEContactsDAOImpl(context);

        Mockito.when(new ICEContactsDAOImpl(context))
                .thenReturn(miceContactsDAO);

        Mockito.when(miceContactsDAO.findAll())
                .thenReturn(null);

        Assert.assertNotNull(iceContactsManager.findAll(context));
    }
}
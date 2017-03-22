package sg.edu.nus.iss.se.ft05.medipal.model;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.model.ICEContact;

/**
 * Created by ashish Katre
 */

public class ICEContactsManager {

    //Dao
    private ICEContactsDAO iceContactsDAO;


    private ICEContact iceContact;

    /**
     * Getter for ICEContact
     *
     * @return ICEContact
     */
    public ICEContact getICEContact() {

        return iceContact;
    }

    /**
     * Setter for ICEContact
     *
     * @param iceContact
     */
    public void setICEContact(ICEContact iceContact) {

        this.iceContact = iceContact;
    }

    /**
     * Constructor of iceContact
     *
     * @param newICEContact
     */
    public ICEContactsManager(ICEContact newICEContact, Context context) {

        this.iceContact = newICEContact;
        this.iceContact.setPriority(getNewPriority(context));
    }

    /**
     * Default Constructor
     */
    public ICEContactsManager() {

    }

    /**
     * New Priority
     *
     * @param context
     * @return int
     */
    public int getNewPriority(Context context) {

        int newPriority = this.getMaxPriority(context);

        newPriority++;

        return newPriority;
    }

    /**
     * Static method for finding all contacts
     *
     * @param context
     * @return Cursor
     */
    public static Cursor findAll(Context context) {

        ICEContactsDAO iceContactsDAO = new ICEContactsDAOImpl(context);

        return iceContactsDAO.findAll();
    }

    /**
     * Method to find contact by id
     *
     * @param context
     * @param id
     * @return ICEContactsManager
     */
    public ICEContact findById(Context context, int id) {

        iceContactsDAO = new ICEContactsDAOImpl(context);
        this.iceContact = iceContactsDAO.findById(id);

        return iceContact;
    }

    /**
     * Insert contact
     *
     * @param context
     * @return long
     */
    public long save(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);
        return iceContactsDAO.insert(this.iceContact);
    }

    /**
     * update contact
     *
     * @param context
     * @return long
     */
    public long update(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);

        return iceContactsDAO.update(this.iceContact);
    }

    /**
     * delete contacts
     *
     * @param context
     * @return int
     */
    public int delete(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);
        return iceContactsDAO.delete(this.iceContact.getId());
        // TODO Priority
    }

    /**
     * Update priority after delete
     *
     * @param context
     */
    public void updatePriority(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);

        int maxPriority = this.getNewPriority(context);

        for (int currentPriority = this.iceContact.getPriority() + 1; currentPriority < maxPriority; currentPriority++) {

            iceContactsDAO.updatePriority(currentPriority, (currentPriority - 1));
        }

        // TODO Priority
    }

    /**
     * Get maximum priority number
     *
     * @param context
     */
    public int getMaxPriority(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);

        return iceContactsDAO.findMaxPriority();
    }
}

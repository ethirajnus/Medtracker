package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.Contacts;

/**
 * Interface for emergency contacts database operations
 * Created by Ashish Katre
 */

public interface ICEContactsDAO {

    /**
     * delete operation
     *
     * @param id int
     * @return int
     */
    public int delete(int id);

    /**
     * select all operation
     *
     * @return Cursor
     */
    public Cursor findAll();

    /**
     * select operation with where clause using ID
     *
     * @param id int
     * @return Contacts
     */
    public Contacts findById(int id);

    /**
     * insert operation
     *
     * @param contact Contacts
     * @return long
     */
    public long insert(Contacts contact);

    /**
     * update operation
     *
     * @param contact Contacts
     * @return int
     */
    public int update(Contacts contact);

    /**
     * update operation for priority update
     *
     * @param currentPriority
     * @param newPriority
     * @return
     */
    public int updatePriority(int currentPriority, int newPriority);

    /**
     * select operation with groupby priority to get maximum priority value
     *
     * @return int
     */
    public int findMaxPriority();
}

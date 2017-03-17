package sg.edu.nus.iss.se.ft05.medipal;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAOImpl;

/**
 * Created by ashish Katre
 */

public class Contacts {

    private static ICEContactsDAO iceContactsDAO;

    private int id;
    private int priority;
    private long phone;
    private String name;
    private String description;
    private String type;

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getPriority() {

        return priority;
    }

    public void setPriority(int priority) {

        this.priority = priority;
    }

    public long getPhone() {

        return phone;
    }

    public void setPhone(long phone) {

        this.phone = phone;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public Contacts(String name, String description, Long phone, String type, Context context) {

        this.name = name;
        this.description = description;
        this.phone = phone;
        this.type = type;
        this.priority = getNewPriority(context);
    }

    public Contacts() {

    }

    public int getNewPriority(Context context) {


        iceContactsDAO = new ICEContactsDAOImpl(context);

        int priority = iceContactsDAO.findMaxPriority();

        priority++;

        Toast.makeText(context, "Priority: " + priority, Toast.LENGTH_SHORT).show();

        return priority;
    }

    public static Cursor findAll(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);

        return iceContactsDAO.findAll();
    }

    public static Contacts findById(Context context, int id) {

        iceContactsDAO = new ICEContactsDAOImpl(context);

        return iceContactsDAO.findById(id);
    }

    public long save(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);
        return iceContactsDAO.insert(this);
    }

    public long update(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);

        return iceContactsDAO.update(this);
    }

    public int delete(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);
        return iceContactsDAO.delete(this.getId());
        // TODO Priority
    }

    public void updatePriority(Context context) {

        iceContactsDAO = new ICEContactsDAOImpl(context);

        int maxPriority = this.getNewPriority(context);

        for (int currentPriority = this.priority + 1; currentPriority < maxPriority; currentPriority++) {

            iceContactsDAO.updatePriority(currentPriority, (currentPriority - 1));
        }

        Toast.makeText(context, "Last Priority: " + (maxPriority - 2), Toast.LENGTH_SHORT).show();
        // TODO Priority
    }
}

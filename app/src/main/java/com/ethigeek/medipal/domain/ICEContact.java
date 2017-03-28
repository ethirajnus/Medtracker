package com.ethigeek.medipal.domain;

/**
 * Domain class of contact
 * Created by Ashish katre on 3/19/2017.
 */
public class ICEContact {

    // Variables
    private int id;
    private int priority;
    private long phone;
    private String name;
    private String description;
    private String type;

    // Getters and Setters
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

    /**
     * Default Constructor
     */
    public ICEContact() {

    }

    public ICEContact(String name, String description, String type, long phone) {

        this.name = name;
        this.description = description;
        this.type = type;
        this.phone = phone;
    }
}

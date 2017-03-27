package sg.edu.nus.iss.se.ft05.medipal.domain;

/**
 * Domain class for consumption
 * @author Ethiraj Srinivasan
 */
public class Consumption {

    private int id, medicineId, quantity;
    private String date, time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Consumption(int medicineId, int quantity, String date, String time) {

        this.medicineId = medicineId;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
    }


    public Consumption() {

    }


    @Override
    public String toString(){
        return getQuantity()+ ","+getDate()+","+getTime()+"\n";
    }
}

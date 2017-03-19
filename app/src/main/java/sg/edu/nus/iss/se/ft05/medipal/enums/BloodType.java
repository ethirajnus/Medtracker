package sg.edu.nus.iss.se.ft05.medipal.enums;

/**
 * @author Moushumi Seal
 */

public enum BloodType {

    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-");

    private final String label;

    private BloodType(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}

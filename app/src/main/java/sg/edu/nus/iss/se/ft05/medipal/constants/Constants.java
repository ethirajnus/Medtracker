package sg.edu.nus.iss.se.ft05.medipal.constants;

/**
 * Created by mseal on 16/03/17.
 */

public class Constants {

    public static final String UPDATE = "Update";
    public static final String SAVE = "Save";
    public static final String SKIP = "Skip";
    public static final String ACTION = "action";
    public static final String EDIT = "edit";
    public static final String NEW = "New";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TITLE_WARNING = "Warning";
    public static final String OK_BUTTON = "OK";


    //HealthBio
    public static final String TITLE_NEW_HEALTHBIO = "New Health Details";
    public static final String TITLE_EDIT_HEALTHBIO = "Edit HeaLth Details";
    public static final String TITLE_VIEW_HEALTHBIO = "Health Details";
    public static final String CONDITION_TYPE_CONDITION = "C";
    public static final String CONDITION_TYPE_ALLERGY = "A";

    //Medicine
    public static final String MEDICINE_NAME = "medicineName";
    public static final String CONSUMPTION_MESSAGE = "Please Consume";
    public static final String MEDICINE = "Medicine";
    public static final String EDIT_MEDICINE = "Edit Medicine";
    public static final String NEW_MEDICINE = "New Medicine";
    public static final String MEDICINE_NOT_SAVED = "Medicine was not inserted properly,Please try again later";
    public static final String MEDICINE_NOT_UPDATED = "Medicine was not updated properly,Please try again later";
    public static final String MEDICINE_NAME_ERROR_MESSAGE = "Please provide medicine name!";
    public static final String MEDICINE_DESCRIPTION_ERROR_MESSAGE = "Please enter description!";
    public static final String MEDICINE_QUANTITY_ERROR_MESSAGE = "Please enter quantity";
    public static final String MEDICINE_CONSUME_QUANTITY_ERROR_MESSAGE = "Please enter consume quantity";
    public static final String MEDICINE_THRESHOLD_ERROR_MESSAGE = "Please enter threshold";
    public static final String MEDICINE_DATE_ISSUED_ERROR_MESSAGE = "Please enter date Issued";
    public static final String MEDICINE_EXPIRE_FACTOR_ERROR_MESSAGE = "Please enter expire factor";
    public static final String MEDICINE_FREQUENCY_ERROR_MESSAGE = "Please enter frequency";
    public static final String MEDICINE_START_TIME_ERROR_MESSAGE = "Please enter start time";
    public static final String MEDICINE_INTERVAL_ERROR_MESSAGE =  "Please enter interval";
    public static final String MEDICINE_CONSUME_QUALITY_LESS_THAN_QUANTITY = "Consume Quantity should be less than Quantity";
    public static final String MEDICINE_THRESHOLD_LESS_THAN_QUANTITY = "Threshold should be less than Quantity";
    public static final String MEDICINE_EXPIRE_FACTOR_LESS_THAN_24 = "Expire Factor should be less than 25";
    public static final String MEDICINE_REMINDER_CANNOT_TURN_OFF_CATEGORY = "Reminder cannot be turned off for this category of medicine";
    public static final String MEDICINE_PROPER_COMBINATION_OF_FREQUENCY_TIME_INTERVAL = "Input proper combination of Frequency,Start Time and Interval";
    
    //Miscellaneous
    public static final int MINUTE = 60000;
    public static final int HOUR = 60;
    public static final int DAY = 24;
    public static final String ID = "id";
    public static final String COLON = ":";
    public static final String CODE ="Code";

    //Category
    public static final String CATEGORY = "Category";
    public static final String EDIT_CATEGORY = "Edit Category";
    public static final String NEW_CATEGORY = "New Category";
    public static final String CATEGORY_NAME_ERROR_MESSAGE = "Please provide category name!";
    public static final String CATEGORY_CODE_ERROR_MESSAGE = "Please specify code!";
    public static final String CATEGORY_DESCRIPTION_ERROR_MESSAGE = "Please enter description";
    public static final String CATEGORY_CATEGORY_UNIQUE_ERROR_MESSAGE = "Please enter unique category name!";
    public static final String CATEGORY_CODE_UNIQUE_ERROR_MESSAGE = "Please enter unique code!";
    public static final String CATEGORY_NOT_SAVED = "Category was not inserted properly,Please try again later";
    public static final String CATEGORY_NOT_UPDATED = "Category was not updated properly,Please try again later";

    //Consumption
    public static final String CONSUMPTION = "Consumption";
    public static final String EDIT_CONSUMPTION = "Edit Consumption";
    public static final String NEW_CONSUMPTION = "New Consumption";
    public static final String CONSUMPTION_NOT_SAVED = "Consumption was not inserted properly,Please try again later";
    public static final String CONSUMPTION_NOT_UPDATED = "Consumption was not updated properly,Please try again later";
    public static final String CONSUMPTION_QUANTITY_ERROR_MESSAGE = "Please enter quantity";
    public static final String CONSUMPTION_DATE_ERROR_MESSAGE = "Please enter date";
    public static final String CONSUMPTION_TIME_ERROR_MESSAGE = "Please enter time";
    public static final String CONSUMPTION_QUANTITY_MORE_THAN_ERROR_MESSAGE = "Medicine should not be consumed more than";
    public static final String CONSUMPTION_FREQUENCY_NOT_MORE_THAN_ERROR_MESSAGE = "Medicine should not be consumed more than";
    public static final String CONSUMPTION_TIMES = "times";
    public static final String CONSUMPTION_NOT_BEFORE_ERROR_MESSAGE = "Medicine should not consumed before the date issued";

    //Appointment
    public static final String CLINIC = "clinic";

}
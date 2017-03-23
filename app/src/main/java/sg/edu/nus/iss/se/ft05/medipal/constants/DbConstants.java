package sg.edu.nus.iss.se.ft05.medipal.constants;

/**
 * Created by mseal on 15/03/17.
 */

public class DbConstants {

    //Tables..
    public static final String TABLE_HEALTH_BIO="healthbio";

    // HealthBio...
    public static final String HEALTH_BIO_KEY_ID = "id";
    public static final String HEALTH_BIO_KEY_CONDITION = "condition";
    public static final String HEALTH_BIO_KEY_CONDITION_TYPE = "conditionType";
    public static final String HEALTH_BIO_KEY_START_DATE = "startDate";

    // Constants
    public static final String DATABASE_COMMAND_SYMBOL = " = ?";
    public static final String DATABASE_COMMAND_SYMBOL_EQUAL = " = ";
    public static final String DATABASE_COMMAND_SELECT_ALL = "SELECT  * FROM ";
    public static final String DATABASE_COMMAND_SELECT_WHERE = " WHERE ";
    public static final String DATABASE_COMMAND_SELECT_MAXP = "MAXP";
    public static final String DATABASE_COMMAND_ORDER_BY = " ORDER BY ";
    public static final String DATABASE_COMMAND_SELECT_MAX_BEFORE = " SELECT  MAX(";
    public static final String DATABASE_COMMAND_SELECT_MAX_BETWEEN = ") AS ";
    public static final String DATABASE_COMMAND_SELECT_MAX_AFTER = " FROM ";
    public static final String DATABASE_COMMAND_ASC = " ASC";
    public static final String DATABASE_COMMAND_SINGLE_QUOTE = "'";
    public static final String DATABASE_COMMAND_SELECT_ID_CATEGORY = "SELECT  id,category FROM ";
    public static final String DATABASE_COMMAND_SELECT_ID_MEDICINE = "SELECT  id,medicine FROM ";
    public static final String DATABASE_COMMAND_SELECT_SUM = "SELECT  SUM";
    public static final String DATABASE_COMMAND_OPEN_BRACKET = "(";
    public static final String DATABASE_COMMAND_CLOSE_BRACKET = ")";
}

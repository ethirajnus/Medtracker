package com.ethigeek.medtracker;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by e0146812 on 3/26/2017.
 */
// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AppointmentDAOInstrumentedTest.class,
        CategoryDAOInstrumentedTest.class,
        HealthBioDAOInstrumentedTest.class,
        ICEContactsDAOInstrumentedTest.class,
        MeasurementDAOInstrumentedTest.class,
        MedicineDAOInstrumentedTest.class,
        PersonalDAOInstrumentedTest.class,
        ReminderDAOInstrumentedTest.class,
        ConsumptionDAOInstrumentedTest.class})
public class InstrumentedTestSuite {
}

package moffett.scheduler.model;

import moffett.scheduler.helper.Queries;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for appointment objects that will populate appointment table view in directory.
 */
public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    private int appointmentCustomerID;
    private int appointmentUserID;
    private int appointmentContactID;
    private String appointmentContactName;
    private String startString;
    private String endString;

    /**
     * Constructor for appointment object.
     * @param appointmentId
     * @param title
     * @param type
     * @param description
     * @param start
     * @param end
     * @param customerId
     * @throws SQLException
     */
    public Appointment(int appointmentId, String title, String type, String description, LocalDateTime start, LocalDateTime end, int customerId) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.appointmentID = appointmentId;
        this.appointmentTitle = title;
        this.appointmentType = type;
        this.appointmentDescription = description;
        this.appointmentCustomerID = customerId;
        this.appointmentContactName = Queries.selectContactName(appointmentContactID);
        this.startString = start.format(formatter);
        this.endString = end.format(formatter);

    }

    /**
     * Method for formatting appointment information as a string.
     * @return returns string representing appointment information.
     */
    public String toString() {
        return "Appointment " + appointmentID +
                "\nStart: " + startString +
                "\nEnd: " + endString +
                "\nTitle: " + appointmentTitle +
                "\nType: " + appointmentType +
                "\nDescription: " + appointmentDescription +
                "\nCustomer ID: " + appointmentCustomerID + "\n\n";
    }

    /**
     * Getters and setters.
     */

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    /**
     * Constructor for appointment object with DateTimeFormatter and Contact name fetching for table representation.
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param appointmentStart
     * @param appointmentEnd
     * @param appointmentCustomerID
     * @param appointmentUserID
     * @param appointmentContactID used to query for contact name and make it retrievable information from the object.
     * @throws SQLException
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation,
                       String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd,
                       int appointmentCustomerID, int appointmentUserID, int appointmentContactID) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.appointmentCustomerID = appointmentCustomerID;
        this.appointmentUserID = appointmentUserID;
        this.appointmentContactID = appointmentContactID;
        this.appointmentContactName = Queries.selectContactName(appointmentContactID);
        this.startString = appointmentStart.format(formatter);
        this.endString = appointmentEnd.format(formatter);


    }

    /**
     * Getters and setters.
     */

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getAppointmentContactName() {
        return appointmentContactName;
    }

    public void setAppointmentContactName(String appointmentContactName) {
        this.appointmentContactName = appointmentContactName;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public int getAppointmentCustomerID() {
        return appointmentCustomerID;
    }

    public void setAppointmentCustomerID(int appointmentCustomerID) {
        this.appointmentCustomerID = appointmentCustomerID;
    }

    public int getAppointmentUserID() {
        return appointmentUserID;
    }

    public void setAppointmentUserID(int appointmentUserID) {
        this.appointmentUserID = appointmentUserID;
    }

    public int getAppointmentContactID() {
        return appointmentContactID;
    }

    public void setAppointmentContactID(int appointmentContactID) {
        this.appointmentContactID = appointmentContactID;
    }

}



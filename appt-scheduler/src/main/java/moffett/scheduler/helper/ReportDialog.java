package moffett.scheduler.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import moffett.scheduler.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for a custom Dialog that gives the user 3 reports to choose from.
 */
public class ReportDialog extends Dialog<Void> {

    /**
     * Constructor method that creates new Report Dialog - CONTAINS LAMBDA
     * <p>
     * The Report Dialog allows the user to generate one of three reports from a radio group, and either
     * confirms their choice with null selection verification or allows them to close the dialog.
     * <p>
     * LAMBDA JUSTIFICATION - The lambda used here both set the onAction event of the
     * button immediately after it is created, allowing me to link the event to the button and to
     * define the behavior of the event in a relevant location without needing a separate declaration.
     * Since the generateButton that uses the lambda calls a different method depending on which radio button is
     * selected (using else ifs) and then closes, having that functionality in the constructor is easier than implementing that
     * functionality elsewhere.
     */
    public ReportDialog() {

        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));

        Label label = new Label("Select Report Type:");
        content.getChildren().add(label);

        RadioButton report1Button = new RadioButton("Number of Customer Appointments by type and month");
        RadioButton report2Button = new RadioButton("Contact schedule");
        RadioButton report3Button = new RadioButton("Customer with the most appointments");
        ToggleGroup group = new ToggleGroup();
        report1Button.setToggleGroup(group);
        report2Button.setToggleGroup(group);
        report3Button.setToggleGroup(group);
        content.getChildren().addAll(report1Button, report2Button, report3Button);
        content.getChildren().add(buttons);

        Button generateButton = new Button("Generate Report");
        generateButton.setOnAction(event -> {
            try {
                if (report1Button.isSelected()) {
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    generateReport1();
                } else if (report2Button.isSelected()) {
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    generateReport2();
                } else if (report3Button.isSelected()) {
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    generateReport3();
                } else {HelperFunctions.sendAlert(Alert.AlertType.ERROR,"No report selected.", "Please select a report to generate.");
                return;}
            }catch (Exception ignored) {}

        });
        buttons.getChildren().add(generateButton);

        Button cancelButton = new Button("Cancel Report");
        cancelButton.setOnAction(this::closeDialog);
        buttons.getChildren().add(cancelButton);

        getDialogPane().setContent(content);
    }

    /**
     * Simple class used to populate an observable list with different data types for multiple entities.
     */
    public static class ReportData {
        Month month;
        String type;
        int total;
        public Month getMonth() {
            return month;}
        public void setMonth(Month month) {
            this.month = month;}
        public String getType() {
            return type;}
        public void setType(String type) {
            this.type = type;}
        public int getTotal() {
            return total;}
        public void setTotal(int total) {
            this.total = total;}
        public ReportData(int monthNum, String type, int total) {
            this.month = Month.of(monthNum);
            this.type = type;
            this.total = total;

        }
    }

    /**
     * Method generates new dialog with a table view containing number of customer appointments by type and month.
     * <p>
     * Queries the database and adds the result to a ReportData observable array list before setting the table view
     * and generating dialog.
     * @throws SQLException
     */
    private void generateReport1() throws SQLException {

        ObservableList<ReportData> data = FXCollections.observableArrayList();

        String sql = "SELECT MONTH(start) AS month, type, COUNT(*) AS total FROM appointments GROUP BY MONTH(start), type";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int month = rs.getInt("month");
            String type = rs.getString("type");
            int total = rs.getInt("total");

            ReportData reportData = new ReportData(month, type, total);
            data.add(reportData);

        }
        TableColumn<ReportData, Month> monthCol = new TableColumn<>("Month");
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<ReportData, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<ReportData, Integer> countCol = new TableColumn<>("Count");
        countCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableView<ReportData> tableView = new TableView<>();
        tableView.getColumns().add(monthCol);
        tableView.getColumns().add(typeCol);
        tableView.getColumns().add(countCol);
        tableView.setItems(data);

        Button close = new Button("Close Report");
        close.setOnAction(this::closeDialog);

        Button backToReports = new Button ("Back to Reports");
        backToReports.setOnAction(this::closeAndBack);


        Dialog<Void> dialog = new Dialog<>();
        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));
        content.getChildren().add(tableView);
        content.getChildren().add(close);
        content.getChildren().add(backToReports);
        content.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(content);

        dialog.show();
    }


    /**
     * Generates custom dialog that shows the schedule and appointment info for each contact in tree view.
     * @throws SQLException
     */
    private void generateReport2() throws SQLException {
        String sql = "SELECT c.Contact_ID, c.Contact_Name, a.Appointment_ID, a.Title, a.Type, a.Description, a.Start, a.End, a.Customer_ID " +
                "FROM contacts c " +
                "JOIN appointments a ON c.Contact_ID = a.Contact_ID " +
                "ORDER BY c.Contact_Name, a.Start";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        Map<String, List<Appointment>> appointmentsByContact = new HashMap<>();

        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String type = rs.getString("Type");
            String description = rs.getString("Description");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            Appointment appointment = new Appointment(appointmentId, title, type, description, start, end, customerId);

            String contactName = rs.getString("Contact_Name");

            List<Appointment> appointments = appointmentsByContact.getOrDefault(contactName, new ArrayList<>());
            appointments.add(appointment);
            appointmentsByContact.put(contactName, appointments);
        }

        TreeItem<String> root = new TreeItem<>("Contacts");

        for (String contactName : appointmentsByContact.keySet()) {
            TreeItem<String> contactItem = new TreeItem<>(contactName);

            List<Appointment> appointments = appointmentsByContact.get(contactName);
            for (Appointment appointment : appointments) {
                TreeItem<String> appointmentItem = new TreeItem<>(appointment.toString());
                contactItem.getChildren().add(appointmentItem);
            }

            root.getChildren().add(contactItem);
        }

        TreeView<String> treeView = new TreeView<>(root);
        treeView.setShowRoot(false);

        Button close = new Button("Close Report");
        close.setOnAction(this::closeDialog);

        Button backToReports = new Button ("Back to Reports");
        backToReports.setOnAction(this::closeAndBack);

        ScrollPane scrollPane = new ScrollPane(treeView);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);

        Dialog<Void> dialog = new Dialog<>();
        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));
        content.getChildren().add(scrollPane);
        content.getChildren().add(close);
        content.getChildren().add(backToReports);
        content.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(content);
        dialog.show();
    }


    /**
     * Generates custom dialog that displays the customer with the most scheduled appointments and their number of
     * appointments.
     * @throws SQLException
     */
    private void generateReport3() throws SQLException {
        try {
            String sql = "SELECT c.Customer_Name, COUNT(*) AS Appointment_Count " +
                    "FROM appointments a " +
                    "JOIN customers c ON a.Customer_ID = c.Customer_ID " +
                    "GROUP BY c.Customer_Name " +
                    "HAVING COUNT(*) = (" +
                    "SELECT COUNT(*) " +
                    "FROM appointments a " +
                    "JOIN customers c ON a.Customer_ID = c.Customer_ID " +
                    "GROUP BY c.Customer_Name " +
                    "ORDER BY COUNT(*) DESC " +
                    "LIMIT 1" +
                    ") " +
                    "ORDER BY c.Customer_Name";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            StringBuilder messageBuilder = new StringBuilder("Customer(s) with the most appointments:\n\n");

            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                int appointmentCount = rs.getInt("Appointment_Count");
                messageBuilder.append(customerName).append(" with ").append(appointmentCount).append(" appointment(s).\n\n");
            }

            String message = messageBuilder.toString();

                Label messageLabel = new Label();
                messageLabel.setText(message);
                messageLabel.setFont(Font.font("Arial", 18));


                Dialog<Void> customerDialog = new Dialog<>();
                VBox content = new VBox();
                Button close = new Button("Close Report");
                close.setOnAction(this::closeDialog);

                Button backToReports = new Button ("Back to Reports");
                backToReports.setOnAction(this::closeAndBack);


                content.setSpacing(10);
                content.setPadding(new Insets(10));
                content.getChildren().add(messageLabel);
                content.getChildren().add(close);
                content.getChildren().add(backToReports);
                content.setAlignment(Pos.CENTER);
                customerDialog.getDialogPane().setContent(content);
                customerDialog.show();

        } catch (SQLException e) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "SQL EXCEPTION", "Error while getting customer with most appointments: " + e.getMessage());
        }




    }


    /**
     * Closes the dialog that contains the source of event.
     * @param event method gets the source node of the event in order to close it.
     */
    private void closeDialog(ActionEvent event){
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
    }

    /**
     * Closes the dialog that contains the source of event and re-generates ReportDialog.
     * @param event method gets the source node of the event in order to close it.
     */
    private void closeAndBack(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        ReportDialog dialog = new ReportDialog();
        dialog.showAndWait();
    }



}

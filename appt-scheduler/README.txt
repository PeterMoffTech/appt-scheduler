This application and all associated files have been meticulously developed and formatted to explicitly meet the requirements specified in the rubric.
They have been designed to include all essential configurations to ensure compatibility with the evaluator's standardized VM environment.


TITLE AND PURPOSE OF APPLICATION:

        The given application is named "appt_scheduler" and its primary function is to enable users to manage customer and appointment records in
    a mysql database. It offers features like creating, deleting, updating, and removing customers and appointments. Moreover, the application
    can generate specific reports based on the database information. It also provides options for sorting customers and appointments in their
    respective tables. Additionally, users can apply filters to the appointments table to view records for a specific month, week, or all records.


AUTHOR: Peter Moffett
CONTACT INFORMATION: pmoffe6@wgu.edu
APPLICATION VERSION: v1.0
DATE: 07/05/2022


IDE: IntelliJ IDEA 2022.1 (Community Edition)
JDK: Runtime version: jdk-17_windows-x64_bin
JAVAFX VERSION: openjfx-17.0.6_windows-x64_bin-sdk


DIRECTIONS TO RUN PROGRAM:

    When you launch the application, you will encounter a login screen. To access the application, you have two options
    for logging in. You can use the username "sqlUser" along with the password "Passw0rd!" or utilize the root user credentials for the locally hosted database.

    Once you have successfully logged in, you will be directed to the directory form where a welcoming alert will be displayed.

    The alert will inform you if there are any appointments scheduled to commence within the next 15 minutes.

    Upon closing the alert, you will have access to view customer and appointment details in their respective tables.
    Additionally, you will be able to apply filters to the appointments table using radio buttons located above the table.
    These filters include options to view all appointments, appointments for the current month, or appointments for the current calendar week.

    To create a new customer, simply click on the 'Add' button located above the customer table. This action will redirect you to a separate
    form where you can input the necessary customer information. Similarly, if you wish to edit a customer's details, you can click on the
    'Edit' button above the customer table. This will redirect you to a similar form, but with all the fields pre-populated with the customer's
    existing information. Moreover, if you want to remove a selected customer, you can utilize the 'Remove' button. It's important to note that
    removing a customer will also result in the deletion of all associated appointments.

    To create a new appointment, click on the 'Add' button located above the appointments table. This action will redirect you to a fresh form where
    you can input all the necessary information for the appointment. Date pickers are available for selecting the desired dates, while spinners allow
    you to specify the hour and minute for both the start and end time.

    Similarly, if you wish to edit an existing appointment, click on the 'Edit' button above the appointment table. This will take you to a similar form,
    where all the fields will be pre-populated with the information of the selected appointment.

    In addition, you can delete a specific appointment by using the 'Remove' button. This action will remove the selected appointment from the system.

    Within the directory section, there is an option to generate three different reports by clicking on the 'Reports' button. Upon clicking this button,
    a prompt will appear, allowing you to select one of the three available reports to generate. The generated reports will be displayed in dialog boxes for your convenience.

    By clicking on the 'Logout' button, you can close the database connection and be redirected back to the Login screen.
    By clicking on the 'Exit Program' button, the database connection will be closed, and the program will be terminated.

ADDITIONAL REPORT DESCRIPTION:

    The additional report that I have selected to be generated by the application shows the customer or customers with the highest number of scheduled appointments,
    along with the corresponding count of their appointments. This particular report can be valuable for businesses seeking to identify their most valuable or active customers.


MYSQL CONNECTOR DRIVER VERSION: mysql-connector-j-8.0.33


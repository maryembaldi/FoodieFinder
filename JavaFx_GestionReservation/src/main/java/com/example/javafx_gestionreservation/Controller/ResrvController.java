package com.example.javafx_gestionreservation.Controller;

import com.example.javafx_gestionreservation.Entite.Reservation;
import com.example.javafx_gestionreservation.Entite.Restaurant;
import com.example.javafx_gestionreservation.Entite.User;
import com.example.javafx_gestionreservation.Services.ServiceReservation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ResrvController {

    @FXML
    private TextField restaurantTextField;

    @FXML
    private TextField nombrePersonneTextField;

    @FXML
    private DatePicker dateReservationDatePicker;

    @FXML
    private TextField reserveParTextField;

    @FXML
    private Button reserverButton;

    @FXML
    private TableView<Reservation> local_table_view_fid;

    @FXML
    private TableColumn<Reservation, String> column_local_id_fid;

    @FXML
    private TableColumn<Reservation, String> column_local_name_fid;

    @FXML
    private TableColumn<Reservation, LocalDate> column_local_capacity_fid;

    @FXML
    private TableColumn<Reservation, String> column_local_available_from_fid;

    @FXML
    private TableColumn<Reservation, Void> column_is_booked_fid;

    private final ServiceReservation serviceReservation = new ServiceReservation();







    private void initializeColumns() {
        column_local_id_fid.setCellValueFactory(new PropertyValueFactory<>("ReservationID"));
        column_local_name_fid.setCellValueFactory(new PropertyValueFactory<>("NombrePersonnes"));
        column_local_capacity_fid.setCellValueFactory(new PropertyValueFactory<>("DateHeureReservation"));
        column_local_available_from_fid.setCellValueFactory(new PropertyValueFactory<>("user"));
        // Assuming you have a boolean property isBooked in your Reservation class
        //column_is_booked_fid.setCellValueFactory(new PropertyValueFactory<>("StatutReservation"));
        column_is_booked_fid.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("❌");
            {
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    handleDeleteReservation(reservation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void loadReservations() {
        initializeColumns();
        try {
            System.out.println(serviceReservation.listerTous());
            ObservableList<Reservation> reservationList = FXCollections.observableArrayList(serviceReservation.listerTous());
            local_table_view_fid.setItems(reservationList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void initialize() {
        //configureTableColumns();
        loadReservations();
//       reserverButton.setOnAction(event -> handleReservation());
    }

    private void configureTableColumns() {
        // Configure your table columns here
        column_local_id_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getReservationID()).asString());
        column_local_name_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getUser()).asString());

        column_is_booked_fid.setCellFactory(param -> new TableCell<>() {
                private final Button deleteButton = new Button("❌");
            {
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    handleDeleteReservation(reservation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }


    @FXML
    void reserver(ActionEvent event) {
        try {
            // Get values from UI components
            String restaurantName = restaurantTextField.getText();
            int nombrePersonne = Integer.parseInt(nombrePersonneTextField.getText());
            LocalDate dateReservation = dateReservationDatePicker.getValue();
            String reservedBy = reserveParTextField.getText();

            // Validate input (you can add more validation as needed)
            if (restaurantName.isEmpty() || nombrePersonne <= 0 || dateReservation == null || reservedBy.isEmpty()) {
                // Handle validation error (show alert, message, etc.)
                System.out.println("Please fill in all fields.");
                return;
            }

            // Create Reservation object
            User user = new User(Integer.parseInt(reservedBy));  // Replace this with actual user retrieval logic
            Restaurant restaurant = new Restaurant(Integer.parseInt(restaurantName));  // Replace this with actual restaurant retrieval logic
            Reservation reservation = new Reservation(0, user, restaurant, Date.from(dateReservation.atStartOfDay(ZoneId.systemDefault()).toInstant()), nombrePersonne);

            // Add reservation
            serviceReservation.ajouter(reservation);

            // Optionally, you can clear the input fields after reservation
            clearInputFields();
            loadReservations();


        } catch (NumberFormatException e) {
            System.out.println("Invalid input for Nombre Personne.");
            // Handle invalid input (show alert, message, etc.)
        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage());
            // Handle SQL exception (show alert, message, etc.)
        }
    }

    private void clearInputFields() {
        restaurantTextField.clear();
        nombrePersonneTextField.clear();
        dateReservationDatePicker.setValue(null);
        reserveParTextField.clear();
    }


    private void handleReservation() {
        try {
            String restaurant = restaurantTextField.getText();
            int nombrePersonnes = Integer.parseInt(nombrePersonneTextField.getText());
            LocalDate dateReservation = dateReservationDatePicker.getValue();
            String reservePar = reserveParTextField.getText();

            // Vous devrez peut-être ajuster cette partie en fonction de votre modèle de données
            Reservation newReservation = new Reservation(0, null, null, java.sql.Date.valueOf(String.valueOf(dateReservation.atStartOfDay())), nombrePersonnes);

            // Appelez le service pour ajouter une nouvelle réservation
            serviceReservation.ajouter(newReservation);

            // Rechargez la liste des réservations après l'ajout
            loadReservations();

            // Effacez les champs de saisie après l'ajout
            clearReservationFields();
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Gérer l'exception si la conversion de texte en entier échoue
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception liée à la base de données
        }
    }

    private void handleDeleteReservation(Reservation reservation) {
        try {
            // Appelez le service pour supprimer la réservation sélectionnée
            serviceReservation.supprimer(reservation.getReservationID());

            // Rechargez la liste des réservations après la suppression
            loadReservations();
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception liée à la base de données
        }
    }

    private void clearReservationFields() {
        // Effacez le contenu des champs de saisie après l'ajout ou l'annulation
        restaurantTextField.clear();
        nombrePersonneTextField.clear();
        dateReservationDatePicker.setValue(null);
        reserveParTextField.clear();
    }
}

package model.services;

import entities.model.CarRental;
import entities.model.Invoice;

import java.time.Duration;

public class RentalServices {

    private Double pricePerDay;
    private Double PricePerHour;

    private BrazilTaxServices taxServices;

    public RentalServices(Double pricePerDay, Double getPricePerHour, BrazilTaxServices taxServices) {
        this.pricePerDay = pricePerDay;
        this.PricePerHour = getPricePerHour;
        this.taxServices = taxServices;
    }

    public void processInvoice(CarRental carRental) {

        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = minutes / 60.00;

        double basicPayment;
        if (hours <= 12.00) {
            basicPayment = PricePerHour * Math.ceil(hours);
        }
        else {
            basicPayment = pricePerDay * Math.ceil(hours / 24.00);
        }
        double tax = taxServices.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment ,tax));
    }

}

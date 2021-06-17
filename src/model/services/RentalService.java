package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		super();
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	
	public void processInvoice(CarRental carRental) {
		long timeStart = carRental.getStart().getTime();
		long timeFinish = carRental.getFinish().getTime();
		double time = (double) (timeFinish - timeStart) / 1000 / 60 / 60;
		
		double basicPayment;
		if (time <= 12.0) {
			basicPayment = Math.ceil(time) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(time / 24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);

		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
	
}

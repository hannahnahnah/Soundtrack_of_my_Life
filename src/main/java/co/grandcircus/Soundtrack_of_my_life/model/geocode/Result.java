package co.grandcircus.Soundtrack_of_my_life.model.geocode;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
	
	@JsonProperty("formatted_address")
	private String formattedAddress;
	private Geometry geometry;
	
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	@Override
	public String toString() {
		return "Result [formattedAddress=" + formattedAddress + ", geometry=" + geometry + "]";
	}
	
	

}

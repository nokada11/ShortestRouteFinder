public class Destination {
	private String name;
	private int longitude;
	private int latitude;
	private int order;
	
	Destination (String name, int longitude, int latitude, int order){
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.order = order;
	}
	
	String getName(){ //Getter
		return name;
	}
	
	int getLongitude (){ //Getter
		return longitude;
	}
	
	int getLatitude (){ //Getter
		return latitude;
	}
	
	int getOrder(){ //Getter
		return order;
	}
	
	double distanceTo(Destination nextDestination){
		double distance = Math.sqrt(Math.pow(longitude - nextDestination.longitude, 2) + Math.pow(latitude - nextDestination.latitude, 2));
	
		return distance;
	}
}
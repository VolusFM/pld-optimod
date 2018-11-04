package model;

import java.util.ArrayList;
import java.util.List;

public class Tour {
	
	private List <Section> stepList;
	private List <Delivery> deliveryPoints;
	private int deliveryManId;
	
	public Tour (List <Section> stepList, List <Delivery> deliveryPoints, int deliveryMan){
		this.stepList = new ArrayList<>(stepList);
		this.deliveryPoints = new ArrayList<>(deliveryPoints);
		this.deliveryManId = deliveryMan; 
	}
	
}

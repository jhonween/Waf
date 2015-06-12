package com.willard.waf.model;

public class Circle {
	
	public Point point;
	public double r;
	
	public Circle() {
		super();
	}

	public Circle(Point point, double r) {
		super();
		this.point = point;
		this.r = r;
	}

	@Override
	public String toString() {
		return "(" + point.x + "," + point.y + "),r="+r;
	}

}

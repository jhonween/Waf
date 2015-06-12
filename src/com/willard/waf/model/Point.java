package com.willard.waf.model;

public class Point {
	
	public double x;
	public double y;
	
	public Point() {
		super();
	}

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		Point point = (Point)o;
		if(this.x == point.x && this.y == point.y){
			return true;   
		}
        return false;   
	}

	@Override
	public int hashCode() {
		return (int)(x*y)^8;
	}
	
	
}

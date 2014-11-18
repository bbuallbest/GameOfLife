package com.bbuallbest.journal;

public enum Importance {
	     NORMAL(1, ".    "),
	    WARNING(2, "!    "),
	      ERROR(3, "!!!  "),
	FATAL_ERROR(4, "!!!!!");
	
	private int value;
	private String view;
	
	private Importance(int value, String view) {
		this.value = value;
		this.view = view;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getView() {
		return view;
	}
}

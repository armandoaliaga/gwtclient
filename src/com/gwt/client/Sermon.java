package com.gwt.client;


public class Sermon {
	 private int id;
	 private String name;
	 private String name_of_predicador;
	 private String description;
	 private String serie;
	 private int duration;
	 private String date;
	 private String play="";
	 
	 
	 
	public Sermon(int id, String name, String name_of_predicador,String description, String serie, int duration, String date) {
		super();
		this.id = id;
		this.name = name;
		this.name_of_predicador = name_of_predicador;
		this.description = description;
		this.serie = serie;
		this.duration = duration;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName_of_predicador() {
		return name_of_predicador;
	}
	public void setName_of_predicador(String name_of_predicador) {
		this.name_of_predicador = name_of_predicador;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getDate() {
		return date;
	}
	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

	public void setDate(String date) {
		this.date = date;
	}	 
}

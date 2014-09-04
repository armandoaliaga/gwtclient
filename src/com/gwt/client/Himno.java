package com.gwt.client;

public class Himno {
	 private int id;
	 private int number;
	 private String name;
	 private String lyrics;
	 private String aux="";
	 
	public Himno(int id, int number, String name, String lyrics) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.lyrics = lyrics;
	}
	public Himno(int number, String name, String lyrics) {
		super();
		this.number = number;
		this.name = name;
		this.lyrics = lyrics;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAux() {
		return aux;
	}
	public void setAux(String aux) {
		this.aux = aux;
	}		
}

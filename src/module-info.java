module moduleinfo {
	exports persistentie;
	exports cui;
	exports gui;
	exports main;
	exports domein;
	exports exceptions;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	
	opens gui;
}
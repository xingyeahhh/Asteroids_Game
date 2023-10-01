module Asteroids_JavaFX_XingYeah_version {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;
	requires java.desktop;
	
    exports Exmaple to javafx.graphics;
    exports Body to javafx.graphics;
    //这个错误可能是由于模块化 Java（Java 9 及更高版本）引入的模块系统导致的。特别是，这个错误消息中提到了模块 Asteroids_JavaFX_XingYeah_version 无法导出 Exmaple 给模块 javafx.graphics，导致 javafx.graphics 无法访问 Exmaple.PaneExample 类。
//	opens example to javafx.fxml;
//    exports example;
}
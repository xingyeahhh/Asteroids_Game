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
    //����������������ģ�黯 Java��Java 9 �����߰汾�������ģ��ϵͳ���µġ��ر��ǣ����������Ϣ���ᵽ��ģ�� Asteroids_JavaFX_XingYeah_version �޷����� Exmaple ��ģ�� javafx.graphics������ javafx.graphics �޷����� Exmaple.PaneExample �ࡣ
//	opens example to javafx.fxml;
//    exports example;
}
package com.project.indonesiainvestorclub.models;

public class MenuModel {

    public int menu, img;
    public String menuName;
    public boolean hasChildren, isGroup;

    public MenuModel(int menu, int img, String menuName, boolean isGroup, boolean hasChildren) {
        this.menu = menu;
        this.img = img;
        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
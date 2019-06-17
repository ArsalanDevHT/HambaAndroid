package net.hamba.android.Instagram;

public class CategoryModel {
    private String cate_name;
    private boolean isSelected;

    public CategoryModel(String cate_name, boolean isSelected) {
        this.cate_name = cate_name;
        this.isSelected = isSelected;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

package net.hamba.android.Utils;

import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import net.hamba.android.Models.WeekdayModel;

public class FormUtils {




    public static  boolean isValidTextInputParent(ViewGroup viewGroup) {
        boolean isvalid = true;
        for (int i=0;i<viewGroup.getChildCount();i++) {
            if (viewGroup.getChildAt(i) instanceof TextInputLayout) {
                TextInputLayout editText = (TextInputLayout) viewGroup.getChildAt(i);
                if(editText.getTag()!=null) {
                    editText.setErrorEnabled(true);
                    String text = editText.getEditText().getText().toString().trim();
                    if (text.isEmpty()) {
                        editText.setError("Required");
                        editText.requestFocus();
                        isvalid = false;
                    } else {
                        if (editText.getEditText().getInputType() == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + InputType.TYPE_CLASS_TEXT) {
                            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                                editText.setError("Invalid Email");
                                editText.requestFocus();
                                return false;
                            } else {
                                editText.setError(null);
                                editText.setErrorEnabled(false);
                                isvalid = (isvalid && true);

                            }
                        } else {
                            editText.setError(null);
                            editText.setErrorEnabled(false);
                            isvalid = (isvalid && true);

                        }
                        if (editText.getTag().equals("password") && editText.getEditText().getText().length() < 6) {
                            editText.setError("Password must contain 6 characters");
                            editText.requestFocus();
                            return false;
                        } else {
                            editText.setError(null);
                            editText.setErrorEnabled(false);
                            isvalid = (isvalid && true);


                        }
                        if (editText.getTag().equals("Phone") && editText.getEditText().getText().length() < 13) {
                            editText.setError("Phone must contain 13 characters");
                            editText.requestFocus();
                            return false;
                        } else {
                            editText.setError(null);
                            editText.setErrorEnabled(false);
                            isvalid = (isvalid && true);


                        }
                        if (editText.getTag().equals("name") && editText.getEditText().getText().length() < 4) {
                            editText.setError("Business name must contain 4 characters");
                            editText.requestFocus();
                            return false;
                        } else {
                            editText.setError(null);
                            editText.setErrorEnabled(false);
                            isvalid = (isvalid && true);

                            if (editText.getTag().equals("fullname") && editText.getEditText().getText().length() < 4) {
                                editText.setError("Full name must contain 6 characters");
                                editText.requestFocus();
                                return false;
                            } else {
                                editText.setError(null);
                                editText.setErrorEnabled(false);
                                isvalid = (isvalid && true);
                            }
                            if (editText.getTag().equals("id") && editText.getEditText().getText().length() < 16) {
                                editText.setError("National/Passport id must contain 16 characters");
                                editText.requestFocus();
                                return false;
                            } else {
                                editText.setError(null);
                                editText.setErrorEnabled(false);
                                isvalid = (isvalid && true);
                            }
                            if (editText.getTag().equals("des") && editText.getEditText().getText().length() < 50) {
                                editText.setError("Description must contain 50 characters");
                                editText.requestFocus();
                                return false;
                            } else {
                                editText.setError(null);
                                editText.setErrorEnabled(false);
                                isvalid = (isvalid && true);
                            }


                        }
                    }
                }

            }
        }

        return isvalid;
    }
    public static String getText(EditText editText){
        return editText.getText().toString();
    };

    public static boolean validateWeekdays(ViewGroup viewGroup){
        boolean isvalid = false;

        for (int i=0;i<viewGroup.getChildCount();i++) {
            if(viewGroup.getChildAt(i) instanceof LinearLayout){
                LinearLayout childViewGroup = (LinearLayout) viewGroup.getChildAt(i);
                for (int j=0;j<childViewGroup.getChildCount();j++) {
                    if (childViewGroup.getChildAt(j) instanceof TextInputLayout) {
                        TextInputLayout editText = (TextInputLayout) childViewGroup.getChildAt(j);
                        String text = editText.getEditText().getText().toString().trim();
                        if (text.isEmpty()) {
                            editText.setErrorEnabled(true);
                            editText.setError("Required");
                            editText.requestFocus();
                            isvalid = false;
                        }else{
                            editText.setErrorEnabled(false);
                            editText.setError(null);
                            isvalid = true;
                        }
                    }


                }
            }
        }
        return isvalid;
    }
    public static boolean clearError(ViewGroup viewGroup){
        boolean isvalid = false;

        for (int i=0;i<viewGroup.getChildCount();i++) {
            if(viewGroup.getChildAt(i) instanceof LinearLayout){
                LinearLayout childViewGroup = (LinearLayout) viewGroup.getChildAt(i);
                for (int j=0;j<childViewGroup.getChildCount();j++) {
                    if (childViewGroup.getChildAt(j) instanceof TextInputLayout) {
                        TextInputLayout editText = (TextInputLayout) childViewGroup.getChildAt(j);
                        String text = editText.getEditText().getText().toString().trim();
                        if (text.isEmpty()) {
                            editText.setErrorEnabled(false);
                            isvalid = false;
                        }else{
                            editText.setErrorEnabled(false);
                            editText.setError(null);
                            isvalid = true;
                        }
                    }


                }
            }
        }
        return isvalid;
    }
    public static WeekdayModel getTime(ViewGroup viewGroup, String name,boolean isOpen){
        WeekdayModel model = new WeekdayModel();
        model.setDayName(name);
        model.setOpen(isOpen);

        for (int i=0;i<viewGroup.getChildCount();i++) {
            if(viewGroup.getChildAt(i) instanceof LinearLayout){
                LinearLayout childViewGroup = (LinearLayout) viewGroup.getChildAt(i);
                for (int j=0;j<childViewGroup.getChildCount();j++) {
                    if (childViewGroup.getChildAt(j) instanceof TextInputLayout) {
                        TextInputLayout editText = (TextInputLayout) childViewGroup.getChildAt(j);
                        String text = editText.getEditText().getText().toString().trim();
                        if (!text.isEmpty()) {
                            if(editText.getTag().equals("from")){
                                model.setFromTime(text);
                            }
                            if(editText.getTag().equals("to")){
                                model.setToTime(text);
                            }
                        }
                    }

                }
            }
        }
        return model;
    }

    public static void setTime(ViewGroup viewGroup, WeekdayModel weekdayModel){

        for (int i=0;i<viewGroup.getChildCount();i++) {
            if(viewGroup.getChildAt(i) instanceof LinearLayout){
                LinearLayout childViewGroup = (LinearLayout) viewGroup.getChildAt(i);
                for (int j=0;j<childViewGroup.getChildCount();j++) {
                    if (childViewGroup.getChildAt(j) instanceof TextInputLayout) {
                        TextInputLayout editText = (TextInputLayout) childViewGroup.getChildAt(j);
                        String text = editText.getEditText().getText().toString().trim();
                            if(editText.getTag().equals("from")){
                                editText.getEditText().setText(weekdayModel.getFromTime());
                            }
                            if(editText.getTag().equals("to")){
                                editText.getEditText().setText(weekdayModel.getToTime());

                            }
                    }

                }
            }
        }
    }
}

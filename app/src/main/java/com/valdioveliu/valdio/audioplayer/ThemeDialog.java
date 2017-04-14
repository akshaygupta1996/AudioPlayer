package com.valdioveliu.valdio.audioplayer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Akshay on 14-04-2017.
 */

public class ThemeDialog extends DialogFragment {

    RadioGroup radioGroup;
    Button btnCancel, btnApply;
    RadioButton defaultTheme, darkTheme, YellowTheme, blueTheme;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflator=getActivity().getLayoutInflater();
        View view=inflator.inflate(R.layout.theme_dialog_layout,null);
        builder.setView(view);

        radioGroup = (RadioGroup)view.findViewById(R.id.radio);
        btnApply = (Button)view.findViewById(R.id.btnApply);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);
        defaultTheme = (RadioButton)view.findViewById(R.id.defaultTheme);
        darkTheme = (RadioButton)view.findViewById(R.id.darkTheme);
        YellowTheme = (RadioButton)view.findViewById(R.id.LightYelloTheme);
        blueTheme = (RadioButton)view.findViewById(R.id.BlueTheme);


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//            }
//        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                if(selectedId == defaultTheme.getId()){
                    themeUtils.changeToTheme(getActivity(),0);

                }else if(selectedId == darkTheme.getId()){

                    themeUtils.changeToTheme(getActivity(),2);

                }else if(selectedId == YellowTheme.getId()){

                    themeUtils.changeToTheme(getActivity(),1);
                }else if(selectedId == blueTheme.getId()){
                    themeUtils.changeToTheme(getActivity(),3);
                }
                else{
                    themeUtils.changeToTheme(getActivity(),0);
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}

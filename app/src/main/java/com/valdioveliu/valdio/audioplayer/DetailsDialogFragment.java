package com.valdioveliu.valdio.audioplayer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Akshay on 14-04-2017.
 */

public class DetailsDialogFragment extends DialogFragment {


    private TextView textTitle, textArtist, textAlbum, textDuration, textPath;
    private String title,artist, album, path;
    private float duration;
    private Button btnDelete, btnOk;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflator=getActivity().getLayoutInflater();
        View view=inflator.inflate(R.layout.song_detail_view,null);
        builder.setView(view);

//        builder.getWindow().setBackgroundDrawableResource(android.R.color.transpare‌​nt);


        textTitle = (TextView)view.findViewById(R.id.textTitle);
        textArtist = (TextView)view.findViewById(R.id.textArtist);
        textAlbum = (TextView)view.findViewById(R.id.textAlbum);
        textDuration = (TextView)view.findViewById(R.id.textDuration);
        textPath = (TextView)view.findViewById(R.id.textPath);
        btnDelete = (Button)view.findViewById(R.id.btnDelete);
        btnOk = (Button)view.findViewById(R.id.btnOk);



        Bundle bundle = getArguments();
        title = bundle.getString("title","");
        artist = bundle.getString("artist","");
        album = bundle.getString("album","");
        duration = bundle.getFloat("duration");
        path = bundle.getString("path","");
        textTitle.setText(title);
        textPath.setText(path);
        textArtist.setText(artist);
        textAlbum.setText(album);
        float minutes = TimeUnit.MILLISECONDS.toMinutes((int)duration);
        textDuration.setText(Float.toString(minutes));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                File sdcard = new File(Environment.getExternalStorageDirectory(),"sample");
                File from = new File(sdcard, path);
                from.delete();
                from.deleteOnExit();

                dismiss();

                Toast.makeText(getActivity(), "Deleted File", Toast.LENGTH_SHORT).show();

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });

        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;

    }
}

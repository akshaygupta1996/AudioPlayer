package com.valdioveliu.valdio.audioplayer;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity  {

    public static final String Broadcast_PLAY_NEW_AUDIO = "com.valdioveliu.valdio.audioplayer.PlayNewAudio";

    private MediaPlayerService player;
    boolean serviceBound = false;
    ArrayList<Audio> audioList,sortedList;
    private MaterialSearchView searchView;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView textFilters, textTheme;
    private String[] filters,themes;
    private int selectedFilter=0, selectedTheme = 0;
    ImageView collapsingImageView;


    int imageIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
//        themeUtils.changeToTheme(this,1);
        setContentView(R.layout.activity_main);
//        audioList.clear();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar);



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Music Player");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        collapsingImageView = (ImageView) findViewById(R.id.collapsingImageView);


        searchView = (MaterialSearchView)findViewById(R.id.searchBar);


        sortedList = new ArrayList<Audio>();

        textFilters = (TextView)findViewById(R.id.textFilters);
        textTheme = (TextView)findViewById(R.id.textTheme);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);


                return;
            }}

        loadCollapsingImage(imageIndex);
        loadAudio();
        initRecyclerView(audioList);

        filters = new String[]{
                "Title",
                "Artist",
                "Album"
        };
        themes = new String[]{
                "Default",
                "Yellow",
                "Dark"
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                playAudio("https://upload.wikimedia.org/wikipedia/commons/6/6c/Grieg_Lyric_Pieces_Kobold.ogg");
                //play the first audio in the ArrayList
//                playAudio(2);
                if (imageIndex == 4) {
                    imageIndex = 0;
                    loadCollapsingImage(imageIndex);
                } else {
                    loadCollapsingImage(++imageIndex);
                }
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//                RecyclerView_Adapter adapter = new RecyclerView_Adapter(audioList, getApplication());
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                recyclerView.addOnItemTouchListener(new CustomTouchListener(MainActivity.this, new onItemClickListener() {
//                    @Override
//                    public void onClick(View view, int index) {
//                        playAudio(index, audioList);
//                    }
//                }));

                initRecyclerView(audioList);


            }
        });


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText!=null && !newText.isEmpty()){
                    final ArrayList<Audio> listFound = new ArrayList<Audio>();
                    for(Audio item: audioList){
                        if(item.getTitle().contains(newText)){
                            listFound.add(item);
                        }
//                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//                        RecyclerView_Adapter adapter = new RecyclerView_Adapter(listFound, getApplication());
//                        recyclerView.setAdapter(adapter);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                        recyclerView.addOnItemTouchListener(new CustomTouchListener(MainActivity.this, new onItemClickListener() {
//                            @Override
//                            public void onClick(View view, int index) {
//                                playAudio(index, listFound);
//                            }
//                        }));

                        initRecyclerView(listFound);

                    }
                }
                else{

//                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//                    RecyclerView_Adapter adapter = new RecyclerView_Adapter(audioList, getApplication());
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                    recyclerView.addOnItemTouchListener(new CustomTouchListener(MainActivity.this, new onItemClickListener() {
//                        @Override
//                        public void onClick(View view, int index) {
//                            playAudio(index, audioList);
//                        }
//                    }));

                    initRecyclerView(audioList);
                }

                return true;
            }
        });


        textFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                sortedList.clear();

//                Toast.makeText(MainActivity.this, "Filter Clicked", Toast.LENGTH_SHORT).show();


                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Apply Filters");
                alertDialog.setSingleChoiceItems(filters, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        selectedFilter = which;

                    }
                });
                alertDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        Log.d("ALERT","Apply Button Clicked"+selectedFilter);

                        //sortedList.clear();
                        sortedList = audioList;

                        Collections.sort(sortedList, new Comparator<Audio>() {
                            @Override
                            public int compare(Audio lhs, Audio rhs) {
                                if(selectedFilter == 0) {
                                     return lhs.getTitle().compareTo(rhs.getTitle());
                                }else if(selectedFilter == 1){
                                    return lhs.getArtist().compareTo(rhs.getArtist());
                                }else{
                                    return lhs.getAlbum().compareTo(rhs.getAlbum());
                                }
                            }

                        });

//                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//                        RecyclerView_Adapter adapter = new RecyclerView_Adapter(sortedList, getApplication());
//                        recyclerView.setAdapter(adapter);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                        recyclerView.addOnItemTouchListener(new CustomTouchListener(MainActivity.this, new onItemClickListener() {
//                            @Override
//                            public void onClick(View view, int index) {
//                                playAudio(index, sortedList);
//                            }
//                        }));


                        initRecyclerView(sortedList);




                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        Log.d("ALERT","Cancel Button Clicked");

                    }
                });
//                alertDialog.create();
                alertDialog.show();
            }
        });

        textTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ThemeDialog themeDialog = new ThemeDialog();
                themeDialog.show(getFragmentManager(),"Theme Dialog");


//                Toast.makeText(MainActivity.this, "Themes Clicked", Toast.LENGTH_SHORT).show();
//                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
//                alertDialog.setTitle("Apply Theme");
//                alertDialog.setSingleChoiceItems(themes, -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        selectedTheme = which;
//
//                    }
//                });
//                alertDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                        if(selectedTheme == 0){
//
//                            themeUtils.changeToTheme(MainActivity.this,0);
//
//                        }else if(selectedTheme == 1){
//
//                            themeUtils.changeToTheme(MainActivity.this,1);
//                        }else if(selectedTheme == 2){
//
//                            themeUtils.changeToTheme(MainActivity.this,2);
//                        }else{
//
//                            themeUtils.changeToTheme(MainActivity.this,0);
//                        }
//
//
//                    }
//                });
//                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//
//                        Log.d("ALERT","Cancel Button Clicked");
//
//                    }
//                });
////                alertDialog.create();
//                alertDialog.show();

            }
        });

    }


    private void initRecyclerView(final ArrayList<Audio>  audioList1 ) {
        if (audioList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            RecyclerView_Adapter adapter = new RecyclerView_Adapter(audioList1, getApplication());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addOnItemTouchListener(new CustomTouchListener(this, recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    playAudio(position, audioList1);

                }

                @Override
                public void onLongClick(View view, int position) throws IOException {

//                    deleteAudio(position, audioList1);

                    Audio audio = audioList1.get(position);
                    String title = audio.getTitle();
                    String artist = audio.getArtist();
                    String album = audio.getAlbum();
                    float duration = audio.getDuration();
                    String path = audio.getData();
                    Bundle bundle = new Bundle();
                    bundle.putString("title",title);
                    bundle.putString("artist", artist);
                    bundle.putString("album",album);
                    bundle.putFloat("duration",duration);
                    bundle.putString("path",path);

                    DetailsDialogFragment detailsDialogFragment = new DetailsDialogFragment();
                    detailsDialogFragment.setArguments(bundle);
                    detailsDialogFragment.show(getFragmentManager(), "Details Dialog");



                    Toast.makeText(MainActivity.this, "Long Pressed", Toast.LENGTH_SHORT).show();
                }
            }));

        }
    }

    private void deleteAudio(int position, ArrayList<Audio> audioList1) throws IOException {

        String data  = audioList1.get(position).getData();

        Uri uri= Uri.parse(data);
        Log.d("PATH", uri+"");
        File file = new File(uri.getPath());
        file.delete();
        if(file.exists()){
            file.getCanonicalFile().delete();
            if(file.exists()){
                getApplicationContext().deleteFile(file.getName());
            }
        }

        loadAudio();
        initRecyclerView(audioList);
    }

    private void loadCollapsingImage(int i) {
        TypedArray array = getResources().obtainTypedArray(R.array.images);
        collapsingImageView.setImageDrawable(array.getDrawable(i));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        MenuItem item = menu.findItem(R.id.action_search);
         searchView.setMenuItem(item);
        return true;
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            appBarLayout.setExpanded(false, false);

//            toolbar.collapseActionView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("serviceStatus", serviceBound);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("serviceStatus");
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };


    private void playAudio(int audioIndex, ArrayList<Audio>  list) {
        //Check is service is active
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudio(list);
            storage.storeAudioIndex(audioIndex);

            Intent playerIntent = new Intent(this, MediaPlayerService.class);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudio(list);
            storage.storeAudioIndex(audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            sendBroadcast(broadcastIntent);
        }
    }


    private void loadAudio() {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            audioList = new ArrayList<>();
            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                float duration = cursor.getFloat(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                // Save to audioList
                audioList.add(new Audio(id,data, title, album, artist, duration));
            }
        }
        cursor.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
        	 unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }
}

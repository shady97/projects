package com.example.srinath.musicplayer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ListView lvMusic;
    Button btnPause, btnStop;
    ImageButton ibtnBack, ibtnForward, ibtnPrevious, ibtnNext;
    MediaPlayer mp;
    String name[], path[];
    static int cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMusic = (ListView)findViewById(R.id.lvMusic);
        btnPause = (Button)findViewById(R.id.btnPause);
        btnStop = (Button)findViewById(R.id.btnStop);
        ibtnBack = (ImageButton)findViewById(R.id.ibtnBack);
        ibtnForward = (ImageButton)findViewById(R.id.ibtnForward);
        ibtnNext = (ImageButton)findViewById(R.id.ibtnNext);
        ibtnPrevious = (ImageButton)findViewById(R.id.ibtnPrevious);
        mp = new MediaPlayer();

        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        path = new String[c.getCount()];
        name = new String[c.getCount()];

        int i = 0;
        while(c.moveToNext()) {
            path[i] = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
            name[i] = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            i++;

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);

        lvMusic.setAdapter(adapter);

        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                try {
                    cs = i;
                    String p = path[i];
                    mp.reset();
                    mp.setDataSource(p);
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mp.isPlaying())
                {
                    mp.pause();
                    btnPause.setText("Resume");
                }
                else
                {
                    mp.start();
                    btnPause.setText("Pause");
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                btnPause.setText("Pause");
            }
        });
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition()  - 4000);
            }
        });
        ibtnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition() + 4000);
            }
        });

        final int finalI = i;
        ibtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ns = cs - 1;
                if (ns < 0)
                    Toast.makeText(MainActivity.this, "First Song", Toast.LENGTH_SHORT).show();
                else
                {
                    try {
                        cs = ns;
                        String p = path[ns];
                        mp.reset();
                        mp.setDataSource(p);
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ibtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ns = cs + 1;
                if (ns == name.length)
                {
                    Toast.makeText(MainActivity.this, "Last Song", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        cs = ns;
                        String p = path[ns];
                        mp.reset();
                        mp.setDataSource(p);
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "App developed by Srinath", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.web){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.srinathshady.com"));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}

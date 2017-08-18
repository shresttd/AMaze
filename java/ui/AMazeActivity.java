package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.ui;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.R.style.Theme;

//import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.R;

/**
 * Created by TJ on 11/6/2016.
 */

public class AMazeActivity extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private SeekBar skillBar;
    private Spinner builderSpinner;
    private Spinner driverSpinner;
    private TextView skillText;
    private Button exploreButton;
    private Button revisitButton;
    private TextView titleText;

    int skill;
    String builder;
    String driver;

    MediaPlayer natesTheme;

    public AMazeActivity() {
    }

    /**
     * Sets up all the views and their operations
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeMedia();
        setSkillBar();
        setSpinner();
        setButton();
        setTitleText();
    }

    /**
     * Initalizes all the views from the corresponding XML file
     */
    private void initializeViews() {
        skillBar = (SeekBar) findViewById(R.id.skill);
        skillText = (TextView) findViewById(R.id.text);

        builderSpinner = (Spinner) findViewById(R.id.builder);
        driverSpinner = (Spinner) findViewById(R.id.driver);

        exploreButton = (Button) findViewById(R.id.explore);
        revisitButton = (Button) findViewById(R.id.revisit);

        titleText = (TextView) findViewById(R.id.title);
    }

    private void initializeMedia() {
        natesTheme = MediaPlayer.create(this, R.raw.nates_theme);
        natesTheme.setLooping(true);
        natesTheme.start();
    }
    /**
     * Sets up the array from string.xml file
     */
    private void setSpinner() {
        ArrayAdapter<CharSequence> builderAdapter = ArrayAdapter.createFromResource(this,
                R.array.builder_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> driverAdapter = ArrayAdapter.createFromResource(this,
                R.array.driver_array, android.R.layout.simple_spinner_item);

       /* ArrayAdapter<CharSequence> builderAdapter = ArrayAdapter.createFromResource(this,
                R.array.builder_array, R.layout.spinner_item);
        ArrayAdapter<CharSequence> driverAdapter = ArrayAdapter.createFromResource(this,
                R.array.driver_array, R.layout.spinner_item);*/

        builderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        builderSpinner.setAdapter(builderAdapter);
        driverSpinner.setAdapter(driverAdapter);

        builderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                builderSpinner.setOnItemSelectedListener(this);
                Log.v("AMazeActivity", "Skill level selected: " + builderSpinner.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(), "Builder: " + builderSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        driverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                driverSpinner.setOnItemSelectedListener(this);
                Log.v("AMazeActivity", "Skill level selected: " + driverSpinner.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(), "Driver: " + driverSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     * Sets up the seekbar for choosing skill level
     */
    private void setSkillBar() {
        skillText.setText("Skill Level: " + skillBar.getProgress() + "/" + skillBar.getMax());
        skillBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skillText.setText("Skill Level: " + skillBar.getProgress() + "/" + skillBar.getMax());
                Log.v("AMazeActivity", "Skill level selected: " + skillBar.getProgress());
                Toast.makeText(getApplicationContext(), "Skill Level: " + skillBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Stores the parameters selected for skill, builder and driver
     */
    private void getMazeSettings() {
        skill = skillBar.getProgress();
        builder = builderSpinner.getSelectedItem().toString();
        driver = driverSpinner.getSelectedItem().toString();
    }

    /**
     * Sets up the explore and revisit buttons to switch to the generating screen
     */
    private void setButton() {
        exploreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("AMazeActivity", "Explore Button pressed");
                Toast.makeText(getApplicationContext(), "Explore", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent( getApplicationContext() , GeneratingActivity.class);
                getMazeSettings();
                intent.putExtra("Skill", skill);
                intent.putExtra("Builder", builder);
                intent.putExtra("Driver", driver);
                natesTheme.stop();
                startActivity(intent);

            }
        });

        revisitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("AMazeActivity", "Revisit Button pressed");
                Toast.makeText(getApplicationContext(), "Revisit", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent( getApplicationContext() , GeneratingActivity.class);
                getMazeSettings();
                intent.putExtra("Skill", skill);
                intent.putExtra("Builder", builder);
                intent.putExtra("Driver", driver);
                natesTheme.stop();
                startActivity(intent);
            }
        });
    }

    /**
     * Sets up the title text
     */
    private void setTitleText() {
    }

}

package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

/**
 * CLass for FinishActivity
 * Created by TJ on 11/6/2016.
 */

public class FinishActivity extends Activity {
    private GoogleApiClient client;

    private TextView winText;
    private TextView loseText;
    private Button newGame;
    private TextView stepsText;
    private TextView energyText;

    private MediaPlayer reunion;
    private MediaPlayer road_to_shambala;

    public FinishActivity() {
    }

    /**
     * Sets up all the views and their operations
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setContentView(R.layout.activity_finish);

        initializeViews();
        initializeMedia();
        setText();
        setButton();
    }

    /**
     * Initializes the music
     */
    private void initializeMedia() {

        String check = getIntent().getExtras().getString("Condition");
        switch (check) {
            case "Win" :
                reunion = MediaPlayer.create(this, R.raw.reunion);
                reunion.start();
                reunion.setLooping(true);
                break;
            case "Lose" :
                road_to_shambala = MediaPlayer.create(this, R.raw.road_to_shambala);
                road_to_shambala.start();
                road_to_shambala.setLooping(true);
                break;
        }
    }

    /**
     * Initalizes all the views from the corresponding XML file
     */
    private void initializeViews() {
        winText = (TextView) findViewById(R.id.winText);
        loseText = (TextView) findViewById(R.id.loseText);
        newGame = (Button) findViewById(R.id.newGame);
        stepsText = (TextView) findViewById(R.id.pathlengthText);
        energyText = (TextView) findViewById(R.id.batteryText);
    }

    /**
     * Sets the text for win/lose, and for pathlength and battery consumption information
     */
    private void setText() {
        String condition = getIntent().getExtras().getString("Condition");
        switch(condition) {
            case "Win" :
                Log.v("FinishActivity", "Winning text set visible");
                winText.setVisibility(View.VISIBLE);
                break;
            case "Lose" :
                Log.v("FinishActivity", "Losing text set visible");
                loseText.setVisibility(View.VISIBLE);
                break;
            default:
        }

        int pathlength = getIntent().getExtras().getInt("Pathlength");
        int energy = getIntent().getExtras().getInt("Energy");

        stepsText.setText("You took " + pathlength + " steps!");
        energyText.setText("You consumed " + energy + " units of energy!");
    }

    /**
     * Sets up the button to start a new game
     */
    private void setButton() {

        String checkAgain = getIntent().getExtras().getString("Condition");

        newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("FinishActivity", "New game button pressed");
                Toast.makeText(getApplicationContext(), "New Game", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), AMazeActivity.class);
                String checkAgain = getIntent().getExtras().getString("Condition");
                switch (checkAgain) {
                    case "Win" :
                        reunion.stop();
                        break;
                    case "Lose" :
                        road_to_shambala.stop();
                        break;
                }
                startActivity(intent);
                finish();
            }
        });
    }
}

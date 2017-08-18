package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.playlog.internal.LogEvent;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.BasicRobot;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.Explorer;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.ManualDriver;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.MazeController;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.MazePanel;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.Pledge;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.RobotDriver;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.WallFollower;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.Wizard;

import static tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.ui.R.id.progressBar;

/**
 * Class for the PlayActivity
 * Created by TJ on 11/6/2016.
 */

public class PlayActivity extends Activity {

    private GoogleApiClient client;

    private Button winButton;
    private Button loseButton;
    private Button backButton;

    private Button startButton;
    private Button stopButton;

    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;

    //RobotDriver driver;

    private TextView energyText;
    private ProgressBar energyBar;

    private Switch showMaze;
    private Switch showSolution;
    private Switch showWall;
    private Button decrementMaze;
    private Button incrementMaze;

    public MazePanel panel ;
    public MazeController controller;

    Handler handler;

    boolean stopRobot = false;
    int batteryLevel;

    private MediaPlayer ambushed;                       //small beginnings?

    /**
     * Sets up all the views and their operations
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setContentView(R.layout.activity_play);

        initializeViews();
        initializeMedia();
        setIntentButton();
        setEnergyBar();
        setSwitch();
        setMazeButtons();
        panel.init();
        initController();
        setNavigation();

        //panel = new MazePanel(this);
        //panel = findViewById(R.id.panel);
        //panel.setBackgroundColor(Color.WHITE);

        //setContentView(panel);
    }

    /**
     * Initalizes all the views from the corresponding XML file
     */
    private void initializeViews() {
        handler = new Handler();
        winButton = (Button) findViewById(R.id.winShortcut);
        loseButton = (Button) findViewById(R.id.loseShortcut);
        backButton = (Button) findViewById(R.id.backButton);

        startButton = (Button) findViewById(R.id.start);
        stopButton = (Button) findViewById(R.id.stop);

        upButton = (Button) findViewById(R.id.up);
        downButton = (Button) findViewById(R.id.down);
        leftButton = (Button) findViewById(R.id.left);
        rightButton = (Button) findViewById(R.id.right);

        energyText = (TextView) findViewById(R.id.energyText);
        energyBar = (ProgressBar) findViewById(R.id.energyBar);

        showMaze = (Switch) findViewById(R.id.mazeSwitch);
        showSolution = (Switch) findViewById(R.id.solutionSwitch);
        showWall = (Switch) findViewById(R.id.wallSwitch);

        incrementMaze = (Button) findViewById(R.id.increment);
        decrementMaze = (Button) findViewById(R.id.decrement);
        panel = (MazePanel) findViewById(R.id.panel);

        bringViewsToFront();
    }

    /**
     * Brings views to the front
     */
    private void bringViewsToFront() {
        winButton.bringToFront();
        loseButton.bringToFront();
        backButton.bringToFront();

        startButton.bringToFront();
        stopButton.bringToFront();

        upButton.bringToFront();
        downButton.bringToFront();
        leftButton.bringToFront();
        rightButton.bringToFront();

        energyText.bringToFront();
        energyBar.bringToFront();
        showMaze.bringToFront();
        showSolution.bringToFront();
        showWall.bringToFront();
    }

    /**
     * Initializes media
     */
    private void initializeMedia() {
        ambushed = MediaPlayer.create(this, R.raw.ambushed);
        ambushed.setLooping(true);
        ambushed.start();
    }

    /**
     * Sets up the shortcut buttons to switch screens
     */
    private void setIntentButton() {
        winButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Win Button Pressed");
                Toast.makeText(getApplicationContext(), "Win Shortcut Pressed", Toast.LENGTH_SHORT).show();

                int[] curPos = controller.getCurrentPosition();
                if (controller.isOutside(curPos[0], curPos[1])) {
                    Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    intent.putExtra("Condition", "Win");
                    ambushed.stop();
                    startActivity(intent);
                    finish();
                }else {
                    Log.v("PlayActivity", "Controller position: " + curPos[0] + ", " + curPos[1]);
                }
            }
        });

        loseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Lose Button Pressed");
                Toast.makeText(getApplicationContext(), "Lose Shortcut Pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( getApplicationContext() , FinishActivity.class);
                intent.putExtra("Condition", "Lose");
                ambushed.stop();
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Back Button Pressed");
                Toast.makeText(getApplicationContext(), "Back Button Pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( getApplicationContext() , AMazeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Sets up the robot's manual or automatic navigation by making certain buttons visible
     */
    private void setNavigation(){
        String driverString = getIntent().getExtras().getString("Driver");
        Log.v("PlayActivity", "Driver is: " + driverString);

        switch (driverString) {
            case "Manual" :
                upButton.setVisibility(View.VISIBLE);
                downButton.setVisibility(View.VISIBLE);
                leftButton.setVisibility(View.VISIBLE);
                rightButton.setVisibility(View.VISIBLE);
                controller.setDriver(new ManualDriver());
                break;
            case "Wizard" :
                startButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                controller.setDriver(new Wizard());
                break;
            case "WallFollower" :
                startButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                controller.setDriver(new WallFollower());
                break;
            case "Pledge" :
                startButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                controller.setDriver(new Wizard());         //Need to change later
                break;
            case "Explorer" :
                startButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                controller.setDriver(new Wizard());             //Need to change later
                break;
        }
        setNavigationButton(driverString);
    }

    /**
     * Sets up each navigation button to do what it is meant to do
     * @param driver
     */
    private void setNavigationButton(String driverString) {

        upButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Up Button Pressed");
                //Toast.makeText(getApplicationContext(), "Manual: Move Forward", Toast.LENGTH_SHORT).show();

                /*controller.walk(1);

                batteryLevel = Math.round(controller.getRobot().getBatteryLevel()) - 5;
                energyBar.setProgress(batteryLevel); */

                controller.getManualDriver().keyDown("Up");
                energyBar.setProgress(Math.round(controller.getRobot().getBatteryLevel()));
                controller.getPanel().invalidate();

                int[] curPos = controller.getCurrentPosition();
                Log.v("PlayActivity", "Controller position: " + curPos[0] + ", " + curPos[1]);

                if (controller.isOutside(controller.getCurrentPosition()[0],
                        controller.getCurrentPosition()[1])) {
                    Intent intent = new Intent( getApplicationContext() , FinishActivity.class);
                    intent.putExtra("Condition", "Win");
                    intent.putExtra("Pathlength", controller.getManualDriver().getPathLength());
                    intent.putExtra("Energy", (int) BasicRobot.INITIAL_BATTERY_LEVEL - energyBar.getProgress());
                    startActivity(intent);
                    finish();
                }
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Down Button Pressed");
                //Toast.makeText(getApplicationContext(), "Manual: Move Backward", Toast.LENGTH_SHORT).show();

                /*controller.walk(-1);
                batteryLevel = Math.round(controller.getRobot().getBatteryLevel()) - 5;
                energyBar.setProgress(batteryLevel);
                controller.getPanel().invalidate(); */

                controller.getManualDriver().keyDown("Down");
                energyBar.setProgress(Math.round(controller.getRobot().getBatteryLevel()));
                controller.getPanel().invalidate();

                if (controller.isOutside(controller.getCurrentPosition()[0],
                        controller.getCurrentPosition()[1])) {
                    Intent intent = new Intent( getApplicationContext() , FinishActivity.class);
                    intent.putExtra("Condition", "Win");
                    intent.putExtra("Pathlength", controller.getManualDriver().getPathLength());
                    intent.putExtra("Energy", (int) BasicRobot.INITIAL_BATTERY_LEVEL - energyBar.getProgress());
                    startActivity(intent);
                    finish();
                }
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Left Button Pressed");
               // Toast.makeText(getApplicationContext(), "Manual: Turn Left", Toast.LENGTH_SHORT).show();
                /*controller.rotate(1);
                batteryLevel = Math.round(controller.getRobot().getBatteryLevel()) - 3;
                energyBar.setProgress(batteryLevel);
                controller.getPanel().invalidate();*/

                controller.getManualDriver().keyDown("Left");
                energyBar.setProgress(Math.round(controller.getRobot().getBatteryLevel()));
                controller.getPanel().invalidate();

            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Right Button Pressed");
                //Toast.makeText(getApplicationContext(), "Manual: Turn Right", Toast.LENGTH_SHORT).show();
                /*controller.rotate(-1);
                int batteryLevel = Math.round(controller.getRobot().getBatteryLevel()) - 3;
                energyBar.setProgress(batteryLevel);
                controller.getPanel().invalidate();*/
                controller.getManualDriver().keyDown("Right");
                energyBar.setProgress(Math.round(controller.getRobot().getBatteryLevel()));
                controller.getPanel().invalidate();


            }
        });

         startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {           //instead of polygon, make it an image
                Log.v("PlayActivity", "Start Robot Button Pressed");
                Toast.makeText(getApplicationContext(), "Start Robot", Toast.LENGTH_SHORT).show();
                stopRobot = false;
                handler.post( new Runnable() {
                    public void run() {
                        try {
                            goToFinishScreen();
                            controller.getDriver().drive2Exit();
                            energyBar.setProgress(Math.round(controller.getRobot().getBatteryLevel()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        controller.getPanel().invalidate();
                        if (stopRobot == false) {
                            handler.postDelayed(this, 1);
                        }
                    }
                });
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Stop Robot Button Pressed");
                Toast.makeText(getApplicationContext(), "Stop Robot", Toast.LENGTH_SHORT).show();
                stopRobot = true;
            }
        });
    }

    /**
     * Sets up the progress bar to show Battery Level
     */
    private void setEnergyBar() {
        energyBar.setProgress(energyBar.getMax());
        Log.v("PlayActivity", "Battery Level is: " + energyBar.getMax());
        Toast.makeText(getApplicationContext(), "Battery Level: " + energyBar.getMax(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets up Switch buttons to toggle map options
     */
    private void setSwitch() {
        showMaze.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("PlayActivity", "Show Maze toggle");
                Toast.makeText(getApplicationContext(), "Toggle Show Maze", Toast.LENGTH_SHORT).show();
                controller.toggleShowMaze();
                controller.notifyViewerRedraw() ;
            }
        });
        showSolution.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("PlayActivity", "Show Solution toggle");
                Toast.makeText(getApplicationContext(), "Toggle Show Solution", Toast.LENGTH_SHORT).show();
                controller.toggleShowSolution();
                controller.notifyViewerRedraw() ;
            }
        });
        showWall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("PlayActivity", "Show Wall toggle");
                Toast.makeText(getApplicationContext(), "Toggle Show Wall", Toast.LENGTH_SHORT).show();
                controller.toggleMapMode();
                controller.notifyViewerRedraw() ;
            }
        });
    }

    /**
     * Sets up increment/decrement
     */
    private void setMazeButtons() {
        incrementMaze.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Increment maze");
                Toast.makeText(getApplicationContext(), "Increment Maze", Toast.LENGTH_SHORT).show();
                controller.notifyViewerIncrementMapScale();

            }
        });

        decrementMaze.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("PlayActivity", "Decrement maze");
                Toast.makeText(getApplicationContext(), "Decrement Maze", Toast.LENGTH_SHORT).show();
                controller.notifyViewerDecrementMapScale();
            }
        });

    }

    /**
     * Initializes controller
     */
    private void initController() {
        controller = new MazeController();
        controller.setPanel(panel);
        controller.init();
        controller.deliver(GeneratingActivity.mazeConfig);
        batteryLevel = (int) controller.getRobot().getBatteryLevel();

    }

    /**
     * Checks to see if wants to go to finish screen or not
     */
    private void goToFinishScreen() {
        if (controller.isOutside( controller.getCurrentPosition()[0] , controller.getCurrentPosition()[1] )) {
            Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
            intent.putExtra("Condition", "Win");
            intent.putExtra("Pathlength", controller.getDriver().getPathLength());
            intent.putExtra("Energy", (int) BasicRobot.INITIAL_BATTERY_LEVEL - energyBar.getProgress());
            ambushed.setVolume(0, 0);
            ambushed.stop();
            startActivity(intent);
            finish();
        }

        else {
            if (controller.getRobot().hasStopped()) {                                       //or dead battery
                Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                intent.putExtra("Condition", "Lose");
                intent.putExtra("Pathlength", controller.getDriver().getPathLength());
                intent.putExtra("Energy", (int) BasicRobot.INITIAL_BATTERY_LEVEL - energyBar.getProgress());
                intent.putExtra("Energy", 6);
                ambushed.setVolume(0, 0);
                ambushed.stop();
                startActivity(intent);
                finish();
            }
        }
    }
}

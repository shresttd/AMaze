package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.MazeController;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.RobotDriver;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Factory;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.MazeConfiguration;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.MazeFactory;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Order;

/**
 * Class for generating maze
 * Created by TJ on 11/6/2016.
 */

public class GeneratingActivity extends Activity implements Order {

    private GoogleApiClient client;
    private ProgressBar progressBar;
    private Button backButton;
    private TextView progressText;
    private int progress;
    private Handler handler;

    private int skill;
    private Builder builder;
    private String driver;

    private Factory factory ;
    public static MazeConfiguration mazeConfig;

    private int percentdone;

    MediaPlayer setup;

    public GeneratingActivity() {
    }
    /**
     * Sets up all the views and their operations
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setContentView(R.layout.activity_generating);


        factory = new MazeFactory() ;
        setParameters();
        initializeViews();
        initializeMedia();
        setBackButton();
        //setProgressBar();

        factory.order(this);

    }

    /**
     * Initalizes all the views from the corresponding XML file
     */
    private void initializeViews() {
        progress = 0;
        handler = new Handler() ;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressText = (TextView) findViewById(R.id.progressText);
        backButton = (Button) findViewById(R.id.backButton);
    }

    /**
     * Initializes the Music
     */
    private void initializeMedia() {
        setup = MediaPlayer.create(this, R.raw.setup);
        setup.start();
        setup.setLooping(true);
    }
    /**
     * Sets up the back button
     */
    private void setBackButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("GeneratingActivity", "Back Button pressed");
                Toast.makeText(getApplicationContext(), "Back Button", Toast.LENGTH_SHORT).show();
                setup.stop();
                Intent intent = new Intent( getApplicationContext() , AMazeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Sets up the generating progres bar
     * Switches to finish screen after it generates to 100%
     */
    /*private void setProgressBar() {
        Log.v("Generating Activity", "Generating maze");
        new Thread(new Runnable() {
            public void run() {
                while (progress < 100) {
                    progress += 1;
                    handler.post( new Runnable() {
                        public void run() {
                            progressBar.setProgress(progress);
                            progressText.setText(progress + "%");
                        }
                    });
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.v("Generating Activity", "Maze generated, going to PlayActivity");

                Intent intent = new Intent( GeneratingActivity.this , PlayActivity.class);
                intent.putExtra("Driver", driver);
                startActivity(intent);
                finish();
            }
        }).start();
    } */

   /* private void initController() {
        controller.setSkillLevel(skill);

        switch(builder) {
            case "DFS" :
                controller.setBuilder(Order.Builder.DFS);
                break;
            case "Prim" :
                controller.setBuilder(Order.Builder.Prim);
                break;
            case "Eller" :
                controller.setBuilder(Order.Builder.Eller);
                break;
        }
        controller.init();
    } */

    public void setParameters() {
        skill = getIntent().getExtras().getInt("Skill");

        String builder = getIntent().getExtras().getString("Builder");
        switch (builder) {
            case "DFS" :
                this.builder = Order.Builder.DFS;
                break;
            case "Prim" :
                this.builder = Order.Builder.Prim;
                break;
            case "Eller" :
                this.builder = Order.Builder.Eller;
                break;
        }

        driver = getIntent().getExtras().getString("Driver");       //driver just passed in to next


    }
    @Override
    public int getSkillLevel() {
        return skill;
    }

    @Override
    public Builder getBuilder() {
        return builder;
    }

    @Override
    public boolean isPerfect() {
        return false;
    }

    @Override
    public void deliver(MazeConfiguration mazeConfig) {
        this.mazeConfig = mazeConfig;

        Intent intent = new Intent( GeneratingActivity.this , PlayActivity.class);
        intent.putExtra("Driver", driver);
        setup.stop();
        startActivity(intent);
        finish();
    }

    @Override
    public void updateProgress(final int percentage) {

        /*if (percentdone < percentage && percentage <= 100) {
            percentdone = percentage;
            progressBar.setProgress(percentage);
            progressText.setText(percentage + "%");
        }*/

        handler.post( new Runnable() {
            public void run() {
                progressBar.setProgress(percentage);
                progressText.setText(percentage + "%");
            }
        });
    }
}

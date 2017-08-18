package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.widget.ImageView;

//import java.awt.Color;
/*import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import java.awt.Panel;*/
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.Constants.StateGUI;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.ui.R;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author pk
 *
 */
public class MazePanel extends View {
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	private Canvas canvas;
	private Paint paint;
	private Bitmap bitmap;

	private Bitmap fillBitmap;
	private BitmapShader fillBMPshader;
	Paint fillPaint;

	BitmapDrawable skyClouds;
	Drawable brickRoad;
	//private Image bufferImage ;
	//private Graphics2D gc ;
	/**
	 * Constructor. Object is not focusable.
	 */
	//public MazePanel() {
		//super() ;
		//this.setFocusable(false) ;
	//}

	/**
	 * One of the several constructors
	 */
	public MazePanel(Context context) {
		super(context);
		init();

	}

	/**
	 * One of the several constructors
	 */
	public MazePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	/**
	 * One of the several constructors
	 */
	public MazePanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * Initializes the necessary aspects of paint, bitmap and canvas objects
	 */
	public void init() {
		//setWillNotDraw(false);
		paint = new Paint();
		//paint.setStyle(Paint.Style.FILL);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(10);
		paint.setFilterBitmap(true);
		Bitmap.Config config = Bitmap.Config.RGB_565;
		bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, config);
		canvas = new Canvas(bitmap);

		fillPaintBMP();
		backgroundDrawables();

	}

	/**
	 *Draws on the canvas
	 */
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (null == canvas) {
			System.out.println("MazePanel.paint: no canvas object, skipping drawBitmap operation") ;
		}
		else {
			//Log.v("MazePanel: ", "inside onDraw else");
			canvas.drawBitmap(bitmap, 0, 0, paint);			//try poygon etc.

			//paint.setColor(Color.BLACK);
			//canvas.drawRect(2000, 1020, 1000, 700, paint);
			//canvas.drawLine(500, 2000, 2000, 500, paint);

		}

		//canvas.drawLine(200, 1020, 1000, 700, paint);
		//canvas.drawLine(500, 2000, 2000, 500, paint);
	}

	/*@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (null == canvas) {
			System.out.println("MazePanel.paint: no canvas object, skipping drawBitmap operation");
		} else {
			canvas.drawBitmap(bitmap, 0, 0, paint);			//try poygon etc.
			Log.v("MazePanel: ", "inside draw else");

			//paint.setColor(Color.BLACK);
			//canvas.drawRect(250, 300, 550, 700, paint);
			//canvas.drawLine(500, 2000, 2000, 500, paint);
		}
		//
	}*/

	/**
	 * Updates the panel given a canvas
	 * @param c
     */
	public void update(Canvas c) {
		//paint(c) ;
		invalidate();
		//this.draw(c);								//wasn't commented out before
	}

	/**
	 * Updates the panel
	 * @param c
	 */
	public void update() {
	//	paint(getGraphics()) ;
		invalidate();
		//this.draw(canvas);
	}

	/**
	 * Returns the canvas
	 * @return
     */
	public Canvas getCanvas() {
		return canvas ;
	}
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 */
	/*@Override
	public void paint(Canvas c) {
		if (null == c) {
			System.out.println("MazePanel.paint: no canvas object, skipping drawBitmap operation") ;
		}
		else {
			c.drawBitmap(bitmap, 0, 0, null);
		}
	}*/

	/*public void initBufferImage() {
		bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		if (null == bufferImage)
		{
			System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
		}
	}*/
	/**
	 * Obtains a graphics object that can be used for drawing. 
	 * Multiple calls to the method will return the same graphics object 
	 * such that drawing operations can be performed in a piecemeal manner 
	 * and accumulate. To make the drawing visible on screen, one
	 * needs to trigger a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on
	 */
	/*public Graphics getBufferGraphics() {
		if (null == bufferImage)
			initBufferImage() ;
		if (null == bufferImage)
			return null ;
		return bufferImage.getGraphics() ;
	}*/

//////////////////////////////////////For MazeController\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in MazeController in notifyViewerRedraw()
	 * Redraws the graphics object
	 * @param it
	 * @param state
	 * @param px
	 * @param py
	 * @param viewdx
	 * @param viewdy
	 * @param walkStep
	 * @param rset
	 * @param angle
	 */
	protected void whileViewerRedraw(Viewer v, StateGUI state, int px, int py,
			int viewdx, int viewdy, int walkStep, RangeSet rset, int angle) {
		//Viewer v = it.next() ;
		Canvas c = getCanvas() ;
		if (null == c) {
			System.out.println("Maze.notifierViewerRedraw: can't get graphics object to draw on, skipping redraw operation") ;
		}
		else {
		 v.redraw(this, state, px, py, viewdx, viewdy, walkStep, Constants.VIEW_OFFSET, rset, angle) ;
		}	
	}

/////////////////////////////////////////For FirstPersonDrawer\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in FirstPersonDrawer to set RenderingHints 
	 */
	/*public void setRenderingHints () {
		gc = (Graphics2D) getBufferGraphics() ;
		gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gc.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	}*/
	
	/**
	 * Called in FirstPersonDrawer to draw the backgrounds
	 * @param view_width
	 * @param view_height
	 */
	protected void drawBackground (int view_width, int view_height) {
		/*gc.setColor(Color.black);
		gc.fillRect(0, 0, view_width, view_height/2);
		gc.setColor(Color.darkGray);
		gc.fillRect(0, view_height/2, view_width, view_height/2); */

		//paint.setColor(Color.CYAN);
		//canvas.drawRect(0,0,view_width, view_height/2, paint);
		//paint.setColor(Color.DKGRAY);
		//canvas.drawRect(0, view_height/2, view_width, view_height, paint);

		/*Drawable sky = getResources().getDrawable(R.drawable.cartoon_sky);
		sky.setBounds(0, 0, view_width, view_height/2);
		sky.draw(canvas);*/

		/*AssetManager assets = getResources().getAssets();
		InputStream buffer = null;
		try {
			buffer = new BufferedInputStream(assets.open("sky_horizon.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap imageBMP = BitmapFactory.decodeStream(buffer);
		BitmapDrawable skyClouds = new BitmapDrawable(getResources(), imageBMP);
		skyClouds.setBounds(0, 0, view_width, view_height/2);*/
		skyClouds.draw(canvas);


		/*Drawable brickRoad = getResources().getDrawable(R.drawable.brick_road);
		brickRoad.setBounds(0, view_height/2, view_width, view_height); */
		brickRoad.draw(canvas);
	}

	/**
	 * Draws background with sky clouds and stone ground
	 */
	protected void backgroundDrawables() {
		AssetManager assets = getResources().getAssets();
		InputStream buffer = null;
		try {
			buffer = new BufferedInputStream(assets.open("sky_horizon.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap imageBMP = BitmapFactory.decodeStream(buffer);
		skyClouds = new BitmapDrawable(getResources(), imageBMP);
		skyClouds.setBounds(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);

		brickRoad = getResources().getDrawable(R.drawable.brick_road);
		brickRoad.setBounds(0, Constants.VIEW_HEIGHT/2, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
	}
	
	/**
	 * Called in FirstPersonDrawer to set the background to white
	 */
	protected void setColorToWhite() {
		//gc.setColor(Color.white);
		paint.setColor(Color.WHITE);
	}
	
	/**
	 * Called in FirstPersonDrawer to fill polygons
	 * @param xps
	 * @param yps
	 */
	protected void fillPolygon(int[] xps, int[] yps)  {
		//gc.fillPolygon(xps, yps, 4);

		Path path = new Path();
		path.moveTo(xps[0], yps[0]); // used for first point
		path.lineTo(xps[1], yps[1]);
		path.lineTo(xps[2], yps[2]);
		path.lineTo(xps[3], yps[3]);
		path.lineTo(xps[0], yps[0]);
		//canvas.drawPath(path, paint);
		canvas.drawPath(path, fillPaint);


		//Drawable brickWall = getResources().getDrawable(R.drawable.buddha_eyes);
		//brickWall.setBounds(xps[0], yps[0], xps[1], yps[1]);
		//brickWall.draw(canvas);

		//PathShape pathShape = new PathShape(path, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);

		//Drawable d = new ShapeDrawable(pathShape);

	}

	protected void fillPaintBMP() {
		//fillBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brick_wall);

		AssetManager brickWall = getResources().getAssets();

		InputStream buffer = null;
		try {
			buffer = new BufferedInputStream(brickWall.open("brick_walls.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		fillBitmap = BitmapFactory.decodeStream(buffer);
		//fillBitmap = ((BitmapDrawable) brickWall).getBitmap();

		//fillBitmap = getResources().getDrawable(R.drawable.brick_wall);

		fillBMPshader = new BitmapShader(fillBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		fillPaint = new Paint();
		fillPaint.setColor(0xFFFFFFFF);
		fillPaint.setStyle(Paint.Style.FILL);
		fillPaint.setShader(fillBMPshader);
	}
	
	///////////////////////////For Seg\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in Seg to set color of the seg
	 * @param col
	 */
	public void setColorSeg(int[] col) {
		//gc.setColor(new Color(col[0], col[1], col[2]));
		paint.setColor(Color.rgb(col[0], col[1], col[2]));					//might need alpha
	}
	
	///////////////////////////For MazeFileReader\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in MazeFileReader to set color of the seg to a RGB value
	 * @param col
	 */
	public void setColorRGBSeg(int col) {			//alpha
		//gc.setColor(new Color(col));
		paint.setColor(col);
	}
	
	//////////////////////////For Map Drawer\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Called in MapDrawer to draw a line
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {
		//gc.drawLine(x1, y1, x2, y2);
		canvas.drawLine(x1, y1, x2, y2, paint);
	}
	/**
	 * Called in MapDrawer to fill the oval
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void fillOval(int x, int y, int width, int height) {
		//gc.fillOval(x, y, width, height);
		//paint.setColor(Color.BLACK);
		//canvas.drawOval(x, y, width, height, paint);
	}
	
	/**
	 * Called in MapDrawer to set a particular color to the graphics.
	 * @param color
	 */
	public void setColorGraphics(String color) {
		Log.v("MazePanel", "Set Color Graphics Red");
		switch (color) {
		case "White" :
			//gc.setColor(Color.WHITE);
			paint.setColor(Color.WHITE);
			break;
		case "Black" :
			//gc.setColor(Color.BLACK);
			paint.setColor(Color.BLACK);
			break;
		case "Gray" : 
			//gc.setColor(Color.GRAY);
			paint.setColor(Color.GRAY);
			break;
		case "DarkGray" :
			//gc.setColor(Color.DARK_GRAY);
			paint.setColor(Color.DKGRAY);
			break;
		case "Yellow" :
			//gc.setColor(Color.YELLOW);
			paint.setColor(Color.YELLOW);
			break;
		case "Red" : 
			//gc.setColor(Color.RED);
			paint.setColor(Color.RED);
			break;
		case "Green" :
			//gc.setColor(Color.GREEN);
			paint.setColor(Color.GREEN);
			break;
		case "Magenta" :
			//gc.setColor(Color.MAGENTA);
			paint.setColor(Color.MAGENTA);
			break;
		case "Blue" :
			//gc.setColor(Color.BLUE);
			paint.setColor(Color.BLUE);
			break;
		case "Pink" :
			//gc.setColor(Color.PINK);
			paint.setColor(Color.CYAN);
			break;
		}
	}
}

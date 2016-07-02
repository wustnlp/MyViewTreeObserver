package com.wust.ws.myviewtreeobserver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener,
    ViewTreeObserver.OnTouchModeChangeListener,
    ViewTreeObserver.OnGlobalFocusChangeListener,
    ViewTreeObserver.OnGlobalLayoutListener,
    ViewTreeObserver.OnPreDrawListener{


    private TextView tv_show;
    private TextView tv_display;
    private Button button;
    private EditText ed1;
    private EditText ed2;
    private View all;
    private ViewTreeObserver vto;
    private boolean btnClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        all = findViewById(R.id.full_screen);
        vto = all.getViewTreeObserver();
        tv_display = (TextView) findViewById(R.id.tv_display);
        tv_show = (TextView) findViewById(R.id.tv_show);
        ed1 = (EditText) findViewById(R.id.ed_enter1);
        ed2 = (EditText) findViewById(R.id.ed_enter2);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);
        vto.addOnGlobalLayoutListener(this);
        vto.addOnTouchModeChangeListener(this);
        vto.addOnGlobalFocusChangeListener(this);
        vto.addOnPreDrawListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        btnClicked = true;
        if(v.getId() == R.id.button){
            if (ed2.isShown())
                ed2.setVisibility(View.INVISIBLE);
            else
                ed2.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Callback method to be invoked when the focus changes in the view tree. When
     * the view tree transitions from touch mode to non-touch mode, oldFocus is null.
     * When the view tree transitions from non-touch mode to touch mode, newFocus is
     * null. When focus changes in non-touch mode (without transition from or to
     * touch mode) either oldFocus or newFocus can be null.
     *
     * @param oldFocus The previously focused view, if any.
     * @param newFocus The newly focused View, if any.
     */
    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        if (oldFocus != null && newFocus!= null )
        {
            tv_display .setText( "Focus /nFROM:/t" + oldFocus.toString() + "/n    TO:/t" + newFocus.toString());
        }
    }

    /**
     * Callback method to be invoked when the global layout state or the visibility of views
     * within the view tree changes
     */
    @Override
    public void onGlobalLayout() {
        if(btnClicked){
            if (!ed2.isShown())
                ed1.setText("第二个EditText不见了");
            else
                ed1.setText("第二个EditText出来了");
        }
        //vto.removeOnGlobalLayoutListener(this);
    }

    /**
     * Callback method to be invoked when the view tree is about to be drawn. At this point, all
     * views in the tree have been measured and given a frame. Clients can use this to adjust
     * their scroll bounds or even to request a new layout before drawing occurs.
     *
     * @return Return true to proceed with the current drawing pass, or false to cancel.
     * @see View#onMeasure
     * @see View#onLayout
     * @see View#onDraw
     */
    @Override
    public boolean onPreDraw() {
        ed1.setHint("在onPreDraw方法中增加一个提示信息");
        ed1.setTextSize((float) 20.0);
        return true;
    }

    /**
     * Callback method to be invoked when the touch mode changes.
     *
     * @param isInTouchMode True if the view hierarchy is now in touch mode, false  otherwise.
     */
    @Override
    public void onTouchModeChanged(boolean isInTouchMode) {
        if (isInTouchMode)
            tv_show.setText("In touch mode");
        else
            tv_show.setText("Not in touch mode");
    }
}

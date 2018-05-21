package com.example.twomack.overlays;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDrawOverlayPermission();

        findViewById(R.id.pikachu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initializeFloatingBubble("pikachu");
                finish(); // make the button vanish after click.
            }
        });

        findViewById(R.id.growlithe).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initializeFloatingBubble("growlithe");
                finish(); // make the button vanish after click.
            }
        });

        findViewById(R.id.seel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initializeFloatingBubble("seel");
                finish(); // make the button vanish after click.
            }
        });

        findViewById(R.id.ponyta).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initializeFloatingBubble("ponyta");
                finish(); // make the button vanish after click.
            }
        });


        //initializeFloatingBubble();
    }

    public final static int REQUEST_CODE = 10101;

    public void checkDrawOverlayPermission() {

        if (!Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));

            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    /**
     * Set and initialize the floating bubble.
     */
    private void initializeFloatingBubble(String pokemon) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("pokemon", pokemon);
        startService(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                initializeFloatingBubble("pikachu");
            } else {
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

/*


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private View topLeftView;

    private Button overlayedButton;
    private float offsetX;
    private float offsetY;
    private int originalXPos;
    private int originalYPos;
    private boolean moving;
    private WindowManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkDrawOverlayPermission();
        Boolean canDraw = Settings.canDrawOverlays(this);
        if (Settings.canDrawOverlays(this)){
            draw();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    public void draw(){
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        new FloatingActionButton
        overlayedButton = new Button(this);
        overlayedButton.setText("Overlay button");
        overlayedButton.setOnTouchListener(this);
        //overlayedButton.setAlpha(0.0f);
        overlayedButton.setBackgroundColor(Color.MAGENTA);
        overlayedButton.setOnClickListener(this);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        wm.addView(overlayedButton, params);

        topLeftView = new View(this);
        WindowManager.LayoutParams topLeftParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        topLeftParams.gravity = Gravity.LEFT | Gravity.TOP;
        topLeftParams.x = 0;
        topLeftParams.y = 0;
        topLeftParams.width = 0;
        topLeftParams.height = 0;
        wm.addView(topLeftView, topLeftParams);
    }


    public final static int REQUEST_CODE = 10101;

    public void checkDrawOverlayPermission() {

        if (!Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));

            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {

        if (requestCode == REQUEST_CODE) {

            if (Settings.canDrawOverlays(this)) {
                draw();
            }
        }
    }

}
*/
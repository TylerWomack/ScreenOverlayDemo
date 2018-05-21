package com.example.twomack.overlays;


import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.*;
import android.widget.ImageView;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyService extends Service {

    private static final double SMOOTH_X_FACTOR = 1.3;
    private static final double SMOOTH_Y_FACTOR = 1.2;

    private View floatingBubbleView;
    private WindowManager windowManager;
    private String pokemonChoice;

    @BindView(R.id.floating_head_image)
    ImageView pokemon;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            pokemonChoice = intent.getStringExtra("pokemon");
            if (startId == Service.START_STICKY) {
                handleStart();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * initiates the layout params and defines a onTouch listener to drag it on user interaction.
     * */
    private void handleStart() {
        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        floatingBubbleView = LayoutInflater.from(this).inflate(R.layout.floating_bubble_layout, null);

        //todo:https://stackoverflow.com/questions/46208897/android-permission-denied-for-window-type-2038-using-type-application-overlay

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        //params.width = 10;
        //params.height = 10;

        /* original
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 50;
        params.y = 100;
        */

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatingBubbleView, params);





        // Set the close button.
        //ImageView closeButton = (ImageView) floatingBubbleView.findViewById(R.id.close_button);

        /*
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });
        */

        final SpringRelativeLayout layout = (SpringRelativeLayout)
                floatingBubbleView.findViewById(R.id.floating_bubble_root);
        layout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ImageView pokemon = (ImageView) layout.findViewById(R.id.floating_head_image);

                switch (pokemonChoice){
                    case "pikachu":
                        pokemon.setImageDrawable(getDrawable(R.drawable.pikachu));
                        break;
                    case "growlithe":
                        pokemon.setImageDrawable(getDrawable(R.drawable.growlithe));
                        break;
                    case "ponyta":
                        pokemon.setImageDrawable(getDrawable(R.drawable.ponyta));
                        break;
                    case "seel":
                        pokemon.setImageDrawable(getDrawable(R.drawable.seel));
                        break;
                }

                //TODO: handle rest of actions.
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        params.x = (int) (SMOOTH_X_FACTOR * layout.getSpringPositionX() + event.getRawX());
                        params.y = (int) (SMOOTH_Y_FACTOR * layout.getSpringPositionY() + event.getRawY());
                        windowManager.updateViewLayout(floatingBubbleView, params);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingBubbleView != null)
            windowManager.removeView(floatingBubbleView);
    }
}
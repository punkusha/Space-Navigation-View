package com.luseen.spacenavigationview;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private SpaceNavigationView spaceNavigationView;

    private CentreButtonMode centreButtonMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("SKIP", R.drawable.ic_fast_forward_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem("ANSWER", R.drawable.ic_done_24dp));
        spaceNavigationView.setCentreButtonIcon( R.drawable.play_to_pause_animation );
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        centreButtonMode = CentreButtonMode.PLAY_TO_PAUSE;
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");
                spaceNavigationView.shouldShowFullBadgeText(true);

                spaceNavigationView.changeCenterButtonIcon(getCentreButtonModeIcon());
                Drawable centerDrawable = spaceNavigationView.getCenterButtonIconDrawable();
                if (centerDrawable instanceof Animatable) {
                    ((Animatable) centerDrawable).start();
                    toggleCentreButtonMode();
//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//
//                        .startAnimation(AnimationUtils.loadAnimation(, ));
//                    }
                }
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Log.d("onItemClick ", "" + itemIndex + " " + itemName);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
            }
        });

        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(MainActivity.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });

        setUpRecyclerView();
    }

    private enum CentreButtonMode {
        PLAY_TO_PAUSE, PAUSE_TO_PLAY
    }

    private void toggleCentreButtonMode() {
        centreButtonMode = (centreButtonMode == CentreButtonMode.PLAY_TO_PAUSE) ? CentreButtonMode.PAUSE_TO_PLAY : CentreButtonMode.PLAY_TO_PAUSE;
    }

    @DrawableRes
    private Integer getCentreButtonModeIcon() {
        switch (centreButtonMode) {
            case PLAY_TO_PAUSE: return R.drawable.play_to_pause_animation;
            case PAUSE_TO_PLAY: return R.drawable.pause_to_play_animation;
            default: return R.drawable.pause_to_play_animation;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(dummyStrings());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerClickListener(new RecyclerAdapter.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    spaceNavigationView.showBadgeAtIndex(1, 54, ContextCompat.getColor(MainActivity.this, R.color.badge_background_color));
                } else if (position == 1) {
                    spaceNavigationView.hideBudgeAtIndex(1);
                }
            }
        });
    }

    private List<String> dummyStrings() {
        List<String> colorList = new ArrayList<>();
        colorList.add("#354045");
        colorList.add("#20995E");
        colorList.add("#76FF03");
        colorList.add("#E26D1B");
        colorList.add("#911717");
        colorList.add("#9C27B0");
        colorList.add("#FFC107");
        colorList.add("#01579B");
        return colorList;
    }
}
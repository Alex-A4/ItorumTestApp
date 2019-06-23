package com.github.alexa4.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.alexa4.R;
import com.github.alexa4.controllers.DataController;
import com.github.alexa4.models.Planet;

/**
 * Activity with full information about one planet
 */
public class PlanetDescriptionActivity extends AppCompatActivity {
    private static String INDEX_TAG = "INDEX_TAG";

    private Planet mPlanet;

    /**
     * Get intent instance of that activity
     * and put index of planet in list to that intent
     *
     * @param context of app
     * @param index   of planet in list
     */
    public static Intent getInstance(Context context, int index) {
        Intent intent = new Intent(context, PlanetDescriptionActivity.class);
        intent.putExtra(INDEX_TAG, index);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet_layout);

        mPlanet = DataController.getInstance().getPlanetByIndex(getIntent()
                .getIntExtra(INDEX_TAG, 0));


        TextView mName = findViewById(R.id.planet_name);
        mName.setText(mPlanet.name());

        TextView mDiameter = findViewById(R.id.planet_diameter);
        mDiameter.setText(mPlanet.diameter());

        TextView mPopulation = findViewById(R.id.planet_population);
        mPopulation.setText(mPlanet.population());

        TextView mGravity = findViewById(R.id.planet_gravity);
        mGravity.setText(mPlanet.gravity());

        TextView mClimate = findViewById(R.id.planet_climate);
        mClimate.setText(mPlanet.climate());

        TextView mTerrain = findViewById(R.id.planet_terrain);
        mTerrain.setText(mPlanet.terrain());

        TextView mWater = findViewById(R.id.planet_water);
        mWater.setText(mPlanet.water());

        // Set up back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Close activity if clicked back button
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

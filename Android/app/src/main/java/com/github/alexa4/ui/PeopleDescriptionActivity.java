package com.github.alexa4.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.alexa4.R;
import com.github.alexa4.controllers.DataController;
import com.github.alexa4.models.People;

public class PeopleDescriptionActivity extends AppCompatActivity {
    private static String INDEX_TAG = "INDEX_TAG";

    private People mPeople;

    /**
     * Get intent instance of that activity
     *
     * @param context of app
     * @param index   of people in list
     */
    public static Intent getInstance(Context context, int index) {
        Intent intent = new Intent(context, PeopleDescriptionActivity.class);
        intent.putExtra(INDEX_TAG, index);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_layout);

        mPeople = DataController.getInstance().getPeopleByIndex(
                getIntent().getIntExtra(INDEX_TAG, 0));

        TextView mName = findViewById(R.id.people_name);
        mName.setText(mPeople.name());

        TextView mGender = findViewById(R.id.people_gender);
        mGender.setText(mPeople.gender());

        ImageView mGenderIcon = findViewById(R.id.people_gender_icon);
        switch (mPeople.gender()) {
            case "male":
                mGenderIcon.setImageResource(R.drawable.ic_men);
                break;
            case "n/a":
                mGenderIcon.setImageResource(R.drawable.ic_robot);
                break;
            case "female":
                mGenderIcon.setImageResource(R.drawable.ic_girl);
                break;
            default:
                mGenderIcon.setImageResource(R.drawable.ic_gender_default);
                break;
        }

        TextView mBirth = findViewById(R.id.people_birth);
        mBirth.setText("Birth: " + mPeople.birth());

        TextView mEye = findViewById(R.id.people_eye);
        mEye.setText(mPeople.eyeColor());

        TextView mWeight = findViewById(R.id.people_weight);
        mWeight.setText(mPeople.mass());

        TextView mHeight = findViewById(R.id.people_height);
        mHeight.setText(mPeople.height());

        TextView mHair = findViewById(R.id.people_hair);
        mHair.setText(mPeople.hairColor());

        TextView mSkin = findViewById(R.id.people_skin);
        mSkin.setText(mPeople.skinColor());

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

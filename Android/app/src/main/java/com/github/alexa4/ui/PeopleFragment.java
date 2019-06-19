package com.github.alexa4.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.alexa4.R;
import com.github.alexa4.controllers.DataController;
import com.github.alexa4.models.People;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class PeopleFragment extends Fragment {
    private Disposable mDisposable;
    private RecyclerView mList;
    private ProgressBar mIndicator;

    private PeopleAdapter mAdapter = new PeopleAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_fragment, container, false);

        mIndicator = root.findViewById(R.id.circular_indicator);

        mList = root.findViewById(R.id.list_of_data);
        mList.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        mList.setAdapter(mAdapter);

        // Download data
        mDisposable = DataController.getInstance().downloadPeople().subscribe(
                people -> {
                    mIndicator.setVisibility(View.GONE);
                    mList.setVisibility(View.VISIBLE);
                    mAdapter.addData(people);
                },
                this::showError
        );


        return root;
    }


    @Override
    public void onDestroy() {
        mDisposable.dispose();
        super.onDestroy();
    }

    private void showError(Throwable e) {
        e.printStackTrace();
        Toast.makeText(getContext(),
                "Unable to load people, check internet", Toast.LENGTH_SHORT).show();
    }

    private class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {
        private ArrayList<People> mPeople;

        PeopleAdapter() {
            this.mPeople = new ArrayList<>();
        }

        public void addData(ArrayList<People> data) {
            mPeople = data;
            if (PeopleFragment.this.isVisible())
                notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.people_item, parent, false);

            return new PeopleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
            People people = mPeople.get(position);
            holder.mName.setText((position + 1) + ".  " + people.name());
            String gender = people.gender();
            switch (gender) {
                case "male":
                    holder.mGenderIcon.setImageResource(R.drawable.ic_men);
                    break;
                case "n/a":
                    holder.mGenderIcon.setImageResource(R.drawable.ic_robot);
                    break;
                case "female":
                    holder.mGenderIcon.setImageResource(R.drawable.ic_girl);
                    break;
                default:
                    holder.mGenderIcon.setImageResource(R.drawable.ic_gender_default);
                    break;
            }
            holder.mGenderText.setText(gender);
            holder.mBirth.setText("Birth: " + people.birth());

            holder.mBirth.getRootView().setOnClickListener(v -> {
                startActivity(PeopleDescriptionActivity.getInstance(getContext(), position));
            });
        }

        @Override
        public int getItemCount() {
            return mPeople.size();
        }

        public class PeopleViewHolder extends RecyclerView.ViewHolder {
            public TextView mName;
            public ImageView mGenderIcon;
            public TextView mBirth;
            public TextView mGenderText;

            public PeopleViewHolder(@NonNull View itemView) {
                super(itemView);
                mName = itemView.findViewById(R.id.people_name);
                mGenderIcon = itemView.findViewById(R.id.people_gender_icon);
                mGenderText = itemView.findViewById(R.id.people_gender);
                mBirth = itemView.findViewById(R.id.people_birth);
            }
        }
    }
}

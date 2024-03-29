package com.github.alexa4.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.alexa4.models.Planet;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * Page with short information about each planets.
 * Planets displays in list where each element contains name, diameter and population
 */
public class PlanetsFragment extends Fragment {
    // Rx instance to dispose downloading if user close the app
    private Disposable mDisposable;

    private RecyclerView mList;
    private ProgressBar mIndicator;

    private PlanetsAdapter mAdapter = new PlanetsAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_fragment, container, false);

        mIndicator = root.findViewById(R.id.circular_indicator);

        mList = root.findViewById(R.id.list_of_data);
        mList.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        mList.setAdapter(mAdapter);

        // Download data
        mDisposable = DataController.getInstance().downloadPlanets().subscribe(
                planets -> {
                    mIndicator.setVisibility(View.GONE);
                    mList.setVisibility(View.VISIBLE);
                    mAdapter.addData(planets);
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
                "Unable to load planets, check internet", Toast.LENGTH_SHORT).show();
    }


    /**
     * Adapter of list that manipulate with items
     */
    private class PlanetsAdapter extends RecyclerView.Adapter<PlanetsAdapter.PlanetsViewHolder> {
        private ArrayList<Planet> mPlanets;

        PlanetsAdapter() {
            this.mPlanets = new ArrayList<>();
        }

        void addData(ArrayList<Planet> data) {
            mPlanets = data;
            if (PlanetsFragment.this.isVisible())
                notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PlanetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.planet_item, parent, false);

            return new PlanetsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlanetsViewHolder holder, int position) {
            Planet planet = mPlanets.get(position);

            holder.mPopulation.setText(planet.population());
            holder.mName.setText((position + 1) + ".  " + planet.name());
            holder.mDiameter.setText(planet.diameter());

            holder.mPopulation.getRootView().setOnClickListener(v -> {
                startActivity(PlanetDescriptionActivity.getInstance(getContext(), position));
            });
        }

        @Override
        public int getItemCount() {
            return mPlanets.size();
        }

        public class PlanetsViewHolder extends RecyclerView.ViewHolder {
            public TextView mName;
            public TextView mDiameter;
            public TextView mPopulation;

            public PlanetsViewHolder(@NonNull View itemView) {
                super(itemView);
                mName = itemView.findViewById(R.id.planet_name);
                mDiameter = itemView.findViewById(R.id.planet_diameter);
                mPopulation = itemView.findViewById(R.id.planet_population);
            }
        }
    }
}

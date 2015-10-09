package com.tanglang.ypt.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.bean.DrugDetail;

/**
 * Author： Administrator
 */
public class DrugAboutFragment extends Fragment {

    private FrameLayout mFrameLayout;
    private View loadingFailView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty_framelayout, container, false);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.drugcomm_content);

        initData();
        return view;
    }

    private void changeView(View view) {
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(view);
    }

    private void loadingFailView() {
        if (loadingFailView == null) {
            loadingFailView = View.inflate(getContext(), R.layout.loadingfail_view, null);
        }

        changeView(loadingFailView);
    }

    private void initData() {
        DrugDetail drug = (DrugDetail) getArguments().getSerializable("drug");
        if (drug == null) {
            loadingFailView();
            return;
        }

        View loadedView = View.inflate(getContext(), R.layout.fragment_drugabout, null);
        changeView(loadedView);

        TextView tvDrugName = (TextView) loadedView.findViewById(R.id.drugabout_drugname);
        TextView tvComposition = (TextView) loadedView.findViewById(R.id.drugabout_composition);
        TextView tvDrugattribute = (TextView) loadedView.findViewById(R.id.drugabout_drugattribute);
        TextView tvGongneng = (TextView) loadedView.findViewById(R.id.drugabout_gongneng);
        TextView tvUnit = (TextView) loadedView.findViewById(R.id.drugabout_unit);
        TextView tvUsage = (TextView) loadedView.findViewById(R.id.drugabout_usage);
        TextView tvAdr = (TextView) loadedView.findViewById(R.id.drugabout_adr);
        TextView tvContraindication = (TextView) loadedView.findViewById(R.id.drugabout_contraindication);
        TextView tvNote = (TextView) loadedView.findViewById(R.id.drugabout_note);
        TextView tvInteraction = (TextView) loadedView.findViewById(R.id.drugabout_interaction);
        TextView tvPharmacology = (TextView) loadedView.findViewById(R.id.drugabout_pharmacology);
        TextView tvStorage = (TextView) loadedView.findViewById(R.id.drugabout_storage);
        TextView tvShelflife = (TextView) loadedView.findViewById(R.id.drugabout_shelflife);
        TextView tvCodename = (TextView) loadedView.findViewById(R.id.drugabout_codename);
        TextView tvStandard = (TextView) loadedView.findViewById(R.id.drugabout_standard);
        TextView tvSpecificationdate = (TextView) loadedView.findViewById(R.id.drugabout_specificationdate);
        TextView tvRefdrugcompanyname = (TextView) loadedView.findViewById(R.id.drugabout_refdrugcompanyname);

        tvDrugName.setText(drug.results.namecn);
        tvComposition.setText(drug.results.composition);
        tvDrugattribute.setText(drug.results.drugattribute);
        tvGongneng.setText(drug.results.gongneng);
        tvUnit.setText(drug.results.unit);
        tvUsage.setText(drug.results.usage);
        tvAdr.setText(drug.results.adr);
        tvContraindication.setText(drug.results.contraindication);
        tvNote.setText(drug.results.note);
        tvInteraction.setText(drug.results.interaction);
        tvPharmacology.setText(drug.results.pharmacology);
        tvStorage.setText(drug.results.storage);
        tvShelflife.setText(drug.results.shelflife);
        tvCodename.setText(drug.results.codename);
        tvStandard.setText(drug.results.standard);
        tvSpecificationdate.setText(drug.results.specificationdate);
        tvRefdrugcompanyname.setText(drug.results.refdrugcompanyname);
    }
}

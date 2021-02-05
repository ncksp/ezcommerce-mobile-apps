package com.ezcommerce.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ezcommerce.activity.MainActivity;
import com.ezcommerce.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.Objects;

public class CategoryFragment extends Fragment {
    private List<String> categories;
    private ChipGroup categoryChips;
    public CategoryFragment (List<String> categories){
        this.categories = categories;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryChips = Objects.requireNonNull(getView()).findViewById(R.id.categories);
        Chip allCategory = Objects.requireNonNull(getView()).findViewById(R.id.all_category);

        setChipOnClick(allCategory, "all");
        allCategory.setChecked(true);

        for (String category : this.categories) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.item_category_chips, categoryChips, false);
            chip.setText(category);
            setChipOnClick(chip, category);

            categoryChips.addView(chip);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setChipOnClick(Chip chip, String category) {
        chip.setOnClickListener(view -> {
            categoryChips.clearCheck();
            chip.setChecked(true);
            MainActivity activity = (MainActivity) getActivity();
            activity.updateProductList(category);
        });
    }
}

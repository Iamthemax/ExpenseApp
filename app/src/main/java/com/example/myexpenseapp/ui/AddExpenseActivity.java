package com.example.myexpenseapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myexpenseapp.R;
import com.example.myexpenseapp.database.MyDbHelper;
import com.example.myexpenseapp.model.Category;
import com.example.myexpenseapp.model.Expense;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {
    ChipGroup chipGroup;
    MyDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        dbHelper=new MyDbHelper(this);
        chipGroup=findViewById(R.id.chipGroup);
        chipGroup.setSingleSelection(true);
        Chip firstChip=null;
        ArrayList<Category> categoryArrayList=dbHelper.getAllCategories();
        for(int i=0;i<categoryArrayList.size();i++)
        {
            Chip chip=new Chip(chipGroup.getContext());

            chip.setText(categoryArrayList.get(i).getName());
            chip.setTag(categoryArrayList.get(i));
            chip.setId(ViewCompat.generateViewId());
            chip.setCheckable(true);
            chipGroup.addView(chip);
            if(i==0)
            {
                firstChip=chip;
            }
        }
        if(firstChip!=null)
        {
            firstChip.setChecked(true);
        }
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup chipGroup, @NonNull List<Integer> list) {

            }
        });
        chipGroup.setSingleSelection(true);


        dbHelper.insertExpense(new Expense("Default Name","Default","2024-04-04",110,1));
        dbHelper.insertExpense(new Expense("Default Name","Default 2","2024-04-04",120,firstChip.getId()));

    }
}
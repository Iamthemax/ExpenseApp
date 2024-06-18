package com.example.myexpenseapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {
    ChipGroup chipGroup;
    MyDbHelper dbHelper;

    TextInputLayout txLayoutExpenseName;
    TextInputLayout txLayoutAmount;
    TextInputEditText etName;
    TextInputEditText etAmount;
    TextInputEditText etDate;
    TextInputEditText etNotes;;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        etName=findViewById(R.id.etName);
        etDate=findViewById(R.id.etDate);
        etAmount=findViewById(R.id.etAmount);
        etNotes=findViewById(R.id.etNote);
        btnAdd=findViewById(R.id.btnAdd);
        txLayoutExpenseName=findViewById(R.id.txExpenseNameLayout);
        txLayoutAmount=findViewById(R.id.txAmountLayout);
        dbHelper=new MyDbHelper(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields())
                {

                }
            }
        });



       /* chipGroup=findViewById(R.id.chipGroup);
        chipGroup.setSingleSelection(true);
        Chip firstChip=null;
       *//* ArrayList<Category> categoryArrayList=dbHelper.getAllCategories();
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
        dbHelper.insertExpense(new Expense("Default Name","Default 2","2024-04-04",120,firstChip.getId()));*/

    }

    private boolean validateFields() {
        ArrayList<Boolean> errors=new ArrayList<>();

        if(!etName.getText().toString().isEmpty() && etName.getText().toString().length()>1)
        {
            errors.add(true);
            etName.setError(null);
        }else
        {
            etName.setError("Please enter a valid name");
            errors.add(false);
        }
        if(!etAmount.getText().toString().isEmpty() && etAmount.getText().toString().length()>0)
        {
            errors.add(true);
            etAmount.setError(null);
        }else{
            etAmount.setError("Please enter a valid amount");
            errors.add(false);
        }
        return !errors.contains(false);
    }
}
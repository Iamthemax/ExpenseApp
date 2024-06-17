package com.example.myexpenseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myexpenseapp.ui.AddExpenseActivity;
import com.example.myexpenseapp.ui.AnalysisFragment;
import com.example.myexpenseapp.ui.ExpenseListFragment;
import com.example.myexpenseapp.ui.MoreFragment;
import com.example.myexpenseapp.ui.OptionsFragment;
import com.example.myexpenseapp.ui.PieChartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new ExpenseListFragment());
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        fabAdd=findViewById(R.id.fabAdd);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId()==R.id.nav_list)
                {
                    loadFragment(new ExpenseListFragment());
                }
                else if(menuItem.getItemId()==R.id.nav_analysis)
                {
                    loadFragment(new AnalysisFragment());
                }
                else if(menuItem.getItemId()==R.id.nav_more)
                {
                    loadFragment(new MoreFragment());
                    Intent intent=new Intent(MainActivity.this, PieChartActivity.class);
                    startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.nav_otions)
                {
                    loadFragment(new OptionsFragment());
                }
                return true;
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount()>1)
        {
            fragmentManager.popBackStack();
        }else{
            finish();
        }
    }
}
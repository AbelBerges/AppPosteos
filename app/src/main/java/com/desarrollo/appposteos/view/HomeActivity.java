package com.desarrollo.appposteos.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.desarrollo.appposteos.R;
import com.desarrollo.appposteos.databinding.ActivityHomeBinding;
import com.desarrollo.appposteos.view.fragments.ChatsFragment;
import com.desarrollo.appposteos.view.fragments.FiltrosFragment;
import com.desarrollo.appposteos.view.fragments.HomeFragment;
import com.desarrollo.appposteos.view.fragments.PerfilFragment;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    final int HOME = R.id.itemHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.itemHome){
                    openFragment(HomeFragment.newInstance());
                } else if (item.getItemId() == R.id.itemFiltros) {
                    openFragment(FiltrosFragment.newInstance("",""));
                } else if (item.getItemId() == R.id.itemChat) {
                    openFragment(ChatsFragment.newInstance("",""));
                } else if (item.getItemId() == R.id.itemPerfil) {
                    openFragment(new PerfilFragment());
                }
                return true;
            }
        });
        openFragment(HomeFragment.newInstance());
    }
    private void openFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public void hideProgressBar(){
        View progressBar = findViewById(R.id.progress_layout);
        if (progressBar != null){
            progressBar.setVisibility(View.GONE);
        }
    }
}
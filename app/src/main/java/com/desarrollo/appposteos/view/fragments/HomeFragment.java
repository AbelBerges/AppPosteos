package com.desarrollo.appposteos.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desarrollo.appposteos.R;
import com.desarrollo.appposteos.adapters.PostAdapter;
import com.desarrollo.appposteos.databinding.FragmentHomeBinding;
import com.desarrollo.appposteos.providers.AuthProvider;
import com.desarrollo.appposteos.view.MainActivity;
import com.desarrollo.appposteos.view.PostActivity;
import com.desarrollo.appposteos.viewmodel.PostViewModel;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private AuthProvider authProvider;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authProvider =new AuthProvider();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.tools);

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PostActivity.class);
            startActivity(intent);
        });

        PostViewModel postViewModel =new ViewModelProvider(this).get(PostViewModel.class);
        binding.recycleViewHome.setLayoutManager(new LinearLayoutManager(getContext()));

        postViewModel.getPost().observe(getViewLifecycleOwner(), posts -> {
            if (posts != null){
                PostAdapter adapter = new PostAdapter(posts);
                binding.recycleViewHome.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem items){
        if (items.getItemId() == R.id.itemLogout) {
            authProvider.logout().observe(getViewLifecycleOwner(), logoutREsult -> {
                if(logoutREsult != null && logoutREsult){
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("fromLoguot", true);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Error al cerrar la sesión. Intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
            return  true;
        }
        return super.onOptionsItemSelected(items);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
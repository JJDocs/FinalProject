package com.example.finalproject;

import static com.example.finalproject.R.id.Favorites;
import static com.example.finalproject.R.id.Markets;
import static com.example.finalproject.R.id.News;
import static com.example.finalproject.R.id.User;
import static com.example.finalproject.R.id.bottomNavigation;
import static com.example.finalproject.R.id.fragmentContainer;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(fragmentContainer,new LogInActivity()).commit();

            bottomNavigationView = findViewById(bottomNavigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

                Fragment fragment = new Home_Activity();

                int itemId = item.getItemId();

                if (itemId == Markets ) {
                    fragment = new Home_Activity();
                } else if (itemId == Favorites) {
                    fragment = new Favourites_activity();
                } else if (itemId == News) {
                    fragment = new News_Activity();
                }
                else if (itemId == User){
                    fragment = new UserActivity();
                }
                getSupportFragmentManager().beginTransaction().replace(fragmentContainer,fragment).commit();
                return true;
            });

        }

}

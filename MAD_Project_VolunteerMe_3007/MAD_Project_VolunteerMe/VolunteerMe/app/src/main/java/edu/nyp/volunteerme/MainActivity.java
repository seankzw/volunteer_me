package edu.nyp.volunteerme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class MainActivity extends AppCompatActivity {

    String arrayName [] = {
            "Edit Profile",
            "Forum",
            "Events"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleMenu circleMenu = (CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_add, R.drawable.ic_remove)
                .addSubMenu(Color.parseColor("#000000"), R.drawable.edit_profile)
                .addSubMenu(Color.parseColor("#000000"), R.drawable.forum)
                .addSubMenu(Color.parseColor("#000000"), R.drawable.events)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        String selectedName = arrayName[index];
                        Toast.makeText(getApplicationContext(),  selectedName, Toast.LENGTH_SHORT).show();

                        switch(selectedName){
                            case "Edit Profile":
                                gotoEditProfile();
                                break;
                            case "Forum":
                                gotoForum();
                                break;
                            case "Events":
                                gotoEvents();
                                break;
                        }; //end of switch(selectedName);

                    }//end of onMenuSelected
                });// end of circlemenu setmainenu
    }//end of on create


    private void gotoEditProfile(){
        Intent intent = new Intent(MainActivity.this, EditProfile.class);
        startActivity(intent);
    }

    private void gotoForum(){
        Intent intent = new Intent(MainActivity.this, Forum.class);
        startActivity(intent);
    }

    private void gotoEvents(){
        Intent intent = new Intent(MainActivity.this, Events.class);
        startActivity(intent);
    }

};

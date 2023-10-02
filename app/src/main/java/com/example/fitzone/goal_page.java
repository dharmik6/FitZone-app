package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class goal_page extends AppCompatActivity {

    ImageView backButton ;
    ImageButton muscle,endurance,strength,toned ;
    boolean isSelected ;
    Button btnNext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_page);

        muscle = findViewById(R.id.muscle);
        endurance = findViewById(R.id.endurance);
        strength = findViewById(R.id.strength);
        toned = findViewById(R.id.toned);
        backButton = findViewById(R.id.btn_next_page);
        btnNext = findViewById(R.id.btn_continue);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ihome = new Intent(goal_page.this,home_page.class);
                startActivity(ihome);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        muscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleImageButton(muscle,R.drawable.muscle_selected,R.drawable.muscle,"muscle");
                deselectOtherImages(muscle);
            }
        });
        endurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleImageButton(endurance,R.drawable.endurance_selected,R.drawable.endurance,"endurance");
                deselectOtherImages(endurance);
            }
        });
        strength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleImageButton(strength,R.drawable.sthergth_selected,R.drawable.sthergth,"strghth");
                deselectOtherImages(strength);
            }
        });
        toned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleImageButton(toned,R.drawable.toned_selected,R.drawable.toned,"toned");
                deselectOtherImages(toned);
            }
        });


    }
    public void toggleImageButton(ImageButton button , int selectedImage ,int unseletedImage, String imagename)
    {
        if(button.getTag() == null || !(boolean) button.getTag())
        {
            button.setImageResource(selectedImage);
            button.setTag(true);
            showToast(imagename + "is selected");
        }
        else {
            button.setImageResource(unseletedImage);
            button.setTag(false);
            showToast(imagename + "is unselected");
        }

    }

    private void deselectOtherImages(ImageButton selectedButton) {
        // Deselect other images except the selected one
        ImageButton[] allButtons = {muscle, endurance, strength, toned};
        for (ImageButton button : allButtons) {
            if (button != selectedButton) {
                button.setImageResource(getUnselectedImageResource(button.getId()));
                button.setTag(false);
            }
        }
    }

    public int getUnselectedImageResource(int buttonId) {
        // Map button IDs to their corresponding unselected image resources

        if(buttonId == R.id.muscle)
        {
            return R.drawable.muscle;
        } else if (buttonId == R.id.endurance) {
            return R.drawable.endurance;
        }
        else if (buttonId == R.id.strength) {
            return R.drawable.sthergth;
        }

        else if (buttonId == R.id.toned) {
            return R.drawable.toned;
        }
        else
        {
            return 0;
        }
    }


    public void showToast(String massage)
    {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

}
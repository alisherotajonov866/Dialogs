package ru.startandroid.learndialogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.startandroid.learndialogs.databinding.ActivityMainBinding;
import ru.startandroid.learndialogs.databinding.CustomDialogBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSimpleAlert.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Rate")
                    .setMessage("Can you want to rate this app?")
                    .setCancelable(true)
                    .setPositiveButton("OK", (dialog, which) -> {
                        Toast.makeText(this, "Clicked - OK", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("NO", (dialog, which) -> {
                        Toast.makeText(this, "Clicked - NO", Toast.LENGTH_SHORT).show();
                    })
                    .setNeutralButton("Cancel", (dialog, which) -> {
                        Toast.makeText(this, "Clicked - Cancel", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });
        binding.btnAlertWithMultiChoice.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String arrayLanguages[] = {"Java", "Kotlin", "React Native", "Flutter", "Other"};
            boolean booleanLanguages[] = new boolean[arrayLanguages.length];
            builder.setMultiChoiceItems(arrayLanguages, booleanLanguages, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                            Toast.makeText(MainActivity.this, arrayLanguages[i], Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        });
        binding.btnAlertWithSingleChoice.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String arrayLanguages[] = {"Java", "Kotlin", "React Native", "Flutter", "Other"};
            int checkedItem = -1; // because, at the beginning of the SingleDialog nothing is checked

            builder.setSingleChoiceItems(arrayLanguages, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, arrayLanguages[which], Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        });
        binding.btnCustomDialog.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View customView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null, false);
            builder.setView(customView);
            builder.setCancelable(false);
            View btnOkCustomDialog = customView.findViewById(R.id.btnOkCustomDialog);

            // We must write these codes to remove dialog background
            AlertDialog alertDialog = builder.create();
            // These codes remove the background of the dialog
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            btnOkCustomDialog.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Toast.makeText(MainActivity.this, "Dismiss the Dialog ", Toast.LENGTH_SHORT).show();
                }
            });
            // here, not -> builder.show() , this is true - alertDialog.show()
            // if you write builder.show(), 'OK' Button won't work
            alertDialog.show();
        });
        binding.btnFragmentDialog.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DialogFragment dialogFragment = DialogFragment.newInstance("Tom", "Jerry");
            dialogFragment.show(fragmentTransaction,"M1");
        });
        binding.btnDatePicker.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // if you write 'context-this', android will give you some error but don't worry and continue writing codes and get object with new key
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int mont, int day) {
                    Toast.makeText(MainActivity.this, day+"/"+(mont+1)+"/"+year, Toast.LENGTH_SHORT).show();
                }
            },year,month,day);
            datePickerDialog.show();
        });
        binding.btnTimePicker.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int localHour = calendar.get(Calendar.HOUR_OF_DAY);
            int localMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    Toast.makeText(MainActivity.this, hour+":"+minute, Toast.LENGTH_SHORT).show();
                }
            },localHour,localMinute,true);
            timePickerDialog.show();
        });
        binding.btnBottomSheet.setOnClickListener(view -> {
           BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
           CustomDialogBinding binding = CustomDialogBinding.inflate(getLayoutInflater());
            // Here, we use the binding of the Custom Dialog and we get the view of the Custom Dialog with the binding value
           bottomSheetDialog.setContentView(binding.getRoot());
           bottomSheetDialog.show();
        });
        binding.btnSnackBar.setOnClickListener(view -> {
            // the 'view' is at the top 'view'
            Snackbar snackbar = Snackbar.make(view,"Undo",Toast.LENGTH_SHORT);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Clicked Undo", Toast.LENGTH_SHORT).show();
                }
            });
            snackbar.show();
        });
    }
}
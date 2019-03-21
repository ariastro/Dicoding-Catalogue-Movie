package com.example.astronout.cataloguemovie30;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.astronout.cataloguemovie30.service.DailyReminder;
import com.example.astronout.cataloguemovie30.service.UpcomingTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.daily_reminder)
    LinearLayout mDailyReminder;
    @BindView(R.id.upcoming_reminder)
    LinearLayout mUpcomingReminder;
    @BindView(R.id.language_setting)
    LinearLayout settingLanguage;
    @BindView(R.id.switch_daily)
    Switch dailySwitch;
    @BindView(R.id.switch_upcoming)
    Switch upcomingSwitch;

    private UpcomingTask mUpcomingTask;
    private DailyReminder dailyReminder;
    private boolean isUpcoming, isDaily;
    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dailySwitch.setOnClickListener(this);
        upcomingSwitch.setOnClickListener(this);
        settingLanguage.setOnClickListener(this);

        dailyReminder = new DailyReminder();

        appPreference = new AppPreference(this);

        setReminder();
    }

    void setReminder(){
        if (appPreference.isDaily()){
            dailySwitch.setChecked(true);
        }else {
            dailySwitch.setChecked(false);
        }

        if (appPreference.isUpcoming()){
            upcomingSwitch.setChecked(true);
        }else {
            upcomingSwitch.setChecked(false);
        }
    }

    @OnClick
    public void onClick(View view){
        switch (view.getId()){
            case R.id.switch_daily:
                isDaily = dailySwitch.isChecked();
                if (isDaily){
                    dailySwitch.setEnabled(true);
                    appPreference.setDaily(isDaily);
                    dailyReminder.setRepeatingAlarm(this, DailyReminder.TYPE_REPEATING,
                            "07:00", getString(R.string.message_daily_reminder));
                }else {
                    dailySwitch.setChecked(false);
                    appPreference.setDaily(isDaily);
                    dailyReminder.cancelAlarm(this, DailyReminder.TYPE_REPEATING);
                }
                break;
            case R.id.switch_upcoming:
                mUpcomingTask = new UpcomingTask(this);
                isUpcoming = upcomingSwitch.isChecked();
                if (isUpcoming){
                    upcomingSwitch.setEnabled(true);
                    appPreference.setUpcoming(isUpcoming);

                    mUpcomingTask.createPeriodicTask();
                    Toast.makeText(this, R.string.message_upcoming_reminder, Toast.LENGTH_SHORT).show();
                }else {
                    upcomingSwitch.setChecked(false);
                    appPreference.setUpcoming(isUpcoming);
                    mUpcomingTask.cancelPeriodicTask();
                    Toast.makeText(this, R.string.message_upcoming_reminder_canceled, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.language_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

package movieapp.mina.com.themoviedbapp.screens.filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

import butterknife.OnClick;
import movieapp.mina.com.themoviedbapp.R;
import movieapp.mina.com.themoviedbapp.screens.BaseActivity;
import movieapp.mina.com.themoviedbapp.screens.movies.MoviesActivity;

public class FilterActivity extends BaseActivity {

    public static final int FILTER_RESULT_CODE = 1011;

    private Date mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.filter_screen_title);

    }

    @OnClick(R.id.filter_button)
    public void onFilterButtonClick() {
        Intent data = null;

        if(mDate != null) {
            data = new Intent();
            data.putExtra(MoviesActivity.FILTER_DATE_DATA, mDate);
        }

        setResult(FILTER_RESULT_CODE, data);
        finish();
    }

    @OnClick(R.id.clear_button)
    public void onClearButtonClick() {
        setResult(FILTER_RESULT_CODE);
        finish();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_filter;
    }

    @Override
    public void onReloadButtonClick() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onRadioButtonClicked(View view) {
        if(view.getId() == R.id.this_year_radio_button || view.getId() == R.id.two_years_radio_button || view.getId() == R.id.ten_years_radio_button) {
            Calendar c = Calendar.getInstance();
            c.set(c.get(Calendar.YEAR) - Integer.parseInt((String) view.getTag()), 0, 1);
            mDate = c.getTime();
        }
    }
}

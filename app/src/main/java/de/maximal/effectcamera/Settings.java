package de.maximal.effectcamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Settings extends Activity implements View.OnClickListener {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        intent = new Intent(this, MainActivity.class);
    }

    @Override
    public void onBackPressed(){

        this.finish();
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

    }
}

package net.hamba.android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.hamba.android.R;

public class ResetSuccessfull_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_successfull);
        initUI();
    }
    private void initUI(){
        btn_continue = (Button) findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                Intent intent = new Intent(this, OwnerEmployeeLoginActivity.class);
                startActivity(intent);

        }
    }
}

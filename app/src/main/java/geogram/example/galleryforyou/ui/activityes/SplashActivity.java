package geogram.example.galleryforyou.ui.activityes;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemClock.sleep(1500);//показываем превью 1,5 секунды
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);//запускаем основную активность
        finish();////завершаем превью активити
    }
}

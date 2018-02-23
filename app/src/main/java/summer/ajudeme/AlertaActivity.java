package summer.ajudeme;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AlertaActivity extends AppCompatActivity {


    private Ringtone r;
    private Vibrator v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1500);
        r.play();
    }

    public void ClickLigar(View v){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "16996096541", null)));
    }

    public void ClickOK(View v){
        r.stop();
    }


}

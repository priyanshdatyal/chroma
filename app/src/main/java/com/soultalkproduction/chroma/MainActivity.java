package com.soultalkproduction.chroma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.BuildConfig;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.soultalkproduction.chroma.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Animation dropdown,dropup,fi,fo,fst,scnd,trd;
    TextView slog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dropdown = AnimationUtils.loadAnimation(this,R.anim.dropdown);
        dropup = AnimationUtils.loadAnimation(this,R.anim.dropup);
        fi = AnimationUtils.loadAnimation(this,R.anim.fi);
        fo = AnimationUtils.loadAnimation(this,R.anim.fo);
        fst = AnimationUtils.loadAnimation(this,R.anim.fst);
        scnd = AnimationUtils.loadAnimation(this,R.anim.scnd);
        trd = AnimationUtils.loadAnimation(this,R.anim.trd);


        slog = (TextView) findViewById(R.id.slog);

        setTextcol(binding.slog,getResources().getColor(R.color.logreen)
                ,getResources().getColor(R.color.logpink));



        binding.abtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.abtinfo.setAnimation(dropdown);
                binding.abtinfo.getAnimation().start();
                binding.abtinfo.setVisibility(View.VISIBLE);
                binding.share.setVisibility(View.VISIBLE);
            }
        });

        binding.abtth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.abtinfo.setAnimation(dropup);
                binding.abtinfo.getAnimation().start();
                binding.abtinfo.setVisibility(View.GONE);
                binding.share.setVisibility(View.GONE);
            }
        });



        binding.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.cont_help).setTitle("Contact")
                        .setMessage("Email your concern at\n" +
                                "soultalk.production@gmail.com\n" +
                                "and add *Wish* in Subject")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });

        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Wish");
                    String shareMessage= "\nHey! I have been using this application called Wish for a while and I think you should try this\n\nIt's really great to get relieved from remembering ocassions";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id="+"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share On"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        binding.toggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (AnotherReq.hasPermission(MainActivity.this)) {
                    AnotherReq.toggleGrayscale(MainActivity.this, !AnotherReq.isGrayscaleEnable(MainActivity.this));
                    finish();
                } else {
                    Dialog dialog = AnotherReq.WarnDialog(MainActivity.this);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    });
                    dialog.show();
                }

            }
        });

    }
    private void setTextcol(TextView slog,int...color){
        TextPaint paint = slog.getPaint();
        float width =   paint.measureText(slog.getText().toString());
        Shader shader = new LinearGradient(0,0,width,slog.getTextSize(),color,null,Shader.TileMode.CLAMP);
        slog.getPaint().setShader(shader);
        slog.setTextColor(color[0]);
    }



}
package com.example.vinneeth.colorify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		Button botonIniciar = (Button)findViewById(R.id.Iniciar);
		Button botonCreditos = (Button)findViewById(R.id.Creditos);

		botonIniciar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentIniciar = new Intent(Menu.this, OpenCVMainActivity.class);
				startActivity(intentIniciar);
			}
		});

		botonCreditos.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intentCreditos = new Intent(Menu.this, Creditos.class);
				startActivity(intentCreditos);			
			}
		});
		
	}
}

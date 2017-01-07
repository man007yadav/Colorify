package com.example.vinneeth.colorify;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;


public class OpenCVMainActivity extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "Colorify::Activity";


    private MenuItem menuTipoCamara = null;
    private MenuItem menuBlancoYNegro = null;
    private MenuItem menuModoReconocimiento = null;
    private MenuItem menuSubmenuResoluciones = null;
    private boolean tipoCamara = true;
    private boolean modoGrises = false;
    private boolean modoReconocimiento = false;
    private int anchoCamara = 1280;
    private int altoCamara = 720;

    public ToggleButton togglebutton;
    public boolean flag1 = false;

    private CameraBridgeViewBase camara;




    //camara.setDisplayOrientation(90);
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    camara.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private View v;

    public OpenCVMainActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }






    @Override
    public void onCreate(final Bundle savedInstanceState) {

        Log.i(TAG, "called onCreate");

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       /*Button hold_button = (Button)findViewById(R.id.hold_button);
        hold_button.setOnLongClickListener(new View.OnLongClickListener() {
                                      @Override
                                      public boolean onLongClick(View v) {
                                          new CountDownTimer(1000, 1000) {
                                              public void onFinish() {
                                                    onCreate(savedInstanceState);                                      // When timer is finished
                                                  // Execute your code here

                                              }

                                              public void onTick(long millisUntilFinished) {
                                                  // millisUntilFinished    The amount of time until finished.
                                              }
                                          }.start();

                                          return true;
                                      }
                                  });*/
       /* togglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flag1 = true;
                }
                else {
                    flag1 = false;
                }
            }
        }); */




        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;


        setContentView(R.layout.opencv_main_activity);

        if (tipoCamara) {
            camara = (CameraBridgeViewBase) findViewById(R.id.camara_nativa);
            //camara.setMaxFrameSize(1120,630);

        } else {
            camara = (CameraBridgeViewBase) findViewById(R.id.camara_java);
        }

        camara.setVisibility(SurfaceView.VISIBLE);
        camara.setCvCameraViewListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }


    @Override
    public void onPause() {
        super.onPause();
        if (camara != null)
            camara.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (camara != null)
            camara.disableView();
    }
    public void selfDestruct(View view) {
        if(!flag1)
        flag1=true;
        else
            flag1=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        menuTipoCamara = menu.add("Cambiar Camara Nativa/Java");
        menuBlancoYNegro = menu.add("Blanco y Negro");
        menuModoReconocimiento = menu.add("Modo Preciso / Rango de Color");

        SubMenu subMenu = menu.addSubMenu(4, 4, 4, "Selecciona una resoluci�n");
        subMenu.add(1, 10, 1, "Alta Resoluci�n (1280x720)");
        subMenu.add(1, 11, 2, "Media Resoluci�n (960x720)");
        subMenu.add(1, 12, 3, "Baja Resoluci�n (800x480)");

        return true;
    }
    //public void
    //camara.setMaxFrameSize(200,200);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mensajeToast = new String();
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);

        if (item == menuTipoCamara) {
            camara.setVisibility(SurfaceView.GONE);
            tipoCamara = !tipoCamara;

            if (tipoCamara) {
                camara = (CameraBridgeViewBase) findViewById(R.id.camara_nativa);
                mensajeToast = "C�mara Nativa";
            } else {
                camara = (CameraBridgeViewBase) findViewById(R.id.camara_java);
                mensajeToast = "C�mara Java";
            }


            camara.setVisibility(SurfaceView.VISIBLE);
            camara.setCvCameraViewListener(this);
            camara.enableView();
            Toast toast = Toast.makeText(this, mensajeToast, Toast.LENGTH_LONG);
            toast.show();
        }

        if (item == menuBlancoYNegro) {
            if (modoGrises) {
                modoGrises = false;
                Toast toast = Toast.makeText(this, "'Modo Grises' desactivado.\n'Modo Normal' habilitado.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                modoGrises = true;
                Toast toast = Toast.makeText(this, "'Modo Normal' desactivado.\n'Modo Grises' habilitado.", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        if (item == menuModoReconocimiento) {
            if (modoReconocimiento) {
                modoReconocimiento = false;
                Toast toast = Toast.makeText(this, "'Modo Preciso' desactivado.\n'Modo Tonalidades' habilitado.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                modoReconocimiento = true;
                Toast toast = Toast.makeText(this, "'Modo Tonalidades' desactivado.\n'Modo Preciso' habilitado.", Toast.LENGTH_LONG);
                toast.show();
            }
        }


        switch (item.getItemId()) {
            case 10:
                anchoCamara = 1280;
                altoCamara = 720;
                Toast toast = Toast.makeText(this, "Resoluci�n del HUD m�xima", Toast.LENGTH_LONG);
                toast.show();
                break;
            case 11:
                anchoCamara = 960;
                altoCamara = 720;
                toast = Toast.makeText(this, "Resoluci�n del HUD media", Toast.LENGTH_LONG);
                toast.show();
                break;
            case 12:
                anchoCamara = 800;
                altoCamara = 480;
                toast = Toast.makeText(this, "Resoluci�n del HUD m�nima", Toast.LENGTH_LONG);
                toast.show();
                break;

        }

        return true;
    }


    public void onCameraViewStarted(int width, int height) {

    }

    public void onCameraViewStopped() {

    }


    public Mat onCameraFrame(CvCameraViewFrame frame) {

        if (modoGrises) {

            return frame.gray();
        } else {


            Mat mat = frame.rgba();


            int alto = altoCamara / 2;
            int ancho = anchoCamara / 2;
            int alto1 =1;
            int ancho1 = anchoCamara/2;
            double[] color1 =mat.get(alto1,ancho1);

            double[] color = mat.get(alto, ancho);


            double[] colorInverso = {255 - color[0], 255 - color[1], 255 - color[2], 255};


            Core.line(mat, new Point(0, altoCamara), new Point(anchoCamara - 25, altoCamara), new Scalar(colorInverso[0], colorInverso[1], colorInverso[2]), 1, 1, 1); //Izquierda
            Core.line(mat, new Point(anchoCamara + 25, altoCamara), new Point(anchoCamara + anchoCamara, altoCamara), new Scalar(colorInverso[0], colorInverso[1], colorInverso[2]), 1, 1, 1); //Derecha


            Core.line(mat, new Point(anchoCamara, 0), new Point(anchoCamara, altoCamara - 25), new Scalar(colorInverso[0], colorInverso[1], colorInverso[2]), 1, 1, 1);
            Core.line(mat, new Point(anchoCamara, altoCamara + 25), new Point(anchoCamara, altoCamara + altoCamara), new Scalar(colorInverso[0], colorInverso[1], colorInverso[2]), 1, 1, 1);

            Core.circle(mat, new Point(ancho, alto), 3, new Scalar(colorInverso[0], colorInverso[1], colorInverso[2]), -1);


            Core.circle(mat, new Point(ancho, alto), 50, new Scalar(colorInverso[0], colorInverso[1], colorInverso[2]), 1);

            String texto = "RGB: " + color[0] + " " + color[1] + " " + color[2];
            if(flag1==true)
            Core.putText(mat, texto, new Point(10, 50), 3, 1, new Scalar(255, 255, 255, 255), 2);


            String nombreColor = getColorName(color[0], color[1], color[2]);

            String noColor= getColorName(color1[0],color1[1],color1[2]);
            if((color1[0]+color1[1]+color1[2])<=(255/3))
            Core.putText(mat, nombreColor, new Point(ancho, 50), 3, 1, new Scalar(255, 255, 255, 255), 2);
            else
                Core.putText(mat, nombreColor, new Point(ancho, 50), 3, 1, new Scalar(0, 0, 0, 0), 2);

            Core.rectangle(mat, new Point( 10 , 80), new Point(anchoCamara - 10, 100), new Scalar(color[0], color[1], color[2], 255), -1); //Al pintar, usamos RGBA


            return mat;
        }

    }


    public String getColorName(double r, double g, double b) {

        String newColor = null;


        if (modoReconocimiento) {


            if (r > 140.0 && g > 140.0 && b > 140.0) {
                if (r > 200.0 && g > 200.0 && b > 200.0) {
                    newColor = "Pure White";
                } else {
                    newColor = "White";
                }
            }


            if (r < 50.0 && g < 50.0 && b < 50.0) {
                newColor = "Black";
            }


            if (r > 100.0 && g < 100.0 && b < 100.0) {
                newColor = "Red";
            }


            if (r < 100.0 && g > 100.0 && b < 100.0) {
                newColor = "Green";
            }


            if (r < 100.0 && g < 100.0 && b > 100.0) {
                newColor = "Blue";
            }


            if (r > 180.0 && r < 230.0 && g > 200.0 && g < 230.0 && b < 30.0) {
                newColor = "Yellow";
            }


            if (r < 10.0 && g > 200.0 && g < 230.0 && b > 230.0 && b < 240.0) {
                newColor = "Cyan";
            }


            if (r > 200.0 && r < 220.0 && g > 30.0 && g < 50.0 && b > 220.0 && b < 240.0) {
                newColor = "Magenta";
            }

        } else {
            if (r >= g && g >= b) {
                newColor = "Yellow";
            }


            if (g > r && r >= b) {
                newColor = "Dark Yellow";
            }


            if (g >= b && b > r) {
                newColor = "Dark green";
            }


            if (b > g && g > r) {
                newColor = "Blue";
            }


            if (b > r && r >= g) {
                newColor = "Dark Blue";
            }


            if (r >= b && b > g) {
                newColor = "Dark Red";
            }


            if (r < 10.0 && g < 10.0 && b < 10.0) {
                newColor = "Black";
            }


            if (r > 140.0 && g > 140.0 && b > 140.0) {
                if (r > 200.0 && g > 200.0 && b > 200.0) {
                    newColor = "Pure White";
                } else {
                    newColor = "White";
                }
            }

        }

        return newColor;
    }
 /* public String getColorName(double r1, double g1, double b1) {

      String nombreColor = null;
      double[] r =new double[17];
      double[] g =new double[17];
      double[] b =new double[17];
      //double g[17];double b[17];
      //Double[] g =new Double(17);
      //Double[] b =new Double(17);
      String s[] = new String[17];
      double res;
      //int ind;

      r[1]=0;b[1]=0;g[1]=0;s[1]="black";
      r[2]=255;g[2]=255;b[2]=255;s[2]="white";
      r[3]=0;g[3]=255;b[3]=0;s[3]="light green";
      r[4]=0;g[4]=0;b[4]=255;s[4]="blue";
      r[5]=255;g[5]=255;b[5]=0;s[5]="yellow";
      r[6]=0;g[6]=255;b[6]=255;s[6]="cyan";
      r[7]=255;g[7]=0;b[7]=255;s[7]="magenta";
      r[8]=192;g[8]=192;b[8]=192;s[8]="silver";
      r[9]=128;g[9]=128;b[9]=128;s[9]="grey";
      r[10]=128;g[10]=0;b[10]=0;s[10]="merun";
      r[11]=128;g[11]=128;b[11]=0;s[11]="olive";
      r[12]=0;g[12]=128;b[12]=0;s[12]="dark green";
      r[13]=128;g[13]=0;b[13]=128;s[13]="purple";
      r[14]=0;g[14]=0;b[14]=128;s[14]="dark blue";
      r[15]=165;g[15]=42;b[15]=42;s[15]="brown";
      r[16]=255;g[16]=182;b[16]=193;s[16]="pink";
      r[0]=255;g[0]=99;b[0]=71;s[0]="orange";
      double min=999999;double m=0;int ind=0;
      for(int i=0;i<17;i++)
      {
          m=Math.abs(r1-r[i])+Math.abs(b1-b[i])+Math.abs(g1-g[i]);
          if(m<min)
          {
              min=m;
              ind=i;
          }
      }
      nombreColor=s[ind];





      return nombreColor;
  }
*/


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("OpenCVMain Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
package com.bags.cse.saks.wifidirect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class gettingcoordinates extends Activity{

    Button button;
    RelativeLayout motionevent;
    LinearLayout mouse;
    int x_cord_down,x_cord=0,y_cord_down,y_cord;
    int tempx,tempy;
    int x_old=0,y_old=0;
    int width=75,height=75;
    int windowheight,windowwidth;
    String address,string="";
    int portnumber;
    DataOutputStream dataOutputStream;
    PrintWriter printWriter;
    Socket socket;
    LayoutInflater layoutInflater;
    View inflatedView;
    EditText editText;
    HorizontalScrollView hsv;
    LinearLayout hsv_linearlayout,updownlinearlayout;
    Boolean powerFlag=false,soundFlag=false,keyboardflag=false;
    Button leftClick,rightClick,centerClick,up,down;
    Button doneButton,closeButton;
    Boolean centerClickFlag=false;
    LinearLayout buttons;
    TextView textView;
    int keyaction;
    int keycode ;
    int keyunicode ;
    char character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gettingcoordinates);

        buttonInitialisation();
        layoutInitialistaion();

        layoutInflater=getLayoutInflater();

        hsvInitialisation();

        View keyboard = hsv_linearlayout.getChildAt(0);
        if (keyboard instanceof Button) {
            keyboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(powerFlag==false && soundFlag==false){
                        //keyboardflag=true;
                        centerClickFlag=true;
                        createKeyboardTextView();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput (InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }}
            });
        }
        View myComputer =hsv_linearlayout.getChildAt(1);
        if(myComputer instanceof Button){
            myComputer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printWriter.println("myComputer");
                }
            });
        }
        View windows =hsv_linearlayout.getChildAt(2);
        if(windows instanceof Button){
            windows.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printWriter.println("windows");
                }
            });
        }
        View browser =hsv_linearlayout.getChildAt(3);
      //  browser.setBackgroundResource(R.drawable.webb);
        if(browser instanceof Button){
            browser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printWriter.println("browser");
                }
            });
        }
        View controlPanel =hsv_linearlayout.getChildAt(4);
        if(controlPanel instanceof Button){
            controlPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printWriter.println("controlPanel");
                }
            });
        }
        View power = hsv_linearlayout.getChildAt(5);
        if (power instanceof Button) {
            power.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    centerClickFlag=false;

                    if(keyboardflag==true){
                        removekeyboard();
                    }
                    if(powerFlag==true){
                        removePowerView();
                    }
                    else if(powerFlag==false && soundFlag==true){
                        removeSoundView();
                        insertPowerView();
                    }
                    else if(powerFlag==false && soundFlag==false) {
                        insertPowerView();
                    }
                }
            });
        }

        View sound = hsv_linearlayout.getChildAt(6);
        if (sound instanceof Button) {
            sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    centerClickFlag=false;

                    if(keyboardflag==true){
                       removekeyboard();
                    }
                    if(soundFlag==true){
                        removeSoundView();
                    }
                    else if(soundFlag==false && powerFlag==true){
                        removePowerView();
                      ///  printWriter.println("powerpoint");
                        insertSoundView();

                    }
                    else if(soundFlag==false && powerFlag==false){

                        ///  printWriter.println("powerpoint");
                        insertSoundView();
                    }}
            });
        }
        View refresh =hsv_linearlayout.getChildAt(7);
        if(refresh instanceof Button){
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printWriter.println("refresh");
                }
            });
        }


        getAndroidScreenDimensions();

        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        portnumber=39999;


        Thread t = new Thread() {
            public void run() {
                try {
                    socket=new Socket(address,portnumber);

                    dataOutputStream=new DataOutputStream(socket.getOutputStream());

                    printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                    dataOutputStream.writeDouble(windowheight);
                    dataOutputStream.writeDouble(windowwidth);
                    printWriter.println(windowwidth+"abc"+windowheight);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(gettingcoordinates.this,"socketing"+address,Toast.LENGTH_SHORT).show();
            }
        });

        createButton();
    }
//    @Override
//    public void onBackPressed() {
//
//       // createDialog("QUIT", "ARE YOU SURE YOU WANT TO QUIT?", "QUIT", "CANCEL");
//
//            if (keyboardflag == true) {
//                removekeyboard();
//            }
//
//          else if (powerFlag == true) {
//                powerFlag = false;
//                removePowerView();
//            } else if (soundFlag == true) {
//                soundFlag = false;
//                removeSoundView();
//            } else {
//                createDialog("QUIT", "ARE YOU SURE YOU WANT TO QUIT?", "QUIT", "CANCEL");
//            }
//    }

    public void rightClick(View view){
        printWriter.println("rightClick");

        // showPopup(gettingcoordinates.this, p);
    }

    public void leftClick(View view){
        printWriter.println("leftClick");
    }

    public void createButton() {
        button = new Button(gettingcoordinates.this);
        button.setBackgroundResource(R.drawable.mousepointer);
        button.setScaleX(0.8f);
        button.setScaleY(0.8f);
        button.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        motionevent.addView(button);

        try {
            View.OnTouchListener onTouchListener = new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        x_cord_down = (int) event.getX();
                        y_cord_down = (int) event.getY();
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();

                        tempx = x_cord - x_cord_down;
                        tempy = y_cord - y_cord_down;

                        x_cord += x_old - width / 2;
                        y_cord += y_old - height / 2;

                        if (x_cord + width > windowwidth) {
                            x_cord = windowwidth - width - 10;
                        }
                        if (y_cord + height > ((windowheight * 4 / 5))) {
                            y_cord = (4 * windowheight / 5) - height - 20;
                        }
                        if (x_cord <= 0) {
                            x_cord = 0;
                        }
                        if (y_cord <= 0) {
                            y_cord = 0;
                        }
                        if (tempy != 0 && tempx != 0) {
                            v.setX(x_cord);
                            v.setY(y_cord);
                        }
                        x_old = x_cord;
                        y_old = y_cord;
                        printWriter.println(x_old + "," + y_old);
                        printWriter.flush();
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            button.setOnTouchListener(onTouchListener);
        }
        catch (Exception e){

        }
    }

    public void up(View view){
        printWriter.println("up");
    }

    public void down(View view){
        printWriter.println("down");
    }

    public void centerClick(View view){
        if(keyboardflag==true){
            removekeyboard();
        }
        if(centerClickFlag==false) {
            mouse.removeView(inflatedView);
            soundFlag = false;
            powerFlag = false;
            updownlinearlayout.setVisibility(View.VISIBLE);
        }
        else{
            centerClickFlag=false;
             removekeyboard();
        }
    }

    public void createDialog(final String title,String message,String positivebutton,String negativebutton){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positivebutton, new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (title.equals("SHUTDOWN")){
                                    printWriter.println("shutdown");
                                }
                                else if(title.equals("RESTART")){
                                    printWriter.println("restart");
                                }
                                else if(title.equals("SLEEP")){
                                    printWriter.println("sleep");
                                }
                                else if(title.equals("LOGOFF")){
                                    printWriter.println("logoff");
                                }
                                else if(title.equals("QUIT")){
                                    System.exit(0);
                                    finish();
                                }
                                else if (title.equals("close")){
                                    printWriter.println(string);
                                    editText.setText("");
                                    motionevent.removeView(editText);
                                    motionevent.removeView(closeButton);
                                    motionevent.removeView(doneButton);
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow (editText.getWindowToken(), 0);
                                }
                            }
                        })
                .setNegativeButton(negativebutton, new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (title.equals("close")){
                                    editText.setText("");
                                    motionevent.removeView(editText);
                                    motionevent.removeView(closeButton);
                                    motionevent.removeView(doneButton);
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                }
                            }
                        })
                .show();
    }

    public void removePowerView(){
        mouse.removeView(inflatedView);
        powerFlag=false;
        updownlinearlayout.setVisibility(View.VISIBLE);
    }

    public void removeSoundView(){
        mouse.removeView(inflatedView);
        soundFlag=false;
        updownlinearlayout.setVisibility(View.VISIBLE);
    }

    public void createKeyboardTextView(){

        if(keyboardflag==false) {
            textView = new TextView(gettingcoordinates.this);
            int textViewheight, textViewwidth, textViewx, textViewy;
            textViewheight = windowheight / 6;
            textViewwidth = 9 * windowwidth / 10;
            textViewx = windowwidth / 20;
            textViewy = windowheight / 10;
            textView.setLayoutParams(new ViewGroup.LayoutParams(textViewwidth, textViewheight));
            textView.setX(textViewx);
            textView.setY(textViewy);
            textView.setGravity(Gravity.LEFT | Gravity.TOP);
            textView.setBackgroundColor(Color.WHITE);
            textView.setTextColor(Color.BLACK);
            motionevent.addView(textView);
            keyboardflag=true;

        }
    }

    public void removekeyboard(){
        keyboardflag=false;
        motionevent.removeView(textView);
        ((InputMethodManager)gettingcoordinates.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

    public void insertPowerView(){
        inflatedView = layoutInflater.inflate(R.layout.power, mouse, false);
        updownlinearlayout.setVisibility(View.GONE);
        mouse.addView(inflatedView);
        powerFlag = true;
        final Button shutdown = (Button)inflatedView.findViewById(R.id.shutdown);
        final Button restart = (Button)inflatedView.findViewById(R.id.restart);
        final Button sleep = (Button)inflatedView.findViewById(R.id.sleep);
        final Button logoff = (Button)inflatedView.findViewById(R.id.logoff);
        shutdown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createDialog("SHUTDOWN","DO YOU REALLY WANT TO SHUTDOWN?","SHUTDOWN","CANCEL");
            }
        });
        restart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createDialog("RESTART","DO YOU REALLY WANT TO RESTART?","RESTART","CANCEL");
            }
        });
        sleep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createDialog("SLEEP","DO YOU REALLY WANT TO  SLEEP?","SLEEP","CANCEL");
            }
        });
        logoff.setOnClickListener(new View.OnClickListener
                (){
            @Override
            public void onClick(View v) {
                createDialog("LOGOFF","DO YOU REALLY WANT TO LOGOFF?","LOGOFF","CANCEL");
            }
        });
    }

    public void insertSoundView(){
        inflatedView = layoutInflater.inflate(R.layout.ppt,mouse, false);
        updownlinearlayout.setVisibility(View.GONE);
        mouse.addView(inflatedView);
        soundFlag = true;


        final Button up = (Button)inflatedView.findViewById(R.id.up);
        final Button closeslideshow = (Button)inflatedView.findViewById(R.id.closeslideshow);
       // final Button closepowerpoint = (Button)inflatedView.findViewById(R.id.closepowerpoint);
        final Button down = (Button)inflatedView.findViewById(R.id.down);
        final Button slideshow = (Button)findViewById(R.id.slideshow);



        closeslideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printWriter.println("closeslideshow");
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              printWriter.println("left");
                Toast.makeText(getApplicationContext(),"up",Toast.LENGTH_SHORT).show();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             printWriter.println("right");

            }
        });
        slideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              printWriter.println("slideshow");
            }
        });
    }

    public void buttonInitialisation(){

        leftClick=(Button)findViewById(R.id.leftClick);
        rightClick=(Button)findViewById(R.id.rightClick);
        centerClick=(Button)findViewById(R.id.centerClick);

        up=(Button)findViewById(R.id.up);
        down=(Button)findViewById(R.id.down);
    }

    public void hsvInitialisation(){
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        hsv_linearlayout = (LinearLayout) findViewById(R.id.hsv_linearlayout);
      //  hsv_linearlayout.setBackgroundColor(Color.rgb(148, 202, 148));
    }

    public void getAndroidScreenDimensions(){

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        windowheight = displaymetrics.heightPixels;
        windowwidth = displaymetrics.widthPixels;
    }

    public void layoutInitialistaion(){

        motionevent=(RelativeLayout)findViewById(R.id.motionevent);
        buttons=(LinearLayout)findViewById(R.id.buttons);
        mouse=(LinearLayout)findViewById(R.id.mouse);
        updownlinearlayout=(LinearLayout)findViewById(R.id.updownlinearlayout);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent KEvent){

         keyaction = KEvent.getAction();
         keycode = KEvent.getKeyCode();
         keyunicode = KEvent.getUnicodeChar(KEvent.getMetaState() );
         character = (char) keyunicode;

        if(keyaction == KeyEvent.ACTION_DOWN){
            printWriter.println("key");

            switch(keycode){

                case KeyEvent.KEYCODE_SPACE:
                    //Toast.makeText(getApplicationContext(),"space",Toast.LENGTH_SHORT).show();
                    printWriter.println(1001);
                    textView.append(" ");
                    break;
                case KeyEvent.KEYCODE_ENTER:
                  //  Toast.makeText(getApplicationContext(),"enter",Toast.LENGTH_SHORT).show();
                    printWriter.println(1002);
                    textView.append("\n");
                    break;
                case KeyEvent.KEYCODE_COMMA:
                    printWriter.println(1003);
                    //Toast.makeText(getApplicationContext(),"comma",Toast.LENGTH_SHORT).show();
                    textView.append(",");
                    break;
                case KeyEvent.KEYCODE_PERIOD:
                    //Toast.makeText(getApplicationContext(),"dot",Toast.LENGTH_SHORT).show();
                    textView.append(".");
                    printWriter.println(1004);
                    break;
                case KeyEvent.KEYCODE_DEL:
                    if(textView.getText()!=""){
                    textView.setText(textView.getText().toString().substring(0, textView.getText().length() - 1));
                    printWriter.println(1000);}
                    break;

                case KeyEvent.KEYCODE_PLUS:
                    textView.append("+");
                    printWriter.println(1200);
                    break;

                case KeyEvent.KEYCODE_MINUS:
                    textView.append("-");
                    printWriter.println(1201);
                    break;

                case KeyEvent.KEYCODE_BACKSLASH:
                    textView.append("/");
                    printWriter.println(1202);
                    break;
                case KeyEvent.KEYCODE_NUMPAD_DIVIDE:
                    textView.append("/");
                    printWriter.println(1202);
                    break;

                case KeyEvent.KEYCODE_EQUALS:
                    textView.append("=");
                    printWriter.println(1204);
                    break;

                case KeyEvent.KEYCODE_SEMICOLON:
                    textView.append(";");
                    printWriter.println(1214);
                    break;

                case KeyEvent.KEYCODE_CAPS_LOCK:
                    break;

                default:
                    if(keycode>=1 && keycode<=59){
                //    Toast.makeText(gettingcoordinates.this, "" + keycode, Toast.LENGTH_SHORT).show();
                    textView.append("" + character);
                    printWriter.println(keycode);
                    }
                    else{
                        printWriter.println(1500);
                    }
                    break;
            }

        }
        return super.dispatchKeyEvent(KEvent);
    }
}

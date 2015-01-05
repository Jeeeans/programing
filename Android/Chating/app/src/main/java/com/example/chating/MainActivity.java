package com.example.chating;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;


public class MainActivity extends Activity {

    private Socket client;
    private PrintWriter printWriter;
    private Scanner scanner;
    private EditText etMsg, etChat;
    private Button button1;
    private Button button2;
    private String message;
    String ip = "192.168.25.58";
    String msg;
    int port = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etChat = (EditText) findViewById(R.id.messageShow);
        etMsg = (EditText) findViewById(R.id.inputMessage);
        button2 = (Button) findViewById(R.id.send);


        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    client = new Socket(ip, port);

                    if(client!=null) {

                        scanner = new Scanner(client.getInputStream());
                        printWriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

                        while(client != null && client.isConnected()){
                            if(scanner.hasNext()) {
                                msg = scanner.nextLine();
                                etChat.append(msg);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    message = etMsg.getText().toString();
                    etMsg.setText("");
                    etChat.append(message+"<br />");
                    printWriter.write(message);
                    printWriter.flush();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        printWriter.close();
        try {
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
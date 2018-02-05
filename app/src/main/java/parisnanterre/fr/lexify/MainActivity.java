package parisnanterre.fr.lexify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import parisnanterre.fr.lexify.database.User;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

public class MainActivity extends Activity {

    User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            //final DatabaseWord db = new DatabaseWord(this);
            initializeWordDatabase();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        TextView txt_welcome = (TextView) findViewById(R.id.activity_main_txt_welcome);

        Button btn_disconnect = (Button) findViewById(R.id.activity_main_btn_disconnect);
        final LinearLayout lil_user = (LinearLayout) findViewById(R.id.activity_main_lil_user);

       /* Bundle b = this.getIntent().getExtras();
        if (b != null)
            currentUser = (User) b.getSerializable("Current user");*/

        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("user.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            currentUser = (User) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        if(currentUser!=null) {
            txt_welcome.setText("Welcome " + currentUser.get_pseudo() + " !");
            lil_user.setVisibility(View.VISIBLE);
        }
        else{
            lil_user.setVisibility(View.GONE);
        }

        Button button2 = (Button) findViewById(R.id.buttongen);
        final TextView txt = (TextView) findViewById(R.id.wooord);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseWord db = new DatabaseWord(getApplicationContext());
                Word random = db.getRandomWord();
                txt.setText(random.getWord());

            }
        });



        Button button = (Button) findViewById(R.id.button);
        //test

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(currentUser!=null) {
                    //go pour le jeu
                }else {
                    Intent i = new Intent(getApplicationContext(),SignInActivity.class);
                    startActivity(i);
                }

            }
        });

        btn_disconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                currentUser = null;
                lil_user.setVisibility(View.GONE);

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(getApplicationContext().getFileStreamPath("user.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                writer.print("");
                writer.close();

                Context context = getApplicationContext();
                CharSequence text = "You are disconnected";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });

    }

    private void initializeWordDatabase() {

        BufferedReader reader = null;
        DatabaseWord db = new DatabaseWord(this);
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("liste_mot.txt"), "iso-8859-1"));

            String mLine = "";

            //changer boucle for par while (bug bizarre)
            for(int i = 0; i<533;i++){
                db.addWord(new Word(reader.readLine(), 0, 0));
            }


            /*while (reader.readLine() != null) {
                String s = mLine;
            }*/

            // do reading, usually loop until end of file reading




        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

    }


}

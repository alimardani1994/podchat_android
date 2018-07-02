package ir.fanap.chat.sdk.application.chatexample;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fanap.podchat.model.Invitee;

import java.util.ArrayList;
import java.util.Date;

import ir.fanap.chat.sdk.R;

public class ChatActivity extends AppCompatActivity implements ChatContract.view, AdapterView.OnItemSelectedListener {
    private ChatContract.presenter presenter;
    private ConstraintLayout constraintLayout;
    private EditText editText;
    private EditText editTextThread;
    private static final String[] paths = {
            "Choose function",
            "get thread",
            "rename thread",
            "get user info",
            "reply message",
            "forward message",
            "send text message",
            "get thread participant",
            "create thread",
            "get thread history",
            "mute thread",
            "un mute thread"
            , "get contacts"
            , "edit message"
            , "add contact"
            , "remove contact"
            , "update contact"
    };

    //fel token
//    private static String TOKEN = "a11768091eac48f2a7b84ed6a241f9c3";
    //Fifi
    private String name = "Fifi";
    private static String TOKEN = "1fcecc269a8949d6b58312cab66a4926";
//    zizi
//    private static final String name = "zizi";
//    private static String TOKEN = "7cba09ff83554fc98726430c30afcfc6";
    //Token Alexi
//    private static String TOKEN = "bebc31c4ead6458c90b607496dae25c6";
//    private static String name = "Alexi";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView textViewState = findViewById(R.id.textViewStateChat);
        TextView textViewToken = findViewById(R.id.textViewUserId);
        editText = findViewById(R.id.editTextMessage);
        editTextThread = findViewById(R.id.editTextThread);
        constraintLayout = findViewById(R.id.constraintLayout);
        textViewToken.setText(TOKEN + name);
        Spinner spinner = findViewById(R.id.spinner);

        presenter = new ChatPresenter(this);
        presenter.getLiveState().observe(this, textViewState::setText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void connect(View view) {
        presenter.connect("ws://172.16.106.26:8003/ws",
                "POD-Chat", "chat-server", TOKEN, "http://172.16.110.76",
                "http://172.16.106.26:8080/hamsam/");
    }

    public void getContact(View view) {
        presenter.getContact(50, 0);
    }

    /**
     * int TO_BE_USER_SSO_ID = 1;
     * int TO_BE_USER_CONTACT_ID = 2;
     * int TO_BE_USER_CELLPHONE_NUMBER = 3;
     * int TO_BE_USER_USERNAME = 4;
     */
    public void createThread(View view) {
        EditText editTextThread = findViewById(R.id.editTextThread);
        String text = editTextThread.getText().toString();
        int textThread = Integer.valueOf(editTextThread.getText().toString());
        if (!text.equals("")) {
            Invitee[] invite = new Invitee[]{new Invitee(textThread, 2)};
            presenter.createThread(0, invite, "");
        } else {
            Snackbar.make(constraintLayout, "Message is Empty", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetContacts(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetUserInfoId(int UserId) {
        Toast.makeText(this, UserId, Toast.LENGTH_SHORT).show();
    }

    //thread id  231
    public void mute(View view) {
        presenter.muteThread(231);
    }

    //thread id  231
    public void unMute(View view) {
        presenter.unMuteThread(231);
    }

    public void EditMsg(View view) {
        presenter.editMessage(533, "edited_at" + new Date().getTime());
    }

    public void getParticipant(View view) {
        EditText editText = findViewById(R.id.editTextMessage);
        EditText editTextThread = findViewById(R.id.editTextThread);
        String text = editText.getText().toString();
        long textThread = Long.valueOf(editTextThread.getText().toString());
        if (!text.equals("")) {
            presenter.getThreadParticipant(50, 5, textThread);
        } else {
            Snackbar.make(constraintLayout, "Message is Empty", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void sendMessage(View view) {
        String text = editText.getText().toString();
        long textThread = Long.valueOf(editTextThread.getText().toString());
        if (!text.equals("")) {
            presenter.sendTextMessage(text, textThread);
        } else {
            Toast.makeText(this, "Message is Empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendReplyMessage(View view) {
        String text = editText.getText().toString();
        long textThread = Long.valueOf(editTextThread.getText().toString());
        if (!text.equals("")) {
            presenter.sendReplyMessage(text, textThread, 532);
        } else {
            Toast.makeText(this, "Message is Empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//            , "add contact"
//            , "remove contact"
//            , "update contact"
        switch (position) {
            case 0:
//            "Choose function",

                break;
            case 1:
                //            "get thread",

                presenter.getThread(10, 0);
                break;
            case 2:
                //            "rename thread",
                presenter.renameThread(379, "new group name");
                break;
            case 3:
                //            "get user info",
                presenter.getUserInfo();
                break;
            case 4:
                //            "reply message",
                presenter.sendReplyMessage("this is reply", 231, 1544);
                break;
            case 5:
                //            "forward message",
                ArrayList<Long> messageIds = new ArrayList<>();
                messageIds.add(11956L);
                presenter.forwardMessage(312, messageIds);
                break;
            case 6:
                //            "send text message",
                break;
            case 7:
                //            "get thread participant",
                break;
            case 8:
                //            "create thread",
                break;
            case 9:
                presenter.getHistory(50, 0, "desc", 312);
                break;
            case 10:
                //            "mute thread",
                break;
            case 11:
//            "un mute thread"
                break;
            case 12:
//            "get contacts"
                presenter.getContact(50,0);
                break;
            case 13:
//            , "edit message"
                break;
            case 14:
                // add contact
                presenter.addContact("", "", "09122451131", "");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

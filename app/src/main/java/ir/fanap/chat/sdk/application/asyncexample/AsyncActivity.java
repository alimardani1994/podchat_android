package ir.fanap.chat.sdk.application.asyncexample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.util.List;

import ir.fanap.chat.sdk.R;

public class AsyncActivity extends AppCompatActivity implements SocketContract.view {

    //("ws://172.16.110.235:8003/ws", "UIAPP")
//    @Inject
    SocketPresenter socketPresenter;
    private static final String SOCKET_SERVER = "ws://172.16.110.235:8003/ws";
    Button connectButton;
    Button getStateButton;
    Button closeButton;
    Button buttonSendMessage;
    TextView textViewPeerId;
    EditText editTextReceiverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        init();

        socketPresenter = new SocketPresenter(this, this);
        textViewPeerId.setText(socketPresenter.getPeerId());

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(editTextReceiverId)) {
                    long receiverId = Long.valueOf(editTextReceiverId.getText().toString().trim());
                    final long[] receiverIdArray = {receiverId};
                    socketPresenter.sendMessage("hello", 3, receiverIdArray);
                }else {
                    Toast.makeText(AsyncActivity.this, "Message is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        socketPresenter.closeSocket();
                    }
                }, 3000);
            }

                    });

        getStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketPresenter.getLiveState();
            }
        });
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                socketPresenter.connect("ws://172.16.106.26:8003/ws", "POD-Chat", "chat-server",
//                        "afa51d8291dc4072a0831d3a18cb5030","http://172.16.110.76",);
            }
        });

        socketPresenter.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                TextView textView = findViewById(R.id.textViewstate);
                textView.setText(s);
            }
        });
    }

    private void init() {
        connectButton = findViewById(R.id.button);
        getStateButton = findViewById(R.id.getState);
        closeButton = findViewById(R.id.buttonclosesocket);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);
        textViewPeerId = findViewById(R.id.textViewPeerId);
        editTextReceiverId = findViewById(R.id.editTextReceiverId);
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0){
            return true;
        }
        return false;
    }

    @Override
    public void showMessage(String message) {
        TextView textViewShowMessage = findViewById(R.id.textViewShowMessage);
        textViewShowMessage.setText(message);
        Log.d("message", message);
    }

    @Override
    public void messageCalled() {

    }

    @Override
    public void showErrorMessage(String error) {

    }

    @Override
    public void showOnMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) {

    }


    @Override
    public void showOnConnectError(WebSocket websocket, WebSocketException exception) {

    }

    @Override
    public void showSocketState(String state) {

    }

    @Override
    public void showLiveDataState(LiveData state) {

    }

    private void sendMessage(String textMessage, int messageType, long[] receiversId) {
        socketPresenter.sendMessage(textMessage, messageType, receiversId);
    }

    public void getThread(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketPresenter.socketLogOut();
    }
}

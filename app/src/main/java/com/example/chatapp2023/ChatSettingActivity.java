package com.example.chatapp2023;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class ChatSettingActivity extends Activity {


    Button btnHide_Chat_Check;

    Button btnHide_Off;

    Button btnHide_On;

    int Chat_Position;

    String My_Email;

    String Chat_Email;

    String Check_Hide;

    String Chat_Id;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chatsetting);
        // 호출시 전송받았던 값 저장
        Chat_Email = getIntent().getStringExtra("Chat_Email");
        My_Email = getIntent().getStringExtra("My_Email");
        Check_Hide = getIntent().getStringExtra("Check_Hide");
        Chat_Position = getIntent().getIntExtra("position", -1);
        Chat_Id = getIntent().getStringExtra("Chat_id");

    }

    public void Hide_Chat_Check(View v) { // 메시지 확인
        if (Chat_Position != -1 && Check_Hide.equals("yes")) {
            //데이터 전달하기
            Intent intent = new Intent();
            intent.putExtra("position", Chat_Position);
            intent.putExtra("option", 1);

            setResult(RESULT_OK, intent);
            //액티비티(팝업) 닫기
            finish();
            // 종류코드 리턴값으로 값 전송 후 pw입력 창 출력( -> ChatActivity )

        }
    }


    public void Chat_Hide_Off(View v){ // 메시지 숨김 해제
        if(Chat_Position != -1 && My_Email.equals(Chat_Email) && Check_Hide.equals("yes")){
            Intent intent = new Intent();
            intent.putExtra("position", Chat_Position);
            intent.putExtra("option",2);

            setResult(RESULT_OK, intent);
            //액티비티(팝업) 닫기
            finish();

        }
    }

    public void Chat_Hide_On(View v){ // 메시지 숨김
        if(Chat_Position != -1 && My_Email.equals(Chat_Email) && Check_Hide.equals("no")){
            Intent intent = new Intent();
            intent.putExtra("position", Chat_Position);
            intent.putExtra("option",3);

            setResult(RESULT_OK, intent);
            //액티비티(팝업) 닫기
            finish();

        }
    }


}

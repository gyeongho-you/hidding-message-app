package com.example.chatapp2023;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;

public class SecondPwdActivity extends Activity {

    private static final String TAG = "SecondPwdActivity";
    EditText etsecond_passwd;
    FirebaseDatabase Firedatabase;

    User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_secondpasswd);

        etsecond_passwd = (EditText) findViewById(R.id.second_passwd);
        Firedatabase = FirebaseDatabase.getInstance();
    }


    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
            Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
            // A new comment has been added, add it to the displayed list

            user = dataSnapshot.getValue(User.class);
            String pwd = user.getSecondpwd();
            String uuser = user.getUserEmail();

            Log.d(TAG, "pwd: "+ pwd);
            Log.d(TAG, "Email: "+ uuser);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
            // A comment has changed, use the key to determine if we are displaying this
            // comment and if so displayed the changed comment.
            User user = dataSnapshot.getValue(User.class);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
            // A comment has changed, use the key to determine if we are displaying this
            // comment and if so remove it.
            String commentKey = dataSnapshot.getKey();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            // A comment has changed position, use the key to determine if we are
            // displaying this comment and if so move it.
            User user = dataSnapshot.getValue(User.class);
            String commentKey = dataSnapshot.getKey();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            Toast.makeText(SecondPwdActivity.this, "Failed to load comments.",
                    Toast.LENGTH_SHORT).show();
        }
    };

    public void Second_Passwd_Click(View v){


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();      // 현재 로그인된 이메일 따오기 위해

        if (etsecond_passwd != null) {  // 입력값이 null이 아닌경우
            DatabaseReference usersRef = Firedatabase.getReference("users");    //  DB 경로 즉 users 노드를 가리킴
            String secondpwd = etsecond_passwd.getText().toString();
            String userEmail = currentUser.getEmail();

            String[] emailParts = userEmail.split("@");     // "DB 경로에는 @ 사용불가" 따라서 이메일 형식 @앞에 문자열만 사용
            String userID = emailParts[0];

            Hashtable<String, String> members = new Hashtable<String, String>(); // 실시간으로 DB에 전송된 글 올리기
            members.put("userEmail:", userEmail);
            members.put("Secondpwd:", secondpwd);

            usersRef.child(userID).setValue(members)    // userID를 노드로 한다음 members 노드로 세팅
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) { // 데이터 성공적으로 업로드된 경우
                            Toast.makeText(SecondPwdActivity.this, "2차 비밀번호 설정 완료", Toast.LENGTH_SHORT).show();
                            etsecond_passwd.getText().clear();
                        }
                    });
        } else {
            Toast.makeText(SecondPwdActivity.this, "2차 비밀번호 설정 실패", Toast.LENGTH_SHORT).show();
            etsecond_passwd.getText().clear();
        }

    }

    public void Second_Passwd_Back(View v){
        Intent intent = new Intent();
        intent.putExtra("result", "false");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}

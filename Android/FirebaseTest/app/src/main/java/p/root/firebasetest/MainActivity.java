package p.root.firebasetest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;

    TextView conditionText;
    Switch isNiceOut;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = rootRef.child("condition");
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);



        //Firebase Auth
        auth = FirebaseAuth.getInstance();

        // UI Elements
        conditionText = (TextView)findViewById(R.id.condition);
        isNiceOut = (Switch)findViewById(R.id.s);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // Sign in

        FirebaseUser currentUser = auth.getCurrentUser();


        // Button and text
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                conditionText.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        isNiceOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(isNiceOut.isChecked())
                {
                    isNiceOut.setText("Lovely time for a strow");
                    conditionRef.setValue("It's nice out!");
                }
                else
                {
                    isNiceOut.setText("Looks depressing :(");
                    conditionRef.setValue("I'd rather stay inside");

                }

            }
        });
    }
}

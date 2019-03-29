package com.example.cncarroyo.firebasestreaming;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogingActivity extends AppCompatActivity {

     private static  final String TAG = "EmailPassword";

     private SignInButton loginGoogle;
     private EditText edEmail;
     private EditText edPassword;
     private Button btnIngresar;
     private Button btnNuevoUsuario;


     private TextView tvEstadoSesion;

     private FirebaseAuth mAuth;
     private FirebaseAuth.AuthStateListener mAuthListener;

     // variables autenticacion con google
    private static final String TAG_GOOGLE = "GoogleActivity";
    private static  final int RC_SIGN_IN = 1 ;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);

        EstadoConexion();

        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnNuevoUsuario = (Button) findViewById(R.id.btnCrearUsuario);
        tvEstadoSesion = (TextView) findViewById(R.id.tvEstadoSesion);
        loginGoogle=(SignInButton) findViewById(R.id.btnLoginGoogl);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()!=null){

                    Intent intent = new Intent(LogingActivity.this,AccountActivity.class);
                    startActivity(intent);

                }

            }
        };


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))//AIzaSyA3Bw0wQZM23XBfZkJAi-_tcVPCK1Ss4D8
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                      Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              signIn();

            }
        });


        btnIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LoginUsuario();


                }
            });


          btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {


                  CreateUsuario();


              }
          });

    /*     mAuthListener = new FirebaseAuth.AuthStateListener() {
              @Override
              public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                  if ( firebaseAuth.getCurrentUser()!=null){

                      Toast.makeText(LogingActivity.this,"Prueba logeo correcto "+ firebaseAuth.getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                    //  Intent intent = new Intent(LogingActivity.this, VerInformacionActivity.class);
                    //     mAuth.signOut();
                  }

              }
          }; */


        }

 /*   @Override
      public void onActivityResult(int requestCode,int resultcode,Intent data){
        super.onActivityResult(requestCode,requestCode,data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {

                Log.w(TAG_GOOGLE, "Google sing in failed", e);

                updateUI(null);

            }
        }
      }


       private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
           Log.d(TAG_GOOGLE,"firebaseAuthWithGoogle:"+acct.getId());


           AuthCredential credencial = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
           mAuth.signInWithCredential(credencial)
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {

                           if (task.isSuccessful()){

                               Log.d(TAG_GOOGLE,"signInWithCredential:success");
                               FirebaseUser user = mAuth.getCurrentUser();
                               updateUI(user);


                           }else{

                               Log.w(TAG_GOOGLE,"signInWithCredential:failure", task.getException());
                               Toast.makeText(getApplicationContext(),"Autentication falied",Toast.LENGTH_SHORT).show();
                               updateUI(null);

                           }

                       }
                   });

        } */



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LogingActivity.this, "Atentificathion FAILLE", Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }

                        // ...
                    }
                });
    }



        public void LoginUsuario(){

        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

         mAuth.signInWithEmailAndPassword(email,password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful()){

                           Log.d(TAG,"singInWithEmail:correcto");
                           FirebaseUser user = mAuth.getCurrentUser();
                           Log.d("Usuario::","usuario :"+user );

                           updateUI(user);

                       }else{

                           Log.w(TAG, "singInWithEmail :fallido", task.getException());
                           Toast.makeText(LogingActivity.this,"Autenticacion fallida",Toast.LENGTH_SHORT).show();
                      //     updateUI(null);

                       }


                     }
                 });


        }

       /*  public  void getGoogleCredentials(){

            String googleIdToken="";
            AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken,null);


         } */

        public void CreateUsuario(){

            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email,password)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {

                          if (task.isSuccessful()){

                   //           Log.d(TAG,"createUserWithEmail : succes");
                              Toast.makeText(LogingActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();

                            //  FirebaseUser user = mAuth.getCurrentUser();

                          }else{

                              //Log.w(TAG,"createUserWhithEmail:fallido");
                              Toast.makeText(LogingActivity.this," error al crear usuario",Toast.LENGTH_SHORT).show();

                          }

                      }
                  });


        }





       @Override
       protected void onStart(){
        super.onStart();

           FirebaseUser currentUser = mAuth.getCurrentUser();
           updateUI(currentUser);

       }





     private void updateUI(FirebaseUser user){

           if (user != null){

               Toast.makeText(getApplicationContext(),"Ya estas logeado " ,Toast.LENGTH_LONG).show();

           }else{

               Toast.makeText(getApplicationContext(),"debe ingresar a su secion",Toast.LENGTH_LONG).show();

           }

     }

    public void EstadoConexion(){

        ConnectivityManager connectivityManager =(ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()){

           // Toast.makeText(getApplicationContext(),"Conectado a red de Internet",Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(getApplicationContext(), "No se encuentra conectado a Internet", Toast.LENGTH_SHORT).show();
        }


    }






    }






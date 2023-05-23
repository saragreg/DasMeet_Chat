package com.example.dasmeet_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Chat extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mensajeriafcm-ea7c9-default-rtdb.europe-west1.firebasedatabase.app/");
    private String chatKey;
    private RecyclerView chatRecView;
    private ArrayList<String> nombres=new ArrayList<String>();
    private ArrayList<String> mensajes=new ArrayList<String>();
    private ArrayList<String> horas=new ArrayList<String>();
    private static final long DURATION_ONE_DAY = 24 * 60 * 60 * 1000; // Duración de un día en milisegundos

    private CountDownTimer countDownTimer;
    private ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //prueba
        //databaseReference.child("chat").child("1").child("user1").setValue(user1mail);

        final ImageView atras=findViewById(R.id.atras_btn);
        final TextView nombre=findViewById(R.id.nombre);
        final EditText messageEditTxt=findViewById(R.id.messageEditTxt);
        final ImageView fotoPerfil=findViewById(R.id.fotoPerfil);
        final ImageView enviar=findViewById(R.id.enviar_btn);
        chatRecView = findViewById(R.id.chatRecyclerView);

        //obtenemos la informacion de la lista de usuarios
        final String getNombre = getIntent().getStringExtra("nombre");
        final String getFotoPerfil = getIntent().getStringExtra("fotoPerfil");
        chatKey = getIntent().getStringExtra("chatKey");
        final String user1mail = getIntent().getStringExtra("mail1");
        final String usermail = getIntent().getStringExtra("mailUser");

        nombre.setText(getNombre);
        //Picasso.get().load(getFotoPerfil).into(fotoPerfil)

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nombres.clear();
                mensajes.clear();
                horas.clear();
                if (chatKey.isEmpty()) {
                    activarTemporizador();
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }else{
                    if (snapshot.hasChild("chat")){
                        for(DataSnapshot messagesnapshot: snapshot.child("chat").child(chatKey).child("messages").getChildren()){
                            if (messagesnapshot.hasChild("msg")&&messagesnapshot.hasChild("emisor")){
                                final String msgTimeStamp=messagesnapshot.getKey();
                                final String usermail=messagesnapshot.child("emisor").getValue(String.class);
                                final String msg=messagesnapshot.child("msg").getValue(String.class);
                                nombres.add(usermail);
                                mensajes.add(msg);
                                horas.add(msgTimeStamp);
                                chatRecView.setHasFixedSize(true);
                                chatRecView.setLayoutManager(new LinearLayoutManager(Chat.this));
                                chatAdapter=new ChatAdapter(usermail,getApplicationContext(),nombres,mensajes,horas);
                                chatRecView.setAdapter(chatAdapter);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //enviar boton
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el tiempo actual en milisegundos
                long currentTimeMillis = System.currentTimeMillis();

// Crear un objeto SimpleDateFormat para el formato deseado
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

// Convertir el tiempo actual a formato de fecha y hora
                String formattedDateTime = dateFormat.format(new Date(currentTimeMillis));

                String message=messageEditTxt.getText().toString();
                if (!message.isEmpty()) {
                    databaseReference.child("chat").child(chatKey).child("user1").setValue(user1mail);
                    databaseReference.child("chat").child(chatKey).child("user2").setValue(usermail);
                    databaseReference.child("chat").child(chatKey).child("messages").child(formattedDateTime).child("msg").setValue(message);
                    databaseReference.child("chat").child(chatKey).child("messages").child(formattedDateTime).child("emisor").setValue(usermail);
                    getMessages();
                }else{
                    Toast.makeText(Chat.this, "No se puede enviar mensajes vacios", Toast.LENGTH_SHORT).show();
                }
                messageEditTxt.setText("");

            }
        });

        //atras boton
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Chat.this, UsersList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void activarTemporizador() {

    }


}
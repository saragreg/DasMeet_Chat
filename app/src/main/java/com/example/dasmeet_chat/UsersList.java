package com.example.dasmeet_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mensajeriafcm-ea7c9-default-rtdb.europe-west1.firebasedatabase.app/");
    private String usermail="saragarcia@gmail.com";
    private String userExtmail="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        ArrayList<String> noms=new ArrayList<>();
        ArrayList<String> imgs=new ArrayList<>();
        ArrayList<String> mails=new ArrayList<>();
        noms.add("jowi");
        noms.add("diego");
        noms.add("vicent");
        mails.add("joel@gmail.com");
        mails.add("diego@gmail.com");
        mails.add("vicent@gmail.com");

        ListView lista=findViewById(R.id.users_list);
        UserListAdapter eladap = new UserListAdapter(getApplicationContext(), noms, imgs);
        lista.setAdapter(eladap);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UsersList.this, Chat.class);
                intent.putExtra("nombre", noms.get(i));
                intent.putExtra("mi_nombre", "sara");
                intent.putExtra("chatKey", "");
                //intent.putExtra("fotoPerfil",imgs.get(i));
                startActivity(intent);
                //obtenerClave(noms.get(i),mails.get(i));
            }
        });
    }

    private void obtenerClave(String nom,String mail) {
        // Crea una referencia a la ubicación "chat" en la base de datos
        DatabaseReference chatRef = databaseReference.child("chat");

// Realiza una consulta para obtener el chatKey basado en user1mail y usermail
        Query query = chatRef.orderByChild("user1").equalTo(mail);
                /*.or().orderByChild("user1").equalTo(usermail)
                .or().orderByChild("user2").equalTo(mail)
                .or().orderByChild("user2").equalTo(usermail);*/

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    if (dataSnapshot.exists()) {

                        String chatKey = chatSnapshot.getKey();
                        Intent intent = new Intent(UsersList.this, Chat.class);
                        intent.putExtra("nombre", nom);
                        intent.putExtra("mi_nombre", "sara");
                        intent.putExtra("chatKey", chatKey);
                        //intent.putExtra("fotoPerfil",imgs.get(i));
                        startActivity(intent);
                        // Aquí tienes el chatKey correspondiente a la consulta
                        // Puedes realizar las operaciones que necesites con el chatKey obtenido
                    }else{
                        Intent intent = new Intent(UsersList.this, Chat.class);
                        intent.putExtra("nombre", nom);
                        intent.putExtra("mi_nombre", "sara");
                        intent.putExtra("chatKey", "");
                        //intent.putExtra("fotoPerfil",imgs.get(i));
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja cualquier error de la consulta
            }
        });

    }
}
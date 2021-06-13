package com.example.notemaking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragment extends Fragment {

    RecyclerView recyclerView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton2) ;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , NotesActivity.class) ;
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recycler) ;
        Show();
        return view;
    }

    public void Show(){
        NotesDetails notesDetails = new NotesDetails(getActivity()) ;
        Cursor cursor = notesDetails.getData() ;
        List<String> titles = new ArrayList<>() ;
        List<String> dates = new ArrayList<>() ;

        if(cursor.getCount()==0){
            Toast.makeText(getActivity(), "Create New", Toast.LENGTH_SHORT).show();
        }
        while(cursor.moveToNext()){
            titles.add(cursor.getString(0)) ;
            dates.add(cursor.getString(2)) ;
        }
        UpdateUI(recyclerView , titles , dates);
        notesDetails.close();
        cursor.close();
    }

    public void UpdateUI(RecyclerView recyclerView , List<String> titles , List<String> dates){
        final RecycleAdapter adapter = new RecycleAdapter(titles , dates) ;
        recyclerView.setAdapter(adapter);
        adapter.setListener(new RecycleAdapter.Listener() {
            @Override
            public void oneClick(int position) {
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(position));
                getActivity().startActivity(intent);
            }

            @Override
            public void Longclick(final int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;
                builder.setTitle("Do you want to remove ?") ;
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotesDetails notesDetails = new NotesDetails(getContext());
                        Cursor cursor = notesDetails.getData();
                        List<String> titles = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            titles.add(cursor.getString(0));
                        }
                        String target_title = titles.get(position) ;
                        boolean deleted = notesDetails.DeleteuserData(target_title) ;
                        onResume();
                    }
                }) ;

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("Closed" , "Closed") ;
                    }
                }) ;
                builder.setCancelable(true) ;
                AlertDialog alertDialog = builder.create() ;
                alertDialog.show();
            }
        }) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        Show() ;
    }

}
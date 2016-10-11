package lander.expandable;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference databaseReference;
    private DatabaseReference queryDataBase;

    private List<ParentObject> chapters;
    private List<ContentItem> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://docs-ifpi.firebaseio.com/documents/-KT2fklX1zYZohhFbFkq");
        queryDataBase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://docs-ifpi.firebaseio.com/document-content");

        chapters = new ArrayList<>();
        dataSet = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.container);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                childAdd(dataSnapshot);
                adapter = new ListAdapter(getApplicationContext(), chapters);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        queryDataBase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                childAddContent(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem,new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do whatever you need
                Toast.makeText(getApplicationContext(),"Digite acima os termos de sua busca!", Toast.LENGTH_SHORT).show();

                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do whatever you need
                Toast.makeText(getApplicationContext(),"teste", Toast.LENGTH_SHORT).show();
                adapter = new ListAdapter(getApplicationContext(), chapters);
                recyclerView.setAdapter(adapter);
                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        });

        SearchView mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setQueryHint("Digite termos do conteudo");
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((SearchView)v).isIconified()){

                }
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),"Query "+query, Toast.LENGTH_SHORT).show();
                funcaoFodonaDeBusca(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getApplicationContext(),"newText "+newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return true;
    }

    private void childAdd(DataSnapshot dataSnapshot) {
        switch (dataSnapshot.getKey()){
            case "title":
                setTitle(dataSnapshot.getValue(String.class));
                break;
            case "chapters":
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String number = "";
                    String chapter = "";
                    String contentKey = "";
                    String description = "";
                    for(DataSnapshot d: data.getChildren()) {
                        switch (d.getKey()){
                            case "number":
                                number = d.getValue(String.class);
                                break;
                            case "title":
                                chapter = " - " + d.getValue(String.class);
                                break;
                            case "description":
                                description = d.getValue(String.class);
                                break;
                            case "content":
                                contentKey = d.getValue(String.class);
                                break;
                        }
                    }
                    chapter = number + chapter;
                    HeaderParent parent = new HeaderParent(chapter);
                    List<Object> child = new ArrayList<>();
                    child.add(new BodyContent(description,contentKey));
                    parent.setChildObjectList(child);
                    chapters.add(0,parent);
                }
        }

    }

    private void childAddContent(DataSnapshot dataSnapshot) {
        ContentItem item = new ContentItem();
        item.setKey(dataSnapshot.getKey());
        for(DataSnapshot data: dataSnapshot.getChildren()) {
            switch (data.getKey()){
                case "definicao":
                    item.setDefinicao(data.getValue(String.class));
                    break;
                case "informacoesGerais":
                    item.setInformacoesGerais(data.getValue(String.class));
                    System.out.println(item.getInformacoesGerais());
                    break;
                case "legislacao":
                    item.setLegislacao(data.getValue(String.class));
                    System.out.println("lg");
                    break;
                case "requisitosBasicos":
                    item.setRequisitosBasicos(data.getValue(String.class));
                    break;
                case "title":
                    item.setTitle(data.getValue(String.class));
                    break;
                case "procedimentosTramites":
                    List<ContentItem.Procedimento> procedimentos = new ArrayList<>();
                    for(DataSnapshot d:data.getChildren()){
                        procedimentos.add(d.getValue(ContentItem.Procedimento.class));
                    }
                    item.setProcedimentosTramites(procedimentos);
                    break;
            }
        }

        dataSet.add(item);
        System.out.println("Tamanho: "+dataSet.size());
    }

    private void funcaoFodonaDeBusca(String query) {
        query.toLowerCase();
        String[] querySplitted = query.split(" ");
        System.out.println(query+" "+querySplitted.length);
        List<String> teste = new ArrayList<>();
        boolean verif;
        for (ContentItem i:
                dataSet){
            verif=false;
            for(int a = 0;a<querySplitted.length;a++){
                if(i.getTitle()!=null&&((i.getTitle().toLowerCase()).matches("(.*)"+querySplitted[a])||
                        (i.getTitle().toLowerCase()).matches("(.*)"+querySplitted[a]+"(.*)")||
                        (i.getTitle().toLowerCase()).matches(querySplitted[a]+"(.*)"))){
                    verif =true;
                }else{
                    if(i.getDefinicao()!=null&&((i.getDefinicao().toLowerCase()).matches("(.*)"+querySplitted[a])||
                            (i.getDefinicao().toLowerCase()).matches("(.*)"+querySplitted[a]+"(.*)")||
                            (i.getDefinicao().toLowerCase()).matches(querySplitted[a]+"(.*)"))){
                        verif =true;
                    }else {
                        if(i.getRequisitosBasicos()!=null&&((i.getRequisitosBasicos().toLowerCase()).matches("(.*)"+querySplitted[a])||
                                (i.getRequisitosBasicos().toLowerCase()).matches("(.*)"+querySplitted[a]+"(.*)")||
                                (i.getRequisitosBasicos().toLowerCase()).matches(querySplitted[a]+"(.*)"))){
                            verif =true;
                        }else {
                            if(i.getLegislacao()!=null&&((i.getLegislacao().toLowerCase()).matches("(.*)"+querySplitted[a])||
                                    (i.getLegislacao().toLowerCase()).matches("(.*)"+querySplitted[a]+"(.*)")||
                                    (i.getLegislacao().toLowerCase()).matches(querySplitted[a]+"(.*)"))){
                                verif =true;
                            }else {
                                if(i.getInformacoesGerais()!=null&&((i.getInformacoesGerais().toLowerCase()).matches("(.*)"+querySplitted[a])||
                                        (i.getInformacoesGerais().toLowerCase()).matches("(.*)"+querySplitted[a]+"(.*)")||
                                        (i.getInformacoesGerais().toLowerCase()).matches(querySplitted[a]+"(.*)"))){
                                    verif =true;
                                }else{
                                    for(ContentItem.Procedimento p:
                                            i.getProcedimentosTramites()) {
                                        if (p.getResponsavel()!=null&&((p.getResponsavel().toLowerCase()).matches("(.*)" + querySplitted[a])||
                                                (p.getResponsavel().toLowerCase()).matches("(.*)" + querySplitted[a] + "(.*)")||
                                                (p.getResponsavel().toLowerCase()).matches(querySplitted[a] + "(.*)"))){
                                            verif = true;
                                            break;
                                        }else {
                                            if(p.getProcedimentos()!=null&&((p.getProcedimentos().toLowerCase()).matches(querySplitted[a]+"(.*)")||
                                                    (p.getProcedimentos().toLowerCase()).matches("(.*)"+querySplitted[a]+"(.*)")||
                                                    (p.getProcedimentos().toLowerCase()).matches("(.*)"+querySplitted[a]))){
                                                verif =true;
                                                break;
                                            }else{
                                                if(p.getPasso()!=null&&((p.getPasso().toLowerCase()).matches(querySplitted[a]+"(.*)")||
                                                        (p.getPasso().toLowerCase()).matches("(.*)"+querySplitted[a]+"(.*)")||
                                                        (p.getPasso().toLowerCase()).matches("(.*)"+querySplitted[a]))){
                                                    verif =true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(verif){
                teste.add(i.getKey());
                System.out.println("key "+i.getKey());
            }
        }
        if(!teste.isEmpty()) {
            List<ParentObject> newChapters = new ArrayList<>();
            for (ParentObject parent :
                    chapters) {
                for (String key :
                        teste) {
                    if (((BodyContent) ((HeaderParent) parent).getChildObjectList().get(0)).getKey().matches(key)) {
                        newChapters.add(parent);
                    }
                }
            }
            Toast.makeText(getApplicationContext(),teste.size()+" resultado(os) encontrados", Toast.LENGTH_SHORT).show();
            adapter = new ListAdapter(getApplicationContext(), newChapters);
            recyclerView.setAdapter(adapter);
        }else {
            Toast.makeText(getApplicationContext(),"Sem resultados, tente novamnte com outras palavras", Toast.LENGTH_SHORT).show();
        }
    }

}

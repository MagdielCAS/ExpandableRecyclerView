package lander.expandable;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ContentAdapter mAdapter;
    private DatabaseReference databaseReference;
    private ContentItem item;
    private List<Object> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        item = new ContentItem();
        dataSet = new ArrayList<>();

        String contentKey = "key";

        if (getIntent().getExtras() != null) {
            contentKey = getIntent().getExtras().getString("content");
        }

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://docs-ifpi.firebaseio.com/document-content");
        System.out.println(databaseReference.getRef());

        recyclerView = (RecyclerView) findViewById(R.id.content_container);
        recyclerView.setHasFixedSize(true); //Seta os elementos de tamanho fixo, ajudar a ganhar desempenho
        linearLayoutManager = new LinearLayoutManager(this); //Define como os dados são apresentados no recycler view
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //define como uma lista vertical
        recyclerView.setLayoutManager(linearLayoutManager); //configurando o recycler view com a especificação de layout
        recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.setAdapter(mAdapter);

        Query query = databaseReference.orderByKey().equalTo(contentKey);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                childAdd(dataSnapshot);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent = new Intent(this, MainActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void childAdd(DataSnapshot dataSnapshot) {
        //item = dataSnapshot.getValue(ContentItem.class);
        item = new ContentItem();
        StickyHeader h;
        for(DataSnapshot data: dataSnapshot.getChildren()) {
            switch (data.getKey()){
                case "definicao":
                    item.setDefinicao(data.getValue(String.class));
                    h = new StickyHeader("Definição");
                    h.setSectionFirstPosition(0);
                    dataSet.add(h);
                    dataSet.add(item.getDefinicao());
                    break;
                case "informacoesGerais":
                    item.setInformacoesGerais(data.getValue(String.class));
                    h = new StickyHeader("Informações Gerais");
                    h.setSectionFirstPosition(dataSet.size());
                    dataSet.add(h);
                    dataSet.add(item.getInformacoesGerais());
                    System.out.println(item.getInformacoesGerais());
                    break;
                case "legislacao":
                    item.setLegislacao(data.getValue(String.class));
                    h = new StickyHeader("Legislação");
                    h.setSectionFirstPosition(dataSet.size());
                    dataSet.add(h);
                    dataSet.add(item.getLegislacao());
                    System.out.println("lg");
                    break;
                case "requisitosBasicos":
                    item.setRequisitosBasicos(data.getValue(String.class));
                    h = new StickyHeader("Requisitos Básicos");
                    h.setSectionFirstPosition(dataSet.size());
                    dataSet.add(h);
                    dataSet.add(item.getRequisitosBasicos());
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
                    h = new StickyHeader("Procedimentos e Tramites");
                    h.setSectionFirstPosition(dataSet.size());
                    dataSet.add(h);
                    for(ContentItem.Procedimento i:
                            item.getProcedimentosTramites()) {
                        dataSet.add("Passo " + i.getPasso());
                        dataSet.add(i.getProcedimentos());
                        dataSet.add("Responsável: "+i.getResponsavel());
                        System.out.println("pt add");
                    }
                    break;
            }
        }

        String teste = "";
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(item.getTitle());

        teste += "<h3>"+item.getTitle() + "</h3><br><br>";
        for(Object o : dataSet){
            if(o instanceof StickyHeader){
                teste += "<b>"+((StickyHeader) o).getHeader()+"</b>"+"<br><br>";
            }else {
                teste += o+"<br><br>";
            }
        }
        dataSet.clear();
        dataSet.add(teste);
        mAdapter = new ContentAdapter(dataSet);
        recyclerView.setAdapter(mAdapter);
    }
}

package com.core2plus.auhda.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.core2plus.auhda.API.Responses.auhdaResponse;
import com.core2plus.auhda.API.RetrofitClient;
import com.core2plus.auhda.Adapter.Recycleradapter;
import com.core2plus.auhda.Fragment.AboutUsFragment;
import com.core2plus.auhda.Fragment.BlogFragment;
import com.core2plus.auhda.Fragment.ContactUsFragment;
import com.core2plus.auhda.Fragment.GalleryFragment;
import com.core2plus.auhda.Fragment.HomeFragment;
import com.core2plus.auhda.Fragment.JobPortalFragment;
import com.core2plus.auhda.Fragment.ResumeDesigningFragment;
import com.core2plus.auhda.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    List<auhdaResponse> listing;
    Recycleradapter recyclerAdapter;
    RecyclerView recyclerView;
   Toolbar toolbar ;
   NavigationView navigationView;
   DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.nav_view);
        final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

//        recyclerView = findViewById(R.id.recycle_availableItems);
        listing=new ArrayList<>();
        recyclerAdapter = new Recycleradapter(listing);
        navigationView.setNavigationItemSelectedListener(this);
       // getAuhdaPosts();


        goToFragment(new HomeFragment(),"HOME_FRAGMENT");
        setTitle("Home");
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Intent intent = new Intent(this, DashboardActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;



        }

        return super.onOptionsItemSelected(item);
    }


    private void goToFragment(Fragment fragment, String TAG) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment,TAG).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id){
            case R.id.nav_home:
                //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                goToFragment(new HomeFragment(),"HOME_FRAGMENT");
                closeDrwaer("Home");
                break;
            case R.id.nav_aboutUs:
                goToFragment(new AboutUsFragment(),"ABOUTUS_FRAGMENT");
                //Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                closeDrwaer("About Us");
                break;
            case R.id.nav_resumeDesigning:
                goToFragment(new ResumeDesigningFragment(),"RESUME_FRAGMENT");
                //Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                closeDrwaer("Resume Designing");
                break;
            case R.id.nav_jobPortal:
                goToFragment(new JobPortalFragment(),"JOBPORTAL_FRAGMENT");
                //Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                closeDrwaer("Job Portal");
                break;
            case R.id.nav_blog:
                goToFragment(new BlogFragment(),"BLOG_FRAGMENT");
                //Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                closeDrwaer("Blog");
                break;
            case R.id.nav_gallery:
                goToFragment(new GalleryFragment(),"GALLERY_FRAGMENT");
                //Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                closeDrwaer("Gallery");
                break;
            case R.id.nav_contactUs:
                goToFragment(new ContactUsFragment(),"CONTACT_FRAGMENT");
                //Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                closeDrwaer("Contact Us");
                break;
            case R.id.nav_signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                closeDrwaer("Contact Us");
                break;

        }
        return true;
    }

    private void closeDrwaer(String Title){
        drawerLayout.closeDrawer(GravityCompat.START);
        toolbar.setTitle(Title);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //    private void getAuhdaPosts(){
//        Call<List<auhdaResponse>> call= RetrofitClient.getInstance().getApi().getPosts();
//        call.enqueue(new Callback<List<auhdaResponse>>() {
//            @Override
//            public void onResponse(Call<List<auhdaResponse>> call, Response<List<auhdaResponse>> response) {
//                List<auhdaResponse> auhdaResponses=response.body();
//                if(auhdaResponses!=null){
////            for(int i=0;i<auhdaResponses.size();i++){
//                    for(auhdaResponse auhdaResponse : auhdaResponses){
////                if(auhdaResponses.get(i).getTitle()!=null && auhdaResponses.get(i).getContent()!=null ){
////                    String id=auhdaResponses.get(i).getId().toString();
////                    String content=auhdaResponses.get(i).getContent().getRendered().toString();
////                    String date=auhdaResponses.get(i).getDate().toString();
//////                    String date2[]=date.split("T");
////                    String date2 =date.replace("T"," ");
////                    String title=auhdaResponses.get(i).getTitle().getRendered().toString();
////                    String a=(content.replace("\\n"," "));
////                    String b=(a.replace("\\r"," "));
////                    String c=(b.replace("\\t"," "));
//                        //String ht=Html.fromHtml(c).toString();
//                        if(auhdaResponse.getTitle()!=null && auhdaResponse.getContent()!=null ){
//                            String id=auhdaResponse.getId().toString();
//                            String content=auhdaResponse.getContent().getRendered().toString();
//                            String date=auhdaResponse.getDate().toString();
////                    String date2[]=date.split("T");
//                            String date2 =date.replace("T"," ");
//                            String title=auhdaResponse.getTitle().getRendered().toString();
//                            String ht= Jsoup.parse(content).text();
//                            Log.v("auhdaID",id);
//                            Log.v("auhdaContent",ht);
//                            Log.v("auhdaDate",date2);
//                            Log.v("auhdaTitle",title);
////                    listing.add(auhdaResponses);
//                            listing.add(auhdaResponse);
//
////                    Set<auhdaResponse> s = new LinkedHashSet<>(listing);
//
//
//                        }
//                        //listing.addAll(auhdaResponses);
//                    }
////                recyclerAdapter = new Recycleradapter(listing, ImageLoader.getInstance());
//                    RecyclerView.LayoutManager recyce = new LinearLayoutManager(getApplicationContext(), GridLayoutManager.VERTICAL, false);
//                    // RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
//                    //recyclerView.addItemDecoration(new GridSpacingdecoration(2, dpToPx(10), true));
//                    recyclerView.setLayoutManager(recyce);
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    recyclerView.setAdapter(recyclerAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<auhdaResponse>> call, Throwable t) {
//                Log.v("auhdaErr",t.getMessage().toString());
//
//            }
//        });
//
//    }


}

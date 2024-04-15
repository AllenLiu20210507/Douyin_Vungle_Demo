package com.example.administrator.douyinviewpager;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.vungle.ads.BannerAd;
import com.vungle.ads.BannerAdListener;
import com.vungle.ads.BannerAdSize;
import com.vungle.ads.BannerView;
import com.vungle.ads.BaseAd;
import com.vungle.ads.InitializationListener;
import com.vungle.ads.VungleAds;
import com.vungle.ads.VungleError;


import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity";
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;
    private String  mrecPlacmentId="ALLEN_TEST_MREC-4448051";
    private String  bannerPlacementID="NON_HB_MREC-4656953";
    private String  vungleAppId="6285f650556a4f983b636be8";
    private BannerAd bannerAd;
    private BannerView bannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSDK();
        initListener();
    }

    private void initSDK() {

        VungleAds.init(MainActivity.this, vungleAppId, new InitializationListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Vungle SDK init onSuccess()");

            }
            @Override
            public void onError(@NonNull VungleError vungleError) {
                Log.d(TAG, "onError():" + vungleError.getErrorMessage());
            }
        });
//        Vungle.init(vungleAppId, getApplicationContext(), new InitCallback() {
//            @Override
//            public void onSuccess() {
//                if (Vungle.isInitialized()){
//                    BannerAdConfig mrecAdConfig = new BannerAdConfig();
//                    mrecAdConfig.setAdSize(AdConfig.AdSize.VUNGLE_MREC);
//                    Banners.loadBanner(mrecPlacmentId, mrecAdConfig, vungleLoadAdCallback);
//                    BannerAdConfig bannerAdConfig = new BannerAdConfig();
//                    bannerAdConfig.setAdSize(AdConfig.AdSize.BANNER);
//                    Banners.loadBanner(bannerPlacementID, bannerAdConfig, vungleLoadAdCallback);
//                }
//            }
//
//            @Override
//            public void onError(VungleException throwable) {
//
//            }
//
//            @Override
//            public void onAutoCacheAdAvailable(final String placementReferenceID) {
//
//            }
//        });
    }

//    // Vungle LoadAdCallback
//    private final LoadAdCallback vungleLoadAdCallback = new LoadAdCallback() {
//        @Override
//        public void onAdLoad(final String placementReferenceID) {
//            Log.d(TAG, "LoadAdCallback - onAdLoad" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID);
//
//        }
//
//        @Override
//        public void onError(final String placementReferenceID, VungleException throwable) {
//            Log.d(TAG, "LoadAdCallback - onError" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID +
//                    "\n\tError = " + throwable.getLocalizedMessage());
//
//        }
//    };


//    //Vungle PlayAdCallback
//    private final PlayAdCallback vunglePlayAdCallback = new PlayAdCallback() {
//        @Override
//        public void creativeId(String creativeId) {
//
//        }
//
//        @Override
//        public void onAdStart(final String placementReferenceID) {
//            Log.d(TAG, "PlayAdCallback - onAdStart" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID);
//
//
//        }
//
//        @Override
//        public void onAdViewed(String placementReferenceID) {
//            Log.d(TAG, "PlayAdCallback - onAdViewed" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID);
//        }
//
//        // Deprecated
//        @Override
//        public void onAdEnd(final String placementReferenceID, final boolean completed, final boolean isCTAClicked) {
//            Log.d(TAG, "PlayAdCallback - onAdEnd" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID +
//                    "\n\tView Completed = " + completed + "" +
//                    "\n\tDownload Clicked = " + isCTAClicked);
//        }
//
//        @Override
//        public void onAdEnd(String placementReferenceID) {
//            Log.d(TAG, "PlayAdCallback - onAdEnd" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID);
//        }
//
//        @Override
//        public void onAdClick(String placementReferenceID) {
//            Log.d(TAG, "PlayAdCallback - onAdClick" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID);
//        }
//
//        @Override
//        public void onAdRewarded(String placementReferenceID) {
//            Log.d(TAG, "PlayAdCallback - onAdRewarded" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID);
//        }
//
//        @Override
//        public void onAdLeftApplication(String placementReferenceID) {
//            Log.d(TAG, "PlayAdCallback - onAdLeftApplication" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID);
//        }
//
//        @Override
//        public void onError(final String placementReferenceID, VungleException throwable) {
//            Log.d(TAG, "PlayAdCallback - onError" +
//                    "\n\tPlacement Reference ID = " + placementReferenceID +
//                    "\n\tError = " + throwable.getLocalizedMessage());
//
//        }
//    };

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler);

        mLayoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        mAdapter = new MyAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                playVideo();

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
                closeMREC();
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.e(TAG, "选中位置:" + position + "  是否是滑动到底部:" + isBottom);
                if ((position % 5 == 0&&position!=0)){
                    renderMREC();
                }else {
                    playVideo();
                }

            }

        });
    }


    private void renderMREC(){
        bannerAd = new BannerAd(MainActivity.this, bannerPlacementID, BannerAdSize.VUNGLE_MREC);
        bannerAd.setAdListener(new BannerAdListener() {
            @Override
            public void onAdLoaded(@NotNull BaseAd baseAd) {
                Log.d(TAG, "onAdLoaded():");
                View itemView = mRecyclerView.getChildAt(0);
                RelativeLayout containerView = itemView.findViewById(R.id.contianer_view);
                if (containerView.getVisibility()==View.VISIBLE){
                    bannerView = bannerAd.getBannerView();
                    Log.d(TAG, "bannerView:"+bannerView);
                    if (bannerView != null) {
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                        containerView.addView(bannerView,layoutParams);
                    }
                }
                itemView=null;//内存释放
            }

            @Override
            public void onAdStart(@NotNull BaseAd baseAd) {
                Log.d(TAG, "onAdStart():");
            }

            @Override
            public void onAdImpression(@NotNull BaseAd baseAd) {
                Log.d(TAG, "onAdImpression():");
            }

            @Override
            public void onAdEnd(@NotNull BaseAd baseAd) {
                Log.d(TAG, "onAdEnd():");
            }

            @Override
            public void onAdClicked(@NotNull BaseAd baseAd) {
                Log.d(TAG, "onAdClicked():");
            }

            @Override
            public void onAdLeftApplication(@NotNull BaseAd baseAd) {
                Log.d(TAG, "onAdLeftApplication():");
            }

            @Override
            public void onAdFailedToLoad(@NotNull BaseAd baseAd, @NotNull VungleError vungleError) {
                Log.d(TAG, "onAdFailedToLoad():");
            }

            @Override
            public void onAdFailedToPlay(@NotNull BaseAd baseAd, @NotNull VungleError vungleError) {
                Log.d(TAG, "onAdFailedToPlay():");
            }
        });
        bannerAd.load(null);

    }


    private  void  closeMREC(){
        View itemView = mRecyclerView.getChildAt(0);
        RelativeLayout containerView = itemView.findViewById(R.id.contianer_view);
        if (bannerView != null) {
            Log.d(TAG, "closeMREC:+bannerView"+bannerView);
            containerView.removeAllViews();
            bannerAd.finishAd();
            bannerAd.setAdListener(null);
            bannerView = null;

        }
        itemView=null;//内存释放
    }

//    private void renderBanner(){
//        View itemView = mRecyclerView.getChildAt(0);
//        RelativeLayout containerView = itemView.findViewById(R.id.contianer_view);
//        BannerAdConfig bannerAdConfig = new BannerAdConfig();
//        bannerAdConfig.setAdSize(AdConfig.AdSize.BANNER);
//        if (containerView.getVisibility()==View.VISIBLE){
//            if (Banners.canPlayAd(bannerPlacementID,bannerAdConfig.getAdSize())){
//                if (vungleBanner != null) {
//                    vungleBanner.destroyAd();
//                    vungleBanner = null;
//                    containerView.removeView(vungleBanner);
//                }
//
//                bannerAdConfig.setAdSize(AdConfig.AdSize.BANNER);
//
//                vungleBanner = Banners.getBanner(bannerPlacementID, bannerAdConfig.getAdSize(), vunglePlayAdCallback);
//
//                if (vungleBanner != null) {
//                    containerView.addView(vungleBanner);
//                }
//            }else{
//                Toast.makeText(MainActivity.this,"No Ads",Toast.LENGTH_LONG).show();
//            }
//        }
//        itemView=null;//内存释放
//    }





    private void playVideo() {
        {
            View itemView = mRecyclerView.getChildAt(0);
            final VideoView videoView = itemView.findViewById(R.id.video_view);
            final RelativeLayout rootView = itemView.findViewById(R.id.root_view);
            final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
            videoView.start();
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    mediaPlayer[0] = mp;
                    Log.e(TAG, "onInfo");
                    mp.setLooping(true);
                    return false;
                }
            });
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.e(TAG, "onPrepared");

                }
            });
        }

    }

    private void releaseVideo(int index) {
        View itemView = mRecyclerView.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view);

        videoView.stopPlayback();

    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private int[] imgs = {R.mipmap.video11, R.mipmap.video12, R.mipmap.video13, R.mipmap.video14, R.mipmap.img_video_2};
        private int[] videos = {R.raw.video21, R.raw.video22, R.raw.video23, R.raw.video24, R.raw.video25};

        public MyAdapter() {
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            if ((position % 5 == 0)&&position!=0) {
                Log.e(TAG, "allen__containerView_____pos"+position);
                holder.videoView.setVisibility(View.GONE);
                holder.containerView.setVisibility(View.VISIBLE);


            } else {
                Log.e(TAG, "allen__videoView_____pos"+position);
                holder.videoView.setVisibility(View.VISIBLE);
                holder.containerView.setVisibility(View.GONE);
                holder.videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videos[position % 5]));
            }
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img_thumb;
            VideoView videoView;
            ImageView img_play;
            RelativeLayout rootView;
            RelativeLayout containerView;

            public ViewHolder(View itemView) {
                super(itemView);
                containerView = itemView.findViewById(R.id.contianer_view);
                videoView = itemView.findViewById(R.id.video_view);
                rootView = itemView.findViewById(R.id.root_view);
            }
        }
    }
}

package com.example.administrator.douyinviewpager;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.vungle.warren.AdConfig;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.VungleNativeAd;
import com.vungle.warren.error.VungleException;

import static com.vungle.warren.Vungle.getValidPlacements;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity";
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;
    private String  mrecPlacmentId="MREC_TEST-9283413";
    private String  vungleAppId="5df0a46e2b8ae90011798d50";
    private VungleNativeAd vungleNativeAd;
    private View nativeAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSDK();
        initListener();
    }

    private void initSDK() {
        Vungle.init(vungleAppId, getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                if (Vungle.isInitialized()) {
                    // Play Vungle ad
                    Vungle.loadAd(mrecPlacmentId, vungleLoadAdCallback);
                    // Button UI

                }
            }

            @Override
            public void onError(VungleException throwable) {

            }

            @Override
            public void onAutoCacheAdAvailable(final String placementReferenceID) {

            }
        });
    }

    // Vungle LoadAdCallback
    private final LoadAdCallback vungleLoadAdCallback = new LoadAdCallback() {
        @Override
        public void onAdLoad(final String placementReferenceID) {
            Log.d(TAG, "LoadAdCallback - onAdLoad" +
                    "\n\tPlacement Reference ID = " + placementReferenceID);

        }

        @Override
        public void onError(final String placementReferenceID, VungleException throwable) {
            Log.d(TAG, "LoadAdCallback - onError" +
                    "\n\tPlacement Reference ID = " + placementReferenceID +
                    "\n\tError = " + throwable.getLocalizedMessage());

        }
    };


    //Vungle PlayAdCallback
    private final PlayAdCallback vunglePlayAdCallback = new PlayAdCallback() {
        @Override
        public void onAdStart(final String placementReferenceID) {
            Log.d(TAG, "PlayAdCallback - onAdStart" +
                    "\n\tPlacement Reference ID = " + placementReferenceID);


        }

        @Override
        public void onAdViewed(String placementReferenceID) {
            Log.d(TAG, "PlayAdCallback - onAdViewed" +
                    "\n\tPlacement Reference ID = " + placementReferenceID);
        }

        // Deprecated
        @Override
        public void onAdEnd(final String placementReferenceID, final boolean completed, final boolean isCTAClicked) {
            Log.d(TAG, "PlayAdCallback - onAdEnd" +
                    "\n\tPlacement Reference ID = " + placementReferenceID +
                    "\n\tView Completed = " + completed + "" +
                    "\n\tDownload Clicked = " + isCTAClicked);
        }

        @Override
        public void onAdEnd(String placementReferenceID) {
            Log.d(TAG, "PlayAdCallback - onAdEnd" +
                    "\n\tPlacement Reference ID = " + placementReferenceID);
        }

        @Override
        public void onAdClick(String placementReferenceID) {
            Log.d(TAG, "PlayAdCallback - onAdClick" +
                    "\n\tPlacement Reference ID = " + placementReferenceID);
        }

        @Override
        public void onAdRewarded(String placementReferenceID) {
            Log.d(TAG, "PlayAdCallback - onAdRewarded" +
                    "\n\tPlacement Reference ID = " + placementReferenceID);
        }

        @Override
        public void onAdLeftApplication(String placementReferenceID) {
            Log.d(TAG, "PlayAdCallback - onAdLeftApplication" +
                    "\n\tPlacement Reference ID = " + placementReferenceID);
        }

        @Override
        public void onError(final String placementReferenceID, VungleException throwable) {
            Log.d(TAG, "PlayAdCallback - onError" +
                    "\n\tPlacement Reference ID = " + placementReferenceID +
                    "\n\tError = " + throwable.getLocalizedMessage());

        }
    };

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
                }else{
                    playVideo();

                }

            }

        });
    }


    private void renderMREC(){
        View itemView = mRecyclerView.getChildAt(0);
        RelativeLayout containerView = itemView.findViewById(R.id.contianer_view);
        if (containerView.getVisibility()==View.VISIBLE){
            if (Vungle.canPlayAd(mrecPlacmentId)) {
                AdConfig adConfig = new AdConfig();
                if (vungleNativeAd != null) {
                    vungleNativeAd.finishDisplayingAd();
                    vungleNativeAd = null;
                    containerView.removeView(nativeAdView);
                }

                adConfig.setAdSize(AdConfig.AdSize.VUNGLE_MREC);
                adConfig.setMuted(false);

                vungleNativeAd = Vungle.getNativeAd(mrecPlacmentId, adConfig, vunglePlayAdCallback);

                if (vungleNativeAd != null) {
                    nativeAdView = vungleNativeAd.renderNativeView();
                    containerView.addView(nativeAdView);
                }
            }
        }
        itemView=null;//内存释放
    }


    private  void  closeMREC(){
        View itemView = mRecyclerView.getChildAt(0);
        RelativeLayout containerView = itemView.findViewById(R.id.contianer_view);
        if (vungleNativeAd != null) {
            vungleNativeAd.finishDisplayingAd();
            vungleNativeAd = null;
            containerView.removeView(nativeAdView);
        }
        itemView=null;//内存释放
    }

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
        private int[] videos = {R.raw.video11, R.raw.video12, R.raw.video13, R.raw.video14, R.raw.video_2};

        public MyAdapter() {
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            if (position % 5 == 0&&position!=0) {
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

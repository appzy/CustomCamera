package anthony.camerademo.coustom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import anthony.camerademo.R;
import anthony.cameralibrary.CameraManager;
import anthony.cameralibrary.widget.CameraLayout;
import anthony.cameralibrary.widget.CameraSurfaceView;
import anthony.cameralibrary.CustomCameraHelper;
import anthony.cameralibrary.constant.ECameraType;
import anthony.cameralibrary.iml.ICameraListenner;

/**
 * 主要功能:演示拍照功能
 * Created by wz on 2017/11/20
 * 修订历史:
 */
public class PictureActivity extends Activity implements View.OnClickListener,ICameraListenner {
    private CameraLayout cameraLayout;
    private Context mContext;
    private ImageView iv_preview;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置横屏
        setContentView(R.layout.activity_picture);
        mContext = this;
        initView();
        initCamera();
    }

    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.surface_view);
        iv_preview = findViewById(R.id.iv_preview);
        iv_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CustomCameraHelper.getInstance().getOutputMediaFileUri() != null) {
                    Intent showIntent = new Intent(PictureActivity.this, ShowActivity.class);
                    showIntent.setDataAndType(CustomCameraHelper.getInstance().getOutputMediaFileUri(), "pic");
                    startActivity(showIntent);
                }

            }
        });
        findViewById(R.id.iv_cancle).setOnClickListener(this);
        findViewById(R.id.iv_photograph).setOnClickListener(this);
        findViewById(R.id.iv_comfirm).setOnClickListener(this);
    }

    private void initCamera() {
        cameraLayout = new CameraLayout.Builder(mContext, this)
                .setCameraType(ECameraType.CAMERA_TAKE_PHOTO)
                .setLoadSettingParams(true)
                .setPreviewImageView(iv_preview).setOutPutDirName("images")
                .setFileName("test.jpg")
                .startCamera();
        if (cameraLayout.getParent() != null)
            ((ViewGroup) cameraLayout.getParent()).removeAllViews();
        frameLayout.addView(cameraLayout);
    }

    /**
     * 锁屏时候这个方法也会被调用
     * 记住要手动设置surfaceView的可见性
     * 不然SurfaceView中surfaceholder.callback的所有方法都不会执行
     */
    @Override
    public void onPause() {
        CustomCameraHelper.getInstance().onPause();
        super.onPause();
    }


    @Override
    public void onResume() {
        CustomCameraHelper.getInstance().onResume();
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancle://返回
                finish();
                break;

            case R.id.iv_photograph://拍照
                CustomCameraHelper.getInstance().startCamera();
                break;

            case R.id.iv_comfirm://提交

                break;
        }
    }

    @Override
    public void error(String msg) {
        ToastUtils.showShortToast(mContext,msg);
    }

    @Override
    public void switchCameraDirection(CameraManager.CameraDirection cameraDirection) {

    }

    @Override
    public void switchLightStatus(CameraManager.FlashLigthStatus flashLigthStatus) {

    }
}

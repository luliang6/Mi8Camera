package org.codeaurora.snapcam;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;

import org.codeaurora.snapcam.camera.CameraHelper;
import org.codeaurora.snapcam.camera.CameraListener;

public class MainActivity extends AppCompatActivity {

	private AutoFitTextureView previewView;
	private Integer cameraID = 5;
	private CameraHelper mCameraHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScreenUtils.setFullScreen(this);

		setContentView(R.layout.activity_main);
		previewView = findViewById(R.id.preview);

		PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE).rationale(shouldRequest -> {
		}).callback(new PermissionUtils.SimpleCallback() {
			@Override
			public void onGranted() {
				initCamera();
				if (mCameraHelper != null) {
					mCameraHelper.start();
				}

			}

			@Override
			public void onDenied() {

			}
		}).theme(ScreenUtils::setFullScreen).request();

	}


	@Override
	protected void onResume() {
		super.onResume();

	}

	private void initCamera() {
		CameraListener listener = new CameraListener() {
			@Override
			public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {

			}

			@Override
			public void onPreview(byte[] data, Camera camera) {

			}

			@Override
			public void onCameraClosed() {

			}

			@Override
			public void onCameraError(Exception e) {

			}

			@Override
			public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {

			}
		};

		mCameraHelper = new CameraHelper.Builder()
				.previewViewSize(new Point(previewView.getMeasuredWidth(), previewView.getMeasuredHeight()))
				.rotation(getWindowManager().getDefaultDisplay().getRotation())
				.specificCameraId(cameraID != null ? cameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
				.isMirror(false)
				.previewOn(previewView)
				.cameraListener(listener)
				.build();

		mCameraHelper.init();
	}
}

package com.codepath.apps.tumblrsnap.activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.codepath.apps.tumblrsnap.ImageFilterProcessor;
import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.TumblrClient;
import com.codepath.apps.tumblrsnap.models.User;
import com.codepath.libraries.androidviewhelpers.SimpleProgressDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PreviewPhotoActivity extends FragmentActivity {
	private Bitmap photoBitmap;
	private Bitmap processedBitmap;
	private SimpleProgressDialog dialog;
	private ImageFilterProcessor filterProcessor;

	@Bind(R.id.llFilters)
	LinearLayout mfilters;
	@Bind(R.id.ivNormal)
	ImageView mNormal;
	@Bind(R.id.ivBlur)
	ImageView mBlur;
	@Bind(R.id.ivChrystal)
	ImageView mChrystal;
	@Bind(R.id.ivSolar)
	ImageView mSolar;
	@Bind(R.id.ivGlow)
	ImageView mGlow;
	@Bind(R.id.ivGrayscale)
	ImageView mGrayscale;
	@Bind(R.id.ivNormalFrame)
	ImageView mNormalFrame;
	@Bind(R.id.ivPreview)
	ImageView ivPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview_photo);
		ButterKnife.bind(this);
		photoBitmap = getIntent().getParcelableExtra("photo_bitmap");
		filterProcessor = new ImageFilterProcessor(photoBitmap);
		redisplayPreview(ImageFilterProcessor.NONE);

		displayAllFilters();
		mNormalFrame.setBackgroundColor(getResources().getColor(R.color.selected_filter));
		mNormalFrame.setBackgroundResource(R.color.selected_filter);
	}
	
	private void redisplayPreview(int effectId) {
        processedBitmap = filterProcessor.applyFilter(effectId);
        ivPreview.setImageBitmap(processedBitmap);
	}

	public void displayAllFilters() {
		displayFilter(mNormal, ImageFilterProcessor.NONE);
		displayFilter(mBlur, ImageFilterProcessor.BLUR);
		displayFilter(mChrystal, ImageFilterProcessor.CRYSTALLIZE);
		displayFilter(mSolar, ImageFilterProcessor.SOLARIZE);
		displayFilter(mGlow, ImageFilterProcessor.GLOW);
		displayFilter(mGrayscale, ImageFilterProcessor.GRAYSCALE);
	}

	public void onSelectFilter(View v) {
		String filter = (String) v.getTag();
		int effectId;

		if (filter.equals("normal")) {
			effectId = ImageFilterProcessor.NONE;
		} else if (filter.equals("blur")) {
			effectId = ImageFilterProcessor.BLUR;
		} else if (filter.equals("christal")) {
			effectId = ImageFilterProcessor.CRYSTALLIZE;
		} else if (filter.equals("solar")) {
			effectId = ImageFilterProcessor.SOLARIZE;
		} else if (filter.equals("grayscale")) {
			effectId = ImageFilterProcessor.GRAYSCALE;
		} else {
			effectId = ImageFilterProcessor.GLOW;
		}

		resetFrames();
		ImageView ivFrame = (ImageView) v.findViewWithTag("frame");
		if (ivFrame != null)
			ivFrame.setBackgroundColor(getResources().getColor(R.color.selected_filter));

		//displayFilter(ivPreview, effectId);
		redisplayPreview(effectId);
	}

	private void resetFrames(){
		ArrayList<View> filterFrames = getViewsByTag(mfilters, "frame");

		for(View ivFrame: filterFrames){
			ivFrame.setBackgroundColor(getResources().getColor(android.R.color.background_light));
		}
	}

	private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
		ArrayList<View> views = new ArrayList<View>();
		final int childCount = root.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = root.getChildAt(i);
			if (child instanceof ViewGroup) {
				views.addAll(getViewsByTag((ViewGroup) child, tag));
			}

			final Object tagObj = child.getTag();
			if (tagObj != null && tagObj.equals(tag)) {
				views.add(child);
			}

		}
		return views;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview_photo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.more || itemId == R.id.action_save)
			return true;
		
		int effectId = 0;
		
		switch (itemId) {
		case R.id.filter_none:
			effectId = ImageFilterProcessor.NONE;
			break;
		case R.id.filter_blur:
			effectId = ImageFilterProcessor.BLUR;
			break;
		case R.id.filter_grayscale:
			effectId = ImageFilterProcessor.GRAYSCALE;
			break;
		case R.id.filter_crystallize:
			effectId = ImageFilterProcessor.CRYSTALLIZE;
			break;
		case R.id.filter_solarize:
			effectId = ImageFilterProcessor.SOLARIZE;
			break;
		case R.id.filter_glow:
			effectId = ImageFilterProcessor.GLOW;
			break;
		default:
			effectId = ImageFilterProcessor.NONE;
			break;
		}
		redisplayPreview(effectId);
		return true;
	}

	public void onSaveButton(MenuItem menuItem) {
		dialog = SimpleProgressDialog.build(this);
		dialog.show();
		
		TumblrClient client = ((TumblrClient) TumblrClient.getInstance(TumblrClient.class, this));
		client.createPhotoPost(User.currentUser().getBlogHostname(), processedBitmap, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				dialog.dismiss();
				PreviewPhotoActivity.this.finish();
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				dialog.dismiss();
			}
		});
	}

	private void displayFilter(ImageView iv, final int effectId) {
		new ApplyFilterAsync(iv, effectId).execute();
	}

	private class ApplyFilterAsync extends AsyncTask<Void, Void, Bitmap> {
		private ImageView iv;
		private int effectId;

		public ApplyFilterAsync(ImageView iv, int effectId){
			super();
			this.iv = iv;
			this.effectId = effectId;
		}

		protected Bitmap doInBackground(Void... voids) {
			return filterProcessor.applyFilter(effectId);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			iv.setImageBitmap(bitmap);
		}
	};

}



// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.tumblrsnap.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PreviewPhotoActivity$$ViewBinder<T extends com.codepath.apps.tumblrsnap.activities.PreviewPhotoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361794, "field 'mfilters'");
    target.mfilters = finder.castView(view, 2131361794, "field 'mfilters'");
    view = finder.findRequiredView(source, 2131361796, "field 'mNormal'");
    target.mNormal = finder.castView(view, 2131361796, "field 'mNormal'");
    view = finder.findRequiredView(source, 2131361797, "field 'mBlur'");
    target.mBlur = finder.castView(view, 2131361797, "field 'mBlur'");
    view = finder.findRequiredView(source, 2131361799, "field 'mChrystal'");
    target.mChrystal = finder.castView(view, 2131361799, "field 'mChrystal'");
    view = finder.findRequiredView(source, 2131361800, "field 'mSolar'");
    target.mSolar = finder.castView(view, 2131361800, "field 'mSolar'");
    view = finder.findRequiredView(source, 2131361801, "field 'mGlow'");
    target.mGlow = finder.castView(view, 2131361801, "field 'mGlow'");
    view = finder.findRequiredView(source, 2131361798, "field 'mGrayscale'");
    target.mGrayscale = finder.castView(view, 2131361798, "field 'mGrayscale'");
    view = finder.findRequiredView(source, 2131361795, "field 'mNormalFrame'");
    target.mNormalFrame = finder.castView(view, 2131361795, "field 'mNormalFrame'");
    view = finder.findRequiredView(source, 2131361793, "field 'ivPreview'");
    target.ivPreview = finder.castView(view, 2131361793, "field 'ivPreview'");
  }

  @Override public void unbind(T target) {
    target.mfilters = null;
    target.mNormal = null;
    target.mBlur = null;
    target.mChrystal = null;
    target.mSolar = null;
    target.mGlow = null;
    target.mGrayscale = null;
    target.mNormalFrame = null;
    target.ivPreview = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.tumblrsnap.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CommentFragment$$ViewBinder<T extends com.codepath.apps.tumblrsnap.fragments.CommentFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361803, "field 'commentTExt'");
    target.commentTExt = finder.castView(view, 2131361803, "field 'commentTExt'");
    view = finder.findRequiredView(source, 2131361804, "field 'commentButton'");
    target.commentButton = finder.castView(view, 2131361804, "field 'commentButton'");
  }

  @Override public void unbind(T target) {
    target.commentTExt = null;
    target.commentButton = null;
  }
}

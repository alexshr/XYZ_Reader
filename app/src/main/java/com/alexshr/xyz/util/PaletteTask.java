package com.alexshr.xyz.util;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import timber.log.Timber;

import static com.alexshr.xyz.AppConfig.DARK_FACTOR;

public class PaletteTask {

    private final String transparency;
    private final View[] transparentViews;
    private final FloatingActionButton fab;
    private final Drawable fabIcon;
    private android.support.v7.graphics.Target swatchTarget;
    private View[] backgroundViews;
    private TextView[] titleViews;
    private TextView[] bodyViews;
    private Window window;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Drawable[] icons;

    private PaletteTask(PaletteTask.Builder builder) {
        swatchTarget = builder.swatchTarget;
        backgroundViews = builder.backgroundViews;
        titleViews = builder.titleViews;
        bodyViews = builder.bodyViews;
        window = builder.window;
        collapsingToolbarLayout = builder.collapsingToolbarLayout;
        transparency = builder.transparency;
        transparentViews = builder.transparentViews;
        icons = builder.icons;
        fab = builder.fab;
        fabIcon = builder.fabIcon;
    }

    public void apply(Palette palette) {

        if (palette == null) {
            Timber.e("no palette!");
            return;
        }

        Palette.Swatch swatch = palette.getSwatchForTarget(swatchTarget);

        if (swatch != null) {

            for (View view : backgroundViews) {
                view.setBackgroundColor(swatch.getRgb());
            }

            for (View view : transparentViews) {
                view.setBackgroundColor(ColorUtils.withTransparency(swatch.getRgb(), transparency));
            }
            for (TextView textView : titleViews) {
                textView.setTextColor(swatch.getTitleTextColor());
            }
            for (TextView textView : bodyViews) {
                textView.setTextColor(swatch.getBodyTextColor());
            }

            for (Drawable icon : icons) {
                icon.setColorFilter(swatch.getBodyTextColor(), PorterDuff.Mode.SRC_ATOP);
            }

            if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ColorUtils.manipulateColor(swatch.getRgb(), DARK_FACTOR));
            }

            if (collapsingToolbarLayout != null) {
                collapsingToolbarLayout.setContentScrimColor(swatch.getRgb());
            }

            if (fab != null && fabIcon != null) {
                fab.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));

                /*incorrect work
                fab.setImageDrawable(fabIcon);
                fabIcon.setColorFilter(swatch.getBodyTextColor(), PorterDuff.Mode.SRC_ATOP);*/

            }
        }
    }

    public static final class Builder {
        private android.support.v7.graphics.Target swatchTarget;
        private View[] backgroundViews = new View[0];
        private View[] transparentViews = new View[0];
        private TextView[] titleViews = new TextView[0];
        private TextView[] bodyViews = new TextView[0];
        private Window window;
        public CollapsingToolbarLayout collapsingToolbarLayout;
        private String transparency;
        private Drawable[] icons = new Drawable[0];
        private FloatingActionButton fab;
        private Drawable fabIcon;

        public PaletteTask.Builder swatchTarget(android.support.v7.graphics.Target val) {
            swatchTarget = val;
            return this;
        }

        public PaletteTask.Builder addCollapsingToolbarLayout(CollapsingToolbarLayout val) {
            collapsingToolbarLayout = val;
            return this;
        }

        public PaletteTask.Builder addRgbBackgroundWithTransparency(String transp, View... val) {
            transparentViews = val;
            transparency = transp;
            return this;
        }

        public PaletteTask.Builder addRgbBackground(View... val) {
            backgroundViews = val;
            return this;
        }

        public PaletteTask.Builder addTitleText(TextView... val) {
            titleViews = val;
            return this;
        }

        public PaletteTask.Builder addBodyText(TextView... val) {
            bodyViews = val;
            return this;
        }

        public PaletteTask.Builder addStatusBar(Window val) {
            window = val;
            return this;
        }

        public PaletteTask.Builder addIcons(Drawable... val) {
            icons = val;
            return this;
        }

        public Builder addFAB(FloatingActionButton fab, Drawable drawable) {
            this.fab = fab;
            fabIcon = drawable;
            return this;
        }

        public PaletteTask build() {
            return new PaletteTask(this);
        }
    }
}
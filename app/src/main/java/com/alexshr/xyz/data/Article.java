package com.alexshr.xyz.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

import static com.alexshr.xyz.AppConfig.DATE_FORMAT_IN;
import static com.alexshr.xyz.AppConfig.DATE_FORMAT_OUT;
import static com.alexshr.xyz.AppConfig.MAX_BODY_LENGTH;

@Entity(tableName = "articles")
public class Article {
    @PrimaryKey
    private int id;
    private String author;
    private String title;
    private String body;
    private String thumb;
    private String photo;
    @SerializedName("aspect_ratio")
    private float aspectRatio;
    @SerializedName("published_date")
    private String publishDate;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id=").append(id);
        sb.append(", author='").append(author).append('\'');
        sb.append(", title='").append(title);

        int limit = body.length() < 20 ? body.length() : 20;
        sb.append(", body='").append(body.substring(0, limit)).append('\'');
        sb.append(", thumb='").append(thumb).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", aspectRatio='").append(aspectRatio).append('\'');
        sb.append(", publishDate='").append(publishDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Spannable getDateAndAuthor() {
        String dateStr = publishDate;
        try {
            Date date = new SimpleDateFormat(DATE_FORMAT_IN, Locale.US).parse(publishDate);
            dateStr = new SimpleDateFormat(DATE_FORMAT_OUT, Locale.US).format(date);
        } catch (ParseException e) {
            Timber.e(e);
        }
        Spannable text = new SpannableString(dateStr + " by " + author);
        text.setSpan(new StyleSpan(Typeface.BOLD), text.length() - author.length() - 1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return text;
    }

    public String getBody() {
        return body;
    }

   /* public Spanned getBodyHtml() {
        return Html.fromHtml(body.replaceAll("(\r\n|\n)", "<br />"));
    }*/

    public void setBody(String body) {
        this.body = body.substring(0, MAX_BODY_LENGTH);
        //this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        Timber.d("%s", title);
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}

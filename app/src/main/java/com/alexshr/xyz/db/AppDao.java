package com.alexshr.xyz.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.alexshr.xyz.data.Article;

import java.util.List;

@Dao
public abstract class AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertArticles(List<Article> articles);

    @Query("SELECT * FROM articles ORDER BY id")
    public abstract LiveData<List<Article>> getArticles();

    @Query("SELECT * FROM articles WHERE id=:articleId")
    public abstract LiveData<Article> getArticle(int articleId);

    @Query("SELECT * FROM articles ORDER BY id LIMIT :position,1")
    public abstract LiveData<Article> getArticleByPosition(int position);

    @Query("DELETE FROM articles")
    public abstract void deleteRecipes();

    @Query("SELECT count(*) FROM articles")
    public abstract LiveData<Integer> getArticlesAmount();
}

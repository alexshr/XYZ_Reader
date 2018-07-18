package com.alexshr.xyz.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.alexshr.xyz.data.Article;

@Database(entities = {Article.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    public abstract AppDao recipesDao();
}

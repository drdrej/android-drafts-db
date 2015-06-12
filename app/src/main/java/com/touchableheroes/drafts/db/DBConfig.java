package com.touchableheroes.drafts.db;


/**
 * Created by asiebert on 12.06.15.
 */
public interface DBConfig {

    public String databaseName();

    public int databaseVersion();

    public Class<?>[] tables();
}

package com.touchableheroes.drafts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.touchableheroes.drafts.log.Logger;


import java.sql.SQLException;

/**
 * Created by asiebert on 02.06.15.
 */
public class AppORMLiteHelper
        extends OrmLiteSqliteOpenHelper {

    private static final Logger LOG = Logger.create(AppORMLiteHelper.class);

    private final DBConfig dbConfig;

    public AppORMLiteHelper(final Context context,
                            final DBConfig dbConfig
                            /*final String databaseName,
                            final SQLiteDatabase.CursorFactory factory,
                            final int databaseVersion*/) {
        super(context, dbConfig.databaseName(), null, dbConfig.databaseVersion());
        this.dbConfig = dbConfig;
    }

    @Override
    public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
        try {
            LOG.debug("onCreate");
            createTables(connectionSource, dbConfig.tables());
        } catch (final SQLException e) {
            LOG.exception("Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        System.out.println( "onUpgrade() called" );
        dropTablesSilently(connectionSource, dbConfig.tables());

        try {
            createTables(connectionSource, dbConfig.tables());
        } catch (SQLException e) {
            LOG.exception("Can't create database", e);
            throw new RuntimeException(e);
        }
    }



    private void createTables(final ConnectionSource connectionSrc, final Class<?>... entities) throws SQLException  {
        for (int i = 0; i < entities.length; i++) {
            TableUtils.createTable(connectionSource, entities[i]);
        }
    }

    private void dropTablesSilently(final ConnectionSource connectionSource, final Class<?>... entities) {
        for (int i = 0; i < entities.length; i++) {
            final Class<?> entity = entities[i];
            try {
                TableUtils.dropTable( connectionSource, entity, true);
            } catch (final SQLException e) {
                LOG.exception("Kann die Tabelle " + entity + " nicht löschen. Überpspringe dropTable()", e);
            }
        }

    }

/*
    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        return super.getDao(clazz);
    }
    */
}

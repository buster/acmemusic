/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq.tables;


import de.acme.jooq.Keys;
import de.acme.jooq.Public;
import de.acme.jooq.tables.LiedAuszeichnungen.LiedAuszeichnungenPath;
import de.acme.jooq.tables.PlaylistLied.PlaylistLiedPath;
import de.acme.jooq.tables.records.LiedRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Lied extends TableImpl<LiedRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.lied</code>
     */
    public static final Lied LIED = new Lied();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LiedRecord> getRecordType() {
        return LiedRecord.class;
    }

    /**
     * The column <code>public.lied.id</code>.
     */
    public final TableField<LiedRecord, String> ID = createField(DSL.name("id"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.lied.tenant</code>.
     */
    public final TableField<LiedRecord, String> TENANT = createField(DSL.name("tenant"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.lied.titel</code>.
     */
    public final TableField<LiedRecord, String> TITEL = createField(DSL.name("titel"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.lied.besitzer_id</code>.
     */
    public final TableField<LiedRecord, String> BESITZER_ID = createField(DSL.name("besitzer_id"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.lied.bytes</code>.
     */
    public final TableField<LiedRecord, byte[]> BYTES = createField(DSL.name("bytes"), SQLDataType.BLOB.nullable(false), this, "");

    private Lied(Name alias, Table<LiedRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Lied(Name alias, Table<LiedRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.lied</code> table reference
     */
    public Lied(String alias) {
        this(DSL.name(alias), LIED);
    }

    /**
     * Create an aliased <code>public.lied</code> table reference
     */
    public Lied(Name alias) {
        this(alias, LIED);
    }

    /**
     * Create a <code>public.lied</code> table reference
     */
    public Lied() {
        this(DSL.name("lied"), null);
    }

    public <O extends Record> Lied(Table<O> path, ForeignKey<O, LiedRecord> childPath, InverseForeignKey<O, LiedRecord> parentPath) {
        super(path, childPath, parentPath, LIED);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class LiedPath extends Lied implements Path<LiedRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> LiedPath(Table<O> path, ForeignKey<O, LiedRecord> childPath, InverseForeignKey<O, LiedRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private LiedPath(Name alias, Table<LiedRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public LiedPath as(String alias) {
            return new LiedPath(DSL.name(alias), this);
        }

        @Override
        public LiedPath as(Name alias) {
            return new LiedPath(alias, this);
        }

        @Override
        public LiedPath as(Table<?> alias) {
            return new LiedPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<LiedRecord> getPrimaryKey() {
        return Keys.PK_TENANT_LIED;
    }

    private transient PlaylistLiedPath _playlistLied;

    /**
     * Get the implicit to-many join path to the
     * <code>public.playlist_lied</code> table
     */
    public PlaylistLiedPath playlistLied() {
        if (_playlistLied == null)
            _playlistLied = new PlaylistLiedPath(this, null, Keys.PLAYLIST_LIED__FK_LIED_IED.getInverseKey());

        return _playlistLied;
    }

    private transient LiedAuszeichnungenPath _liedAuszeichnungen;

    /**
     * Get the implicit to-many join path to the
     * <code>public.lied_auszeichnungen</code> table
     */
    public LiedAuszeichnungenPath liedAuszeichnungen() {
        if (_liedAuszeichnungen == null)
            _liedAuszeichnungen = new LiedAuszeichnungenPath(this, null, Keys.LIED_AUSZEICHNUNGEN__LIED_AUSZEICHNUNGEN_FK.getInverseKey());

        return _liedAuszeichnungen;
    }

    @Override
    public Lied as(String alias) {
        return new Lied(DSL.name(alias), this);
    }

    @Override
    public Lied as(Name alias) {
        return new Lied(alias, this);
    }

    @Override
    public Lied as(Table<?> alias) {
        return new Lied(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Lied rename(String name) {
        return new Lied(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Lied rename(Name name) {
        return new Lied(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Lied rename(Table<?> name) {
        return new Lied(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Lied where(Condition condition) {
        return new Lied(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Lied where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Lied where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Lied where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Lied where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Lied where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Lied where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Lied where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Lied whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Lied whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}

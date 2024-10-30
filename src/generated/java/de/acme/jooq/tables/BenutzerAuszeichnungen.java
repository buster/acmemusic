/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq.tables;


import de.acme.jooq.Keys;
import de.acme.jooq.Public;
import de.acme.jooq.tables.Benutzer.BenutzerPath;
import de.acme.jooq.tables.records.BenutzerAuszeichnungenRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class BenutzerAuszeichnungen extends TableImpl<BenutzerAuszeichnungenRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.benutzer_auszeichnungen</code>
     */
    public static final BenutzerAuszeichnungen BENUTZER_AUSZEICHNUNGEN = new BenutzerAuszeichnungen();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BenutzerAuszeichnungenRecord> getRecordType() {
        return BenutzerAuszeichnungenRecord.class;
    }

    /**
     * The column <code>public.benutzer_auszeichnungen.benutzer</code>.
     */
    public final TableField<BenutzerAuszeichnungenRecord, String> BENUTZER = createField(DSL.name("benutzer"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.benutzer_auszeichnungen.auszeichnung</code>.
     */
    public final TableField<BenutzerAuszeichnungenRecord, String> AUSZEICHNUNG = createField(DSL.name("auszeichnung"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.benutzer_auszeichnungen.tenant</code>.
     */
    public final TableField<BenutzerAuszeichnungenRecord, String> TENANT = createField(DSL.name("tenant"), SQLDataType.VARCHAR.nullable(false), this, "");

    private BenutzerAuszeichnungen(Name alias, Table<BenutzerAuszeichnungenRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private BenutzerAuszeichnungen(Name alias, Table<BenutzerAuszeichnungenRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.benutzer_auszeichnungen</code> table
     * reference
     */
    public BenutzerAuszeichnungen(String alias) {
        this(DSL.name(alias), BENUTZER_AUSZEICHNUNGEN);
    }

    /**
     * Create an aliased <code>public.benutzer_auszeichnungen</code> table
     * reference
     */
    public BenutzerAuszeichnungen(Name alias) {
        this(alias, BENUTZER_AUSZEICHNUNGEN);
    }

    /**
     * Create a <code>public.benutzer_auszeichnungen</code> table reference
     */
    public BenutzerAuszeichnungen() {
        this(DSL.name("benutzer_auszeichnungen"), null);
    }

    public <O extends Record> BenutzerAuszeichnungen(Table<O> path, ForeignKey<O, BenutzerAuszeichnungenRecord> childPath, InverseForeignKey<O, BenutzerAuszeichnungenRecord> parentPath) {
        super(path, childPath, parentPath, BENUTZER_AUSZEICHNUNGEN);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class BenutzerAuszeichnungenPath extends BenutzerAuszeichnungen implements Path<BenutzerAuszeichnungenRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> BenutzerAuszeichnungenPath(Table<O> path, ForeignKey<O, BenutzerAuszeichnungenRecord> childPath, InverseForeignKey<O, BenutzerAuszeichnungenRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private BenutzerAuszeichnungenPath(Name alias, Table<BenutzerAuszeichnungenRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public BenutzerAuszeichnungenPath as(String alias) {
            return new BenutzerAuszeichnungenPath(DSL.name(alias), this);
        }

        @Override
        public BenutzerAuszeichnungenPath as(Name alias) {
            return new BenutzerAuszeichnungenPath(alias, this);
        }

        @Override
        public BenutzerAuszeichnungenPath as(Table<?> alias) {
            return new BenutzerAuszeichnungenPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public List<ForeignKey<BenutzerAuszeichnungenRecord, ?>> getReferences() {
        return Arrays.asList(Keys.BENUTZER_AUSZEICHNUNGEN__BENUTZER_AUSZEICHNUNGEN_FK);
    }

    private transient BenutzerPath _benutzer;

    /**
     * Get the implicit join path to the <code>public.benutzer</code> table.
     */
    public BenutzerPath benutzer() {
        if (_benutzer == null)
            _benutzer = new BenutzerPath(this, Keys.BENUTZER_AUSZEICHNUNGEN__BENUTZER_AUSZEICHNUNGEN_FK, null);

        return _benutzer;
    }

    @Override
    public BenutzerAuszeichnungen as(String alias) {
        return new BenutzerAuszeichnungen(DSL.name(alias), this);
    }

    @Override
    public BenutzerAuszeichnungen as(Name alias) {
        return new BenutzerAuszeichnungen(alias, this);
    }

    @Override
    public BenutzerAuszeichnungen as(Table<?> alias) {
        return new BenutzerAuszeichnungen(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public BenutzerAuszeichnungen rename(String name) {
        return new BenutzerAuszeichnungen(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BenutzerAuszeichnungen rename(Name name) {
        return new BenutzerAuszeichnungen(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public BenutzerAuszeichnungen rename(Table<?> name) {
        return new BenutzerAuszeichnungen(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public BenutzerAuszeichnungen where(Condition condition) {
        return new BenutzerAuszeichnungen(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public BenutzerAuszeichnungen where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public BenutzerAuszeichnungen where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public BenutzerAuszeichnungen where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public BenutzerAuszeichnungen where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public BenutzerAuszeichnungen where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public BenutzerAuszeichnungen where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public BenutzerAuszeichnungen where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public BenutzerAuszeichnungen whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public BenutzerAuszeichnungen whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
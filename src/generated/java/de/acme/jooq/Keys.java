/*
 * This file is generated by jOOQ.
 */
package de.acme.jooq;


import de.acme.jooq.tables.Benutzer;
import de.acme.jooq.tables.Lied;
import de.acme.jooq.tables.Playlist;
import de.acme.jooq.tables.PlaylistLied;
import de.acme.jooq.tables.records.BenutzerRecord;
import de.acme.jooq.tables.records.LiedRecord;
import de.acme.jooq.tables.records.PlaylistLiedRecord;
import de.acme.jooq.tables.records.PlaylistRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<BenutzerRecord> PK_TENANT_BENUTZER = Internal.createUniqueKey(Benutzer.BENUTZER, DSL.name("pk_tenant_benutzer"), new TableField[] { Benutzer.BENUTZER.TENANT, Benutzer.BENUTZER.ID }, true);
    public static final UniqueKey<LiedRecord> PK_TENANT_LIED = Internal.createUniqueKey(Lied.LIED, DSL.name("pk_tenant_lied"), new TableField[] { Lied.LIED.TENANT, Lied.LIED.ID }, true);
    public static final UniqueKey<PlaylistRecord> PK_TENANT_PLAYLIST = Internal.createUniqueKey(Playlist.PLAYLIST, DSL.name("pk_tenant_playlist"), new TableField[] { Playlist.PLAYLIST.TENANT, Playlist.PLAYLIST.ID }, true);
    public static final UniqueKey<PlaylistLiedRecord> PK_TENANT_PLAYLIST_LIED = Internal.createUniqueKey(PlaylistLied.PLAYLIST_LIED, DSL.name("pk_tenant_playlist_lied"), new TableField[] { PlaylistLied.PLAYLIST_LIED.TENANT, PlaylistLied.PLAYLIST_LIED.LIED_ID, PlaylistLied.PLAYLIST_LIED.PLAYLIST_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<PlaylistRecord, BenutzerRecord> PLAYLIST__PLAYLIST_BESITZER_FK = Internal.createForeignKey(Playlist.PLAYLIST, DSL.name("playlist_besitzer_fk"), new TableField[] { Playlist.PLAYLIST.TENANT, Playlist.PLAYLIST.BESITZER }, Keys.PK_TENANT_BENUTZER, new TableField[] { Benutzer.BENUTZER.TENANT, Benutzer.BENUTZER.ID }, true);
    public static final ForeignKey<PlaylistLiedRecord, LiedRecord> PLAYLIST_LIED__FK_LIED_IED = Internal.createForeignKey(PlaylistLied.PLAYLIST_LIED, DSL.name("fk_lied_ied"), new TableField[] { PlaylistLied.PLAYLIST_LIED.TENANT, PlaylistLied.PLAYLIST_LIED.LIED_ID }, Keys.PK_TENANT_LIED, new TableField[] { Lied.LIED.TENANT, Lied.LIED.ID }, true);
    public static final ForeignKey<PlaylistLiedRecord, PlaylistRecord> PLAYLIST_LIED__FK_PLAYLIST_IED = Internal.createForeignKey(PlaylistLied.PLAYLIST_LIED, DSL.name("fk_playlist_ied"), new TableField[] { PlaylistLied.PLAYLIST_LIED.TENANT, PlaylistLied.PLAYLIST_LIED.PLAYLIST_ID }, Keys.PK_TENANT_PLAYLIST, new TableField[] { Playlist.PLAYLIST.TENANT, Playlist.PLAYLIST.ID }, true);
}

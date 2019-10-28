-- col contains a single row that holds various information about the collection
CREATE TABLE IF NOT EXISTS col (
    id              integer primary key,
      -- arbitrary number since there is only one row
    crt             integer not null,
      -- timestamp of the creation date. It's correct up to the day. For V1 scheduler, the hour corresponds to starting a new day. By default, new day is 4.
    mod             integer not null,
      -- last modified in milliseconds
    scm             integer not null,
      -- schema mod time: time when "schema" was modified.
      --   If server scm is different from the client scm a full-sync is required
    ver             integer not null,
      -- version
    dty             integer not null,
      -- dirty: unused, set to 0
    usn             integer not null,
      -- update sequence number: used for finding diffs when syncing.
      --   See usn in cards table for more details.
    ls              integer not null,
      -- "last sync time"
    conf            text not null,
      -- json object containing configuration options that are synced
    models          text not null,
      -- json array of json objects containing the models (aka Note types)
    decks           text not null,
      -- json array of json objects containing the deck
    dconf           text not null,
      -- json array of json objects containing the deck options
    tags            text not null
      -- a cache of tags used in the collection (This list is displayed in the browser. Potentially at other place)
);

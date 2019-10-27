CREATE TABLE IF NOT EXISTS notes
(
    id    integer primary key, /* 0 */
    guid  text    not null, /* 1 */
    mid   integer not null, /* 2 */
    mod   integer not null, /* 3 */
    usn   integer not null, /* 4 */
    tags  text    not null, /* 5 */
    flds  text    not null, /* 6 */
    sfld  integer not null, /* 7 */
    csum  integer not null, /* 8 */
    flags integer not null, /* 9 */
    data  text    not null /* 10 */
);
CREATE INDEX ix_notes_csum on notes (csum);
CREATE INDEX ix_notes_usn on notes (usn);

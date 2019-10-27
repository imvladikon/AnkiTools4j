﻿CREATE TABLE IF NOT EXISTS cards
(
    id     integer primary key, /* 0 */
    nid    integer not null, /* 1 */
    did    integer not null, /* 2 */
    ord    integer not null, /* 3 */
    mod    integer not null, /* 4 */
    usn    integer not null, /* 5 */
    type   integer not null, /* 6 */
    queue  integer not null, /* 7 */
    due    integer not null, /* 8 */
    ivl    integer not null, /* 9 */
    factor integer not null, /* 10 */
    reps   integer not null, /* 11 */
    lapses integer not null, /* 12 */
    left   integer not null, /* 13 */
    odue   integer not null, /* 14 */
    odid   integer not null, /* 15 */
    flags  integer not null, /* 16 */
    data   text    not null /* 17 */
);
CREATE INDEX ix_cards_nid on cards (nid);
CREATE INDEX ix_cards_sched on cards (did, queue, due);
CREATE INDEX ix_cards_usn on cards (usn);
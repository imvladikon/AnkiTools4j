-- Contains deleted cards, notes, and decks that need to be synced.
-- usn should be set to -1,
-- oid is the original id.
-- type: 0 for a card, 1 for a note and 2 for a deck
CREATE TABLE IF NOT EXISTS graves
(
    usn  integer not null,
    oid  integer not null,
    type integer not null
);
-- revlog is a review history; it has a row for every review you've ever done!
CREATE TABLE IF NOT EXISTS revlog
(
    id      integer primary key,
    -- epoch-milliseconds timestamp of when you did the review
    cid     integer not null,
    -- cards.id
    usn     integer not null,
    -- update sequence number: for finding diffs when syncing.
    --   See the description in the cards table for more info
    ease    integer not null,
    -- which button you pushed to score your recall.
    -- review:  1(wrong), 2(hard), 3(ok), 4(easy)
    -- learn/relearn:   1(wrong), 2(ok), 3(easy)
    ivl     integer not null,
    -- interval (i.e. as in the card table)
    lastIvl integer not null,
    -- last interval (i.e. the last value of ivl. Note that this value is not necessarily equal to the actual interval between this review and the preceding review)
    factor  integer not null,
    -- factor
    time    integer not null,
    -- how many milliseconds your review took, up to 60000 (60s)
    type    integer not null
    --  0=learn, 1=review, 2=relearn, 3=cram
);
CREATE INDEX ix_revlog_cid on revlog (cid);
CREATE INDEX ix_revlog_usn on revlog (usn);
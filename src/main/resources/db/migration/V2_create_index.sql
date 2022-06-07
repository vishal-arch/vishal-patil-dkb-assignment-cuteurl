CREATE index idx_urlmetadata_hash on urlmetadata using btree (hash)

CREATE index idx_urlmetadata_short_url on urlmetadata using btree (short_url)

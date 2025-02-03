ALTER TABLE user_saved_events
    DROP CONSTRAINT fki36lqnoyq3uyafvsjtkrw7s7s;
ALTER TABLE user_saved_events
ADD CONSTRAINT fk_usersavedevents_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE
   ON UPDATE CASCADE;

ALTER TABLE user_saved_events
    DROP CONSTRAINT fkoqqq93p7o6o5093wg9qv6o46h;
ALTER TABLE user_saved_events
ADD CONSTRAINT fk_usersavedevents_event
FOREIGN KEY (event_id)
REFERENCES events(id)
ON DELETE CASCADE
   ON UPDATE CASCADE;

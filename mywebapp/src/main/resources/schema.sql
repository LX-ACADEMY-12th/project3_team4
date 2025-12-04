use cyworld;

-- 1. BGM Table
CREATE TABLE bgm (
    bgm_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(50),
    artist VARCHAR(50),
    youtube_url VARCHAR(255),
    thumbnail_url VARCHAR(255),
    minihome_minihome_id INT NOT NULL,
    PRIMARY KEY (bgm_id),
    UNIQUE (minihome_minihome_id)
);

-- 2. Guestbook Table
CREATE TABLE guestbook (
    guestbook_id INT NOT NULL AUTO_INCREMENT,
    guestbook_content TEXT,
    written_at DATETIME,
    guestbook_minihome_id INT NOT NULL,
    guestbook_reply_id INT,
    guestbook_writer_id INT NOT NULL,
    PRIMARY KEY (guestbook_id),
    UNIQUE (guestbook_reply_id)
);

-- 3. Ilchon Relation Table
CREATE TABLE ilchon_relation (
    ilchon_relation_id INT NOT NULL AUTO_INCREMENT,
    requested_at DATETIME,
    accepted_at DATETIME,
    relation_status VARCHAR(10),
    requester_id INT NOT NULL,
    accepter_id INT NOT NULL,
    PRIMARY KEY (ilchon_relation_id)
);

-- 4. Minihome Table
CREATE TABLE minihome (
    minihome_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    background_color_id VARCHAR(7),
    applied_theme_id INT,
    minihome_owner_id INT NOT NULL,
    theme_theme_id INT NOT NULL,
    PRIMARY KEY (minihome_id),
    UNIQUE (minihome_owner_id),
    UNIQUE (theme_theme_id)
);

-- 5. Minihome-Minime Relation
CREATE TABLE minihome_minime (
    minihome_minihome_id INT NOT NULL,
    minime_minime_id INT NOT NULL,
    PRIMARY KEY (minihome_minihome_id, minime_minime_id),
    UNIQUE (minihome_minihome_id),
    UNIQUE (minime_minime_id)
);

-- 6. Minime Table
CREATE TABLE minime (
    minime_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    image_path VARCHAR(255),
    PRIMARY KEY (minime_id)
);

-- 7. Photo Table
CREATE TABLE photo (
    photo_id INT NOT NULL AUTO_INCREMENT,
    original_filename VARCHAR(50),
    storage_path VARCHAR(255),
    title VARCHAR(50),
    description TEXT,
    photo_album_album_id INT NOT NULL,
    PRIMARY KEY (photo_id)
);

-- 8. Photo Album Table
CREATE TABLE photo_album (
    album_id INT NOT NULL AUTO_INCREMENT,
    album_name VARCHAR(50),
    created_at DATETIME,
    album_minihome_id INT NOT NULL,
    PRIMARY KEY (album_id),
    UNIQUE (album_minihome_id)
);

-- 9. Photo Comment Table
CREATE TABLE photo_comment (
    photo_comment_id INT NOT NULL AUTO_INCREMENT,
    comment_content TEXT,
    written_at DATETIME,
    photo_photo_id INT NOT NULL,
    photo_comment_writer_id INT NOT NULL,
    PRIMARY KEY (photo_comment_id)
);

-- 10. Theme Table
CREATE TABLE theme (
    theme_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    category VARCHAR(10),
    style_info TEXT,
    minihome_minihome_id INT NOT NULL,
    PRIMARY KEY (theme_id),
    UNIQUE (minihome_minihome_id)
);

-- 11. User Table
CREATE TABLE `user` (
    user_id INT NOT NULL AUTO_INCREMENT,
    login_id VARCHAR(20),
    login_pw VARCHAR(255),
    nickname VARCHAR(50),
    profile_photo_path VARCHAR(255),
    status_message VARCHAR(255),
    birth_date DATE,
    region VARCHAR(255),
    hobby VARCHAR(255),
    gender VARCHAR(50),
    today_mood VARCHAR(50),
    PRIMARY KEY (user_id)
);

-- 12. Visit Log Table
CREATE TABLE visit_log (
    visit_log_id INT NOT NULL AUTO_INCREMENT,
    visited_at DATETIME,
    visitor_ip VARCHAR(255),
    minihome_minihome_id INT NOT NULL,
    user_user_id INT NOT NULL,
    PRIMARY KEY (visit_log_id)
);

-- 13. Visit Stats Table
CREATE TABLE visit_stats (
    visit_stats_id INT NOT NULL AUTO_INCREMENT,
    target_minihome_id INT NOT NULL,
    visited_at DATETIME,
    daily_visitors INT,
    visit_log_visit_log_id INT NOT NULL,
    visit_log_minihome_minihome_id INT NOT NULL,
    PRIMARY KEY (visit_stats_id)
);

-- 🔗 Foreign Keys
ALTER TABLE guestbook 
    ADD CONSTRAINT fk_guestbook_reply FOREIGN KEY (guestbook_reply_id) REFERENCES guestbook (guestbook_id);

ALTER TABLE guestbook 
    ADD CONSTRAINT fk_guestbook_minihome FOREIGN KEY (guestbook_minihome_id) REFERENCES minihome (minihome_id);

ALTER TABLE bgm 
    ADD CONSTRAINT fk_bgm_minihome FOREIGN KEY (minihome_minihome_id) REFERENCES minihome (minihome_id);

ALTER TABLE photo_album 
    ADD CONSTRAINT fk_album_minihome FOREIGN KEY (album_minihome_id) REFERENCES minihome (minihome_id);

ALTER TABLE visit_log 
    ADD CONSTRAINT fk_visitlog_minihome FOREIGN KEY (minihome_minihome_id) REFERENCES minihome (minihome_id);

ALTER TABLE minihome_minime 
    ADD CONSTRAINT fk_minime_minihome FOREIGN KEY (minihome_minihome_id) REFERENCES minihome (minihome_id);

ALTER TABLE minihome_minime 
    ADD CONSTRAINT fk_minime FOREIGN KEY (minime_minime_id) REFERENCES minime (minime_id);

ALTER TABLE photo 
    ADD CONSTRAINT fk_photo_album FOREIGN KEY (photo_album_album_id) REFERENCES photo_album (album_id);

ALTER TABLE photo_comment 
    ADD CONSTRAINT fk_photo FOREIGN KEY (photo_photo_id) REFERENCES photo (photo_id);

ALTER TABLE minihome 
    ADD CONSTRAINT fk_user_minihome FOREIGN KEY (minihome_owner_id) REFERENCES `user` (user_id);

-- (기존에 있던 인덱스 삭제 구문 유지)
ALTER TABLE minihome DROP INDEX theme_theme_id;

ALTER TABLE visit_log 
    ADD CONSTRAINT fk_user_visitlog FOREIGN KEY (user_user_id) REFERENCES `user` (user_id);

ALTER TABLE guestbook 
    ADD CONSTRAINT fk_user_guestbook FOREIGN KEY (guestbook_writer_id) REFERENCES `user` (user_id);

ALTER TABLE photo_comment 
    ADD CONSTRAINT fk_user_comment FOREIGN KEY (photo_comment_writer_id) REFERENCES `user` (user_id);

ALTER TABLE ilchon_relation 
    ADD CONSTRAINT fk_user_requester FOREIGN KEY (requester_id) REFERENCES `user` (user_id);

ALTER TABLE ilchon_relation 
    ADD CONSTRAINT fk_user_accepter FOREIGN KEY (accepter_id) REFERENCES `user` (user_id);

ALTER TABLE visit_stats 
    ADD CONSTRAINT fk_visitlog_stats FOREIGN KEY (visit_log_visit_log_id) REFERENCES visit_log (visit_log_id);

-- 일촌 신청 중복 방지 (Requester + Accepter 복합 유니크 제약조건)
ALTER TABLE ilchon_relation
ADD CONSTRAINT unique_ilchon_pair UNIQUE (requester_id, accepter_id);

drop procedure removeUserDetails;
drop procedure deleteMessages;
drop table bi_hs_reward_user;
drop table bi_tr_user_feedback;
drop table bi_lk_event_profile;
drop table bi_lk_event_user;
drop table bi_tr_event;
drop table bi_hs_message_user;
drop table bi_lk_message_user;
drop table bi_lk_message_profile;
drop table bi_tr_message_content;
drop table bi_tr_message;
drop table bi_lk_profile_device;
drop table bi_lk_user_profile;
drop table bi_tr_user_current_location;
drop table bi_ma_user_location_challenge;
drop table bi_ma_device;
drop table bi_ma_profile;
drop table bi_tr_user_device;
drop table bi_ma_user;

CREATE TABLE bi_ma_user (
    code varchar(15) NOT NULL,
    name varchar(50) DEFAULT NULL,
    image_url varchar(300) DEFAULT NULL,
    date_of_birth date DEFAULT NULL,
    password varchar(20) NOT NULL,
    manager varchar(15) NULL,
    internal varchar(1) DEFAULT 'N',
    PRIMARY KEY (code)
);

CREATE TABLE bi_ma_profile (
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(100) DEFAULT NULL,
	owner varchar(15) NOT NULL,
	KEY fk_owner_profile ( owner ),
	CONSTRAINT fk_owner_profile FOREIGN KEY (owner) REFERENCES bi_ma_user (code),
    PRIMARY KEY (id)
);

CREATE TABLE bi_ma_device (
    id int(11) NOT NULL,
    locationmap varchar(100) DEFAULT NULL,
    location varchar(100) DEFAULT NULL,
	owner varchar(15) NOT NULL,
	KEY fk_owner_device ( owner ),
	CONSTRAINT fk_owner_device FOREIGN KEY (owner) REFERENCES bi_ma_user (code),
    PRIMARY KEY (id)
);

CREATE TABLE bi_ma_user_location_challenge(
	id int(11) NOT NULL AUTO_INCREMENT,
	device_id int(11) NOT NULL,
	challenge_code varchar(50) NOT NULL,
	start_time datetime NULL,
	end_time datetime NULL,
	PRIMARY KEY(id),
	KEY fk_device_id_challenge( device_id),
	CONSTRAINT fk_device_id_challenge FOREIGN KEY (device_id) REFERENCES bi_ma_device(id)
);

CREATE TABLE bi_tr_user_device (
    user_code varchar(15) NOT NULL,
    user_device_id varchar(50) NOT NULL,
    active boolean NOT NULL,
    KEY fk_usr_device_code (user_code),
    CONSTRAINT fk_usr_device_code FOREIGN KEY (user_code)
        REFERENCES bi_ma_user (code)
);

CREATE TABLE bi_tr_user_current_location(
	id int(11) NOT NULL AUTO_INCREMENT,
	user_code varchar(15) NOT NULL,
	device_id int(11) NOT NULL,
	checkin datetime NULL,
	checkout datetime NULL,
	PRIMARY KEY(id),
	KEY fk_user_code_location ( user_code ),
	KEY fk_device_id_location( device_id),
	CONSTRAINT fk_user_code_location FOREIGN KEY (user_code) REFERENCES bi_ma_user (code),
	CONSTRAINT fk_device_id_location FOREIGN KEY (device_id) REFERENCES bi_ma_device(id)
);

CREATE TABLE bi_tr_message (
    id int(11) NOT NULL AUTO_INCREMENT,
    interval_desc varchar(100) DEFAULT NULL,
    created varchar(15) DEFAULT NULL,
    start_date datetime DEFAULT NULL,
    end_date datetime DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_msg_created (created),
    CONSTRAINT fk_msg_created FOREIGN KEY (created)
        REFERENCES bi_ma_user (code)
);

CREATE TABLE bi_tr_message_content (
    id int(11) NOT NULL AUTO_INCREMENT,
    message_id int(11) NOT NULL,
    content varchar(2000) DEFAULT NULL,
    image_url varchar(300) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_msg_content_message_id (message_id),
    CONSTRAINT fk_msg_content_message_id FOREIGN KEY (message_id)
        REFERENCES bi_tr_message (id)
);

CREATE TABLE bi_lk_user_profile (
    id int(11) NOT NULL AUTO_INCREMENT,
    user_code varchar(15) DEFAULT NULL,
    profile_id int(11) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_up_u_id (user_code),
    KEY fk_up_p_id (profile_id),
    CONSTRAINT fk_up_p_id FOREIGN KEY (profile_id)
        REFERENCES bi_ma_profile (id),
    CONSTRAINT fk_up_u_id FOREIGN KEY (user_code)
        REFERENCES bi_ma_user (code)
);

CREATE TABLE bi_lk_profile_device (
    id int(11) NOT NULL AUTO_INCREMENT,
    profile_id int(11) DEFAULT NULL,
    device_id int(11) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_pd_p_id (profile_id),
    KEY fk_pd_d_id (device_id),
    CONSTRAINT fk_pd_d_id FOREIGN KEY (device_id)
        REFERENCES bi_ma_device (id),
    CONSTRAINT fk_pd_p_id FOREIGN KEY (profile_id)
        REFERENCES bi_ma_profile (id)
);

CREATE TABLE bi_lk_message_profile (
    id int(11) NOT NULL AUTO_INCREMENT,
    message_id int(11) DEFAULT NULL,
    profile_id int(11) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_mp_msg_id (message_id),
    KEY fk_mp_p_id (profile_id),
    CONSTRAINT fk_mp_msg_id FOREIGN KEY (message_id)
        REFERENCES bi_tr_message (id),
    CONSTRAINT fk_mp_p_id FOREIGN KEY (profile_id)
        REFERENCES bi_ma_profile (id)
);

CREATE TABLE bi_lk_message_user (
    id int(11) NOT NULL AUTO_INCREMENT,
    message_id int(11) DEFAULT NULL,
    user_code varchar(15) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_mu_msg_id (message_id),
    KEY fk_mu_u_id (user_code),
    CONSTRAINT fk_mu_msg_id FOREIGN KEY (message_id)
        REFERENCES bi_tr_message (id),
    CONSTRAINT fk_mu_u_id FOREIGN KEY (user_code)
        REFERENCES bi_ma_User (code)
);

CREATE TABLE bi_hs_reward_user (
    id int(11) NOT NULL AUTO_INCREMENT,
    reward_enc varchar(500) DEFAULT NULL,
    user_code varchar(15) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_mu_hs_u_id (user_code),
    CONSTRAINT fk_re_hs_u_id FOREIGN KEY (user_code)
        REFERENCES bi_ma_user (code)
);

CREATE TABLE bi_hs_message_user (
    id int(11) NOT NULL AUTO_INCREMENT,
    message_id int(11) DEFAULT NULL,
    user_code varchar(15) DEFAULT NULL,
    date datetime DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_mu_hs_u_id (user_code),
    KEY fk_mu_hs_m_id (message_id),
    CONSTRAINT fk_mu_hs_u_id FOREIGN KEY (user_code)
        REFERENCES bi_ma_user (code),
    CONSTRAINT fk_mu_hs_m_id FOREIGN KEY (message_id)
        REFERENCES bi_tr_message (id)
);

CREATE TABLE bi_tr_user_feedback(
	id int(11) NOT NULL AUTO_INCREMENT,
	user_code varchar(15) NOT NULL,
	device_id int(11) NOT NULL,
	question varchar(2000) NOT NULL,
	answer varchar(2000) NOT NULL,
	insertion_date datetime DEFAULT NULL,
	PRIMARY KEY(id),
	KEY fk_user_feedback(user_code),
	KEY fk_device_code_feedback (device_id),
	CONSTRAINT fk_user_feedback FOREIGN KEY (user_code)
        REFERENCES bi_ma_user(code),
    CONSTRAINT fk_device_code_feedback FOREIGN KEY (device_id)
        REFERENCES bi_ma_device(id)
);

CREATE TABLE bi_tr_event(
	id int(11) NOT NULL AUTO_INCREMENT,
	title varchar(200) NOT NULL,
	created varchar(15) NOT NULL,
	description varchar(1000),
	location varchar(50),
	start_date datetime DEFAULT NULL,
    end_date datetime DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_eve_created (created),
    CONSTRAINT fk_eve_created FOREIGN KEY (created)
        REFERENCES bi_ma_user (code)
);

CREATE TABLE bi_lk_event_profile (
    id int(11) NOT NULL AUTO_INCREMENT,
    event_id int(11) DEFAULT NULL,
    profile_id int(11) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_ep_event_id (event_id),
    KEY fk_ep_p_id (profile_id),
    CONSTRAINT fk_ep_eve_id FOREIGN KEY (event_id)
        REFERENCES bi_tr_event (id),
    CONSTRAINT fk_ep_p_id FOREIGN KEY (profile_id)
        REFERENCES bi_ma_profile (id)
);

CREATE TABLE bi_lk_event_user (
    id int(11) NOT NULL AUTO_INCREMENT,
    event_id int(11) DEFAULT NULL,
    user_code varchar(15) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_eu_eve_id (event_id),
    KEY fk_eu_u_id (user_code),
    CONSTRAINT fk_eu_eve_id FOREIGN KEY (event_id)
        REFERENCES bi_tr_event (id),
    CONSTRAINT fk_eu_u_id FOREIGN KEY (user_code)
        REFERENCES bi_ma_User (code)
);
	
CREATE TABLE bi_tr_daily_log(
	id int(11) NOT NULL AUTO_INCREMENT,
	user_code varchar(15) NOT NULL,
	first_in datetime NOT NULL,
    last_out datetime DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_daily_log_user (user_code),
    CONSTRAINT fk_daily_log_user FOREIGN KEY (user_code)
        REFERENCES bi_ma_user (code)
);

DELIMITER $$
CREATE PROCEDURE `removeUserDetails`(IN userCode varchar(200))
BEGIN
	delete from bi_hs_message_user where user_code = userCode;
	delete from bi_hs_reward_user where user_code = userCode;
	delete from bi_lk_message_user where user_code = userCode; 
	update bi_tr_message set created = 'ADMIN' where created = usercode;
	delete from bi_tr_user_current_location where user_code = userCode;
	delete from bi_tr_user_feedback where user_code = userCode;
	delete from bi_tr_user_device where user_code = userCode;
	delete from bi_lk_user_profile where user_code = userCode;
	update bi_ma_profile set owner = 'ADMIN' where owner = userCode;
	update bi_ma_device set owner = 'ADMIN' where owner = userCode;
	delete from bi_ma_user where code = userCode;
END $$

CREATE PROCEDURE `deleteMessages`( IN userCode varchar(200), IN messageId varchar(20))
BEGIN
	delete from bi_lk_message_user where message_id = messageId and message_id in ( select id from bi_tr_message where created = usercode);
	delete from bi_lk_message_profile where message_id = messageId and message_id in ( select id from bi_tr_message where created = usercode);
	delete from bi_hs_message_user where message_id = messageId and message_id in ( select id from bi_tr_message where created = usercode);
	delete from bi_tr_message_content where message_id = messageId and message_id in ( select id from bi_tr_message where created = usercode);
	delete from bi_tr_message where created = usercode and id = messageId;
END $$
DELIMITER ;
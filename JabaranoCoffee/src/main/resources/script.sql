DROP DATABASE IF EXISTS jabarano_coffee;
CREATE DATABASE jabarano_coffee
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE jabarano_coffee;

CREATE TABLE users (
    id         INT           NOT NULL AUTO_INCREMENT,
    username   VARCHAR(50)   NOT NULL,
    password   VARCHAR(255)  NOT NULL,
    created_at DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_users     PRIMARY KEY (id),
    CONSTRAINT uq_username  UNIQUE (username)
);

CREATE TABLE cafe_tables (
    id           INT          NOT NULL AUTO_INCREMENT,
    table_number VARCHAR(10)  NOT NULL,
    capacity     INT          NOT NULL,
    status       VARCHAR(20)  NOT NULL DEFAULT 'AVAILABLE',
    CONSTRAINT pk_cafe_tables    PRIMARY KEY (id),
    CONSTRAINT uq_table_number   UNIQUE (table_number),
    CONSTRAINT chk_capacity      CHECK (capacity > 0),
    CONSTRAINT chk_table_status  CHECK (status IN ('AVAILABLE', 'RESERVED'))
);

CREATE TABLE queue_entries (
    id           INT          NOT NULL AUTO_INCREMENT,
    queue_number VARCHAR(10)  NOT NULL,
    access_code  VARCHAR(20)  NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    party_size   INT          NOT NULL,
    table_id     INT          NULL,
    status       VARCHAR(20)  NOT NULL DEFAULT 'WAITING',
    joined_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    called_at    DATETIME     NULL,
    queue_date   DATE         NOT NULL,
    CONSTRAINT pk_queue_entries   PRIMARY KEY (id),
    CONSTRAINT uq_access_code     UNIQUE (access_code),
    CONSTRAINT uq_queue_per_date  UNIQUE (queue_number, queue_date),
    CONSTRAINT fk_table           FOREIGN KEY (table_id)
                                      REFERENCES cafe_tables(id)
                                      ON UPDATE CASCADE
                                      ON DELETE SET NULL,
    CONSTRAINT chk_party_size     CHECK (party_size > 0),
    CONSTRAINT chk_queue_status   CHECK (status IN ('WAITING','CALLED','FINISHED','CANCELLED'))
);

CREATE INDEX idx_queue_status_date
    ON queue_entries (status, queue_date);

CREATE TABLE queue_status_history (
    id         INT         NOT NULL AUTO_INCREMENT,
    queue_id   INT         NOT NULL,
    old_status VARCHAR(20) NULL,
    new_status VARCHAR(20) NOT NULL,
    changed_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_history    PRIMARY KEY (id),
    CONSTRAINT fk_queue_hist FOREIGN KEY (queue_id)
                                 REFERENCES queue_entries(id)
                                 ON UPDATE CASCADE
                                 ON DELETE CASCADE
);

-- View: v_queue_display (untuk Public Display)
CREATE VIEW v_queue_display AS
SELECT
    qe.id,
    qe.queue_number,
    qe.customer_name,
    qe.party_size,
    qe.status,
    qe.called_at,
    ct.table_number
FROM queue_entries qe
LEFT JOIN cafe_tables ct ON qe.table_id = ct.id
WHERE qe.status = 'CALLED'
ORDER BY qe.called_at DESC;

-- Trigger: trg_queue_status_history
-- Mencatat perubahan status queue_entries secara otomatis
DELIMITER $$

CREATE TRIGGER trg_queue_status_history
AFTER UPDATE ON queue_entries
FOR EACH ROW
BEGIN
    IF OLD.status <> NEW.status THEN
        INSERT INTO queue_status_history (queue_id, old_status, new_status, changed_at)
        VALUES (NEW.id, OLD.status, NEW.status, NOW());
    END IF;
END$$

-- Stored Procedure: sp_call_queue
-- Memanggil antrean: ubah status -> CALLED, alokasi meja, isi called_at
CREATE PROCEDURE sp_call_queue(
    IN  p_queue_id  INT,
    IN  p_table_id  INT,
    OUT p_result    VARCHAR(50)
)
BEGIN
    DECLARE v_current_status VARCHAR(20);
    DECLARE v_table_status   VARCHAR(20);
    DECLARE v_table_capacity INT;
    DECLARE v_party_size     INT;

    -- Ambil data antrean
    SELECT status, party_size
    INTO v_current_status, v_party_size
    FROM queue_entries
    WHERE id = p_queue_id;

    -- Validasi status antrean
    IF v_current_status <> 'WAITING' THEN
        SET p_result = 'ERROR: Antrean bukan berstatus WAITING';
    ELSE
        -- Ambil data meja
        SELECT status, capacity
        INTO v_table_status, v_table_capacity
        FROM cafe_tables
        WHERE id = p_table_id;

        -- Validasi ketersediaan meja
        IF v_table_status <> 'AVAILABLE' THEN
            SET p_result = 'ERROR: Meja tidak tersedia';
        ELSEIF v_table_capacity < v_party_size THEN
            SET p_result = 'ERROR: Kapasitas meja tidak cukup';
        ELSE
            -- Update status antrean
            UPDATE queue_entries
            SET status    = 'CALLED',
                table_id  = p_table_id,
                called_at = NOW()
            WHERE id = p_queue_id;

            -- Update status meja
            UPDATE cafe_tables
            SET status = 'RESERVED'
            WHERE id = p_table_id;

            SET p_result = 'SUCCESS';
        END IF;
    END IF;
END$$

-- Stored Procedure: sp_finish_queue
-- Menyelesaikan antrean dan mengembalikan status meja
CREATE PROCEDURE sp_finish_queue(
    IN  p_queue_id INT,
    OUT p_result   VARCHAR(50)
)
BEGIN
    DECLARE v_current_status VARCHAR(20);
    DECLARE v_table_id       INT;

    -- Ambil data antrean
    SELECT status, table_id
    INTO v_current_status, v_table_id
    FROM queue_entries
    WHERE id = p_queue_id;

    -- Validasi status antrean
    IF v_current_status <> 'CALLED' THEN
        SET p_result = 'ERROR: Antrean bukan berstatus CALLED';
    ELSE
        -- Update status antrean
        UPDATE queue_entries
        SET status = 'FINISHED'
        WHERE id = p_queue_id;

        -- Kembalikan status meja jika ada
        IF v_table_id IS NOT NULL THEN
            UPDATE cafe_tables
            SET status = 'AVAILABLE'
            WHERE id = v_table_id;
        END IF;

        SET p_result = 'SUCCESS';
    END IF;
END$$

DELIMITER ;
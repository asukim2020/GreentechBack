create database back character set utf8 collate UTF8_GENERAL_CI;

-- 테이블 레코드 수, 용량 변경
alter table measure_item max_rows=1000000000 avg_row_length=1073741824000;
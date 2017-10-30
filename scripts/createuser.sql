define data_tablespace_postfix="_data"
define data_file_postfix="_data.dbf"
define tmp_tablespace_postfix="_temp"
define tmp_file_postfix="_temp.dbf"
create tablespace &2&data_tablespace_postfix logging datafile '&3/&2&data_file_postfix' size 20480m;
create temporary tablespace &2&tmp_tablespace_postfix tempfile '&3/&2&tmp_file_postfix' size 4096m;
create user &2 identified by &4 default tablespace &2&data_tablespace_postfix  temporary tablespace &2&tmp_tablespace_postfix;
grant create session ,create any table ,create any view ,create any index,create any procedure ,create any trigger,create any sequence ,alter any table ,alter any procedure,alter any trigger,alter any sequence,select any table ,drop any table ,drop any view ,drop any trigger ,drop any sequence,drop any index,drop any procedure ,insert any table, update any table ,delete any table, unlimited tablespace  to &2;

--&2 数据库用户
--&3 表空间路径
--&4 用户密码
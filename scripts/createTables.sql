-- Create table
create table TRADEDETAIL
(
  tradecode NUMBER,
  deatil    VARCHAR2(100)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
INSERT INTO TRADEDETAIL(TRADECODE,DEATIL) VALUES ('1','哈哈，恭喜你，连通了');
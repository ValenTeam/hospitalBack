# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table hospital (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  address                   varchar(255),
  constraint pk_hospital primary key (id))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists hospital;

SET REFERENTIAL_INTEGRITY TRUE;


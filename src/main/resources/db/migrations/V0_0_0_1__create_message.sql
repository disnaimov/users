create table if not exists tr_message
(
    id uuid not null,
    description text,
    assignee_id uuid not null,
    reporter_id uuid not null,
    create_date bigint not null,
    delete_date bigint not null,
    status text,
    UNIQUE (id),
    PRIMARY KEY (id),
    FOREIGN KEY (assignee_id) references users(id),
    FOREIGN KEY (reporter_id) references users(id)
/*

 */


);

create index message_assignee__i
    on public.tr_message (assignee_id);

create index message_reporter__i
    on public.tr_message (reporter_id);
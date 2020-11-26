alter table chatbot_message
	add chatbot_revision float4 default 0.5;

alter table chatbot_message drop column user_id;

alter table chatbot_message
	add is_in_error bool default false;

alter table chatbot_message alter column user_id set default 0;

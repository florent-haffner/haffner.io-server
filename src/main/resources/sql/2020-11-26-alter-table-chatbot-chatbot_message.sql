alter table chatbot_message
	add chatbot_revision float4 default 0.5;

alter table public.chatbot_message
	add is_in_error bool default false;

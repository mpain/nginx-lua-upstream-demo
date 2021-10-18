
local cpg = {}

cpg.ALI_VTERMS = {
	AERU = true,
	AERU_CARD = true
}

function cpg.get_upstream(search_string, ali_upstream, default_upstream) 
	local id_matched = (ngx.re.match(search_string, [[(?:^|&)(?:(?:transaction_)?id=1[23]\d{18}|order_id=2[23]\d{18}|card_id=[35][23]\d{18})(?:$|&)]]))

   	ngx.log(ngx.NOTICE, "Incoming: ", search_string)
    if id_matched then
    	ngx.log(ngx.NOTICE, "Detected a matched id")
		return ali_upstream
    else
    	ngx.log(ngx.NOTICE, "Detecting a vterm")
		local vterm = (ngx.re.match(search_string, [[vterm_id=([^&$]+)]]) or {})[1]
		if vterm and cpg.ALI_VTERMS[vterm] then
	    	ngx.log(ngx.NOTICE, "Detected a vterm")
			return ali_upstream
		end
	end

	return default_upstream
end

return cpg
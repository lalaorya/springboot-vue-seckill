if redis.call('get',KEYS[1] )> 0 then
	return redis.call('decr',KEYS[1])
else
	return 0
end
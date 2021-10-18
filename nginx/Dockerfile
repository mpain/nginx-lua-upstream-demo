FROM alpine:3.6

RUN apk add --no-cache nginx-mod-http-lua

# Delete default config
RUN rm -r /etc/nginx/conf.d && rm /etc/nginx/nginx.conf

# Create folder for PID file
RUN mkdir -p /run/nginx

# Create folder for LUA files
RUN mkdir -p /opt/lua

# Add our nginx conf
COPY ./lua /opt/lua

# Add our nginx conf
COPY ./nginx.conf /etc/nginx/nginx.conf

CMD ["nginx"]

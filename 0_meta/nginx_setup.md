## Setup Nginx
- `sudo apt-get update && sudo apt-get install -y nginx`
- ensure that firewall alows nginx
    - `sudo ufw allow 'Nginx HTTP'`
    - check the status with `sudo ufw status`
- ensure that `/etc/nginx/sites-available` and `/etc/nginx/sites-enabled` exist.
    - if they do not exist:
        - make the directories: `sudo mkdir /etc/nginx/sites-available && sudo mkdir /etc/nginx/sites-enabled`
        - update nginx config to pull from sites-enabled: edit the http block inside `/etc/nginx/nginx.conf` and add the line `include /etc/nginx/sites-enabled/*;`
        - create the `default` file with the content located in [this gist](https://gist.github.com/uladkasach/9279e9b0d4a1818cdf368664a399db4f). 
            - `sudo nano /etc/nginx/sites-available/default`
        - create sym-link for default into sites-enabled
            - `sudo ln -s /etc/nginx/sites-available/default /etc/nginx/sites-enabled/default`
- ensure that `/snippets/fastcgi-php.conf` exists
    - if it does not exist
        - `sudo mkdir /etc/nginx/snippets`
        - `sudo nano /etc/nginx/snippets/fastcgi-php.conf` 
        ```
        # regex to split $uri to $fastcgi_script_name and $fastcgi_path
        fastcgi_split_path_info ^(.+\.php)(/.+)$;

        # Check that the PHP script exists before passing it
        try_files $fastcgi_script_name =404;

        # Bypass the fact that try_files resets $fastcgi_path_info
        # see: http://trac.nginx.org/nginx/ticket/321
        set $path_info $fastcgi_path_info;
        fastcgi_param PATH_INFO $path_info;

        fastcgi_index index.php;
        include fastcgi_params;
        ```

- setup `/etc/nginx/sites-available/personalcancertoolkit` serverblock 
  - `sudo nano /etc/nginx/sites-available/personalcancertoolkit`
    ```
    server {
        listen 80 default_server;
        server_name personalcancertoolkit.org;
        index index.html index.htm;
        return 301 https://$host$request_uri;
    }
    server {
        listen 443 ssl;
        server_name personalcancertoolkit.org;
        index index.html index.htm;
        access_log /var/log/nginx/demo_access.log;
        error_log /var/log/nginx/demo_error.log;
        ssl_certificate /etc/letsencrypt/live/personalcancertoolkit.org/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/personalcancertoolkit.org/privkey.pem;
        location / {
          return 301 https://personalcancertoolkit.org/openmrs;
        }
        location /openmrs {
          proxy_pass http://127.0.0.1:8080/openmrs;
        }
    }
    ```
- Add this available site to `sites-enabled` with sym-link
    - `sudo ln -s /etc/nginx/sites-available/personalcancertoolkit /etc/nginx/sites-enabled/personalcancertoolkit`
- `sudo service nginx restart`

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
           listen 80;
           listen [::]:80;

           server_name personalcancertoolkit.org; 

           root /var/www/personalcancertoolkit; ## Modify path if required
           index index.html;

           ## ADD SSL CONFIG HERE

           location / {
                   try_files $uri $uri/ =404;
           }
    }
    ```
- Add this available site to `sites-enabled` with sym-link
    - `sudo ln -s /etc/nginx/sites-available/personalcancertoolkit /etc/nginx/sites-enabled/personalcancertoolkit`
- `sudo service nginx restart`

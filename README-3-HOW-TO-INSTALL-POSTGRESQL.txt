1. Install PortgeSQL server on droplet
    sudo apt update
    sudo apt install postgresql postgresql-contrib

2. Allow to connect from public network:
    2.1. update sudo nano /etc/postgresql/10/main/pg_hba.conf with
        host all all 0.0.0.0/0 md5
    2.2. update listen_addresses='*' into postgresql.conf file
    2.3. restart postgres
        sudo service postgresql restart
    2.4. connect to DB
        sudo -u postgres psql

3. Create a new postgres user: smartbot
    createuser --interactive

4. change password for it and set grants
    alter user smartbot with encrypted password '...';
    grant all privileges on database smartbot to smartbot;

5. Create a new DB for application
    createdb smartbot

6. Create a new Linux user for DB
    sudo adduser smartbot

7. Restore backup

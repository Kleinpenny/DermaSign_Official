<div style="text-align: center;">
  <img src="dermasign_logo.png" alt="DermaSign Logo" />
</div>




# Documentation

## HOW TO START 
```bash
git clone https://github.com/Kleinpenny/DermaSign_Official.git

# Package the Project in the Compiler
# Navigate to the directory with pom.xml and run (recommand using IntellJi IDEA):
mvn clean package

# Run the Packaged Jar File Uploaded to the Server

java -jar Dermasign-Project-0.0.1-SNAPSHOT.jar

After running successfully, access localhost:8000.

```


## Deploying the Project on Linux (Step-by-Step)
### 1.update linux src:
```commandline
sudo apt update
```
### 2.Install java:
```bash
sudo apt install openjdk-19-jdk
```
You can also install jdk with version >= 19

### 3.Install mysql
```bash
apt-get install mysql-server
apt-get install mysql-client
apt-get install libmysqlclient-dev
```
### 4.Log into mysql:
```bash
mysql -u root -p
```
When using MySQL for the first time, no password is set, so press Enter directly.

### 5.Configure mysql
```mysql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'PASSWORD';
FLUSH PRIVILEGES;
```
Note: Replace the PASSWORD field with the specified password. 
(The initial root password must match the one defined in the following field.)
```
spring.datasource.password = PASSWORD
```
The above field is located at:  `src/main/resources/application.properties`


<details>
  <summary>OPTIONAL (if you encounter login issues)</summary>

- If more users are needed, you can create an admin account and set a password for it:

````mysql
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'PASSWORD';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
````

- If you forget the password for the root or admin user, you can reset it as follows:
    - First, stop the MySQL service:
    ```bash
    sudo systemctl stop mysql
    ```
    - Start MySQL in skip-grant-tables mode:
      ```bash
      sudo mysqld_safe --skip-grant-tables &
      ```
    - Log into MySQL without a password:
     ```bash
     mysql -u root
     ```
    - Once logged in, reset the password for the admin or root user:
     ```mysql
     ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';
     FLUSH PRIVILEGES;
     ```
    - Finally, restart the MySQL service:
     ```bash
     sudo systemctl start mysql
     ```
</details>

### 6.Create database
```mysql
Create database dermasign;
```

### 7.Set Environment Variables for the Current User
- Edit the ~/.bashrc file:
```bash
nano ~/.bashrc
```
- Add environment variables at the end of the file:
```bash
export PROJECT_PASSWORD=PASSWORD
```
- Save and exit the editor, then apply the changes immediately:
```bash
source ~/.bashrc
```

### 8.Package the Project in the Compiler
Navigate to the directory with pom.xml and run (recommand using IntellJi IDEA):
```bash
mvn clean package
```

### 9.Run the Packaged Jar File Uploaded to the Server
```bash
java -jar Dermasign-Project-0.0.1-SNAPSHOT.jar
```
After running successfully, access localhost:8000.


## Loading a Database on Linux (If You Have Additional Data in Another Environment)
### Export Data from MySQL to a JSON File
1. Right-click the table to export (e.g., products), select Table Data Export Wizard, and export the data as a JSON file.
2. Convert the exported file to UTF-8 encoding (to avoid untranslated German and Chinese characters in the data).
<details>
  <summary>(Python Code for Conversion)</summary>

```python
import json

with open('products.json', 'r', encoding='utf-8') as file:
json_data = json.load(file)

output_file = 'products_utf8.json'

with open(output_file, 'w', encoding='utf-8') as file:
json.dump(json_data, file, ensure_ascii=False, indent=4)

```
</details>

### Configure the Python Environment on the Server
(Alternatively, use Anaconda. Below is a system-wide Python configuration method.)
```bash
sudo apt install python3
sudo apt install python3-pip

# Set up Python aliases
nano ~/.bashrc
alias python='python3'
alias pip='pip3'
source ~/.bashrc

# Create a virtual environment (venv)
python -m venv myenv
source myenv/bin/activate
pip install mysql-connector-python

# Exit the virtual environment
deactivate
```
### Execute the Code on the Server to Import Data into MySQL

<details>
  <summary>Python Code</summary>

```python
# If the database structure has not been modified, otherwise adjust the code as needed.

import json
import mysql.connector

# Connect to the MySQL database
conn = mysql.connector.connect(
  host="localhost",
  user="root",  # Replace with your MySQL username
  password="Your_PWD",  # Replace with your MySQL password
  database="dermasign"
)
cursor = conn.cursor()

# Create the `products` table
cursor.execute("""
    CREATE TABLE IF NOT EXISTS products (
        id INT PRIMARY KEY,
        name VARCHAR(1000),
        description_en TEXT,
        description_de TEXT,
        volume VARCHAR(1000),
        benefit_en TEXT,
        benefit_de TEXT,
        usage_en TEXT,
        usage_de TEXT,
        ingredients_en TEXT,
        ingredients_de TEXT,
        pic_name TEXT,
        nursing_stage VARCHAR(1000),
        product_type VARCHAR(1000),
        professional VARCHAR(20),
        skin_problem VARCHAR(1000),
        dosage VARCHAR(1000),
        suitable_en TEXT,
        suitable_de TEXT
    )
""")

# Open the JSON file and read the data
with open('products_utf8', 'r', encoding='utf-8') as file:
  data = json.load(file)

  # Insert the data into the database
  for item in data:
    sql = """
            INSERT INTO products 
            (id, name, description_en, description_de, volume, benefit_en, benefit_de, usage_en, usage_de, 
             ingredients_en, ingredients_de, pic_name, nursing_stage, product_type, professional, skin_problem, 
             dosage, suitable_en, suitable_de)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            """
    val = (
      item['id'],
      item.get('name'),
      item.get('description_en'),
      item.get('description_de'),
      item.get('volume'),
      item.get('benefit_en'),
      item.get('benefit_de'),
      item.get('usage_en'),
      item.get('usage_de'),
      item.get('ingredients_en'),
      item.get('ingredients_de'),
      item.get('pic_name'),
      item.get('nursing_stage'),
      item.get('product_type'),
      item.get('professional'),
      item.get('skin_problem'),
      item.get('dosage'),
      item.get('suitable_en'),
      item.get('suitable_de')
    )
    cursor.execute(sql, val)

# Commit the transaction
conn.commit()

# Close the connection
cursor.close()
conn.close()

print("Data inserted successfully!")
```
</details>


## Appendix

#### About Nginx Configuration

1. First, set up nginx:

```bash
sudo apt update
sudo apt install nginx -y
sudo systemctl enable --now nginx
```

2. Apply for HTTPS certificates for each domain

Use Certbot to automatically apply for free certificates:

```bash
sudo apt update
sudo apt install certbot python3-certbot-nginx
```

Apply for the certificates:
```bash
sudo certbot --nginx -d www.dermasign.com -d www.dermasign.de
```
Follow the prompts to complete the verification. Certbot will automatically update the Nginx configuration to enable HTTPS.


<details>
  <summary>Update Nginx conf file and activate</summary>

  Edit /etc/nginx/sites-available/default File, replace all the contents generated by Certbot to the contents below:

```commandline
# Content of the conf file (for reference)

# www.dermasign.com (English website)
server {
    listen 443 ssl;
    server_name www.dermasign.com;

    ssl_certificate /etc/letsencrypt/live/www.dermasign.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/www.dermasign.com/privkey.pem;
    include /etc/letsencrypt/options-ssl-nginx.conf;
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;

    location / {
        rewrite ^/$ /?localeData=en_US break;
        proxy_pass http://localhost:8000/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}

# www.dermasign.de (German website)
server {
    listen 443 ssl;
    server_name www.dermasign.de;

    ssl_certificate /etc/letsencrypt/live/www.dermasign.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/www.dermasign.com/privkey.pem;
    include /etc/letsencrypt/options-ssl-nginx.conf;
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;

    location / {
        proxy_pass http://localhost:8000/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}

# HTTP -> HTTPS redirection
server {
    listen 80;
    server_name www.dermasign.com www.dermasign.de;

    return 301 https://$host$request_uri;
}

```

```commandline
# Restart Nginx:
sudo nginx -t
sudo systemctl reload nginx

# Check service status:
curl -I https://www.dermasign.com
curl -I https://www.dermasign.de

```

</details>





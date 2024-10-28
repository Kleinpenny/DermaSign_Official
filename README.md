<div style="text-align: center;">
  <img src="dermasign_logo.png" alt="DermaSign Logo" />
</div>

# 说明文档

## Linux上部署项目
### 1.更新src:
```commandline
sudo apt update
```
### 2.安装java:
```bash
sudo apt install openjdk-19-jdk
```
可以安装19版本及以上

### 3.安装mysql
```bash
apt-get install mysql-server
apt-get install mysql-client
apt-get install libmysqlclient-dev
```
### 4.进入mysql:
```bash
mysql -u root -p
```
初次使用mysql，没有设置密码，因此直接回车即可

### 5.配置mysql
```mysql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'PASSWORD';
FLUSH PRIVILEGES;
```
注意,PASSWORD字段替换为给定的密码
(填写密码 root初始密码与下面字段定义的一致)
```
spring.datasource.password = PASSWORD
```
上述字段存在于路径: `src/main/resources/application.properties`


<details>
  <summary>OPTIONAL(或者遇到登陆问题)</summary>

- 若需要更多用户，可以创建例如: ‘admin’ 账户并为其设置密码：

````mysql
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'PASSWORD';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
````

- 如果你忘记了 `root` 或 `admin` 用户的密码，可以尝试以下步骤来重置密码：
  -  首先停止 MySQL 服务：
     ```bash
     sudo systemctl stop mysql
     ```
  - 然后以跳过权限表的模式启动 MySQL：
     ```bash
     sudo mysqld_safe --skip-grant-tables &
     ```
  - 再次登录 MySQL，此时不需要密码：
    ```bash
    mysql -u root
    ```
  - 登录成功后，重置 `admin` 或 `root` 用户的密码：
    ```mysql
    ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';
    FLUSH PRIVILEGES;
    ```
  - 最后，重启 MySQL 服务：
    ```bash
    sudo systemctl start mysql
    ```
</details>

### 6.创建database
```mysql
Create database dermasign;
```

### 7.为当前用户设置环境变量
- 编辑 ~/.bashrc 文件
```bash
nano ~/.bashrc
```
- 在文件的最后添加环境变量
```bash
export PROJECT_PASSWORD=PASSWORD
```
- 保存并退出编辑器后，使修改立即生效
```bash
source ~/.bashrc
```

### 7.在编译器段将项目打包
在项目有pom.xml路径下，执行 
```bash
mvn clean package
```

### 8.运行上传到server的打包好的jar文件
java -jar Dermasign-Project-0.0.1-SNAPSHOT.jar
成功运行之后，访问localhost:8080即可


## Linux上载入数据库(如果在别的开发环境中有另外的数据)
### 从MYSQL中导出数据，存储在JSON文件中
1. 右键要导出的table(products)，选择Tabel Data Export Wizard,导出json文件
2. 将导出的文件转成utf-8编码(否则数据中会包含未转译的德语和中文字符)
<details>
  <summary>OPTIONAL(转译用的python代码)</summary>

```python
import json

with open('products.json', 'r', encoding='utf-8') as file:
json_data = json.load(file)

output_file = 'products_utf8.json'

with open(output_file, 'w', encoding='utf-8') as file:
json.dump(json_data, file, ensure_ascii=False, indent=4)

```
</details>

### 服务器上配置python环境
(其实也可以使用anaconda，这里展示在system-wide配置python的方法)
```bash
sudo apt install python3
sudo apt install python3-pip

# 设置Python的别名
nano ~/.bashrc
alias python='python3'
alias pip='pip3'
source ~/.bashrc

#创建venv的虚拟环境
python -m venv myenv
source myenv/bin/activate
pip install mysql-connector-python

#退出虚拟环境
deactivate
```
### 在服务器端执行代码将数据导入MYSQL

<details>
  <summary>Python代码</summary>

```python
#如果没有更改数据库的结构的前提下，否则按情况修改下列代码

import json
import mysql.connector

# 连接到 MySQL 数据库
conn = mysql.connector.connect(
  host="localhost",
  user="root",  # 替换为您的 MySQL 用户名
  password="Your_PWD",  # 替换为您的 MySQL 密码
  database="dermasign"
)
cursor = conn.cursor()

# 创建 products 表
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

# 打开 JSON 文件并读取数据
with open('products_utf8', 'r', encoding='utf-8') as file:
  data = json.load(file)

  # 遍历 JSON 数据并插入到数据库中
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

# 提交事务
conn.commit()

# 关闭连接
cursor.close()
conn.close()

print("数据插入成功！")
```
</details>



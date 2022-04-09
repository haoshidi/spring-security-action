###spring-security-oauth简单的oauth事例
####访问过程演示说明
#####密码模式访问模式
    curl访问方式:
    curl -v -XPOST -u client:secret "http://localhost:8080/oauth/token?grant_type=password&username=john&password=12345&scope=read"` 
    postman访问方式: 
        http://localhost:8080/oauth/token?grant_type=password&username=john&password=12345&scope=read
        Authorization: Type:Basic Username:client Password:secret
    返回值事例：
        {
        "access_token": "5e1c43b9-2651-46c2-9c0c-37d05ca0b194",
        "token_type": "bearer",
        "refresh_token": "9bc2c6c5-aaf2-4617-80b1-e10274abbca0",
        "expires_in": 43199,
        "scope": "read"
        }
        http://localhost:8080/oauth/check_token?token=5e1c43b9-2651-46c2-9c0c-37d05ca0b194
        Authorization: Type:Basic Username:resourceserver Password:resourceserversecret  
    返回值事例：
        {
            "active": true,
            "exp": 1649521959,
            "user_name": "john",
            "authorities": [
                "read"
            ],
            "client_id": "client",
            "scope": [
                "read"
            ]
        }
    注意：这里的client权限范围只能是grant_type=password，不可用与查看/oauth/check_token方法
#####授权码访问模式
        浏览器访问 http://localhost:8080/oauth/authorize?response_type=code&client_id=client&scope=read
        返回登陆页面：登录名 john 密码 12345
        返回授权页面: 选择Approve 点击授权按钮
        返回：http://localhost:9090/home?code=8ZvIum  这个就是授权码
        PostMan下访问：
            http://localhost:8080/oauth/token?grant_type=authorization_code&scope=read&code=8ZvIum
            Authorization: Type:Basic Username:client1 Password:secret1  
        返回值示例：
            {
                "access_token": "515d0251-7cd1-4b0d-bb66-d4491a58c8ca",
                "token_type": "bearer",
                "expires_in": 43199,
                "scope": "read"
            }
    注意：如果出现OAuth Error错误页面
        错误信息：
        error="invalid_grant", error_description="A redirect_uri can only be used by implicit or authorization_code grant types."
        清空浏览器缓存，F12后输入clear 清空缓存
##资源服务器、授权服务器
###security-oauth-authorization-server/security-oauth-resource-server
***数据库存储 access_token 值，服务器重启后token值不变 
***
####MySQL数据库
    CREATE TABLE IF NOT EXISTS `oauth_access_token` (
    `token_id` varchar(255) NOT NULL,
    `token` blob,
    `authentication_id` varchar(255) DEFAULT NULL,
    `user_name` varchar(255) DEFAULT NULL,
    `client_id` varchar(255) DEFAULT NULL,
    `authentication` blob,
    `refresh_token` varchar(255) DEFAULT NULL,
     PRIMARY KEY (`token_id`));

    CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
    `token_id` varchar(255) NOT NULL,
    `token` blob,
    `authentication` blob,
    PRIMARY KEY (`token_id`));
####访问过程演示说明
#####密码模式访问模式
    postman访问方式:
    http://localhost:8080/oauth/token?grant_type=password&scope=read&password=12345&username=john
    Authorization: Type:Basic Username:client Password:secret
    返回值示例：
        {
            "access_token": "eb51fc92-a197-4e38-b0fa-554d21a749d0",
            "token_type": "bearer",
            "refresh_token": "a8cac5c5-0530-4a44-9119-75ce94fad1fd",
            "expires_in": 38405,
            "scope": "read"
        }
    http://localhost:9090/hello
    Authorization: Type:Bearer token=eb51fc92-a197-4e38-b0fa-554d21a749d0



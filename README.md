# demo
一个日常记录的demo工程，目前已实现基于SM2的加解密逻辑


### 压测
下列的压测命令基于hey命令(https://github.com/rakyll/hey) -c 表示并发数 -n 表示请求总数

- demo
    - 命令
    ```bash
    hey -c 50 -n 1000 -m POST  -T "application/json" -d '{"name": "2023年了, 谁还用传统的编程方式", "age": 23}' http://127.0.0.1:8080/demo
    ```
    - 结果
    ```text
    Summary:
      Total:	0.0591 secs
      Slowest:	0.0103 secs
      Fastest:	0.0004 secs
      Average:	0.0028 secs
      Requests/sec:	16920.5273
    ```
    
- api/sm2demo
    - 命令
    ```bash
    hey -c 50 -n 1000 -m POST  -T "application/json" -d '{"timestamp":"1691344099395","data":"04902B0B202337A307B092A8FE482A9B736802C118B598A5B2DFEAE01C2B4FB34785BBC77553AC165AD3DB8CADEB016CD23691B406CC88F796D90A46054A1A4B1D483487C947CC1CF6C806B1C7F068EB45C2A36705649BE12C51125EFD9DDE051C0F9EDEBEFACE0265A8A02732D74A621478BD1BEDFF89E211A8F5047FAE49CBD8203B745651216C63E4FE6C024EA2058DFA55A2B8F524DDA03FB3F3611364E8","signature":"3045022100a3badb1d1ded8adf50aae7c04eb40f2d83ddd95baf1ae35804708933a191d33702200dd9f6311646b1ca8acd897cf3e2ca59326c8e9ce4e7b0811db7111e8c821eb1","appId":"app-id-demo","nonce":"90d58bbbbc954c7393beefe2ab49f9cc"}' http://127.0.0.1:8080/api/sm2demo
    ```
    - 结果
    ```text
    Summary:
      Total:	3.4387 secs
      Slowest:	0.3386 secs
      Fastest:	0.0162 secs
      Average:	0.1674 secs
      Requests/sec:	290.8067
    ```
    
- client/demo
    - 命令
    ```bash
    hey -c 50 -n 1000 -m POST  -T "application/json" -d '{"name": "2023年了, 谁还用传统的编程方式", "age": 25}' http://127.0.0.1:8080/client/demo
    ```
    - 结果
    ```text
    Summary:
      Total:	0.1219 secs
      Slowest:	0.0105 secs
      Fastest:	0.0021 secs
      Average:	0.0059 secs
      Requests/sec:	8200.9932
    ```
    
- client/api/demo
    - 命令
    ```bash
    hey -c 50 -n 1000 -m POST  -T "application/json" -d '{"name": "2023年了, 谁还用传统的编程方式", "age": 25}'  http://127.0.0.1:8080/client/api/demo
    ```
    - 结果
    ```text
    Summary:
      Total:	8.2896 secs
      Slowest:	0.7240 secs
      Fastest:	0.0350 secs
      Average:	0.4039 secs
      Requests/sec:	120.6325
    ```
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
    hey -c 50 -n 1000 -m POST  -T "application/json" -d '{"timestamp":"1691470743702","data":"042F6A08F7001ACDE7027C87D2B27ECEFAF184B35FC93E08C59395A3A4B990D3A98648C7BB1C8F1DBCCB60D4874E615835A10836C2AFF139CA797646942DCA214B937D041EACAC601D5607DF1B2F01ACB30332E99B752DE51FA6EB9A0D59BAA67BF8F7ACAD3BFD04AE27197D4ABDA8F252B91B4A98189D6CC6C891CEC78D114AA54EFD29551D6209DABDA3CDF4C24F5F9A9B1ACC54D819FE652C8E3E624E5517","signature":"3046022100ded53c096029f30409e53074b88e4d97af49e232db7ff9217e2bde0086546f92022100ca8a31ab629052fd099b4f836af77585e152f145b84fe868e4574c119d9e518d","appId":"c-appId-demo-SM2","nonce":"9026a74e449b4cd1affd75f7a1feb0f2"}' http://127.0.0.1:8080/api/sm2demo
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
  
- client/api/sharedKey/demo
    - 命令
    ```bash
    hey -c 50 -n 1000 -m POST  -T "application/json" -d '{"name": "2023年了, 谁还用传统的编程方式", "age": 25}'  http://127.0.0.1:8080/client/api/sharedKey/demo
    ```
    - 结果
    ```text
    Summary:
      Total:	2.4481 secs
      Slowest:	0.3379 secs
      Fastest:	0.0179 secs
      Average:	0.1130 secs
      Requests/sec:	408.4838
    ```
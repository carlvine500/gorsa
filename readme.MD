# java&golang communicate with rsa
* java publicKey encrypt golang privateKey decrypt
* java privateKey encrypt golang publicKey decrypt
* golang privateKey encrypt java publicKey decrypt
* golang publicKey encrypt java privateKey decrypt

# java golang 之间使用rsa通信
* java 公钥加密 golang 私钥解密
* java 私钥加密 golang 公钥加密
* golang 私钥解密 java 公钥加密
* golang 公钥加密 java 私钥解密

# 生成证书(shell脚本/容器 )
##生成私钥
* openssl genrsa -out rsa_private_key.pem 2048
##生成公钥
* openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
##转换成pkcs8私钥(java/golang通用)
* openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform pem -nocrypt -out rsa_private_pkcs8.pem
##生成证书
* openssl req -new -x509 -key rsa_private_key.pem -out rsa_cert.pem -days 360
##使用私钥加密明文,输出base64串
* openssl rsautl -sign -in plain.txt -inkey private_pkcs8.pem | openssl enc -A -base64
##利用容器快速生成private.key public.key,在当前目录下
* docker container run -it -v `pwd`:/opt  verhas/license3jrepl
* L3j> generateKeys algorithm=RSA size=1024 format=BASE64 public=public.key private=private.key

# java中测试
参考: RsaUtilTest.java
* 公钥加密私钥解密: rsa.publicEncrypt(data) rsa.privateDecrypt(encrypted)
* 私钥加密公钥解密: rsa.privateEncrypt(data) rsa.publicDecrypt(encrypted)
# golang中测试
参考: rsa_ext_test.go
* 公钥加密私钥解密: RSA.PubKeyENCTYPT(data) RSA.PriKeyDECRYPT(encrypted)
* 私钥加密公钥解密: RSA.PriKeyENCTYPT(data)  RSA.PubKeyDECRYPT(encrypted)

package gorsa

import (
	"encoding/base64"
	"fmt"
	"io/ioutil"
	"net"
	"testing"
	"time"
)

var publicKeyPem, _ = ioutil.ReadFile("../javarsa/rsa_public_key.pem")
var privateKeyPem, _ = ioutil.ReadFile("../javarsa/rsa_private_pkcs8.pem")
var fileContent, _ = ioutil.ReadFile("../javarsa/access.log")

func init() {
	if err := RSA.SetPublicKey(string(publicKeyPem)); err != nil {
		fmt.Println(err)
	}

	if err := RSA.SetPrivateKey(string(privateKeyPem)); err != nil {
		fmt.Println(err)
	}
}


func Test_SimplePublicEncryptPrivateDescrypt(t *testing.T) {
	//fmt.Println(string(publicKeyPem))
	fmt.Println("----public key encrypt, private key decrypt----")
	if err := RSA.SetPublicKey(string(publicKeyPem)); err != nil {
		fmt.Println(err)
	}

	if err := RSA.SetPrivateKey(string(privateKeyPem)); err != nil {
		fmt.Println(err)
	}
	pubenctypt, err := RSA.PubKeyENCTYPT([]byte(`hello world`))
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println("public encrypt body:\n", base64.RawURLEncoding.EncodeToString(pubenctypt))

	privateKeyDecrypt, err := RSA.PriKeyDECRYPT(pubenctypt)
	fmt.Println("private decrypt body:\n", string(privateKeyDecrypt))
}


func Test_SimplePrivateEncryptPublicDescrypt(t *testing.T) {
	//fmt.Println(string(publicKeyPem))
	fmt.Println("----private key encrypt, public key decrypt----")

	pubenctypt, err := RSA.PriKeyENCTYPT([]byte(`hello world`))
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println("private encrypt body:\n", base64.RawURLEncoding.EncodeToString(pubenctypt))

	privateKeyDecrypt, err := RSA.PubKeyDECRYPT(pubenctypt)
	fmt.Println("public decrypt body:\n", string(privateKeyDecrypt))
}

// 公钥处理速度0.3seconds/M,私钥处理速度10seconds/M
func Test_SimplePublicEncryptPrivateDescrypt_speed(t *testing.T) {
	fmt.Println("----public key encrypt, private key decrypt  bigdata----")
	start := time.Now();
	pubenctypt, err := RSA.PubKeyENCTYPT(fileContent)
	if err != nil {
		fmt.Println(err)
	}
	//fmt.Println(base64.RawURLEncoding.EncodeToString(pubenctypt))
	fmt.Printf("public encrypt elapse=%v\n", time.Now().Sub(start).Seconds())
	start = time.Now();
	privateKeyDecrypt, err := RSA.PriKeyDECRYPT(pubenctypt)
	fmt.Println(string(privateKeyDecrypt)[0:100])
	fmt.Printf("private decrypt elapse=%v\n", time.Now().Sub(start).Seconds())
}

func Test_SimplePrivateEncryptPublicDescrypt_speed(t *testing.T) {
	fmt.Println("----private key encrypt, public key decrypt  bigdata----")
	start := time.Now();
	pubenctypt, err := RSA.PriKeyENCTYPT(fileContent)
	if err != nil {
		fmt.Println(err)
	}
	//fmt.Println(base64.RawURLEncoding.EncodeToString(pubenctypt))
	fmt.Printf("private encrypt elapse=%v\n", time.Now().Sub(start).Seconds())
	start = time.Now();
	privateKeyDecrypt, err := RSA.PubKeyDECRYPT(pubenctypt)
	fmt.Println(string(privateKeyDecrypt)[0:100])
	fmt.Printf("public decrypt elapse=%v\n", time.Now().Sub(start).Seconds())
}

func Test_mac_addr(t *testing.T){

	as, err := getMacAddr()
	if err != nil {
		//log.Fatal(err)
	}
	for _, a := range as {
		fmt.Println(a)
	}
}
func getMacAddr() ([]string, error) {
	ifas, err := net.Interfaces()
	if err != nil {
		return nil, err
	}
	var as []string
	for _, ifa := range ifas {
		a := ifa.HardwareAddr.String()
		if a != "" {
			as = append(as, a)
		}
	}
	return as, nil
}


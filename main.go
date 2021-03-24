package main
import "C"
import (
	"fmt"
	"unsafe"
)
//export GetValueByName
func GetValueByName(name *C.char,size C.int) *C.char {
	data := C.GoBytes( unsafe.Pointer(name), size)
	dataStr :=  string(data)
	fmt.Println("Hello3," + dataStr)
	return C.CString(dataStr)
}

func main() {

}


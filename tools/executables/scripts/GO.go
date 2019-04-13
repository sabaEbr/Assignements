package main

import (
		"fmt"
		"os/exec"
		"os"
)
	
	

func main(){  
	cmd := exec.Command("jre/bin/java.exe", "-jar" , "launcher.jar")
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	fmt.Printf("Starting Game...\n")
	err := cmd.Run()
	if err != nil {
		fmt.Printf("cmd.Run() failed with %s\n", err)
	}
	fmt.Printf("Game finished with error: %v", err) 
}
.DEFAULT_GOAL := build

fmt:
	go fmt ./..

lint: fmt
	golint ./..

vet: fmt
	go vet ./..

build: vet
	go build hello.go

.PHONY: gmt lint vet 

#!/bin/bash
echo Criando o bucket da aws S3

aws --endpoint-url=http://localhost:4566 s3 mb s3://algafood-dev

echo Listando os buckets da aws S3

aws --endpoint-url=http://localhost:4566 s3api list-buckets
aws --endpoint-url=http://localhost:4566 s3 ls s3://algafood-dev/product-photos/

echo Recursos locais da AWS criados com sucesso!!!
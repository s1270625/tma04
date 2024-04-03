REM Remember to replace you student id in variables.tf

terraform init
terraform plan -out main.tfplan
terraform apply main.tfplan

REM terraform plan -destroy -out main.destroy.tfplan
REM terraform apply main.destroy.tfplan
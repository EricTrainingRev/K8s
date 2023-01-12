provider "aws" {
  region     = "us-east-1"
  access_key = var.access_key
  secret_key = var.secret_key
}

module "ec2-instance" {
  source  = "terraform-aws-modules/ec2-instance/aws"
  version = "4.3.0"

  name = "Terraform-Instance"

  ami                    = "ami-0b5eea76982371e91" # ami we used for ec2 demo
  instance_type          = "t2.micro"
  key_name               = "some-key"
  vpc_security_group_ids = ["security-group-id"]

  tags = {
    Terraform   = "true"
    Environment = "dev"
  }
}
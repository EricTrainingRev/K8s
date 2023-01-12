terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }

  backend "s3" {
    bucket         = "revature-example-state-bucket"
    key            = "state/terraform.tfstate"
    region         = "us-east-1"
    profile        = "demo-user" # can be set in .aws/config document
    dynamodb_table = "demo-terraform-locks"
    encrypt        = true
  }
}
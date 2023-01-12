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

# this bucket will be used to store terraform state
resource "aws_s3_bucket" "terraform_state" {
  bucket = "revature-example-state-bucket"
  # Prevent accidental deletion of this S3 bucket
  lifecycle {
    prevent_destroy = true
  }
  # Enable versioning so we can see the full revision history of our
  # state files
  versioning {
    enabled = true
  }
  # Enable server-side encryption by default
  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }
}

# this dynamodb instance helps manage users interacting with the terraform state at the same time
resource "aws_dynamodb_table" "terraform_locks" {
  name         = "demo-terraform-locks"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"
  attribute {
    name = "LockID"
    type = "S"
  }
}
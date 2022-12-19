# What is Terraform?
Terraform is an Infastructure as Code (IaC) solution that lets us configure our deployment environments in a programatic way by creating Terraform configurations (modules) which Terraform will use to set up our environments and deploy our applications. We will be using it with AWS EKS and Docker.

# Terraform configuration
A terraform configuration file can be created to tell terraform about the infratructure we desire. There are three main blocks in the main terraform file: terraform, providers, and resources (NOTE: example was made while practicing using gcp)
```terraform
terraform {
  required_providers {
    google = {
      source = "hashicorp/google"
      version = "3.5.0"
    }
  }
}

provider "google" {
  credentials = file("<NAME>.json")

  project = "<PROJECT_ID>"
  region  = "us-central1"
  zone    = "us-central1-c"
}

resource "google_compute_network" "vpc_network" {
  name = "terraform-network"
}
```

## Terraform Block
This block should contain any terraform specific information, and it is where you will provide the `required_providers` block. In the example above, the module is informing terraform that the provider should be gcp, and the necesary information for interacting with the provider can be found in the hashicorp/google [Terraform Registry](https://registry.terraform.io/). It is also good practice to lock down what version you are using, since always defaulting to "latest" can lead to broken deployments (remember Jenkins values...)

## Provider Block
The provider block is used to configure the required providers set in the terraform block. Here is where you can provide information such as credentials and other cloud resource configuration information

## Resource Block
The resource block is used to define individual components of your deployment: this can be things like vpcs, servers, 3rd party resources, etc.

## .terraform.lock.hcl
Upon running the terraform init command successfully a file called `.terraform.lock.hcl` is created: this file contains the provider versions used in the init command. This helps ensure terraform uses the same providers in subsequent commands to ensure consistency.

## terraform.tfstate
Upon running the terraform apply command successfully a file called `terraform.tfstate` is created: this file is read whenever you use the terraform show command and provides you with information about the state of the resources terraform is managing for you

## in-place vs destructive change
a `~` before a property in the preview of an apply command indicates the property will be updated in-place: the resource being updated will be affected without needing to be rebuilt/destroyed.

Sometimes terraform will need to destroy a resource and replace it instead of updating in place: this typically happens when there is no way for the cloud provider to upgrade the resource (such as changing a disk image). a `-` means the resource in the preview is being removed, a `+` means the resource is being added.

## Terraform Module
A module is a collection of terraform configurations that work together. Any time you run a terraform command in a directory with one or more terraform files that directory is considered the root module. However, putting abosultely every configuration you need in the root directory can create unneccessary bloat. Configurations are able to reference content within other modules, so you can organize your configurations into modules. Modules referenced by the root module are called child modules. Child modules can be local files, or they can be remote. Some (but not all) options for storing modules remotely are:
- Terraform Registry
- Most Version Control Systems
- HTTP URLs
- Terraform Cloud / Terraform Enterprise

When using modules (and you really should think of writting your configurations in module form) there are some best practices to follow ([via Hashicorp docs](https://developer.hashicorp.com/terraform/tutorials/modules/module))
- use the following naming convention: `terraform-[provider]-[name]`
- write your configurations with modules in mind
- use local modules to organize and encapsulate your configurations
- use the Terrafrom Registry to find common implementations so you don't need to recreate the wheel
- publish modules you need shared among teammembers
  - remember not to put anything sensitive in publicly accessible modules



# TERRAFORM CLI COMMANDS
```bash
terraform fmt # tells terraform to look fo .tf files in the cwd and reformat them for a consistent look

terraform verify # tells terraform to validate .tf file syntax and consistency in the cwd

terraform show # desplays the state of the resources managed by terraform: usually contains sensitive information

terraform init # this tells terraform to download the providers set in the .tf file in the CWD

terraform plan # will print out the execution plan for the module in the CWD

terraform apply # terraform will show an execution plan and ask confirmation to execute commands to create your given infrastructure desires

terraform destroy # terraform terminates all resources assocaited with the current project


```
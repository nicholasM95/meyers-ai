terraform {
  required_providers {
    helm = {
      source  = "hashicorp/helm"
      version = "3.1.1"
    }

    aws = {
      source  = "hashicorp/aws"
      version = "6.30.0"
    }
  }

  backend "s3" {
    bucket = "nicholasmeyers-meyers-ai-prd-terraform-state"
    key    = "terraform.tfstate"
    region = "eu-west-1"
  }
}

provider "helm" {
  kubernetes = {
    config_path = "~/.kube/config"
  }
}

provider "aws" {
  region = var.aws_region

  default_tags {
    tags = {
      "app" = "meyers_ai"
    }
  }
}

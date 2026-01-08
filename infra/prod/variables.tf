variable "docker_config" {
  description = "Docker config to pull an image"
  type        = string
}

variable "aws_region" {
  description = "aws region"
  type        = string
  default     = "eu-west-1"
}

variable "namespace" {
  description = "Kubernetes namespace"
  type        = string
  default     = "meyers-ai"
}

variable "name" {
  description = "Application name"
  type        = string
  default     = "meyers-ai"
}

variable "image_tag" {
  description = "Image tag"
  type        = string
}

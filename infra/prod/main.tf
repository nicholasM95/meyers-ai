module "trust_profile" {
  source    = "../modules/trust-profile"
  name      = var.name
  namespace = var.namespace
}

module "application" {
  source            = "git::https://github.com/nicholasM95/terraform-modules.git//modules/k8s-helm-release?ref=v1.10.10"
  image_tag         = var.image_tag
  application_name  = var.name
  namespace_name    = var.namespace
  helm_path         = "../../application"
  docker_config     = var.docker_config
  aws_trust_profile = module.trust_profile.aws_trust_profile
  aws_trust_anchor  = module.trust_profile.aws_trust_anchor
  aws_role          = module.trust_profile.aws_role
}
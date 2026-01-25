module "trust_profile" {
  source = "../modules/trust-profile"
}

module "application" {
  source           = "git::https://github.com/nicholasM95/terraform-modules.git//modules/k8s-helm-release?ref=v1.12.74"
  image_tag        = var.image_tag
  application_name = var.name
  namespace_name   = var.namespace
  helm_path        = "../../application"
  docker_config    = var.docker_config
}

module "static_website" {
  source                  = "git::https://github.com/nicholasM95/terraform-modules.git//modules/static-website?ref=v1.12.74"
  domain_name             = "nicholasmeyers.be"
  sub_domain_name         = "ai"
  project_name            = "ai-nicholasmeyers-be"
  website_host            = "ai.nicholasmeyers.be"
  website_path            = "../../frontend-dist"
  content_security_policy = var.content_security_policy
  permission_policy       = var.permission_policy
  web_acl_id              = "arn:aws:wafv2:us-east-1:896918338968:global/webacl/waf-cloudfront/6fea776f-ac4b-4be3-b959-df5acdfe8e35"
}

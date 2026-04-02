module "trust_profile" {
  source = "../modules/trust-profile"
}

module "vault" {
  source     = "git::https://github.com/nicholasM95/terraform-modules.git//modules/vault?ref=v1.15.19"
  vault_path = var.name
}

module "vault_connection" {
  depends_on                = [module.vault]
  source                    = "git::https://github.com/nicholasM95/terraform-modules.git//modules/vault-k8s?ref=v1.15.19"
  vault_path                = var.name
  service_account_name      = var.name
  service_account_namespace = var.namespace
}

module "application" {
  source           = "git::https://github.com/nicholasM95/terraform-modules.git//modules/k8s-helm-release?ref=v1.15.19"
  image_tag        = var.image_tag
  application_name = var.name
  namespace_name   = var.namespace
  helm_path        = "../../application"
  docker_config    = var.docker_config
}

module "static_website" {
  source                  = "git::https://github.com/nicholasM95/terraform-modules.git//modules/static-website?ref=v1.15.19"
  domain_name             = "nicholasmeyers.be"
  sub_domain_name         = "ai"
  project_name            = "ai-nicholasmeyers-be"
  website_host            = "ai.nicholasmeyers.be"
  website_path            = "../../frontend-dist/dist"
  content_security_policy = var.content_security_policy
  permission_policy       = var.permission_policy
  web_acl_id              = "arn:aws:wafv2:us-east-1:896918338968:global/webacl/waf-cloudfront/6fea776f-ac4b-4be3-b959-df5acdfe8e35"
}

module "keycloak_resource_server_meyers-ai-api" {
  source                          = "git::https://github.com/nicholasM95/terraform-modules.git//modules/keycloak-client-resource-server?ref=v1.15.19"
  client_id                       = "meyers-ai-api"
  client_name                     = "Meyers AI API"
  realm_id                        = "meyers-prive"
  service_accounts_enabled        = true
  standard_token_exchange_enabled = true
}

module "keycloak_client" {
  source      = "git::https://github.com/nicholasM95/terraform-modules.git//modules/keycloak-client?ref=v1.15.19"
  client_id   = "meyers-ai-frontend"
  client_name = "Meyers AI"
  realm_id    = "meyers-prive"
  valid_redirect_uris = [
    "http://localhost:5173",
    "https://ai.nicholasmeyers.be"
  ]
  audiences = [
    "meyers-ai-api"
  ]
}
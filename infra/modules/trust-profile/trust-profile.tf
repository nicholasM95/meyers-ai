resource "aws_iam_role" "trust_profile_role" {
  name               = "${var.namespace}-${var.name}"
  path               = "/"
  assume_role_policy = data.aws_iam_policy_document.rolesanywhere_profile_role_policy_document.json

  inline_policy {
    name   = "${var.namespace}-${var.name}-session"
    policy = data.aws_iam_policy_document.rolesanywhere_profile_role_session_policy_document.json
  }
}

resource "aws_rolesanywhere_profile" "trust_profile" {
  name      = "${var.namespace}-${var.name}-profile"
  enabled   = true
  role_arns = [aws_iam_role.trust_profile_role.arn]
}

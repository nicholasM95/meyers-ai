output "aws_trust_profile" {
  value = aws_rolesanywhere_profile.trust_profile.arn
}

output "aws_trust_anchor" {
  value = data.aws_ssm_parameter.trust_anchor.value
}

output "aws_role" {
  value = aws_iam_role.trust_profile_role.arn
}
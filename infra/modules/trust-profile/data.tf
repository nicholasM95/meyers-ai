data "aws_ssm_parameter" "trust_anchor" {
  name = "/nicholasmeyers/cluster/trust/anchor"
}

data "aws_iam_policy_document" "rolesanywhere_profile_role_policy_document" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["rolesanywhere.amazonaws.com"]
    }

    actions = [
      "sts:AssumeRole",
      "sts:TagSession",
      "sts:SetSourceIdentity"
    ]

    condition {
      test     = "StringLike"
      variable = "aws:PrincipalTag/x509SAN/URI"
      values = [
        "spiffe://nicholas.home.cluster/*/sa/*"
      ]
    }

    condition {
      test     = "ArnEquals"
      variable = "aws:SourceArn"
      values = [
        data.aws_ssm_parameter.trust_anchor.value
      ]
    }
  }
}

data "aws_iam_policy_document" "rolesanywhere_profile_role_session_policy_document" {
  statement {
    effect = "Allow"

    actions = [
      "bedrock:InvokeModel",
      "bedrock:InvokeModelWithResponseStream"
    ]

    resources = [
      "arn:aws:bedrock:*::foundation-model/amazon.titan-text-lite-v1"
    ]

    condition {
      test     = "StringLike"
      variable = "aws:PrincipalTag/x509SAN/URI"
      values = [
        "spiffe://nicholas.home.cluster/ns/${var.namespace}/sa/${var.name}"
      ]
    }
  }
}

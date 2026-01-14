resource "aws_iam_managed_policy" "app_policy" {
  name = "MeyersAIPolicy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "bedrock:InvokeModel",
          "bedrock:InvokeModelWithResponseStream"
        ]
        Resource = "arn:aws:bedrock:*::foundation-model/amazon.titan-text-lite-v1"
      }
    ]
  })
}

resource "aws_iam_role" "app_iam_role" {
  name = "MeyersAIPolicy"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Federated = "arn:aws:iam::896918338968:oidc-provider/oidc-discovery.nicholasmeyers.be"
        }
        Action = "sts:AssumeRoleWithWebIdentity"
        Condition = {
          "ForAnyValue:StringEquals" = {
          "oidc-discovery.nicholasmeyers.be:aud" = "sts.amazonaws.com"
          }
          StringEquals = {
            "oidc-discovery.nicholasmeyers.be:sub" = "spiffe://nicholasmeyers.be/ns/meyers-ai/sa/meyers-ai"
          }
        }
      }
    ]
  })
}


resource "aws_iam_role_policy_attachment" "attach_policy" {
  role       = aws_iam_role.app_iam_role.name
  policy_arn = aws_iam_managed_policy.app_policy.arn
}
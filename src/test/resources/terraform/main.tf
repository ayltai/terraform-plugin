terraform {
  required_version = ">=0.12"
}

data "template_file" "example" {
  template = var.example
}

resource "local_file" "example" {
  filename = "example.txt"
  content  = data.template_file.example.rendered
}

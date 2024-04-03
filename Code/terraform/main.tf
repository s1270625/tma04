resource "azurerm_resource_group" "comps368tma04" {
  name     = "comps368-tma04"
  location = var.resource_group_location
}

resource "azurerm_service_plan" "app-plan" {
  name                = "${var.student_id}web"
  resource_group_name = azurerm_resource_group.comps368tma04.name
  location            = azurerm_resource_group.comps368tma04.location
  os_type             = "Windows"
  sku_name            = "B1"
}

resource "azurerm_windows_web_app" "apps" {
  name                = "${var.student_id}webportal"
  resource_group_name = azurerm_resource_group.comps368tma04.name
  location            = azurerm_service_plan.app-plan.location
  service_plan_id     = azurerm_service_plan.app-plan.id

  site_config {
    application_stack {
      current_stack = "java"
      java_version = 17
      java_embedded_server_enabled = true
    }
    local_mysql_enabled = true
  }

  app_settings = {
    "USR" = "azure"
    "PWD" = "6#vWHD_$"
    "URL" = "jdbc:mysql://127.0.0.1:3306/cafedemetro"
  }
    
}
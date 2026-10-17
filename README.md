# Supplier Service

API propietaria del esquema `supplier`, puerto `8083`. Gestiona proveedores, catálogo, órdenes de compra y recepciones; los productos se validan por REST contra Inventory.

Las órdenes transicionan por endpoints `enviar`, `aprobar` y `cancelar`; el cliente no puede modificar estados ni historial directamente. Una recepción posteada bloquea orden/líneas, valida cantidades, publica entradas idempotentes en Inventory y actualiza `received_quantity` y el estado de la orden. Repostear la misma recepción no vuelve a impactar stock.

Variables adicionales: `INVENTORY_SERVICE_URL`, timeouts y autenticación interservicio.

```bash
export SPRING_PROFILES_ACTIVE=local DB_URL=jdbc:postgresql://localhost:5432/nexastock DB_USER=postgres DB_PASSWORD='<local>'
./mvnw clean test
```

OpenAPI: `/v3/api-docs`; health: `/actuator/health`.

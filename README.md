# Supplier Service

Microservicio de proveedores y abastecimiento. Es propietario del esquema PostgreSQL `supplier`.

## Responsabilidades

- Proveedores y productos ofrecidos.
- Órdenes de compra y detalles.
- Historial de estados.
- Recepciones de mercadería.
- Validación de productos y almacenes mediante Inventory Service.

## Configuración

| Variable | Obligatoria | Valor local predeterminado |
|---|---:|---|
| `DB_URL` | No | `jdbc:postgresql://localhost:5432/nexasupplyai` |
| `DB_USER` | No | `postgres` |
| `DB_PASSWORD` | No | `postgres` |
| `JWT_ISSUER_URI` | Producción | `http://localhost:9999` |
| `JWT_JWK_SET_URI` | Producción | `http://localhost:9999/.well-known/jwks.json` |
| `APP_SECURITY_ENABLED` | No | `true` |
| `CORS_ALLOWED_ORIGINS` | No | `http://localhost:4200` |
| `INVENTORY_SERVICE_URL` | No | `http://localhost:8081` |

La base se inicializa con [`../nexasupplyai.sql`](../nexasupplyai.sql). El token entrante y `X-Correlation-ID` se propagan a Inventory Service.

## Ejecución

```bash
export JWT_ISSUER_URI="https://login.microsoftonline.com/<tenant-id>/v2.0"
./mvnw spring-boot:run
```

Puerto: `8083`. Salud pública: `GET /actuator/health`. El resto de la API exige JWT.

### Docker

```bash
docker build -t nexasupply/supplier-service:local .
docker run --rm --add-host=host.docker.internal:host-gateway \
  -p 8083:8083 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/nexasupplyai \
  -e DB_USER=postgres \
  -e DB_PASSWORD=postgres \
  -e JWT_ISSUER_URI=https://login.microsoftonline.com/<tenant-id>/v2.0 \
  -e INVENTORY_SERVICE_URL=http://host.docker.internal:8081 \
  nexasupply/supplier-service:local
```

## API

| Recurso | Colección | Elemento |
|---|---|---|
| Proveedores | `GET/POST /api/v1/proveedores` | `GET/PUT/DELETE /api/v1/proveedores/{id}` |
| Productos de proveedor | `GET/POST /api/v1/productos-proveedor?idProveedor={uuid}` | `GET/PUT/DELETE /api/v1/productos-proveedor/{id}` |
| Órdenes de compra | `GET/POST /api/v1/ordenes-compra?idProveedor={uuid}` | `GET/PUT/DELETE /api/v1/ordenes-compra/{id}` |
| Detalles de orden | `GET/POST /api/v1/detalles-orden-compra?idOrden={uuid}` | `GET/PUT/DELETE /api/v1/detalles-orden-compra/{id}` |
| Historial | `GET/POST /api/v1/historial-ordenes-compra?idOrden={uuid}` | `GET /api/v1/historial-ordenes-compra/{id}` |
| Recepciones | `GET/POST /api/v1/recepciones-mercaderia?idOrden={uuid}` | `GET/PUT/DELETE /api/v1/recepciones-mercaderia/{id}` |
| Detalles de recepción | `GET/POST /api/v1/detalles-recepcion-mercaderia?idRecepcion={uuid}` | `GET/DELETE /api/v1/detalles-recepcion-mercaderia/{id}` |

Estados de proveedor: `ACTIVE`, `INACTIVE`, `BLOCKED`. Estados de orden: `DRAFT`, `SUBMITTED`, `APPROVED`, `PARTIALLY_RECEIVED`, `RECEIVED`, `CANCELLED`. Estados de recepción: `DRAFT`, `POSTED`, `CANCELLED`.

## Pruebas

```bash
./mvnw clean test
./mvnw clean package
```

## Publicación de recepciones

`POST /api/v1/recepciones-mercaderia/{id}/postear` registra cada línea en Inventory Service con la clave idempotente `receipt:{recepcionId}:{detalleId}`, actualiza las cantidades acumuladas y cambia la orden a `PARTIALLY_RECEIVED` o `RECEIVED`. Si una dependencia falla, se puede reintentar sin duplicar entradas ya procesadas.

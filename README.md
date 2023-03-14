# McKingOfTheHill

Minecraft Paper Server Plugin

Gamemode King of the hill

## Permissions

### Commands

- setup - kingofthehill.setup

## Database table structure

Location

| **Name** | **World** | **X** | **Y** | **Z** | **Yaw** | **Pitch** |
|----------|-----------|-------|-------|-------|---------|-----------|
| Spawn    | world     | 100   | 100   | 100   | 180     | 0         |

## Local database setup

Use scripts to run local databases in docker

### MariaDb / MySql

```bash
# Pull MariaDb container
docker pull mariadb
# Run MariaDb locally 
docker run \
	--detach \
	--name local-mariadb \
	-e MARIADB_USER=mylocaluser \
	-e MARIADB_PASSWORD=mylocalpassword \
	-e MARIADB_ROOT_PASSWORD=mylocalrootpassword \
	-e MARIADB_DATABASE=mckingofthehill \
	-p 3306:3306 \
	-d mariadb:latest
```

### Postgres

```bash
# Pull Postgres container
docker pull postgres
# Run Postgres locally 
docker run \
    --detach \
    --name local-postgres \
    -e POSTGRES_USER=mylocaluser \
    -e POSTGRES_PASSWORD=mylocalpassword \
    -e POSTGRES_DB=mckingofthehill \
    -p 5432:5432 \
    -d postgres
```

